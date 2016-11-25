package info.stakes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Long2;
import android.util.Log;

/**
 * Created by SHRADDHA on 17-09-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 20;
    public static final String DATABASE_NAME = "DatabaseManager.db";

    //Table Name
    public static final String TABLE_GOAL = "GoalTable";


    //column names
    //public static final String GOAL_ID = "Id";
    public static final String GOAL_MAIN = "Goal";
    public static final String GOAL_AMOUNT = "Amount";


    //Create Query for Goal
    public static final String CREATE_GOAL_TABLE = "CREATE TABLE IF NOT EXISTS" + " " + TABLE_GOAL +
            "(" + GOAL_MAIN + " TEXT," + GOAL_AMOUNT + " INTEGER )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DatabaseConstructor", " " + DATABASE_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GOAL_TABLE);
        Log.d("InsideCreate", CREATE_GOAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        onCreate(db);
    }

    public boolean insertgoal(String Goal, int Amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_GOAL);
        ContentValues v = new ContentValues();
        v.put(GOAL_MAIN, Goal);
        v.put(GOAL_AMOUNT, Amount);
        long result = db.insert(TABLE_GOAL, null, v);
        Log.d("Resultinsert"," "+result);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllGoals() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_GOAL, null);

        Log.d("GetAllGoals"," "+res);
        return res;
    }

    public boolean UpdateGoal(String Goal, int Amount) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(GOAL_MAIN, Goal);
        v.put(GOAL_AMOUNT, Amount);

        boolean check = checkifexists(Goal);
        Log.d("checkifexist"," "+check);

        if (check == true) {
            Cursor c= getAllGoals();
            int flag = 0;

            c.moveToFirst();

            while (!c.isAfterLast())
            {
                String goal=c.getString(0);

                if(goal.equals(Goal))
                {
                    flag = 1;
                    break;
                }
                c.moveToNext();
            }

            Log.d("Flag"," "+flag);

            if(flag == 1) {
                db.update(TABLE_GOAL, v, GOAL_MAIN + " = ?", new String[]{Goal});
            }

            else
            {
                db.update(TABLE_GOAL, v, GOAL_MAIN + " != ?", new String[]{Goal});
            }

            return true;

        } else {

            return false;
        }
    }

    public boolean checkifexists(String Goal) {
        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor mCursor = db1.rawQuery("SELECT * FROM " + TABLE_GOAL +
                " WHERE " + GOAL_MAIN + "=?", new String[]{Goal});

        if (mCursor != null) {
            return true;
            /* record exist */
        } else {
            return false;
            /* record not exist */
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
