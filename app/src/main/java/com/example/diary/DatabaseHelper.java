package com.example.diary;

import android.app.ListActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";    //数据库名
    public static final int version_code=1;
    public static final String DB_NAME = "text";              //表名
    private static final String TAG ="DatabaseHelper" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,version_code);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //只创建一次
        Log.d(TAG,"create！");
        //创建
        String sql="create table "+DB_NAME+"(_id integer PRIMARY KEY, _title text,_content text,_datetime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')),_version integer,_editer text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"UPDATE！");

    }

}
