package com.example.android.smergybike.localDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.smergybike.Player;

import java.util.List;

/**
 * Created by Joren on 12-4-2018.
 */
@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player WHERE id IN (:playerIds)")
    List<Player> loadAllByIds(int[] playerIds);

    @Insert
    void insert(Player player);

    @Query("DELETE FROM player")
    void deleteAll();

    @Delete
    void delete(Player player);
}
