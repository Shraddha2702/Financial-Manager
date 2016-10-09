package info.stakes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SHRADDHA on 25-09-2016.
 */
public class DatabaseHelperSchedule extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScheduleDatabase.db";

    //Table Name
    public static final String TABLE_SCHEDULE = "ScheduleTable";

    //Column Names
    public static final String SCHEDULE_FIELD = "Field";
    public static final String SCHEDULE_PERCENT = "Percent";

    public static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULE + "(" +
            SCHEDULE_FIELD + " TEXT PRIMARY KEY, " + SCHEDULE_PERCENT + " INTEGER )";


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

    public boolean insertschedule(String field, int percent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(SCHEDULE_FIELD, field);
        v.put(SCHEDULE_PERCENT, percent);

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

}
