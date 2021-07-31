package com.androcourse.nighnotes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DbItem.class}, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract DbDao dbDao();
}
