package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by vincentshaw on 2018/3/15.
 */
@Entity
public class CardWarehouse {
    @PrimaryKey
    private long CW_id;

    private long CT_id;
    private String CT_name;
    private long U_id;
    private String U_nick;
    private String UCW_time;
    private String CW_name;
    private int CW_privilege;
    private int CW_card_num;
    private String CW_abstract;
    private String CW_detail;
    private int CW_training;
    private String CW_cover_url;

    public CardWarehouse(){}

    public CardWarehouse(long CW_id, long CT_id, String CT_name, long u_id, String u_nick, String UCW_time, String CW_name, int CW_privilege, int CW_card_num, String CW_abstract, String CW_detail, int CW_training) {
        this.CW_id = CW_id;
        this.CT_id = CT_id;
        this.CT_name = CT_name;
        U_id = u_id;
        U_nick = u_nick;
        this.UCW_time = UCW_time;
        this.CW_name = CW_name;
        this.CW_privilege = CW_privilege;
        this.CW_card_num = CW_card_num;
        this.CW_abstract = CW_abstract;
        this.CW_detail = CW_detail;
        this.CW_training = CW_training;
    }

    private String CW_content;

    public String getCW_cover_url() {
        return CW_cover_url;
    }

    public void setCW_cover_url(String CW_cover_url) {
        this.CW_cover_url = CW_cover_url;
    }

    public String getCW_content() {
        return CW_content;
    }

    public void setCW_content(String CW_content) {
        this.CW_content = CW_content;
    }

    public long getCW_id() {
        return CW_id;
    }

    public void setCW_id(long CW_id) {
        this.CW_id = CW_id;
    }

    public long getCT_id() {
        return CT_id;
    }

    public void setCT_id(long CT_id) {
        this.CT_id = CT_id;
    }

    public String getCT_name() {
        return CT_name;
    }

    public void setCT_name(String CT_name) {
        this.CT_name = CT_name;
    }

    public long getU_id() {
        return U_id;
    }

    public void setU_id(long u_id) {
        U_id = u_id;
    }

    public String getU_nick() {
        return U_nick;
    }

    public void setU_nick(String u_nick) {
        U_nick = u_nick;
    }

    public String getUCW_time() {
        return UCW_time;
    }

    public void setUCW_time(String UCW_time) {
        this.UCW_time = UCW_time;
    }

    public String getCW_name() {
        return CW_name;
    }

    public void setCW_name(String CW_name) {
        this.CW_name = CW_name;
    }

    public int getCW_privilege() {
        return CW_privilege;
    }

    public void setCW_privilege(int CW_privilege) {
        this.CW_privilege = CW_privilege;
    }

    public int getCW_card_num() {
        return CW_card_num;
    }

    public void setCW_card_num(int CW_card_num) {
        this.CW_card_num = CW_card_num;
    }

    public String getCW_abstract() {
        return CW_abstract;
    }

    public void setCW_abstract(String CW_abstract) {
        this.CW_abstract = CW_abstract;
    }

    public String getCW_detail() {
        return CW_detail;
    }

    public void setCW_detail(String CW_detail) {
        this.CW_detail = CW_detail;
    }

    public int getCW_training() {
        return CW_training;
    }

    public void setCW_training(int CW_training) {
        this.CW_training = CW_training;
    }
}
