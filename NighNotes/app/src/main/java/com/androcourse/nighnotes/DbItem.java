package com.androcourse.nighnotes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class DbItem {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "kind")
    public String kind;

    @ColumnInfo(name = "data")
    public byte[] data;
}

