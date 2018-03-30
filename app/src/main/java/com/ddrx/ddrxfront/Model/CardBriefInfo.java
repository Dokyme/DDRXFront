package com.ddrx.ddrxfront.Model;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class CardBriefInfo {
    private int card_id;
    private String card_intro;

    public CardBriefInfo(int card_id, String card_intro) {
        this.card_id = card_id;
        this.card_intro = card_intro;
    }

    public int getCard_id() {

        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getCard_intro() {
        return card_intro;
    }

    public void setCard_intro(String card_intro) {
        this.card_intro = card_intro;
    }
}
