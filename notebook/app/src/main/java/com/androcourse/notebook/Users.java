package com.androcourse.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androcourse.notebook.database.UserBaseHelper;
import com.androcourse.notebook.database.UserDBSchema;

import java.util.ArrayList;

// db access for users
class Users {
    private SQLiteDatabase database;
    private Context context;

    public Users(Context context) {
        this.context = context;
        database = new UserBaseHelper(context).getWritableDatabase();
    }

    private static ContentValues userContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDBSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDBSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDBSchema.Cols.PHONE, user.getPhone());
        return values;
    }

    public void updateUser(User user) {
        database.update(UserDBSchema.UserTable.NAME,
                userContentValues(user),
                UserDBSchema.Cols.UUID + " = ?",
                new String[]{user.getUuid().toString()});
    }

    public void deleteUser(User user) {
        database.delete(
                UserDBSchema.UserTable.NAME,
                UserDBSchema.Cols.UUID + " = ?",
                new String[]{user.getUuid().toString()});
    }

    public void addUser(User user) {
        ContentValues values = userContentValues(user);
        try {
            database.insertOrThrow(UserDBSchema.UserTable.NAME, null, values);
        } catch (Exception e) {
            Log.d("===", "db error " + e + " values=" + values);
        }
    }

    private UserCursorWrapper queryUsers() {
        Cursor cursor = database.query(UserDBSchema.UserTable.NAME, null, null, null, null, null, null);
        return new UserCursorWrapper(cursor);
    }

    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<User>();
        UserCursorWrapper cursorWrapper = queryUsers();
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return userList;
    }

}
