package com.ddrx.ddrxfront.Controller;


import android.os.Handler;
import android.util.Log;

import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class UpdateCardFragment {
    private Handler handler;
    private OkHttpClient client;
    private final String GET_USER_ALL_CW_INFO_URL = "localhost:3000/abc";

    public UpdateCardFragment(Handler handler){
        this.handler = handler;
        client = OKHttpClientWrapper.getInstance();
    }

    public void updateData(){
        Request request = new Request.Builder().url(GET_USER_ALL_CW_INFO_URL).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            String json = response.body().string();
        } catch(IOException e){
            Log.e("Network Error: ", "getAllUserWarehouseInfo");
        }
    }


}
