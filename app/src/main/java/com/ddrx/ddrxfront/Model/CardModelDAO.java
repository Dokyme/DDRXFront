package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/16.
 */

@Dao
public interface CardModelDAO {
    @Query("Select * From CardModel")
    List<CardModel> queryAllCardModels();

    @Query("Select CT_context From CardModel Where CT_id = :CT_id")
    String queryCardModelContextById(long CT_id);

    @Query("Select * From CardModel Where CT_id = :CT_id")
    CardModel queryCardModelById(long CT_id);

    @Query("Select CT_id From CardModel")
    List<Long> queryAllCT_ID();

    @Query("Select CT_id From CardModel Where CT_id not in (:CT_ids)")
    List<Long> queryAllNotExistCT_ID(List<Long> CT_ids);

    @Query("Delete From CardModel Where CT_id in (:CT_ids)")
    void deleteCardModelById(List<Long> CT_ids);

    @Insert
    void insertSingleCardModel(CardModel cardModel);

    @Insert
    void insertMultipleCardModel(List<CardModel> mul_cardModel);

    @Update
    void updateCardModels(CardModel... cardModels);

    @Delete
    void deleteCardModel(CardModel... cardModels);
}
