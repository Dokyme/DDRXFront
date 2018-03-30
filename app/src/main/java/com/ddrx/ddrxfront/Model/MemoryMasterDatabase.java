package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by vincentshaw on 2018/3/30.
 */

@Database(entities = {CardWarehouse.class, CardModel.class, CardTranningRecord.class, MemoryCard.class,
                      TrainingRecord.class}, version = 1)
public abstract class MemoryMasterDatabase extends RoomDatabase{
    private static final String DB_NAME = "MemoryMasterDatabase.db";
    private static volatile MemoryMasterDatabase instance;

    public static synchronized MemoryMasterDatabase getInstance(Context context){
        if (instance ==  null){
            instance = create(context);
        }
        return instance;
    }

    private static MemoryMasterDatabase create(final Context context){
        return Room.databaseBuilder(context, MemoryMasterDatabase.class, DB_NAME).build();
    }

    public abstract CardWarehouseDAO getCardWarehouseDAO();
    public abstract CardModelDAO getCardModelDAO();
    public abstract CardTrainingRecordDAO getCardTrainingRecordDAO();
    public abstract MemoryCardDAO getMemoryCardDAO();
    public abstract TrainingRecordDAO getTrainingRecordDAO();

}
