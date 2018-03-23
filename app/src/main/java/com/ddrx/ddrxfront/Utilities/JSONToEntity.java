package com.ddrx.ddrxfront.Utilities;

import android.util.Log;

import com.ddrx.ddrxfront.Model.CardWarehouse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentshaw on 2018/3/22.
 */

public class JSONToEntity {
    public static List<CardWarehouse> getCardWarehouseList(String jsonData){
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
        } catch(JSONException exception){
            Log.e("JSONArray_parsing_error", "getCardWarehouseList");
            return null;
        }

        long CW_id, CT_id, U_id;
        String CT_name, U_nick, CW_name, CW_abstract, UCW_time, CW_detail;
        int CW_privilege, CW_card_num, CW_per_day, CW_per_month;

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
                CW_per_day = obj.getInt("CW_per_day");
                CW_per_month = obj.getInt("CW_per_month");
                CW_privilege = obj.getInt("CW_privilege");
                CW_card_num = obj.getInt("CW_card_num");
            }catch(JSONException exception){
                Log.e("JSON_parsing_error", "getCardWarehouseList");
                continue;
            }
            result.add(new CardWarehouse(CW_id, CT_id, CT_name, U_id, U_nick, UCW_time, CW_name, CW_privilege, CW_card_num
                                        , CW_abstract, CW_detail, CW_per_day, CW_per_month));
        }
        return result;
    }
}
