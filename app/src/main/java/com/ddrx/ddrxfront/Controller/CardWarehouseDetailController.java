package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ddrx.ddrxfront.CardWarehouseDetailActivity;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Utilities.GenericPair;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import com.ddrx.ddrxfront.Utilities.UserInfoPreference;

import okhttp3.OkHttpClient;


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
        okHttpClient = OKHttpClientWrapper.Companion.getInstance(context);
    }

    public void updateInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoPreference preference = new UserInfoPreference(context);
                long U_id = preference.getUserInfo().getId();
                MemoryMasterDatabase w_db = MemoryMasterDatabase.getInstance(context);
                CardWarehouse warehouse = w_db.getCardWarehouseDAO().queryCardWarehouseById(CW_id);

                MemoryMasterDatabase t_db = MemoryMasterDatabase.getInstance(context);
                String latest_time = t_db.getTrainingRecordDAO().queryLatestTrainingTime(CW_id, U_id);

                Message message = new Message();
                message.what = CardWarehouseDetailActivity.UPDATE_DATA;
                GenericPair<String, CardWarehouse> send_obj = new GenericPair<>();
                send_obj.setFirst(latest_time);
                send_obj.setSecond(warehouse);
                message.obj = send_obj;
            }
        }).start();
    }
}
