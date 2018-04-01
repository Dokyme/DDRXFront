package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/27.
 */

@Dao
public interface TrainingRecordDAO {

    @Query("Select training_time From TrainingRecord Where training_time = " +
            "(Select Max(training_time) From TrainingRecord Where CW_id = :CW_id and U_id = :U_id)")
    String queryLatestTrainingTime(long CW_id, long U_id);

    @Query("Select CW_id, training_time From TrainingRecord Where U_id = :U_id")
    List<TrainingRecordItem> queryUserTrainingRecord(long U_id);

    @Insert
    void insertIntoTrainingRecord(List<TrainingRecord> trainingRecords);

    @Insert
    void insertIntoTrainingRecord(TrainingRecord... trainingRecords);

    @Update
    void updateTrainingRecord(TrainingRecord... trainingRecords);

    @Update
    void updateTrainingRecord(List<TrainingRecord> trainingRecords);

    @Delete
    void deleteTrainingRecord(TrainingRecord... trainingRecords);

    @Delete
    void deleteTrainingRecord(List<TrainingRecord> trainingRecords);

    @Query("Delete From TrainingRecord Where 1=1")
    void deleteAllTrainingRecord();
}
