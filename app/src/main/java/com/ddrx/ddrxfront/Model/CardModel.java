package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by vincentshaw on 2018/3/16.
 */

@Entity
public class CardModel {
    @PrimaryKey
    private int model_id;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    private String model_name;
    private String creator_name;
    private String intro;
    private int privilege;
    private String storage;

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
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

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public CardModel(int model_id, String model_name, String creator_name, String intro, int privilege, String storage) {

        this.model_id = model_id;
        this.model_name = model_name;
        this.creator_name = creator_name;
        this.intro = intro;
        this.privilege = privilege;
        this.storage = storage;
    }
}
