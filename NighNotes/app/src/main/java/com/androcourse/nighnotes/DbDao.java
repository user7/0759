package com.androcourse.nighnotes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DbDao {
    @Query("SELECT * FROM items WHERE kind LIKE :itemKind")
    List<DbItem> getKind(String itemKind);

    @Query("SELECT * FROM items WHERE id = :id")
    DbItem getById(long id);

    @Query("INSERT INTO items (kind, data) VALUES (:kind, :data)")
    long insert(String kind, byte[] data);

    @Update
    void update(DbItem item);
}
