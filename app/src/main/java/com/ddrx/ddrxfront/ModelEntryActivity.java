package com.ddrx.ddrxfront;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class ModelEntryActivity extends AppCompatActivity {

    private EditText entry_name;
    private Switch entry_name_visible;
    private Spinner entry_type;
    private Switch entry_only;
    private Spinner entry_align;
    private Spinner entry_size;
    private Switch entry_trainable;
    private Switch entry_keyword;
    private int entry_id;
    private int entry_type_num = 1;
    private int entry_align_num = 1;
    private int entry_size_num = 1;
    private boolean entry_name_visible_val = true;
    private boolean entry_only_val = true;
    private boolean entry_trainable_val = true;
    private boolean entry_keyword_val = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_entry);

        EditText entry_name = findViewById(R.id.AME_name);
        Switch entry_name_visible = findViewById(R.id.AME_name_visible);
        Spinner entry_type = findViewById(R.id.AME_type);
        Switch entry_only = findViewById(R.id.AME_only);
        final Spinner entry_align = findViewById(R.id.AME_align);
        Spinner entry_size = findViewById(R.id.AME_size);
        Switch entry_trainable = findViewById(R.id.AME_trainable);
        Switch entry_keyword = findViewById(R.id.AME_keyword);

        Intent intent = getIntent();
        if(intent.getBooleanExtra("has_content", false)){
            entry_name.setText(intent.getStringExtra("name"));
            entry_name_visible.setChecked(intent.getBooleanExtra("name_visible", true));
            entry_type.setSelection(intent.getIntExtra("type", 0), true);
            entry_only.setChecked(intent.getBooleanExtra("only", true));
            entry_align.setSelection(intent.getIntExtra("align", 0), true);
            entry_size.setSelection(intent.getIntExtra("size", 0), true);
            entry_trainable.setChecked(intent.getBooleanExtra("trainable", true));
            entry_keyword.setChecked(intent.getBooleanExtra("keyword", true));
        }
        else{
            entry_name_visible.setChecked(true);
            entry_type.setSelection(0, true);
            entry_only.setChecked(true);
            entry_align.setSelection(0, true);
            entry_size.setSelection(0, true);
            entry_trainable.setChecked(true);
            entry_keyword.setChecked(true);
        }
        entry_id = intent.getIntExtra("entry_id", 0);

        entry_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entry_type_num = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        entry_align.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entry_align_num = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                entry_align.setSelection(0);
            }
        });

        entry_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entry_size_num = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        entry_name_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entry_name_visible_val = isChecked;
            }
        });

        entry_only.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entry_only_val = isChecked;
            }
        });

        entry_trainable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entry_trainable_val = isChecked;
            }
        });

        entry_keyword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entry_keyword_val = isChecked;
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("name", entry_name.getText().toString());
        intent.putExtra("type", entry_type_num);
        intent.putExtra("name_visible", entry_name_visible_val);
        intent.putExtra("only", entry_only_val);
        intent.putExtra("align", entry_align_num);
        intent.putExtra("size", entry_size_num);
        intent.putExtra("trainable", entry_trainable_val);
        intent.putExtra("keyword", entry_keyword_val);
        intent.putExtra("entry_id", entry_id);
        setResult(RESULT_OK, intent);
        finish();
    }
}
