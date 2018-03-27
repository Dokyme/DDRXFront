package com.ddrx.ddrxfront.Controller;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.CardFragment;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.CardWarehouseDatabase;
import com.ddrx.ddrxfront.Model.CardWarehouseIntro;
import com.ddrx.ddrxfront.Utilities.JSONToEntity;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;
import com.ddrx.ddrxfront.Utilities.ParseBackDataPack;
import com.ddrx.ddrxfront.Utilities.SaveBitmapFromNetwork;
import com.ddrx.ddrxfront.Utilities.UserInfoPreference;

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
    private OkHttpClient client;
    private final String GET_USER_ALL_CW_INFO_URL = "localhost:3000/abc";
    private final String GET_CW_COVER_URL = "localhost:3000/aaa";
    private final String GET_CW_COVER_TIMESTAMP_URL = "localhost:3000/aaad";
    private Context context;

    public UpdateCardFragmentController(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
        client = OKHttpClientWrapper.getInstance();
    }

    public void getDataListFromNetwork(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = getResponse(GET_USER_ALL_CW_INFO_URL);
                if(response != null) {
                    if (response.isSuccessful()) {
                        String response_data;
                        int return_code;
                        try {
                            response_data = response.body().string();
                        } catch (IOException e) {
                            Log.e("Network Error", "getAllUserWarehouseInfo");
                            sendMessageToUI(CardFragment.NETWORK_ERROR);
                            return;
                        }
                        ParseBackDataPack parser =  new ParseBackDataPack(response_data);
                        return_code = parser.getCode();
                        if (return_code == 0) {
                            List<CardWarehouse> warehouseList = JSONToEntity.getCardWarehouseList(parser.getBody());
                            if (warehouseList == null) {
                                sendMessageToUI(CardFragment.NETWORK_ERROR);
                            } else if (warehouseList.isEmpty()) {
                                sendMessageToUI(CardFragment.EMPTY_LIST);
                            } else {
                                updateDatabase(warehouseList);
                                Message message = new Message();
                                message.what = CardFragment.UPDATE_UI;
                                message.obj = get_up_to_date_intros();
                                handler.sendMessage(message);
                            }
                        } else {
                            sendMessageToUI(CardFragment.NETWORK_ERROR);
                        }
                    } else {
                        sendMessageToUI(CardFragment.NETWORK_ERROR);
                    }
                }
            }
        });
    }

    public void getDataListFromDB(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            CardWarehouseDatabase db = CardWarehouseDatabase.getInstance(context);
            List<CardWarehouseIntro> db_warehouse_intro_list = db.getCardWarehouseDAO().queryAllCardWarehouseIntro();
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

    private void updateDatabase(List<CardWarehouse> warehouseList){
        CardWarehouseDatabase db = CardWarehouseDatabase.getInstance(context);
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

    private Response getResponse(String url){
        UserInfoPreference preference = new UserInfoPreference(context);
        RequestBody formBody = new FormBody.Builder()
                .add("U_id", String.valueOf(preference.getUserInfo().getId())).build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
        } catch(IOException e){
            Log.e("Network Error", "getAllUserWarehouseInfo");
            sendMessageToUI(CardFragment.NETWORK_ERROR);
            return null;
        }
        return response;
    }

    private void downloadPictures(List<CardWarehouse> warehouseList){
        Response response = getResponse(GET_CW_COVER_TIMESTAMP_URL);
        if(response != null){
            if(response.isSuccessful()){
                String response_data;
                int return_code;
                try {
                    response_data = response.body().string();
                } catch (IOException e) {
                    Log.e("Network Error", "downloadPictures@UpdateCardFragmentController");
                    sendMessageToUI(CardFragment.NETWORK_ERROR);
                    return;
                }
                ParseBackDataPack parser =  new ParseBackDataPack(response_data);
                return_code = parser.getCode();
                Map<Long, Integer> timestamps;
                if(return_code == 0){
                    try{
                        timestamps = JSONToEntity.getTimestamps(parser.getBody());
                    } catch (JSONException e){
                        Log.e("JSON Format Error", "downloadPictures@UpdateCardFragmentController");
                        sendMessageToUI(CardFragment.NETWORK_ERROR);
                        return;
                    }
                    List<Long> download_cover_id = findAllDownloadCoverWID(timestamps);
                    List<String> save_names = new ArrayList<>();
                    List<String> get_urls = new ArrayList<>();
                    for(Long CW_id: download_cover_id){
                        save_names.add("CW_" + String.valueOf(CW_id) + "_" + String.valueOf(timestamps.get(CW_id)));
                        get_urls.add(GET_CW_COVER_URL + "?CW_ID=" + String.valueOf(CW_id));

                    }
                    List<String> error_File_names = SaveBitmapFromNetwork.downloadPictureSync(get_urls, save_names, client, context);
                }
            }
            else{
                sendMessageToUI(CardFragment.NETWORK_ERROR);
            }
        }
    }

    private List<Long> findAllDownloadCoverWID(Map<Long, Integer> timestamps){
        List<Long> result = new ArrayList<>();
        String[] file_names = context.fileList();
        Set<Long> now_ids = new HashSet<>();
        for(String file_name: file_names){
            String[] info = file_name.split("_");
            if(info[0].equals("CW")){
                if(timestamps.containsKey(Long.valueOf(info[1]))){
                    if(!timestamps.get(Long.valueOf(info[1])).equals(Integer.valueOf(info[2]))){
                        result.add(Long.valueOf(info[1]));
                    }
                    now_ids.add(Long.valueOf(info[1]));
                }
                else{
                    context.deleteFile(file_name);
                }
            }
        }
        Iterator iter = timestamps.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            if(!now_ids.contains(entry.getKey()))
                result.add((Long)entry.getKey());
        }
        return result;
    }

    private List<CardWarehouseIntro> get_up_to_date_intros(){
        CardWarehouseDatabase db = CardWarehouseDatabase.getInstance(context);
        List<CardWarehouseIntro> introList = db.getCardWarehouseDAO().queryAllCardWarehouseIntro();
        db.close();
        return introList;
    }

    private void sendMessageToUI(int message_type){
        Message message = new Message();
        message.what = message_type;
        handler.sendMessage(message);
    }

}
