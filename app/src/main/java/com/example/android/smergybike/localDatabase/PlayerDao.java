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

//    @Query("SELECT MAX(power) FROM player")
//    int getMaxPower();
//
//    @Query("SELECT MAX(energy) FROM player")
//    int getMaxEnergy();

    @Query("SELECT * FROM player WHERE eventId = (:eventId)")
    List<Player> getPlayersFromEvent(long eventId);

    @Query("SELECT * FROM player WHERE raceId = (:raceId)")
    List<Player> getPlayersFromRace(long raceId);

    @Query("SELECT * FROM player WHERE raceId = (:raceId) AND colorBlue = (:isblue)")
    Player getPlayer(long raceId, boolean isblue);

    @Update
    void update(Player player);

    @Insert
    long insert(Player player);

    @Query("DELETE FROM player")
    void deleteAllPlayers();

    @Delete
    void delete(Player player);
}
