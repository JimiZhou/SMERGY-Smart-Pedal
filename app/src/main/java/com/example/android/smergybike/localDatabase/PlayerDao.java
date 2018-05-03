package com.example.android.smergybike.localDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM player WHERE id = (:playerId)")
    Player getPlayerById(long playerId);

    @Query("SELECT MAX(totalPower) FROM player")
    int getMaxPower();

    @Query("SELECT MAX(totalEnergy) FROM player")
    int getMaxEnergy();

    @Query("SELECT MAX(totalDistance) FROM player")
    int getMaxDistance();

    @Update
    void update(Player player);

    @Insert
    long insert(Player player);

    @Query("DELETE FROM player")
    void deleteAllPlayers();

    @Delete
    void delete(Player player);
}
