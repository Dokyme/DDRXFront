package com.ddrx.ddrxfront;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.AddCardController;
import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.Model.Model;
import com.ddrx.ddrxfront.Model.ModelInput;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddCardAdapter adapter = null;
    private Model model = null;
    public final static int GET_MODEL_INFO = 1;
    public final static int NO_CONTENT = 2;
    public final static int UPDATE_ENTRY_KEYWORD = 3;

    private static class MyHandler extends Handler {

        private final WeakReference<AddCardActivity> mActivity;

        public MyHandler(AddCardActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_MODEL_INFO:
                    mActivity.get().model = (Model) msg.obj;
                    List<ModelInput> inputs = null;
                    try {
                        inputs = mActivity.get().model.getModelInputs();
                    } catch (JSONException e) {
                        Log.e("JSON Format Error", "handleMessage@AddCardActivity");
                    }
                    if (inputs != null) {
                        mActivity.get().adapter = new AddCardAdapter(inputs, mActivity.get());
                        mActivity.get().recyclerView.setAdapter(mActivity.get().adapter);
//                        mActivity.get().recyclerView.invalidate();
                    }
                    break;
                case NO_CONTENT:
                    Toast.makeText(mActivity.get(), "卡片模板无内容！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mActivity.get(), AddNewWarehouseActivity.class);
                    mActivity.get().setResult(RESULT_CANCELED, intent);
                    mActivity.get().finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        recyclerView = findViewById(R.id.AC_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        long CT_id = intent.getLongExtra("CT_id", 0);
        AddCardController controller = new AddCardController(new MyHandler(this), this);
        controller.getModelInputs(CT_id);
        Button commit_btn = findViewById(R.id.AC_commit_card);
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, AddNewWarehouseActivity.class);
                if (adapter == null) {
                    Toast.makeText(AddCardActivity.this, "未选择模型！", Toast.LENGTH_SHORT).show();
                } else {
                    Map<Integer, View> entry_manager = adapter.getEntryManager();
                    intent.putExtra("num", entry_manager.values().size());
                    int count = 0;
                    for (View view : entry_manager.values()) {
                        intent.putExtra("name" + String.valueOf(count), ((TextView) view.findViewById(R.id.AC_entry_name)).getText().toString());
                        intent.putExtra("data" + String.valueOf(count), ((EditText) view.findViewById(R.id.AC_entry_data)).getText().toString());
                        count++;
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}
