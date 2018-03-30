package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/22.
 */

public class CardModelIntro {
    private String model_name;
    private String creator_name;
    private String intro;
    private int type;
    private String UCT_time;

    public CardModelIntro(String model_name, String creator_name, String intro, int type, String UCT_time) {
        this.model_name = model_name;
        this.creator_name = creator_name;
        this.intro = intro;
        this.type = type;
        this.UCT_time = UCT_time;
    }

    public String getModel_name() {

        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
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

    public String getUCT_time() {
        return UCT_time;
    }

    public void setUCT_time(String UCT_time) {
        this.UCT_time = UCT_time;
    }
}
