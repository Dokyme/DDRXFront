package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.ModelFragment;

import java.util.List;


/**
 * Created by vincentshaw on 2018/3/23.
 */

public class UpdateModelFragmentController {
    private Handler handler;
    private Context context;

    public UpdateModelFragmentController(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
    }

    public void getModelListFromDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
                List<CardModel> db_model_list = db.getCardModelDAO().queryAllCardModels();
                if(!db_model_list.isEmpty()){
                    Message message = new Message();
                    message.what = ModelFragment.UPDATE_UI;
                    message.obj = db_model_list;
                    handler.sendMessage(message);
                }
                else{
                    Message message = new Message();
                    message.what = ModelFragment.EMPTY_LIST;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

}
