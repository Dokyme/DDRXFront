package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by dokym on 2018/3/28.
 */

@Dao
public interface CardTrainingRecordDAO {

    @Query("Select cw.CW_name as warehouseName," +
            "cw.CW_id as warehouseId," +
            "cw.CW_cover_url as imageUrl," +
            "(Select max(training_time) from TrainingRecord where CW_id=cw.CW_id and U_id=:userId) as lastTrainingDate " +
            "from CardTranningRecord ctr " +
            "inner join CardWarehouse cw on cw.CW_id=ctr.CW_id " +
            "group by ctr.CW_id,ctr.CC_id,cw.CW_training,cw.CW_name,cw.CW_cover_url " +
            "having count(*) < cw.CW_training")
    List<NeedTraining> queryNeedTrainingsByUserId(Long userId);

    @Query("Select CW_id,CC_id,training_count from CardTranningRecord where CW_id=:cwId and CC_id=:CC_id")
    CardTranningRecord queryTrainRecord(Long cwId, long CC_id);

    @Update
    void updateCardsTrainingRecords(CardTranningRecord... cardTranningRecords);

    @Delete
    void deleteCardsTrainingRecords(CardTranningRecord... cardTranningRecords);
}
