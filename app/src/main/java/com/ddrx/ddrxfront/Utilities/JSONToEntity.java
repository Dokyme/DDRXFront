package com.ddrx.ddrxfront.Utilities;

import android.content.Context;
import android.util.Log;

import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.MemoryCard;
import com.ddrx.ddrxfront.Model.TrainingRecord;
import com.ddrx.ddrxfront.Model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentshaw on 2018/3/22.
 */

public class JSONToEntity {
    public static UserInfo getUserInfo(Context context, JSONObject data) throws Exception {
        long id = (Long) data.get("U_id");
        UserInfo userInfo = getUserDetailInfo(context, data);
        userInfo.setId(id);
        return userInfo;
    }

    public static UserInfo getUserDetailInfo(Context context, JSONObject data) throws Exception {
        String nickname = (String) data.get("U_nick");
        long experience = (long) data.get("U_exp");
        float balance = (float) data.get("U_balance");
        int cardLimit = (int) data.get("U_card_limit");
        String sex = (String) data.get("U_sex");
        String birthday = (String) data.get("U_birth_day");
        String city = (String) data.get("U_city");
        String brief = (String) data.get("U_brief");
        UserInfo userInfo = new UserInfoPreference(context).getUserInfo();
        userInfo.setNickname(nickname);
        userInfo.setExperience(experience);
        userInfo.setBalance(balance);
        userInfo.setCardLimit(cardLimit);
        userInfo.setSex(sex);
        userInfo.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        userInfo.setCity(city);
        userInfo.setBrief(brief);
        return userInfo;
    }

    public static List<CardWarehouse> getCardWarehouseList(JSONArray jsonArray){

        long CW_id, CT_id, U_id;
        String CT_name, U_nick, CW_name, CW_abstract, UCW_time, CW_detail;
        int CW_privilege, CW_card_num, CW_training;

        List<CardWarehouse> result = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try{
                JSONObject obj = jsonArray.getJSONObject(i);
                CW_id = obj.getLong("CW_id");
                CT_id = obj.getLong("CT_id");
                U_id = obj.getLong("U_id");
                CT_name = obj.getString("CT_name");
                U_nick = obj.getString("U_nick");
                CW_name = obj.getString("CW_name");
                CW_abstract = obj.getString("CW_abstract");
                UCW_time = obj.getString("UCW_time");
                CW_detail = obj.getString("CW_detail");
                CW_training = obj.getInt("CW_training");
                CW_privilege = obj.getInt("CW_privilege");
                CW_card_num = obj.getInt("CW_card_num");
            }catch(JSONException exception){
                Log.e("JSON_parsing_error", "getCardWarehouseList");
                continue;
            }
            result.add(new CardWarehouse(CW_id, CT_id, CT_name, U_id, U_nick, UCW_time, CW_name, CW_privilege, CW_card_num
                                        , CW_abstract, CW_detail, CW_training));
        }
        return result;
    }

    public static Map<Long, Integer> getTimestamps(JSONArray jsonArray) throws JSONException{
        Map<Long, Integer> result = new HashMap<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            result.put(obj.getLong("CW_id"), obj.getInt("timestamp"));
        }
        return result;
    }

    public static List<CardModel> getCardModelList(JSONArray array){
        long CT_id;
        String CT_name, U_name, CT_brief, CT_context;
        int CT_privilege, CT_type;

        List<CardModel> models = new ArrayList<>();
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                CT_id = obj.getLong("CT_id");
                CT_name = obj.getString("CT_name");
                U_name = obj.getString("U_name");
                CT_privilege = obj.getInt("CT_privilege");
                CT_brief = obj.getString("CT_brief");
                CT_type = obj.getInt("CT_type");
                CT_context = obj.getString("CT_context");
                models.add(new CardModel(CT_id, CT_name, U_name, CT_brief, CT_privilege, CT_type, CT_context));
            }
        } catch(JSONException e){
            Log.e("JSON Format Error", "getCardModelList@JSONToEntity");
            return null;
        }
        return models;
    }

    public static List<TrainingRecord> getTrainingRecordList(JSONArray array){
        long CW_id, U_id;
        String training_time;
        List<TrainingRecord> recordList = new ArrayList<>();
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                CW_id = obj.getLong("CW_id");
                U_id = obj.getLong("U_id");
                training_time = obj.getString("TR_finish");
                recordList.add(new TrainingRecord(CW_id, U_id, training_time));
            }
        } catch(JSONException e){
            Log.e("JSON Format Error", "getTrainingRecordList@JSONToEntity");
            return null;
        }
        return recordList;
    }

    public static List<MemoryCard> getMemoryCardList(JSONArray array){
        long CC_id, CW_id;
        String CC_content;
        List<MemoryCard> memoryCardList = new ArrayList<>();
        try{
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                CC_id = obj.getLong("CC_id");
                CW_id = obj.getLong("CW_id");
                CC_content = obj.getString("CC_content");
                memoryCardList.add(new MemoryCard(CC_id, CW_id, CC_content));
            }
        } catch (JSONException e){
            Log.e("JSON Format Error", "getMemoryCardList@JSONToEntity");
            return null;
        }
        return memoryCardList;
    }
}
