package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by dokym on 2018/3/29.
 */

@Database(entities = {CardTranningRecord.class}, version = 1)
public abstract class CardTrainingRecordDatabase extends RoomDatabase {
    private static final String DB_NAME = "CardTrainingRecordDatabase.db";
    private static volatile CardTrainingRecordDatabase instance;

    public static synchronized CardTrainingRecordDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    private static CardTrainingRecordDatabase create(final Context context) {
        return Room.databaseBuilder(context, CardTrainingRecordDatabase.class, DB_NAME).build();
    }

    public abstract CardTrainingRecordDAO getCardTrainingRecordDAO();
}
