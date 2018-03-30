package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.ddrx.ddrxfront.AddNewWarehouseActivity;
import com.ddrx.ddrxfront.Model.CardModelIntro;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;

import java.util.List;

import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import okhttp3.OkHttpClient;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class AddNewWarehouseController {
    private Context context;
    private Handler handler;
    private OkHttpClient client;

    public void getAllModels() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
                List<CardModelIntro> introList = db.getCardModelDAO().queryAllCardModelIntro();
                Message message = new Message();
                message.what = AddNewWarehouseActivity.GET_MODEL;
                message.obj = introList;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void uploadCover(Bitmap bitmap) {

    }

    public AddNewWarehouseController(Context context, Handler handler) {
        client = OKHttpClientWrapper.Companion.getInstance(context);
        this.context = context;
        this.handler = handler;
    }


}
