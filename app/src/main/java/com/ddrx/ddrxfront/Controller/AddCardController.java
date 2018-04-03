package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.AddCardActivity;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.Model;
import com.ddrx.ddrxfront.Model.ModelInput;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by vincentshaw on 2018/3/31.
 */

public class AddCardController {
    private Handler handler;
    private Context context;

    public AddCardController(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
    }

    public void getModelInputs(final long CT_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
                String content = db.getCardModelDAO().queryCardModelContextById(CT_id);
                if(content.isEmpty()){
                    Message message = new Message();
                    message.what = AddCardActivity.NO_CONTENT;
                    handler.sendMessage(message);
                }
                else{
                    Model model = new Model(content);
                    Message message = new Message();
                    message.what = AddCardActivity.GET_MODEL_INFO;
                    message.obj = model;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
