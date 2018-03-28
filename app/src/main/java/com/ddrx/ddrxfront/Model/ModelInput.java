package com.ddrx.ddrxfront.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/28.
 */

public class ModelInput {
    private String name;
    private int type;
    private int num;

    public List<ModelInput> getSub_models() {
        return sub_models;
    }

    public void setSub_models(List<ModelInput> sub_models) {
        this.sub_models = sub_models;
    }

    private List<ModelInput> sub_models;

    public ModelInput(String name, int type, int num) {
        this.name = name;
        this.type = type;
        this.num = num;
        sub_models = null;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
