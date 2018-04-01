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

    public Card(){items = new ArrayList<>();}

    public void addCardItem(CardItem cardItem){
        items.add(cardItem);
    }

    public List<CardFieldDisplayItem> getCardDisplayItem(){
        List<CardFieldDisplayItem> displayItems = new ArrayList<>();
        for(CardItem card_item: items){
            displayItems.add(new CardFieldDisplayItem(card_item.name, card_item.type, card_item.data, card_item.name_visible, card_item.text_align, card_item.text_size));
        }
        return  displayItems;
    }

    public List<CardFieldTrainingItem> getCardTrainingItem(){
        List<CardFieldTrainingItem> trainingItems = new ArrayList<>();
        for(CardItem card_item: items){
            if(card_item.trainable){
                CardFieldTrainingItem temp = new CardFieldTrainingItem(card_item.name, card_item.type, card_item.data, card_item.name_visible, card_item.text_size, card_item.text_align, card_item.keyword.split("\\s+"));
            }
        }
        return trainingItems;
    }

    public static class CardItem{
        public String name;
        public int type;
        public String data;
        public boolean name_visible;
        public int text_size;
        public int text_align;
        public boolean trainable;
        public String keyword;

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

        public CardItem(JSONObject obj, String data){
            try{
                name = obj.getString("name");
                type = obj.getInt("type");
                this.data = data;
                name_visible = obj.getBoolean("name_visible");
                text_size = obj.getInt("size");
                text_align = obj.getInt("align");
                trainable = obj.getBoolean("trainable");
                keyword = "";
                if(obj.getBoolean("keyword")){
                    for(int i = 0; i < data.length(); i++){
                        if(i % 4 == 0){
                            keyword = keyword + data.charAt(i);
                        }
                    }
                }
            }catch(JSONException e){
                Log.e("JSON Format Error", "CardItem@Card");
            }
        }

        public JSONObject toJSONObject(){
            JSONObject result = new JSONObject();
            try{
                result.put("name", name);
                result.put("type", type);
                result.put("data", data);
                result.put("name_visible", name_visible);
                result.put("text_size", text_size);
                result.put("text_align",text_align);
                result.put("trainable", trainable);
                result.put("keyword", keyword);
            }catch (JSONException e){
                Log.e("JSON Format Error", "CardItem@Card");
                return null;
            }
            return result;
        }
    }

    @Override
    public String toString(){
        JSONArray array = new JSONArray();
        for(CardItem item: items){
            JSONObject obj = item.toJSONObject();
            if(obj != null){
                array.put(obj);
            }
        }
        return array.toString();
    }
}
