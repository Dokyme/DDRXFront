package com.ddrx.ddrxfront.Utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vincentshaw on 2018/3/26.
 */

public class SaveBitmapFromNetwork {
    public static List<String> downloadPictureSync(List<String> urls, List<String> save_names, OkHttpClient client, Context context){
        if(urls.size() != save_names.size())
            return urls;

        List<String> failed_names = new ArrayList<>();
        for(int i = 0; i < urls.size(); i++){
            String url = urls.get(i);
            String save_name = save_names.get(i);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = null;
            try{
                response = client.newCall(request).execute();
                ParseBackDataPack parser = new ParseBackDataPack(response.body().string());
                if(parser.getCode() == 0){
                    FileOutputStream out = null;
                    BufferedWriter writer = null;
                    try{
                        out = context.openFileOutput(save_name, Context.MODE_PRIVATE);
                        writer = new BufferedWriter(new OutputStreamWriter(out));
                        writer.write(parser.getBody().getJSONObject(0).getString("pic"));
                    } catch(IOException e){
                        Log.e("File Open Error", "downloadPictureSync@DownloadPicture");
                        failed_names.add(save_name);
                    } catch(JSONException e){
                        Log.e("JSON Format Error", "downloadPictureSync@DownloadPicture");
                        failed_names.add(save_name);
                    }
                    finally {
                        try{
                            if (writer != null){
                                writer.close();
                            }
                        } catch (IOException e1){
                            Log.e("File Close Error", "downloadPictureSync@DownloadPicture");
                            failed_names.add(save_name);
                        }
                    }
                }
                else{
                    failed_names.add(save_name);
                }
            }catch(IOException e){
                Log.e("Network error", "downloadPictureSync@DownloadPicture");
                failed_names.add(save_name);
            }
        }
        return failed_names;
    }
}
