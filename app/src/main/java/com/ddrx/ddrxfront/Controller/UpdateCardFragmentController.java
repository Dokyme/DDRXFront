package com.ddrx.ddrxfront.Controller;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.CardFragment;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.CardWarehouseIntro;
import com.ddrx.ddrxfront.Utilities.*;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by vincentshaw on 2018/3/23.
 */

public class UpdateCardFragmentController {
    private Handler handler;
    private Context context;

    public UpdateCardFragmentController(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
    }

    public void getDataListFromDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
            List<CardWarehouseIntro> db_warehouse_intro_list = db.getCardWarehouseDAO().queryAllCardWarehouseIntro();
            List<Long> ids = db.getCardWarehouseDAO().queryAllCW_ID();
            if(!db_warehouse_intro_list.isEmpty()){
                Message message = new Message();
                message.what = CardFragment.UPDATE_UI;
                message.obj = db_warehouse_intro_list;
                handler.sendMessage(message);
            }
            else{
                Message message = new Message();
                message.what = CardFragment.EMPTY_LIST;
                handler.sendMessage(message);
            }
            }
        }).start();
    }
}
