package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/27.
 */

public class UserTrainingRecord {
    private long CW_id;
    private String training_time;

    public long getCW_id() {
        return CW_id;
    }

    public void setCW_id(long CW_id) {
        this.CW_id = CW_id;
    }

    public String getTraining_time() {
        return training_time;
    }

    public void setTraining_time(String training_time) {
        this.training_time = training_time;
    }

    public UserTrainingRecord(long CW_id, String training_time) {

        this.CW_id = CW_id;
        this.training_time = training_time;
    }
}
