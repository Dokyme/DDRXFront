package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/15.
 */
@Dao
public interface CardWarehouseDAO {
    @Query("Select CW_cover_url as image_id, CW_name as warehouse_name, CW_abstract as introduction, UCW_time as save_date From CardWarehouse")
    List<CardWarehouseIntro> queryAllCardWarehouseIntro();

    @Query("Select * From CardWarehouse")
    List<CardWarehouse> queryAllCardWarehouse();

    @Insert
    void insertSingleCardWarehouse(CardWarehouse cardWarehouse);

    @Insert
    void insertMultipleCardWarehouse(List<CardWarehouse> mul_cardWarehouse);

    @Update
    void updateCardWarehouse(CardWarehouse... cardWarehouses);

    @Delete
    void deleteCardWarehouse(CardWarehouse... cardWarehouses);
}
