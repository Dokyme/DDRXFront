package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.MemoryCard;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.TrainingRecord;
import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Utilities.JSONToEntity;
import com.ddrx.ddrxfront.Utilities.MacAddressUtil;
import com.ddrx.ddrxfront.Utilities.ParseBackDataPack;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitUpdateDatabase {
    private static final String HOST_NAME = "localhost:3000";
    private static final String GET_USER_ALL_CW_INFO_URL = HOST_NAME + "/warehouse/down_list";
    private static final String GET_COVER_URL = HOST_NAME + "/warehouse/down_list";
    private static final String GET_USER_ALL_CT_INFO_URL = HOST_NAME + "/";
    private static final String GET_USER_ALL_TRAINING_RECORD = HOST_NAME + "/down_record";
    public static final int NETWORK_ERROR = 1;

    public InitUpdateDatabase(){}

    public static void updateCardWarehouseDatabase(final Context context, final Handler handler, final OkHttpClient client){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = getResponse(context, GET_USER_ALL_CW_INFO_URL, client, handler);
                if(response != null) {
                    if (response.isSuccessful()) {
                        String response_data;
                        int return_code;
                        try {
                            response_data = response.body().string();
                        } catch (IOException e) {
                            Log.e("Network Error", "getAllUserWarehouseInfo");
                            sendMessageToUI(handler, NETWORK_ERROR);
                            return;
                        }
                        ParseBackDataPack parser = new ParseBackDataPack(response_data);
                        return_code = parser.getCode();
                        if (return_code == 0) {
                            List<CardWarehouse> warehouseList = JSONToEntity.getCardWarehouseList(parser.getBody());
                            List<String> cover_url_list = downloadCovers(handler, client, context, warehouseList);
                            if (warehouseList == null || cover_url_list == null || warehouseList.size() != cover_url_list.size()) {
                                sendMessageToUI(handler, NETWORK_ERROR);
                            } else {
                                for (int i = 0; i < warehouseList.size(); i++) {
                                    CardWarehouse warehouse = warehouseList.get(i);
                                    String cover_url = cover_url_list.get(i);
                                    warehouse.setCW_cover_url(cover_url);
                                }
                                updateCWDatabase(context, warehouseList);
                            }
                        } else {
                            sendMessageToUI(handler, NETWORK_ERROR);
                        }
                    }
                }
            }
        }).start();
    }

    public static void updateCardModelDatabase(final Context context, final Handler handler, final OkHttpClient client){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = getResponse(context, GET_USER_ALL_CT_INFO_URL, client, handler);
                if(response != null) {
                    if (response.isSuccessful()) {
                        String response_data;
                        int return_code;
                        try {
                            response_data = response.body().string();
                        } catch (IOException e) {
                            Log.e("Network Error", "updateCardModelDatabase@InitUpdateDatabase");
                            sendMessageToUI(handler, NETWORK_ERROR);
                            return;
                        }
                        ParseBackDataPack parser =  new ParseBackDataPack(response_data);
                        return_code = parser.getCode();
                        if (return_code == 0) {
                            List<CardModel> cardModelList = JSONToEntity.getCardModelList(parser.getBody());
                            if (cardModelList == null) {
                                sendMessageToUI(handler, NETWORK_ERROR);
                            }else {
                                updateCTDatabase(context, cardModelList);
                            }
                        } else {
                            sendMessageToUI(handler, NETWORK_ERROR);
                        }
                    } else {
                        sendMessageToUI(handler, NETWORK_ERROR);
                    }
                }
            }
        }).start();
    }

    public static void updateTrainingRecordDatabase(final Context context, final Handler handler, final OkHttpClient client){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = getResponse(context, GET_USER_ALL_TRAINING_RECORD, client, handler);
                if(response != null) {
                    if (response.isSuccessful()) {
                        String response_data;
                        int return_code;
                        try {
                            response_data = response.body().string();
                        } catch (IOException e) {
                            Log.e("Network Error", "updateTrainingRecordDatabase@InitUpdateDatabase");
                            sendMessageToUI(handler, NETWORK_ERROR);
                            return;
                        }
                        ParseBackDataPack parser =  new ParseBackDataPack(response_data);
                        return_code = parser.getCode();
                        if (return_code == 0) {
                            List<TrainingRecord> trainingRecordList = JSONToEntity.getTrainingRecordList(parser.getBody());
                            if (trainingRecordList == null) {
                                sendMessageToUI(handler, NETWORK_ERROR);
                            }else {
                                updateTRDatabase(context, trainingRecordList);
                            }
                        } else {
                            sendMessageToUI(handler, NETWORK_ERROR);
                        }
                    } else {
                        sendMessageToUI(handler, NETWORK_ERROR);
                    }
                }
            }
        }).start();
    }

    public static void updateMemoryCardDatabase(final Context context, final Handler handler, final OkHttpClient client){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = getResponse(context, GET_USER_ALL_TRAINING_RECORD, client, handler);
                if(response != null) {
                    if (response.isSuccessful()) {
                        String response_data;
                        int return_code;
                        try {
                            response_data = response.body().string();
                        } catch (IOException e) {
                            Log.e("Network Error", "updateMemoryCardDatabase@InitUpdateDatabase");
                            sendMessageToUI(handler, NETWORK_ERROR);
                            return;
                        }
                        ParseBackDataPack parser =  new ParseBackDataPack(response_data);
                        return_code = parser.getCode();
                        if (return_code == 0) {
                            List<MemoryCard> memoryCardList = JSONToEntity.getMemoryCardList(parser.getBody());
                            if (memoryCardList == null) {
                                sendMessageToUI(handler, NETWORK_ERROR);
                            }else {
                                updateMCDatabase(context, memoryCardList);
                            }
                        } else {
                            sendMessageToUI(handler, NETWORK_ERROR);
                        }
                    } else {
                        sendMessageToUI(handler, NETWORK_ERROR);
                    }
                }
            }
        }).start();
    }

    private static Response getResponse(Context context, String url, OkHttpClient client, Handler handler){
        MacAddressUtil mac = new MacAddressUtil(context);
        RequestBody formBody = new FormBody.Builder()
                .add("_MAC", String.valueOf(mac.getMacAddr())).build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
        } catch(IOException e){
            Log.e("Network Error", "getAllUserWarehouseInfo");
            sendMessageToUI(handler, NETWORK_ERROR);
            return null;
        }
        return response;
    }

    private static void sendMessageToUI(Handler handler, int message_type){
        Message message = new Message();
        message.what = message_type;
        handler.sendMessage(message);
    }

    private static void updateCWDatabase(Context context, List<CardWarehouse> warehouseList){
        MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
        HashSet<Long> warehouse_ids = new HashSet<>(db.getCardWarehouseDAO().queryAllCW_ID());
        List<Long> now_warehouse_ids = new LinkedList<>();
        for(CardWarehouse warehouse: warehouseList){
            if(warehouse_ids.contains(warehouse.getCW_id()))
                db.getCardWarehouseDAO().updateCardWarehouse(warehouse);
            else
                db.getCardWarehouseDAO().insertSingleCardWarehouse(warehouse);
            now_warehouse_ids.add(warehouse.getCW_id());
        }
        List<Long> delete_warehouse_ids = db.getCardWarehouseDAO().queryAllNotExistCW_ID(now_warehouse_ids);
        db.getCardWarehouseDAO().deleteCardWarehouseById(delete_warehouse_ids);
        db.close();
    }

    private static List<String> downloadCovers(Handler handler, OkHttpClient client, Context context, List<CardWarehouse> warehouse_list){
        List<String> coverLists = new ArrayList<>();
        for(int i = 0; i < warehouse_list.size(); i++){
            long CW_id = warehouse_list.get(i).getCW_id();
            Request request = new Request.Builder().url(GET_COVER_URL + "warehouse_id_" + String.valueOf(CW_id) + "/cover.jpg").get().build();
            Response response = null;
            try{
                response = client.newCall(request).execute();
            } catch(IOException e){
                Log.e("Network Error", "downloadCovers@InitUpdateDatabase");
                sendMessageToUI(handler, NETWORK_ERROR);
                return null;
            }
            if(response.isSuccessful()){
                FileOutputStream out = null;
                BufferedWriter writer = null;
                try{
                    out = context.openFileOutput(String.valueOf(CW_id) + "_cover.jpg", Context.MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(response.body().string());
                } catch(IOException e){
                    Log.e("File Error", "downloadCovers@InitUpdateDatabase");
                    sendMessageToUI(handler, NETWORK_ERROR);
                    return null;
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch(IOException e){
                        Log.e("File Error", "downloadCovers@InitUpdateDatabase");
                        sendMessageToUI(handler, NETWORK_ERROR);
                        return null;
                    }
                }
            }
            else{
                Log.e("Network Error", "downloadCovers@InitUpdateDatabase");
                sendMessageToUI(handler, NETWORK_ERROR);
                return null;
            }
        }
        return coverLists;
    }

    private static void updateCTDatabase(final Context context, List<CardModel> modelList){
        MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
        HashSet<Long> model_ids = new HashSet<>(db.getCardModelDAO().queryAllCT_ID());
        List<Long> now_model_ids = new LinkedList<>();
        for(CardModel model: modelList){
            if(model_ids.contains(model.getCT_name()))
                db.getCardModelDAO().updateCardModels(model);
            else
                db.getCardModelDAO().insertSingleCardModel(model);
            now_model_ids.add(model.getCT_id());
        }
        List<Long> delete_model_ids = db.getCardModelDAO().queryAllNotExistCT_ID(now_model_ids);
        db.getCardModelDAO().deleteCardModelById(delete_model_ids);
        db.close();
    }

    private static void updateTRDatabase(final Context context, List<TrainingRecord> trainingRecordList){
        MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
        db.getTrainingRecordDAO().deleteAllTrainingRecord();
        db.getTrainingRecordDAO().insertIntoTrainingRecord(trainingRecordList);
    }

    private static void updateMCDatabase(final Context context, List<MemoryCard> memoryCardList){
        MemoryMasterDatabase db = MemoryMasterDatabase.getInstance(context);
        db.getMemoryCardDAO().deleteAllMemoryCard();
        db.getMemoryCardDAO().insetIntoMemoryCard(memoryCardList);
    }
}
