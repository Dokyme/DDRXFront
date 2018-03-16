package com.ddrx.ddrxfront;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by vincentshaw on 2018/3/15.
 */
@Entity
public class CardWarehouse {
    @PrimaryKey
    private int warehouse_id;

    private String warehouse_name;
    private String warehouse_creator;
    private int cover_url;
    private String create_time;
    private int orthorize;
    private int card_number;

    public CardWarehouse(int warehouse_id, String warehouse_name, String warehouse_creator, int cover_url, String create_time, int orthorize, int card_number, String card_directory_url, int model_id, String intro, String description, double price) {
        this.warehouse_id = warehouse_id;
        this.warehouse_name = warehouse_name;
        this.warehouse_creator = warehouse_creator;
        this.cover_url = cover_url;
        this.create_time = create_time;
        this.orthorize = orthorize;
        this.card_number = card_number;
        this.card_directory_url = card_directory_url;
        this.model_id = model_id;
        this.intro = intro;
        this.description = description;
        this.price = price;
    }

    private String card_directory_url;
    private int model_id;
    private String intro;
    private String description;

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public String getWarehouse_creator() {
        return warehouse_creator;
    }

    public void setWarehouse_creator(String warehouse_creator) {
        this.warehouse_creator = warehouse_creator;
    }

    public int getCover_url() {
        return cover_url;
    }

    public void setCover_url(int cover_url) {
        this.cover_url = cover_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getOrthorize() {
        return orthorize;
    }

    public void setOrthorize(int orthorize) {
        this.orthorize = orthorize;
    }

    public int getCard_number() {
        return card_number;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }

    public String getCard_directory_url() {
        return card_directory_url;
    }

    public void setCard_directory_url(String card_directory_url) {
        this.card_directory_url = card_directory_url;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;
}
