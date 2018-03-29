package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


/**
 * Created by vincentshaw on 2018/3/27.
 */

@Entity(primaryKeys = {"CW_id", "U_id", "training_time"})
public class TrainingRecord {

    private long CW_id;
    private long U_id;
    private String training_time;

    public long getCW_id() {
        return CW_id;
    }

    public void setCW_id(long CW_id) {
        this.CW_id = CW_id;
    }

    public long getU_id() {
        return U_id;
    }

    public void setU_id(long u_id) {
        U_id = u_id;
    }

    public String getTraining_time() {
        return training_time;
    }

    public void setTraining_time(String training_time) {
        this.training_time = training_time;
    }

    public TrainingRecord(@NonNull long CW_id,@NonNull long U_id,@NonNull String training_time) {
        this.CW_id = CW_id;
        this.U_id = U_id;
        this.training_time = training_time;
    }
}
