package com.ddrx.ddrxfront.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/28.
 */

public class ModelInput {
    private String name;
    private int type;
    private boolean only;

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

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public ModelInput(String name, int type, boolean only) {

        this.name = name;
        this.type = type;
        this.only = only;
    }
}
