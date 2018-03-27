package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;

import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.CardWarehouseDatabase;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by vincentshaw on 2018/3/27.
 */

public class CardWarehouseDetailController {
    private Handler handler;
    private Context context;
    private long CW_id;
    private OkHttpClient okHttpClient;

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
        Request
    }
}
