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

    /**
     * 选出还有卡片没有达到次数要求的CardWarehouse(即NeedTranning)
     *
     * @return
     */
    @Query("Select cw.CW_id as warehouseId," +
            "cw.CW_cover_url as imageUrl," +
            "cw.CW_name as warehouseName," +
            "(Select max(tr.training_time) from TrainingRecord tr where tr.CW_id=cw.CW_id) as lastTrainingDate " +
            "from CardWarehouse cw " +
            "where exists (Select * from CardTranningRecord ctr where ctr.CW_id=cw.CW_id and ctr.training_count < cw.CW_training)")
    List<NeedTrainingItem> queryNeedTrainings();

    /**
     * 选出cwId卡片仓库的还未完成训练次数的卡片
     *
     * @param cwId
     * @param CC_id
     * @return
     */
    @Query("Select CW_id,CC_id,training_count from CardTranningRecord where CW_id=:cwId and CC_id=:CC_id")
    CardTranningRecord queryTrainRecord(Long cwId, long CC_id);

    @Query("Select mc.CC_id as CC_id," +
            "mc.CW_id as CW_id," +
            "mc.CC_content as CC_content " +
            "from MemoryCard mc " +
            "where mc.CW_id=:cwId and " +
            "(Select training_count from CardTranningRecord ctr where ctr.CW_id=:cwId and ctr.CC_id=mc.CC_id)" +
            " < (Select cw.CW_training from CardWarehouse cw where cw.CW_id=:cwId)")
    List<MemoryCard> queryNeedTrainingMemoryCardByCWId(Long cwId);

    @Update
    void updateCardsTrainingRecords(CardTranningRecord... cardTranningRecords);

    @Delete
    void deleteCardsTrainingRecords(CardTranningRecord... cardTranningRecords);
}
