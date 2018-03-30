package com.ddrx.ddrxfront;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class AddNewModelActivity extends AppCompatActivity {
    private List<CardModel> cardModelList;
    private ImageView add_new_model_entry;
    private Button commit_model;
    private LinearLayout entry_layout;
    private HashMap<Integer, LinearLayout> entry_manager;
    private Context context;
    private int max_count = 0;
    private int CT_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_model);
        context = this;
        add_new_model_entry = findViewById(R.id.ANM_add_new_stuff);
        commit_model = findViewById(R.id.ANM_commit);
        entry_layout = findViewById(R.id.ANM_entry_layout);
        Spinner type_tag = findViewById(R.id.ANM_type);
        type_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CT_type = position;
            }
        });
        add_new_model_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout entry = (LinearLayout) View.inflate(context, R.layout.add_new_model_record, entry_layout);
                ImageView delete_btn = entry.findViewById(R.id.delete_entry);
                delete_btn.setTag(max_count);
                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int item_id = (int) v.getTag();
                        LinearLayout delete_entry = entry_manager.get(item_id);
                        entry_layout.removeView(delete_entry);
                        entry_manager.remove(item_id);
                    }
                });
                entry_manager.put(max_count++, entry);
                entry_layout.addView(entry);
                Intent intent = new Intent(context, ModelEntryActivity.class);
                intent.putExtra("has_content", false);
                intent.putExtra("entry_id", max_count - 1);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String CT_content = getModelJsonFile(data.getStringExtra("name"), data.getIntExtra("type", 0),
                                                         data.getBooleanExtra("name_visible", true), data.getBooleanExtra("only", true),
                                                         data.getIntExtra("align", 0), data.getIntExtra("size", 0),
                                                         data.getBooleanExtra("trainable", true), data.getBooleanExtra("keyword", true));
                    EditText name_tag = findViewById(R.id.ANM_name);
                    String CT_name = name_tag.getText().toString();
                    EditText brief_tag = findViewById(R.id.ANM_intro);
                    String CT_brief = brief_tag.getText().toString();
                    UserInfo userInfo = new UserInfo(context);
                    CardModel new_model = new CardModel(0, userInfo.getNickname(), CT_name, userInfo.getId(), CT_brief, CT_type, 0, CT_content);
                    cardModelList.add(new_model);
                }
        }
    }

    private String getModelJsonFile(String name, int type, boolean name_visible, boolean only, int align, int size, boolean trainable, boolean keyword){
        JSONObject obj = new JSONObject();
        try{
            obj.put("name", name);
            obj.put("type", type);
            obj.put("name_visible", name_visible);
            obj.put("only", only);
            obj.put("align", align);
            obj.put("size", size);
            obj.put("trainable", trainable);
            obj.put("keyword", keyword);
        }catch(JSONException e){
            Log.e("JSON Create Error", "getModelJsonFile@AddNewModelActivity");
            return null;
        }
        return obj.toString();
    }
}
