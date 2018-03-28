package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/13.
 */

public class CardWarehouseIntro {
    private String image_url;

    private int warehouse_id;

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    private String warehouse_name;
    private String introduction;
    private String save_date;

    public String getImage_url() {
        return image_url;
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

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public CardWarehouseIntro(String image_url, String warehouse_name, String introduction, String save_date, int warehouse_id) {
        this.image_url = image_url;
        this.warehouse_name = warehouse_name;
        this.introduction = introduction;
        this.save_date = save_date;
        this.warehouse_id = warehouse_id;
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
