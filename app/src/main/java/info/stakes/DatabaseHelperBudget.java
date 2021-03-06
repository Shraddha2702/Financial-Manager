package info.stakes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SHRADDHA on 03-10-2016.
 */
public class DatabaseHelperBudget extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "Budget.db";

    public static final String TABLE_BUDGET = "BudgetTable";

    public static final String BUDGET = "Budget";
    public static final String DATE = "date";

    public static final String CREATE_BUDGET_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_BUDGET+"("+BUDGET+" INTEGER, "+DATE+" INTEGER )";

    public DatabaseHelperBudget(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUDGET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BUDGET);
        onCreate(db);
    }

    public boolean insertBudget(int budget, int date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_BUDGET);

        ContentValues v = new ContentValues();
        v.put(BUDGET,budget);
        v.put(DATE, date);

        long result = db.insert(TABLE_BUDGET, null, v);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllBudget()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_BUDGET, null);
        return res;
    }

    public int UpdateBudget(int Amount,int date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(BUDGET,Amount);
        v.put(DATE, date);

        Cursor c = getAllBudget();
        Log.d("checkifexist", " " + c.getCount());

        if (c.getCount() > 0) {
            boolean ca = insertBudget(Amount, date);
            if(ca)
            {
            return 1;}
            else {
                return 3;
            }

        } else {
            boolean see = insertBudget(Amount, date);
            if(see)
            {
                Log.d("see"," "+see);
                return 2;
            }
            else
            {
                Log.d("Error"," "+see);
                return 3;
            }

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
