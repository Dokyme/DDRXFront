package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by dokym on 2018/3/28.
 */

@Entity(primaryKeys = {"CW_id", "serial_id"}, foreignKeys = @ForeignKey(entity = CardWarehouse.class, parentColumns = "CW_id", childColumns = "CW_id", onDelete = CASCADE))
public class CardTranningRecord {
    private long CW_id;
    private int serial_id;
    private int training_count;

    public CardTranningRecord(long CW_id, int serial_id, int training_count) {
        this.serial_id = serial_id;
        this.CW_id = CW_id;
        this.training_count = training_count;
    }

    public long getCW_id() {
        return CW_id;
    }

    public void setCW_id(long CW_id) {
        this.CW_id = CW_id;
    }

    public int getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(int serial_id) {
        this.serial_id = serial_id;
    }

    public int getTraining_count() {
        return training_count;
    }

    public void setTraining_count(int training_count) {
        this.training_count = training_count;
    }
}
