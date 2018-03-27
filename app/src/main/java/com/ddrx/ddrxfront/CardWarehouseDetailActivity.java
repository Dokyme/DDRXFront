package com.ddrx.ddrxfront;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Utilities.GenericPair;

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
        CW_id = intent.getIntExtra("CW_id", 0);
        handler = new MyHandler(this);
        Toolbar toolbar = findViewById(R.id.CWDetail_toolbar);
        setSupportActionBar(toolbar);
        ImageView show_cards = findViewById(R.id.CWDetail_showCards);
        show_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button delete_warehouse = findViewById(R.id.CWDetail_delete);
        delete_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private static class MyHandler extends Handler {

        private final WeakReference<CardWarehouseDetailActivity> mActivity;

        public MyHandler(CardWarehouseDetailActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_DATA:{
                    GenericPair<String, CardWarehouse> obj = (GenericPair<String, CardWarehouse>) msg.obj;
                    String latest_training_time = obj.getFirst();
                    CardWarehouse warehouse = obj.getSecond();

                    TextView warehouse_name = mActivity.get().findViewById(R.id.CWDetail_name);
                    warehouse_name.setText(warehouse.getCW_name());

                    TextView warehouse_creator = mActivity.get().findViewById(R.id.CWDetail_author);
                    warehouse_creator.setText("上传人：" + warehouse.getU_nick());

                    TextView warehouse_add_time = mActivity.get().findViewById(R.id.CWDetail_add_time);
                    warehouse_add_time.setText("添加时间：" + warehouse.getUCW_time());

                    ImageView warehouse_cover = mActivity.get().findViewById(R.id.CWDetail_cover);
                    warehouse_cover.setImageBitmap(BitmapFactory.decodeFile(warehouse.getCW_cover_url()));

                    TextView warehouse_description = mActivity.get().findViewById(R.id.CWDetail_description);
                    warehouse_description.setText(warehouse.getCW_detail());

                    TextView warehouse_card_num = mActivity.get().findViewById(R.id.CWDetail_cardNum);
                    warehouse_card_num.setText("卡片预览    " + String.valueOf(warehouse.getCW_card_num()) + " 张卡片");

                    TextView training_schedule = mActivity.get().findViewById(R.id.CWDetail_schedule);
                    training_schedule.setText("每张卡片目标训练次数：");

                    TextView lateset_time = mActivity.get().findViewById(R.id.CWDetail_last_training_time);
                    lateset_time.setText("上一次训练于  " + latest_training_time);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.warehouse_detail_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit_warehouse:{
                break;
            }
        }
        return true;
    }
}
