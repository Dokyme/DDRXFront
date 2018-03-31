package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by vincentshaw on 2018/3/28.
 */

@Entity(primaryKeys = {"CC_id", "CW_id"})
public class MemoryCard {
    private long CC_id;

    private long CW_id;
    private String CC_content;

    public long getCC_id() {
        return CC_id;
    }

    public void setCC_id(long CC_id) {
        this.CC_id = CC_id;
    }

    public long getCW_id() {
        return CW_id;
    }

    public void setCW_id(long CW_id) {
        this.CW_id = CW_id;
    }

    public String getCC_content() {
        return CC_content;
    }

    public void setCC_content(String CC_content) {
        this.CC_content = CC_content;
    }

    public MemoryCard(long CC_id, long CW_id, String CC_content) {

        this.CC_id = CC_id;
        this.CW_id = CW_id;
        this.CC_content = CC_content;
    }
}
