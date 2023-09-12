package com.example.earringdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "userdays.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERDAY = "userdays";
    public static final String COLUMN_TITLE = "userday_title";
    public static final String COLUMN_LOC = "userday_loc";
    public static final String COLUMN_CMT = "userday_cmt";
    public static final String COLUMN_SDAY = "userday_startday";
    public static final String COLUMN_EDAY = "userday_endday";
    public static final String COLUMN_STIME = "userday_starttime";
    public static final String COLUMN_ETIME = "userday_endtime";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERDAY + " (" +
                    COLUMN_TITLE + " VARCHAR(50) PRIMARY KEY, " +
                    COLUMN_LOC + " VARCHAR(200), " +
                    COLUMN_CMT + " VARCHAR(300), " +
                    COLUMN_SDAY + " VARCHAR(30), " +
                    COLUMN_EDAY + " VARCHAR(30), " +
                    COLUMN_STIME + " VARCHAR(30), " +
                    COLUMN_ETIME + " VARCHAR(30)); ";


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 로직
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDAY);
        onCreate(db);
    }

    void addDay(String title, String loc, String cmt, String sday, String eday, String stime, String etime)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_LOC, loc);
        cv.put(COLUMN_CMT, cmt);
        cv.put(COLUMN_SDAY, sday);
        cv.put(COLUMN_EDAY, eday);
        cv.put(COLUMN_STIME, stime);
        cv.put(COLUMN_ETIME, etime);

        long result = db.insert(TABLE_USERDAY, null, cv);
        if (result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "일정 추가 성공", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 일정 전체 가져오기
     */
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_USERDAY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
