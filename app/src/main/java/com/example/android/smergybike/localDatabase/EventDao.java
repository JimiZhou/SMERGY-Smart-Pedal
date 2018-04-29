package com.example.android.smergybike.localDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.smergybike.Event;

import java.util.List;

/**
 * Created by Joren on 29-4-2018.
 */
@Dao
public interface EventDao {

    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Insert
    long insert(Event event);

    @Query("SELECT * FROM event WHERE id = (:eventId)")
    Event getEventById(long eventId);

    @Query("DELETE FROM Event")
    void deleteAllPlayers();

    @Delete
    void delete(Event event);


}
