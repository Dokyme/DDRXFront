package com.ddrx.ddrxfront;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddrx.ddrxfront.Model.Model;

public class CardModelDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int model_type = intent.getIntExtra("type", 0);
        String model_name = intent.getStringExtra("name");
        String creator_name = intent.getStringExtra("creator_name");
        String add_time = intent.getStringExtra("add_time");
        String brief = intent.getStringExtra("brief");
        String content = intent.getStringExtra("content");
        ImageView cover = findViewById(R.id.CTDetail_cover);
        cover.setImageResource(Model.TYPE[model_type]);
        TextView name = findViewById(R.id.CTDetail_name);
        name.setText(model_name);
        TextView creator = findViewById(R.id.CTDetail_author);
        creator.setText("上传人：" + creator_name);
        TextView addTime = findViewById(R.id.CTDetail_add_time);
        addTime.setText("添加时间：" + add_time);
        TextView brief_view = findViewById(R.id.CTDetail_description);
        brief_view.setText(brief);
        TextView entry_num = findViewById(R.id.CTDetail_entryNum);
        Model model = new Model(content);
        entry_num.setText("模版内容     共 " + String.valueOf(model.getEntrys().size()) + " 字段");
        setContentView(R.layout.activity_card_model_detail);
    }
}
