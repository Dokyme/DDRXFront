package com.ddrx.ddrxfront.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincentshaw on 2018/3/25.
 */

public class ParseBackDataPack {
    private int code;
    private JSONArray body;
    private String message;
    private int state = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JSONArray getBody() {
        return body;
    }

    public void setBody(JSONArray body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParseBackDataPack(String data){
        try{
            JSONObject obj = new JSONObject(data);
            code = obj.getInt("code");
            message = obj.getString("msg");
            body = obj.getJSONArray("body");
            state = 1;
        }catch (JSONException e){
            Log.e("JSON Parse Error", "constructor@ParseBackDataPack");
        }
    }
}
