package com.ddrx.ddrxfront;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class CardWarehouseDetailActivity extends AppCompatActivity {

    private int CW_id;
    public static final int UPDATE_DATA = 1;
    private CardWarehouseDetailActivity.MyHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_warehouse_detail);
        Intent intent = getIntent();
        CW_id = intent.getIntExtra("CW_id");
        handler = new MyHandler(this);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<CardWarehouseDetailActivity> mActivity;

        public MyHandler(CardWarehouseDetailActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_DATA:
                    break;
            }
        }
    }
}
