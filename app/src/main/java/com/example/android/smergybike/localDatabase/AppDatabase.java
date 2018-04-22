package com.example.android.smergybike.localDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.smergybike.Player;
import com.example.android.smergybike.Race;

/**
 * Created by Joren on 12-4-2018.
 */
@Database(entities = {Player.class, Race.class}, version = 3, exportSchema = false)
//@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    public abstract RaceDao raceDao();
    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "SmergyDatabase").fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}