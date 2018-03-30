package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.ShowCardActivity;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.OkHttpClient;

/**
 * Created by vincentshaw on 2018/3/27.
 */

public class ShowCardController {
    private Handler handler;
    private Context context;
    private OkHttpClient client;
    private long CW_id;

    public ShowCardController(Handler handler, Context context, long CW_id){
        this.handler = handler;
        this.context = context;
        this.CW_id = CW_id;
        client = OKHttpClientWrapper.getInstance();
    }

    public void getAllCardByCWid(final long CW_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Card> result = new ArrayList<>();
                String[] file_list = context.fileList();
                for(String file_name: file_list){
                    if(file_name.split("_")[0].equals("CD")){
                        if(file_name.split("_")[1].equals(String.valueOf(CW_id))){
                            String read_json = readStringFromFile(file_name);
                            if(read_json != null)
                                result.add(new Card(read_json));
                        }
                    }
                }
                Message message = new Message();
                message.what = ShowCardActivity.GET_MEMORY_CARD_FRAGMENT;
                message.obj = result;
            }
        }).start();

    }

    public void updateEditCard(){

    }

    private String readStringFromFile(String path){
        String result = null;
        FileInputStream in = null;
        BufferedReader reader = null;
        try{
            in = context.openFileInput(path);
            reader = new BufferedReader(new InputStreamReader(in));
            result = reader.readLine();
        } catch(IOException e){
            Log.e("FileInputError", "readStringFromFile@ShowCardController");
            return null;
        } finally {
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                Log.e("FileCloseError", "readStringFromFile@ShowCardController");
            }
        }
        return result;
    }
}
