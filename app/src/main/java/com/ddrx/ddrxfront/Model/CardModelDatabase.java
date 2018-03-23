package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by vincentshaw on 2018/3/19.
 */

@Database(entities = {CardModel.class}, version = 1)
public abstract class CardModelDatabase extends RoomDatabase{
    private static final String DB_NAME = "CardModelDatabase.db";
    private static volatile CardModelDatabase instance;

    static synchronized CardModelDatabase getInstance(Context context){
        if (instance ==  null){
            instance = create(context);
        }
        return instance;
    }

    private static CardModelDatabase create(final Context context){
        return Room.databaseBuilder(context, CardModelDatabase.class, DB_NAME).build();
    }

    public abstract CardModelDAO getCardModeleDAO();
}
