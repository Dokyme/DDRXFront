package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.AddNewWarehouseActivity;
import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.MemoryCard;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.ddrx.ddrxfront.Utilities.MacAddressUtil;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import com.ddrx.ddrxfront.Utilities.ParseBackDataPack;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincentshaw on 2018/3/30.
 */

public class AddNewWarehouseController {
    private Context context;
    private Handler handler;
    private OkHttpClient client;
    private final String HOST_NAME = "http://120.79.35.160:3000/api";
    private final String UPDATE_CARD_WAREHOUSE_URL = HOST_NAME + "/warehouse/upload";
    private final String UPDATE_COVER_URL = HOST_NAME + "/warehouse/upload_cover";
    private final String UPDATE_CARD_URL = HOST_NAME + "/card/upload";

    public void getAllModels() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
                List<CardModel> modelList = db.getCardModelDAO().queryAllCardModels();
                Message message = new Message();
                message.what = AddNewWarehouseActivity.GET_MODEL;
                message.obj = modelList;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void uploadWarehouse(final CardWarehouse info, final Bitmap cover, final List<Card> cards) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MacAddressUtil mac = new MacAddressUtil(context);
                RequestBody formBody = new FormBody.Builder()
                        .add("_MAC", mac.getMacAddr())
                        .add("_WAREHOUSE_ID", String.valueOf(info.getCW_id()))
                        .add("_TEMPLATE_ID", String.valueOf(info.getCT_id()))
                        .add("_NAME", info.getCW_name())
                        .add("_PRIVILEGE", "0")
                        .add("_ABSTRACT", info.getCW_abstract())
                        .add("_DETAIL", info.getCW_detail())
                        .add("_PRICE", "0")
                        .add("_TOTAL", String.valueOf(info.getCW_training())).build();
                Request request = new Request.Builder()
                        .url(UPDATE_CARD_WAREHOUSE_URL)
                        .post(formBody)
                        .build();
                Response response = null;
                ParseBackDataPack parser = null;
                try {
                    response = client.newCall(request).execute();
                    parser = new ParseBackDataPack(response.body().string());
                } catch (IOException e) {
                    Log.e("Network Error", "uploadModel@AddNewModelController");
                    sendFailedMessage();
                    return;
                }
                if (response.isSuccessful()) {
                    if (parser.getCode() == 0) {
                        long CW_id = 0;
                        try {
                            CW_id = parser.getBody().getJSONObject(0).getLong("CW_id");
                        } catch (JSONException e) {
                            Log.e("JSON Format Error", "uploadWarehouse@AddNewWarehouseController92");
                            sendFailedMessage();
                            return;
                        }
                        info.setCW_id(CW_id);
                        MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
                        info.setCT_name(db.getCardModelDAO().queryCardModelById(info.getCT_id()).getCT_name());
                        db.getCardWarehouseDAO().insertCardWarehouse(info);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cover.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        FileOutputStream out = null;
                        Log.d("dokyme", "cover.compress(Bitmap.CompressFormat.PNG, 100, baos);");
                        byte[] datas = baos.toByteArray();
                        Log.d("dokyme", "byte[] datas = baos.toByteArray();");
                        try {
                            out = context.openFileOutput("CW_" + String.valueOf(info.getCW_id()) + ".jpg", Context.MODE_PRIVATE);
                            out.write(datas);
                        } catch (IOException e) {
                            Log.e("Write Error", "uploadWarehouse@AddNewWarehouseController110");
                            sendFailedMessage();
                            return;
                        } finally {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    Log.e("Write Error", "uploadWarehouse@AddNewWarehouseController118");
                                    sendFailedMessage();
                                    return;
                                }
                            }
                        }
//                        File file = new File(c"CW_" + String.valueOf(info.getCW_id()) + ".jpg");
                        File file = context.getFileStreamPath("CW_" + info.getCW_id() + ".jpg");
                        RequestBody formBody2 = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addPart(Headers.of("Content-Disposition", "form-data; name=\"_MAC\""), RequestBody.create(null, mac.getMacAddr()))
                                .addPart(Headers.of("Content-Disposition", "form-data; name=\"_WAREHOUSE_ID\""), RequestBody.create(null, String.valueOf(info.getCW_id())))
                                .addPart(Headers.of("Content-Disposition", "form-data; name=\"cover\"; filename=\"CW_" + info.getCW_id() + ".jpg\""), RequestBody.create(MediaType.parse("image/jpeg"), file))
                                .build();
//                                .add("_MAC", mac.getMacAddr())
//                                .add("_WAREHOUSE_ID", String.valueOf(info.getCW_id()))
//                                .add("cover", new String(datas)).build();
                        Log.d("dokyme", ".add(\"cover\", new String(datas)).build();e");
                        Request request1 = new Request.Builder()
                                .url(UPDATE_COVER_URL)
                                .post(formBody2)
                                .build();
                        Response response1 = null;
                        ParseBackDataPack parser1 = null;
                        try {
                            response1 = client.newCall(request1).execute();
                            parser1 = new ParseBackDataPack(response1.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController145");
                            sendFailedMessage();
                            return;
                        }
                        if (response1.isSuccessful()) {
                            if (parser1.getCode() == 0) {
                                Response response2 = null;
                                for (Card card : cards) {
                                    String card_content = card.toString();
                                    RequestBody formbody3 = new FormBody.Builder()
                                            .add("_MAC", mac.getMacAddr())
                                            .add("_WAREHOUSE_ID", String.valueOf(CW_id))
                                            .add("_CONTEXT", card_content).build();
                                    Request request2 = new Request.Builder()
                                            .url(UPDATE_CARD_URL)
                                            .post(formbody3)
                                            .build();
                                    ParseBackDataPack parser2 = null;
                                    try {
                                        response2 = client.newCall(request2).execute();
                                        parser2 = new ParseBackDataPack(response2.body().string());
                                    } catch (IOException e) {
                                        Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController167");
                                        sendFailedMessage();
                                        return;
                                    }
                                    if (response2.isSuccessful()) {
                                        if (parser2.getCode() == 0) {
                                            try {
                                                long CC_id = parser2.getBody().getJSONObject(0).getLong("CC_id");
                                                MemoryCard new_memory_card = new MemoryCard(CC_id, CW_id, card_content);
                                                db.getMemoryCardDAO().insertIntoMemoryCard(new_memory_card);
                                            } catch (JSONException e) {
                                                Log.e("JSON Format Error", "uploadWarehouse@AddNewWarehouseController178");
                                                sendFailedMessage();
                                                return;
                                            }
                                        } else {
                                            Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController183");
                                            sendFailedMessage();
                                            return;
                                        }
                                    } else {
                                        Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController188");
                                        sendFailedMessage();
                                        return;
                                    }
                                }
                            } else {
                                Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController194");
                                sendFailedMessage();
                                return;
                            }
                        } else {
                            Log.e("Network Error", "uploadWarehouse@AddNewWarehouseController199");
                            sendFailedMessage();
                            return;
                        }
                        Message message = new Message();
                        message.what = AddNewWarehouseActivity.SUCCESS_UPLOAD;
                        handler.sendMessage(message);
                    } else {
                        sendFailedMessage();
                        return;
                    }
                } else {
                    sendFailedMessage();
                    return;
                }
            }
        }).start();
    }

    public AddNewWarehouseController(Context context, Handler handler) {
        client = OKHttpClientWrapper.Companion.getInstance(context);
        this.context = context;
        this.handler = handler;
    }

    private void sendFailedMessage() {
        Message message = new Message();
        message.what = AddNewWarehouseActivity.FAILED_UPLOAD;
        handler.sendMessage(message);
    }


}
