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
    @Insert
    void insertSingleCardModel(CardModel cardModel);

    @Insert
    void insertMultipleCardModel(List<CardModel> mul_cardModel);

    @Update
    void updateCardModels(CardModel... cardModels);

    @Delete
    void deleteCardModel(CardModel... cardModels);
}
