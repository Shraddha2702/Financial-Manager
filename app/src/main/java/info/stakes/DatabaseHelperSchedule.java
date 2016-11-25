package info.stakes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by SHRADDHA on 25-09-2016.
 */
public class DatabaseHelperSchedule extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "ScheduleDatabase.db";

    //Table Name
    public static final String TABLE_SCHEDULE = "ScheduleTable";

    //Column Names
    public static final String SCHEDULE_FIELD = "Field";
    public static final String SCHEDULE_PERCENT = "Percent";
    public static final String SCHEDULE_UPDATE = "Updated";

    public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULE + "(" +
            SCHEDULE_FIELD + " TEXT PRIMARY KEY, " + SCHEDULE_PERCENT + " INTEGER, "+SCHEDULE_UPDATE+ " INTEGER )";


    public DatabaseHelperSchedule(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }

    public boolean insertschedule(String field, int percent, int update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(SCHEDULE_FIELD, field);
        v.put(SCHEDULE_PERCENT, percent);
        v.put(SCHEDULE_UPDATE, update);

        Cursor c = getAllSch();
        c.moveToFirst();
        int flag = 0;

        while (!c.isAfterLast()) {
            String fieldmain = c.getString(0);

            if (fieldmain.equals(field)) {
                flag = 1;
                break;
            }
            c.moveToNext();
        }

        if (flag == 1) {
            return false;
        }
        else {
            long insert = db.insert(TABLE_SCHEDULE, null, v);

            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Cursor getAllSch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_SCHEDULE, null);
        return res;
    }

    public boolean UpdateSchedule(String field, int Amount, int update) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(SCHEDULE_FIELD, field);
        v.put(SCHEDULE_PERCENT, Amount);
        v.put(SCHEDULE_UPDATE, update);

        Cursor c = getAllSch();
        int flag = 0;

        c.moveToFirst();

        while (!c.isAfterLast()) {
            String field1 = c.getString(0);

            if (field1.equals(field)) {
                flag = 1;
                break;
            }
            c.moveToNext();
        }

        Log.d("Flag", " " + flag);

        if (flag == 1) {
            db.update(TABLE_SCHEDULE, v, SCHEDULE_FIELD + " = ?", new String[]{field});
            return true;
        } else {

            return false;
        }
    }

    public boolean checkDatabase()
    {
        SQLiteDatabase checkdb = null;
        String path = "/data/data/info.stakes/databases/"+DATABASE_NAME+"/";
        try {
            checkdb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            checkdb.close();
        }catch (SQLiteException e)
        {
            return false;
        }
        return true;
    }

    public boolean deleteALl()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteString = "DELETE FROM "+TABLE_SCHEDULE;
        Cursor check = db.rawQuery(deleteString,null);
        if(check != null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}
