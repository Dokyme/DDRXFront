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
    @Query("Select cover_url as image_id, warehouse_name, _abstract as introduction, add_time as save_date From CardWarehouse")
    List<CardWarehouseIntro> queryAllCardModelIntro();

    @Insert
    void insertSingleCardModel(CardModel cardModel);

    @Insert
    void insertMultipleCardModel(List<CardWarehouse> mul_cardWarehouse);

    @Update
    void updateCardWarehouse(CardWarehouse... cardWarehouses);

    @Delete
    void deleteCardWarehouse(CardWarehouse... cardWarehouses);
}
