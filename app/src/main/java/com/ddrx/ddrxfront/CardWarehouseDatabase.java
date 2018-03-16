package com.ddrx.ddrxfront;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by vincentshaw on 2018/3/15.
 */

@Database(entities = {CardWarehouse.class}, version = 1)
public abstract class CardWarehouseDatabase extends RoomDatabase {
    private static final String DB_NAME = "CardWarehouseDatabase.db";
    private static volatile CardWarehouseDatabase instance;

    static synchronized CardWarehouseDatabase getInstance(Context context){
        if (instance ==  null){
            instance = create(context);
        }
        return instance;
    }

    private static CardWarehouseDatabase create(final Context context){
        return Room.databaseBuilder(context, CardWarehouseDatabase.class, DB_NAME).build();
    }
    public abstract CardWarehouseDAO getCardWarehouseDAO();
}
