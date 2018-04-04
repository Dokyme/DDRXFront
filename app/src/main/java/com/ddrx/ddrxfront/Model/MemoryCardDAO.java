package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/28.
 */
@Dao
public interface MemoryCardDAO {
    @Query("Select * From MemoryCard Where CW_id = :CW_id")
    List<MemoryCard> queryMemoryCardByCW_id(long CW_id);

    @Query("Select * from MemoryCard")
    List<MemoryCard> queryAllMemoryCard();

    @Insert
    void insertIntoMemoryCard(MemoryCard... card);

    @Insert
    void insetIntoMemoryCard(List<MemoryCard> cards);

    @Update
    void updateMemoryCard(MemoryCard... card);

    @Update
    void updateMemoryCard(List<MemoryCard> cards);

    @Query("Delete From MemoryCard Where 1=1")
    void deleteAllMemoryCard();

    @Delete
    void deleteMemoryCard(MemoryCard... card);

    @Delete
    void deleteMemoryCard(List<MemoryCard> cards);
}
