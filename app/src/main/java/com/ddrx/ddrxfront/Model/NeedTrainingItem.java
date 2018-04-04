package com.ddrx.ddrxfront.Model;

/**
 * Created by dokym on 2018/3/28.
 */

public class NeedTrainingItem {
    private String imageUrl;
    private long warehouseId;
    private String warehouseName;
    private String lastTrainingDate;

    public NeedTrainingItem(String imageUrl, long warehouseId, String warehouseName, String lastTrainingDate) {
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

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
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
