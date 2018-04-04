package com.ddrx.ddrxfront.Utilities;

import android.content.Context;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import com.ddrx.ddrxfront.AddCardActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vincentshaw on 2018/4/4.
 */

public class SeparateWords {
    private static final String SEPARATE_URL = "http://api.ltp-cloud.com/analysis/?api_key=M1D7J6n1c6PJpxYTmU7dtCZJKp1BvoqkSjIRwsbb";
    private static final String OPTIONS = "&pattern=pos&format=json";

    public static void getSeparateWord(final int num, final String data, final Context context, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = OKHttpClientWrapper.Companion.getInstance(context);
                Request request = new Request.Builder().url(SEPARATE_URL + "&text=" + data + OPTIONS).get().build();
                Response response = null;
                String keywords = null;
                try{
                    response = client.newCall(request).execute();
                    keywords = response.body().string();
                } catch(IOException e){
                    Log.e("Network Error", "@SeparateWords");
                }
                if(response.isSuccessful()){
                    JSONArray arr = null;
                    try{
                        arr = new JSONArray(keywords);
                        List<String> keyword_list = new ArrayList<>();
                        for(int i = 0; i < arr.length(); i++){
                            if(arr.getJSONObject(i).getString("pos").equals("n")){
                                keyword_list.add(arr.getJSONObject(i).getString("cont"));
                            }
                        }
                        Message message = new Message();
                        message.what = AddCardActivity.UPDATE_ENTRY_KEYWORD;
                        GenericPair<Integer, List<String>> tmp = new GenericPair<>();
                        tmp.setFirst(num);
                        tmp.setSecond(keyword_list);
                        message.obj = tmp;
                        handler.sendMessage(message);
                    }catch (JSONException e){
                        Log.e("JSON Format Error", "@SeparateWords");
                    }
                }
                else{
                    Log.e("Network Error", "@SeparateWords");
                }
            }
        }).start();
    }
}
