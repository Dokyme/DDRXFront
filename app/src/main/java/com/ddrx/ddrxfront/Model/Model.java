package com.ddrx.ddrxfront.Model;

import android.util.Log;

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

    private int version_number;
    private long CT_id;
    private List<JSONObject> model_obj;

    public Model(String json_string, long CT_id){
        this.CT_id = CT_id;
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
            if(obj.getInt("type") == COMPLEX_TYPE){
                Stack<JSONObject> obj_stack = new Stack<>();
                Stack<ModelInput> model_stack = new Stack<>();
                ModelInput top = new ModelInput(obj.getString("name"), obj.getInt("type"), obj.getInt("num"));
                obj_stack.push(obj);
                model_stack.push(top);
                while(!obj_stack.empty()){
                    top = model_stack.peek();
                    obj = obj_stack.peek();
                    JSONArray array = obj.getJSONArray("sub");
                    List<ModelInput> top_sub = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject now_obj = array.getJSONObject(i);
                        ModelInput now_input = new ModelInput(now_obj.getString("name"), obj.getInt("type"), obj.getInt("num"));
                        top_sub.add(now_input);
                        if(now_obj.getInt("type") == Model.COMPLEX_TYPE){
                            obj_stack.push(now_obj);
                        }
                    }
                    top.setSub_models(top_sub);
                    result.add(top);
                    obj_stack.pop();
                    model_stack.pop();
                }
            }
            else{
                result.add(new ModelInput(obj.getString("name"), obj.getInt("type"), obj.getInt("num")));
            }
        }
        return result;
    }

}
