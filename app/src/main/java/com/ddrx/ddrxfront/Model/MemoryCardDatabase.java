package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by vincentshaw on 2018/3/28.
 */

@Database(entities = {MemoryCard.class}, version = 1)
public abstract class MemoryCardDatabase extends RoomDatabase {
    private static final String DB_NAME = "MemoryCardDatabase.db";
    private static volatile MemoryCardDatabase instance;

    public static synchronized MemoryCardDatabase getInstance(Context context){
        if (instance ==  null){
            instance = create(context);
        }
        return instance;
    }

    private static MemoryCardDatabase create(final Context context){
        return Room.databaseBuilder(context, MemoryCardDatabase.class, DB_NAME).build();
    }

    public abstract MemoryCardDAO getCardWarehouseDAO();
}
