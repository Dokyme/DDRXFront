package com.ddrx.ddrxfront.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ddrx.ddrxfront.Model.MemoryMasterDatabase;
import com.ddrx.ddrxfront.Model.NeedTrainingItem;
import com.ddrx.ddrxfront.Utilities.UserInfoPreference;

import java.util.List;

/**
 * Created by dokym on 2018/3/29.
 */

public class NeedTrainingController {

    private Handler handler;
    private Context context;
    private UserInfoPreference preference;

    public static final int UPDATE_UI = 0;
    public static final int EMPTY_LIST = 1;

    public NeedTrainingController(Handler handler, Context context) {
        this.context = context;
        this.handler = handler;
        preference = new UserInfoPreference(context);
    }

    public void updateNeedTrainingFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MemoryMasterDatabase database = MemoryMasterDatabase.getInstance(context);
                List<NeedTrainingItem> needTrainingItemList = database.getCardTrainingRecordDAO().queryNeedTrainingsByUserId(preference.getUserInfo().getId());
                Message message = new Message();
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
                if (!needTrainingItemList.isEmpty()) {
                    message.what = UPDATE_UI;
                    message.obj = needTrainingItemList;
                } else {
                    message.what = EMPTY_LIST;
                }
                handler.sendMessage(message);
            }
        }).start();
    }
}
