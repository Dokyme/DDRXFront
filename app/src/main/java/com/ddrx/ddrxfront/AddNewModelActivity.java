package com.ddrx.ddrxfront;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.AddNewModelController;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.Model;
import com.ddrx.ddrxfront.Model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddNewModelActivity extends AppCompatActivity {
    private List<JSONObject> cardModelContextList;
    private ImageView add_new_model_entry;
    private Button commit_model;
    private LinearLayout entry_layout;
    private HashMap<Integer, ConstraintLayout> entry_manager;
    private Context context;
    private MyHandler handler;
    private ProgressDialog progressDialog;
    private int max_count = 0;
    private int CT_type = 0;
    public final static int NETWORK_ERROR = 1;
    public final static int NETWORK_PASS = 2;

    private static class MyHandler extends Handler {

        private final WeakReference<AddNewModelActivity> mActivity;

        public MyHandler(AddNewModelActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NETWORK_ERROR:
                    mActivity.get().progressDialog.dismiss();
                    Toast.makeText(mActivity.get(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_PASS:
                    //TODO: disable waiting dialog
                    mActivity.get().progressDialog.dismiss();
                    Toast.makeText(mActivity.get(), "上传成功！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_model);
        context = this;
        cardModelContextList = new ArrayList<>();
        add_new_model_entry = findViewById(R.id.ANM_add_new_stuff);
        commit_model = findViewById(R.id.ANM_commit);
        entry_layout = findViewById(R.id.ANM_entry_layout);
        entry_manager = new HashMap<>();
        handler = new MyHandler(this);
        final Spinner type_tag = findViewById(R.id.ANM_type);
        type_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CT_type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type_tag.setSelection(0);
            }
        });

        add_new_model_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout entry = (ConstraintLayout) View.inflate(context, R.layout.add_new_model_record, null);
                ImageView delete_btn = entry.findViewById(R.id.delete_entry);
                delete_btn.setTag(max_count);
                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int item_id = (int) v.getTag();
                        ConstraintLayout delete_entry = entry_manager.get(item_id);
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

        commit_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name_tag = findViewById(R.id.ANM_name);
                String CT_name = name_tag.getText().toString();
                EditText brief_tag = findViewById(R.id.ANM_intro);
                String CT_brief = brief_tag.getText().toString();
                UserInfo userInfo = new UserInfo(context);
                JSONArray array = new JSONArray();
                for (JSONObject obj : cardModelContextList) {
                    array.put(obj);
                }
                String CT_content = array.toString();
                Calendar now = Calendar.getInstance();
                String UCT_time = String.valueOf(now.get(Calendar.YEAR)) + "-" + String.valueOf(now.get(Calendar.MONTH)) + "-" + String.valueOf(now.get(Calendar.DAY_OF_MONTH));
                CardModel new_model = new CardModel(0, userInfo.getNickname(), CT_name, userInfo.getId(), UCT_time, CT_brief, CT_type, 0, CT_content, userInfo.getNickname(), userInfo.getId());
                AddNewModelController controller = new AddNewModelController(handler, context);
                controller.uploadModel(new_model);
                //TODO: show wait modal dialog
                progressDialog = new ProgressDialog(AddNewModelActivity.this);
                progressDialog.setTitle("上传模版中");
                progressDialog.setMessage("等待中...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    JSONObject CT_content_obj = getModelJsonFile(data.getStringExtra("name"), data.getIntExtra("type", 0),
                            data.getBooleanExtra("name_visible", true), data.getBooleanExtra("only", true),
                            data.getIntExtra("align", 0), data.getIntExtra("size", 0),
                            data.getBooleanExtra("trainable", true), data.getBooleanExtra("keyword", true));
                    if (CT_content_obj != null) {
                        cardModelContextList.add(CT_content_obj);
                        ConstraintLayout entry = entry_manager.get(data.getIntExtra("entry_id", 0));
                        TextView entry_name = entry.findViewById(R.id.entry_name);
                        TextView entry_type = entry.findViewById(R.id.entry_type);
                        entry_name.setText(data.getStringExtra("name"));
                        entry_type.setText(Model.ENTRY_TYPE_NAME[data.getIntExtra("type", 0)]);

                    }
                }
        }
    }

    private JSONObject getModelJsonFile(String name, int type, boolean name_visible, boolean only, int align, int size, boolean trainable, boolean keyword) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("type", type);
            obj.put("name_visible", name_visible);
            obj.put("only", only);
            obj.put("align", align);
            obj.put("size", size);
            obj.put("trainable", trainable);
            obj.put("keyword", keyword);
        } catch (JSONException e) {
            Log.e("JSON Create Error", "getModelJsonFile@AddNewModelActivity");
            return null;
        }
        return obj;
    }
}
