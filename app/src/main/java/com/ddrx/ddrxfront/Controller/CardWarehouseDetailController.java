package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.CardFragment;
import com.ddrx.ddrxfront.CardWarehouseDetailActivity;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.CardWarehouseDatabase;
import com.ddrx.ddrxfront.Model.TrainingRecord;
import com.ddrx.ddrxfront.Model.TrainingRecordDatabase;
import com.ddrx.ddrxfront.Utilities.GenericPair;
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
                UserInfoPreference preference = new UserInfoPreference(context);
                long U_id = preference.getUserInfo().getId();
                CardWarehouseDatabase w_db = CardWarehouseDatabase.getInstance(context);
                CardWarehouse warehouse = w_db.getCardWarehouseDAO().queryCardWarehouseById(CW_id);

                TrainingRecordDatabase t_db = TrainingRecordDatabase.getInstance(context);
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
