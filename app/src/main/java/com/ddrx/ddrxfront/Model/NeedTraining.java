package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;

/**
 * Created by dokym on 2018/3/28.
 */

public class NeedTraining {
    private String imageUrl;
    private int warehouseId;
    private String warehouseName;
    private String lastTrainingDate;

    public NeedTraining(String imageUrl, int warehouseId, String warehouseName, String lastTrainingDate) {
        this.imageUrl = imageUrl;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.lastTrainingDate = lastTrainingDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLastTrainingDate() {
        return lastTrainingDate;
    }

    public void setLastTrainingDate(String lastTrainingDate) {
        this.lastTrainingDate = lastTrainingDate;
    }
}
