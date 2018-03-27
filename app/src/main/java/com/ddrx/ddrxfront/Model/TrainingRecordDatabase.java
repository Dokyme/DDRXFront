package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by vincentshaw on 2018/3/27.
 */

@Database(entities = {TrainingRecord.class}, version = 1)
public abstract class TrainingRecordDatabase extends RoomDatabase {
    private static final String DB_NAME = "TrainingRecordDatabase.db";
    private static volatile TrainingRecordDatabase instance;

    public static synchronized TrainingRecordDatabase getInstance(Context context){
        if (instance ==  null){
            instance = create(context);
        }
        return instance;
    }

    private static TrainingRecordDatabase create(final Context context){
        return Room.databaseBuilder(context, TrainingRecordDatabase.class, DB_NAME).build();
    }

    public abstract TrainingRecordDAO getTrainingRecordDAO();
}
