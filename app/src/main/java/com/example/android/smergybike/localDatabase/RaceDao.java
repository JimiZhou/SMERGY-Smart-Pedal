package com.example.android.smergybike.localDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.smergybike.Race;

import java.util.List;

/**
 * Created by Joren on 21-4-2018.
 */
@Dao
public interface RaceDao {
    @Query("SELECT * FROM race")
    List<Race> getAll();

    @Query("SELECT * FROM Race WHERE id = (:raceId)")
    Race getRaceById(long raceId);

    @Query("SELECT * FROM Race WHERE event = (:eventId)")
    List<Race> getRacesFromEvent(long eventId);

    @Update
    void update(Race race);
    @Insert
    long insert(Race race);

    @Query("DELETE FROM race")
    void deleteAllRaces();

    @Delete
    void delete(Race race);
}
