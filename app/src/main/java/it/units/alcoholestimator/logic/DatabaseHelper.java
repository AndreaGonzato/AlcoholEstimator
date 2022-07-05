package it.units.alcoholestimator.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COLUMN_1 = "ID";
    private static final String COLUMN_2 = "CLOUD_ID";
    private static final String COLUMN_3 = "EMAIL";
    private static final String COLUMN_4 = "GENDER";
    private static final String COLUMN_5 = "WEIGHT";
    private static final String COLUMN_6 = "IS_SIGNED_IN";

    public static DatabaseHelper instance;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, CLOUD_ID TEXT, EMAIL TEXT, GENDER TEXT, WEIGHT INTEGER, IS_SIGNED_IN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public static boolean insertData(String cloudId, String email, String gender, int weight, String isSignedIn){
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, cloudId);
        contentValues.put(COLUMN_3, email);
        contentValues.put(COLUMN_4, gender);
        contentValues.put(COLUMN_5, weight);
        contentValues.put(COLUMN_6, isSignedIn);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static Cursor getAllData(){
        SQLiteDatabase db = instance.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

    }
}
