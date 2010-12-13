package fi.jamk.e6379;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataHelper {

   private static final String DATABASE_NAME = "iiolulz.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME_LOGS = "log";
   private static final String TABLE_NAME_NOTES = "note";
   
   private Context context;
   private SQLiteDatabase db;
   private OpenHelper openHelper;

   public DataHelper(Context context) {
      this.context = context;
      openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
   }
   
   public void closeDb() {
	   openHelper.close();
   }
   
   public long insertLog(fi.jamk.e6379.Log log) {	   
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ContentValues initialValues = new ContentValues();
	   initialValues.put("noteText", log.getNoteText());
	   initialValues.put("title", log.getTitle());
	   initialValues.put("date", dateFormat.format(log.getDate().getTime()));
	   initialValues.put("type", log.getType());
	   initialValues.put("cacheId", log.getCacheId());
	   
	   return db.insert(TABLE_NAME_LOGS, null, initialValues);
   }
   
   public long insertNote(Note note) {	 
	   
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ContentValues initialValues = new ContentValues();
	   
	   initialValues.put("noteText", note.getNoteText());
	   initialValues.put("title", note.getTitle());
	   initialValues.put("date", dateFormat.format(note.getDate().getTime()));
	   initialValues.put("cacheId", note.getCacheId());
	   
	   return db.insert(TABLE_NAME_NOTES, null, initialValues);
   }

   public void deleteAll() {
      this.db.delete(TABLE_NAME_LOGS, null, null);
      this.db.delete(TABLE_NAME_NOTES, null, null);
   }

   public List<Note> selectNotes(String cacheId) {
	      List<Note> list = new ArrayList<Note>();
	      Cursor cursor = this.db.query(TABLE_NAME_NOTES, new String[] { "title","noteText","date","cacheId" }, 
	    		  "cacheId like '"+cacheId+"'", null, null, null, "date desc");
	      if (cursor.moveToFirst()) {
	         do {
	        	 Note note = new Note();
	        	 
	            note.setTitle(cursor.getString(0));
	            note.setNoteText(cursor.getString(1));
	            
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Calendar cal = Calendar.getInstance();
	            try {
					cal.setTime(dateFormat.parse(cursor.getString(2)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            note.setDate(cal);
	            note.setCacheId(cursor.getString(3));
	            
	            list.add(note);
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	}

	public List<fi.jamk.e6379.Log> selectLogs(String cacheId) {
	      List<fi.jamk.e6379.Log> list = new ArrayList<fi.jamk.e6379.Log>();
	      Cursor cursor = this.db.query(TABLE_NAME_LOGS, new String[] { "type","title","noteText","date","cacheId" },
	    		  "cacheId like '"+cacheId+"'", null, null, null, "date desc");
	      if (cursor.moveToFirst()) {
	         do {
	        	 fi.jamk.e6379.Log log = new fi.jamk.e6379.Log();
	        	 
	            log.setType(Integer.parseInt(cursor.getString(0)));
	            log.setTitle(cursor.getString(1));
	            log.setNoteText(cursor.getString(2));
	            
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            Calendar cal = Calendar.getInstance();
	            try {
					cal.setTime(dateFormat.parse(cursor.getString(3)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            log.setDate(cal);
	            log.setCacheId(cursor.getString(4));
	            
	            list.add(log);
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	}
   
	public List<String> selectFoundCacheIds() {
	      List<String> list = new ArrayList<String>();
	      Cursor cursor = this.db.query(TABLE_NAME_LOGS, new String[] { "type","cacheId" }, null, null, null, null, "date desc");
	      if (cursor.moveToFirst()) {
	    	  do {
				 if(Integer.parseInt(cursor.getString(0)) == fi.jamk.e6379.Log.TYPE_FOUND)
					 list.add(cursor.getString(1));

	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	}
	
	private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME_LOGS + "(id INTEGER PRIMARY KEY, type TEXT, title TEXT, noteText TEXT, date DATETIME, cacheId TEXT)");
         db.execSQL("CREATE TABLE " + TABLE_NAME_NOTES + "(id INTEGER PRIMARY KEY, title TEXT, noteText TEXT, date DATETIME, cacheId TEXT)");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOGS);
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTES);
         onCreate(db);
      }
   }
}