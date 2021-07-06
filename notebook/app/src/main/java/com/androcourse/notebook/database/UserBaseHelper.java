package com.androcourse.notebook.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "users0759-3.db";

    public UserBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserDBSchema.NAME + " (" +
                UserDBSchema.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserDBSchema.Cols.UUID + " TEXT, " +
                UserDBSchema.Cols.USERNAME + " TEXT," +
                UserDBSchema.Cols.USERLASTNAME + " TEXT," +
                UserDBSchema.Cols.PHONE + " TEXT" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}