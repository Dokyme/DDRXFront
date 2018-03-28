package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/28.
 */

public class ModelIntro {
    private long CT_id;
    private String CT_name;
    private String UCT_time;
    private String intro;
    private int type;

    public ModelIntro(long CT_id, String CT_name, String UCT_time, String intro, int type) {
        this.CT_id = CT_id;
        this.CT_name = CT_name;
        this.UCT_time = UCT_time;
        this.intro = intro;
        this.type = type;
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

    public String getUCT_time() {
        return UCT_time;
    }

    public void setUCT_time(String UCT_time) {
        this.UCT_time = UCT_time;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
