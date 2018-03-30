package com.ddrx.ddrxfront.Model;

import java.util.List;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class CardFieldTrainingItem {
    private String name;
    private int type;
    private String data;
    private boolean name_visible;
    private int text_size;
    private int align;
    private String[] keywords;

    public CardFieldTrainingItem(String name, int type, String data, boolean name_visible, int text_size, int align, String[] keywords) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.name_visible = name_visible;
        this.text_size = text_size;
        this.align = align;
        this.keywords = keywords;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isName_visible() {
        return name_visible;
    }

    public void setName_visible(boolean name_visible) {
        this.name_visible = name_visible;
    }

    public int getText_size() {
        return text_size;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
}