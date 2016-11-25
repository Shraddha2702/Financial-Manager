package info.stakes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SHRADDHA on 17-09-2016.
 */
public class DatabaseHelperDaily extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "DatabaseManagerDaily.db";

    //Table Name
    public static final String TABLE_DAILY = "DailyTable";


    //column names
    //public static final String GOAL_ID = "Id";
    public static final String DAILY_FIELD = "Field";
    public static final String DAILY_AMOUNT = "Amount";
    public static final String DAILY_CHECKED = "Checked";


    //Create Query for Goal
    public static final String CREATE_DAILY_TABLE = "CREATE TABLE IF NOT EXISTS" + " " + TABLE_DAILY +
            "(" + DAILY_FIELD + " TEXT PRIMARY KEY, " + DAILY_AMOUNT + " INTEGER, " + DAILY_CHECKED + " INTEGER )";

    public DatabaseHelperDaily(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DAILY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY);
        onCreate(db);
    }

    public boolean insertdaily(String field, int Amount, int check) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(DAILY_FIELD, field);
        v.put(DAILY_AMOUNT, Amount);
        v.put(DAILY_CHECKED, check);
        long result = db.insert(TABLE_DAILY, null, v);
        Log.d("Resultinsert", " " + result);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDaily() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DAILY, null);

        Log.d("GetAllGoals", " " + res);
        return res;
    }

    public boolean UpdateDaily(String field, int Amount, int check) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(DAILY_FIELD, field);
        v.put(DAILY_AMOUNT, Amount);
        v.put(DAILY_CHECKED, check);

        Cursor c = getAllDaily();
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
            db.update(TABLE_DAILY, v, DAILY_FIELD + " = ?", new String[]{field});
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


}

