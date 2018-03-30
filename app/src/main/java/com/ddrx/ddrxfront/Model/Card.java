package com.ddrx.ddrxfront.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class Card {

    private List<CardItem> items;

    public Card(String json_array){
        JSONArray array = null;
        items = new ArrayList<>();
        try{
            array = new JSONArray(json_array);
            for(int i = 0; i < array.length(); i++){
                items.add(new CardItem(array.getJSONObject(i)));
            }
        }catch (JSONException e){
            Log.e("JSON Format Error", "Card@Card");
        }
    }

    public List<CardFieldDisplayItem> getCardDisplayItem(){
        List<CardFieldDisplayItem> displayItems = new ArrayList<>();
        for(CardItem card_item: items){
            displayItems.add(new CardFieldDisplayItem(card_item.name, card_item.type, card_item.data, card_item.name_visible, card_item.text_align, card_item.text_size));
        }
        return  displayItems;
    }

    public CardFieldTrainingItem getCardTrainingItem(){
        List<CardFieldTrainingItem> trainingItems = new ArrayList<>();
        for(CardItem card_item: items){
            if(card_item.trainable){
                CardFieldTrainingItem temp = new CardFieldTrainingItem(card_item.name, card_item.type, card_item.data, card_item.name_visible, card_item.text_size, card_item.text_align, new ArrayList<String>(card_item.keyword.split("\\s+")));
            }

        }
    }

    public class CardItem{
        private String name;
        private int type;
        private String data;
        private boolean name_visible;
        private int text_size;
        private int text_align;
        private boolean trainable;
        private String keyword;

        public CardItem(JSONObject obj){
            try{
                name = obj.getString("name");
                type = obj.getInt("type");
                data = obj.getString("data");
                name_visible = obj.getBoolean("name_visible");
                text_size = obj.getInt("size");
                text_align = obj.getInt("align");
                trainable = obj.getBoolean("trainable");
                keyword = obj.getString("keyword");
            }catch(JSONException e){
                Log.e("JSON Format Error", "CardItem@Card");
            }
        }
    }
}
