package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/13.
 */

public class CardWarehouseIntro {
    private int image_id;

    private String warehouse_name;
    private String introduction;
    private String save_date;

    public int getImage_id() {
        return image_id;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getSave_date() {
        return save_date;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public CardWarehouseIntro(int image_id, String warehouse_name, String introduction, String save_date) {
        this.image_id = image_id;
        this.warehouse_name = warehouse_name;
        this.introduction = introduction;
        this.save_date = save_date;
    }

    public void setWarehouse_name(String warehouse_name) {

        this.warehouse_name = warehouse_name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setSave_date(String save_date) {
        this.save_date = save_date;
    }
}
