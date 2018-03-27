package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.CardFragment;
import com.ddrx.ddrxfront.CardWarehouseDetailActivity;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.CardWarehouseDatabase;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import com.ddrx.ddrxfront.Utilities.ParseBackDataPack;
import com.ddrx.ddrxfront.Utilities.UserInfoPreference;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by vincentshaw on 2018/3/27.
 */

public class CardWarehouseDetailController {
    private Handler handler;
    private Context context;
    private long CW_id;
    private OkHttpClient okHttpClient;
    private final String GET_LAST_TRAINING_TIME_URL = "localhost:3000/abc";

    public CardWarehouseDetailController(Handler handler, Context context, long CW_id){
        this.handler = handler;
        this.context = context;
        this.CW_id = CW_id;
        okHttpClient = OKHttpClientWrapper.getInstance();
    }

    public void updateInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CardWarehouseDatabase db = CardWarehouseDatabase.getInstance(context);
                CardWarehouse warehouse = db.getCardWarehouseDAO().queryCardWarehouseById(CW_id);
                String last_training_time = getLastTrainingTime();
            }
        })
    }

    private String getLastTrainingTime(){
        RequestBody formBody = new FormBody.Builder()
                .add("CW_id", String.valueOf(CW_id)).build();
        Request request = new Request.Builder()
                .url(GET_LAST_TRAINING_TIME_URL)
                .post(formBody)
                .build();
        Response response = null;
        try{
            response = okHttpClient.newCall(request).execute();
        } catch(IOException e){
            Log.e("Network Error", "getLastTrainingTime@CardWarehouseDetailController");
            sendMessageToUI(CardWarehouseDetailActivity.NETWORK_ERROR);
            return null;
        }
        ParseBackDataPack parser = new ParseBackDataPack(response.body().string());
        int return_code = parser.getCode();
        if(return_code == 0){

        }
        else{
            Log.e("Network Error", "getLastTrainingTime@CardWarehouseDetailController");
            sendMessageToUI(CardWarehouseDetailActivity.NETWORK_ERROR);
            return null;
        }
    }

    private void sendMessageToUI(int message_type){
        Message message = new Message();
        message.what = message_type;
        handler.sendMessage(message);
    }
}
