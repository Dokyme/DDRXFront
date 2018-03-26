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
    private List<JSONObject> card_content;
    private int serial_id;
    private long timestamp;
    private int CW_id;

    public Card(String json_string, int CW_id){
        this.CW_id = CW_id;
        card_content = new LinkedList<>();
        try{
            JSONObject object = new JSONObject(json_string);
            serial_id = object.getInt("serial_id");
            timestamp = object.getInt("timestamp");
            JSONArray content = object.getJSONArray("content");
            for(int i = 0; i < content.length(); i++){
                card_content.add(content.getJSONObject(i));
            }
        }catch(JSONException e){
            Log.e("CardFormatError", "Warehouse " + String.valueOf(CW_id) + "Card " + String.valueOf(serial_id));
        }
    }

    boolean isUpToDate(long up_to_date_timestamp){
        return timestamp == up_to_date_timestamp;
    }

    public boolean updateCard(String json_string){
        card_content.clear();
        try{
            JSONObject object = new JSONObject(json_string);
            timestamp = object.getInt("timestamp");
            JSONArray content = object.getJSONArray("content");
            for(int i = 0; i < content.length(); i++){
                card_content.add(content.getJSONObject(i));
            }
        }catch(JSONException e){
            Log.e("CardFormatError", "Warehouse " + String.valueOf(CW_id) + "Card " + String.valueOf(serial_id));
            return false;
        }
        return true;
    }

    public List<CardFieldDisplayItem> getDisplayItems(){
        List<CardFieldDisplayItem> items = new LinkedList<>();
        for(JSONObject obj: card_content){
            List<JSONObject> stack = new LinkedList<>();
            List<Integer> levels = new LinkedList<>();
            stack.add(obj);
            levels.add(1);
            while(!stack.isEmpty()){
                JSONObject top_obj = stack.get(stack.size() - 1);
                int now_level = levels.get(levels.size() - 1);
                try{
                    if(top_obj.getInt("type") == Model.COMPLEX_TYPE){
                        items.add(new CardFieldDisplayItem(top_obj.getString("name"), top_obj.getInt("type"), "", top_obj.getBoolean("name_visible"), now_level, top_obj.getInt("align")));
                        stack.remove(top_obj);
                        levels.remove(levels.size() - 1);
                        JSONArray array = top_obj.getJSONArray("data");
                        for(int i = 0; i < array.length(); i++){
                            stack.add(array.getJSONObject(i));
                            levels.add(now_level + 1);
                        }
                    }
                    else{
                        items.add(new CardFieldDisplayItem(obj.getString("name"), obj.getInt("type"), obj.getString("data"), obj.getBoolean("name_visible"), now_level, top_obj.getInt("align")));
                        stack.remove(top_obj);
                        levels.remove(levels.size() - 1);
                    }
                }catch(JSONException e){
                    Log.e("CardFormatError", "Warehouse " + String.valueOf(CW_id) + "Card " + String.valueOf(serial_id));
                    return null;
                }
            }
        }
        return items;
    }

    public List<CardFieldTrainingItem> getTrainingItems(){
        List<CardFieldTrainingItem> items = new LinkedList<>();
        for(JSONObject obj: card_content){
            try{
                if(!obj.getBoolean("training_visible")){
                    List<JSONObject> stack = new LinkedList<>();
                    List<Integer> levels = new LinkedList<>();
                    stack.add(obj);
                    levels.add(1);
                    while(!stack.isEmpty()){
                        JSONObject top_obj = stack.get(stack.size() - 1);
                        int now_level = levels.get(levels.size() - 1);
                        try{
                            if(top_obj.getInt("type") == Model.COMPLEX_TYPE){
                                items.add(new CardFieldTrainingItem(top_obj.getString("name"), top_obj.getInt("type"), top_obj.getBoolean("name_visible"), now_level, top_obj.getInt("align"), new ArrayList<TrainingSet>()));
                                stack.remove(top_obj);
                                levels.remove(levels.size() - 1);
                                JSONArray array = top_obj.getJSONArray("data");
                                for(int i = 0; i < array.length(); i++){
                                    stack.add(array.getJSONObject(i));
                                    levels.add(now_level + 1);
                                }
                            }
                            else{
                                JSONArray array = top_obj.getJSONArray("training");
                                List<TrainingSet> trainingSets = new ArrayList<>();
                                for(int j = 0; j < array.length(); j++){
                                    JSONObject object = array.getJSONObject(j);
                                    trainingSets.add(new TrainingSet(object.getInt("start_period"), object.getInt("end_period"), object.getInt("training_type"), object.getString("training_content")));
                                }
                                items.add(new CardFieldTrainingItem(obj.getString("name"), obj.getInt("type"), obj.getBoolean("name_visible"), now_level, top_obj.getInt("align"), trainingSets));
                                stack.remove(top_obj);
                                levels.remove(levels.size() - 1);
                            }
                        }catch(JSONException e){

                        }
                    }
                }
            }catch(JSONException e){
                Log.e("CardFormatError", "Warehouse " + String.valueOf(CW_id) + "Card " + String.valueOf(serial_id));
                return null;
            }
        }
        return items;
    }
}
