package com.ddrx.ddrxfront.Model;

import android.graphics.PorterDuff;
import android.util.Log;

import com.ddrx.ddrxfront.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class Model {

    public static final int COMPLEX_TYPE = 0;
    public static final int DIGIT = 1;
    public static final int WORD = 2;
    public static final int SENTENCE = 3;
    public static final int PARAGRAPH = 4;
    public static final int MULTIPLE_CHOICE = 5;

    public static final int LEFT_ALIGN = 1;
    public static final int MID_ALIGN = 2;
    public static final int RIGHT_ALIGN = 3;

    public static final int TRAINING_NONE = 1;
    public static final int TRAINING_HINT = 2;
    public static final int TRAINING_ALL = 3;

    public static final int FONT_SIZE_DEFAULT = 0;
    public static final int FONT_SIZE_BIG = 22;
    public static final int FONT_SIZE_MID = 18;
    public static final int FONT_SIZE_SMALL = 14;

    public static final int ONLY_ONE = 1;
    public static final int ONE_OR_MORE = 2;

    public static final int[] TYPE = {R.mipmap.model_chinese, R.mipmap.model_math, R.mipmap.model_english,
                                      R.mipmap.model_history, R.mipmap.model_politics, R.mipmap.model_academic, R.mipmap.model_other};
    public static final String[] TYPE_NAME = {"语文", "数学", "英语", "历史", "政治", "专业", "其他"};
    public static final String[] ENTRY_TYPE_NAME = {"数字", "单词", "句子", "段落"};

    private List<JSONObject> model_obj;

    public Model(String json_string){
        model_obj = new ArrayList<>();
        try{
            JSONArray array = new JSONArray(json_string);
            for(int i = 0; i < array.length(); i++){
                model_obj.add(array.getJSONObject(i));
            }
        }catch (JSONException e){
            Log.e("JSON Format Error", "Model@Model");
        }
    }

    public List<ModelInput> getModelInputs() throws JSONException{
        List<ModelInput> result = new ArrayList<>();
        for(JSONObject obj: model_obj){
            result.add(new ModelInput(obj.getString("name"), obj.getInt("type"), obj.getBoolean("only")));
        }
        return result;
    }

    public List<JSONObject> getEntrys(){
        return model_obj;
    }

    public JSONObject getEntry(String entry_name){
        try{
            for(JSONObject obj: model_obj){
                if(obj.getString("name").equals(entry_name))
                    return obj;
            }
        }catch(JSONException e){
            Log.e("JSON Format Error", "getEntry@Model");
            return null;
        }
        return null;
    }
}
