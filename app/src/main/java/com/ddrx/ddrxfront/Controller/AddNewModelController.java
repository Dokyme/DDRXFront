package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.os.Handler;

import com.ddrx.ddrxfront.AddNewModelActivity;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Utilities.MacAddressUtil;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import com.ddrx.ddrxfront.Utilities.ParseBackDataPack;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class AddNewModelController {
    private OkHttpClient client;
    private Handler handler;
    private Context context;
    private final String HOST_NAME = "localhost:3000";
    private final String UPDATE_CARD_MODEL_URL = HOST_NAME + "/template/upload";

    public AddNewModelController(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
        client = OKHttpClientWrapper.getInstance();
    }

    public void uploadModel(final CardModel new_model){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MacAddressUtil mac = new MacAddressUtil(context);
                RequestBody formBody = new FormBody.Builder().add("_MAC", mac.getMacAddr())
                                            .add("_TEMPLATE_ID", "0")
                                            .add("_NAME", new_model.getCT_name())
                                            .add("_CONTEXT", new_model.getCT_context())
                                            .add("_PRIVILEGE", "0")
                                            .add("_BRIEF", new_model.getCT_brief())
                                            .add("_PRICE", "0")
                                            .add("_TYPE", String.valueOf(new_model.getCT_type())).build();
                Request request = new Request.Builder()
                                        .url(UPDATE_CARD_MODEL_URL)
                                        .post(formBody)
                                        .build();
                Response response = null;
                ParseBackDataPack parser = null;
                try{
                    response = client.newCall(request).execute();
                    parser = new ParseBackDataPack(response.body().string());
                }catch(IOException e){
                    Log.e("Network Error", "uploadModel@AddNewModelController");
                    Message message = new Message();
                    message.what = AddNewModelActivity.NETWORK_ERROR;
                    handler.sendMessage(message);
                    return;
                }
                if(parser.getCode() == 0){
                    long CT_id = 0;
                    try{
                        CT_id = parser.getBody().getJSONObject(0).getLong("CT_id");
                    }catch(JSONException e){
                        Log.e("JSON Format Error", "uploadModel@AddNewModelController");
                        Message message = new Message();
                        message.what = AddNewModelActivity.NETWORK_ERROR;
                        handler.sendMessage(message);
                        return;
                    }
                    new_model.setCT_id(CT_id);
                    MemoryMasterDatabase db = MemoryMasterDatabase.getInstance();
                    db.getCardModelDAO().insertSingleCardModel(new_model);
                    Message message = new Message();
                    message.what = AddNewModelActivity.NETWORK_PASS;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
