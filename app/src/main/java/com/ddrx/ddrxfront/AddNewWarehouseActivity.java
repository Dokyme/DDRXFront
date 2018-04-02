package com.ddrx.ddrxfront;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.AddNewWarehouseController;
import com.ddrx.ddrxfront.Model.Card;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.CardWarehouse;
import com.ddrx.ddrxfront.Model.Model;
import com.ddrx.ddrxfront.Model.UserInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewWarehouseActivity extends AppCompatActivity {

    private MyHandler handler;
    private RecyclerView model_recycler_view;
    private AddNewWarehouseController controller;
    private ProgressDialog progressDialog;
    private List<CardModel> models;
    private List<Card> cards;
    private EditText training_time;
    private Bitmap cover = null;
    private long chosen_model_id = 0;
    private Context context;

    public static final int GET_MODEL = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int ADD_CARD = 3;
    public static final int SUCCESS_UPLOAD = 4;
    public static final int FAILED_UPLOAD = 5;

    public long getChosen_model_id() {
        return chosen_model_id;
    }

    public void setChosen_model_id(long chosen_model_id) {
        this.chosen_model_id = chosen_model_id;
    }

    private static class MyHandler extends Handler {

        private final WeakReference<AddNewWarehouseActivity> mActivity;

        public MyHandler(AddNewWarehouseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_MODEL:{
                    mActivity.get().models = (List<CardModel>) msg.obj;
                    ANMModelAdapter modelAdapter = new ANMModelAdapter(mActivity.get().models, mActivity.get());
                    mActivity.get().model_recycler_view.setAdapter(modelAdapter);
                    break;
                }
                case SUCCESS_UPLOAD:{
                    mActivity.get().progressDialog.dismiss();
                    Toast.makeText(mActivity.get(), "上传成功！", Toast.LENGTH_SHORT).show();
                }
                case FAILED_UPLOAD:{
                    mActivity.get().progressDialog.dismiss();
                    Toast.makeText(mActivity.get(), (String)msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_warehouse);
        handler = new MyHandler(this);
        controller = new AddNewWarehouseController(this, handler);
        cards = new ArrayList<>();
        training_time = findViewById(R.id.ANW_training_time);
        context = this;

        model_recycler_view = findViewById(R.id.ANW_card_model);
        LinearLayoutManager layoutManager_1 = new LinearLayoutManager(this);
        layoutManager_1.setOrientation(LinearLayoutManager.HORIZONTAL);
        model_recycler_view.setLayoutManager(layoutManager_1);
        controller.getAllModels();

        Button upload_img = findViewById(R.id.ANW_cover);
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddNewWarehouseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddNewWarehouseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else{
                    openAlbum();
                }
            }
        });
        final Button commit = findViewById(R.id.ANW_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cover != null){
                    if(!cards.isEmpty()){
                        if(((EditText)AddNewWarehouseActivity.this.findViewById(R.id.ANW_name)).getText().toString().isEmpty()){
                            Toast.makeText(AddNewWarehouseActivity.this, "未添加仓库名！", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String CW_name = ((EditText)findViewById(R.id.ANW_name)).getText().toString();
                            String CW_intro = ((EditText)findViewById(R.id.ANW_intro)).getText().toString();
                            String CW_detail = ((EditText)findViewById(R.id.ANW_description)).getText().toString();
                            int training_time = Integer.valueOf(((EditText)findViewById(R.id.ANW_training_time)).getText().toString());
                            UserInfo userInfo = new UserInfo(AddNewWarehouseActivity.this);
                            Calendar calendar = Calendar.getInstance();
                            String UCW_time = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + String.valueOf(calendar.get(Calendar.MONTH)) + "-" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                            CardWarehouse new_house = new CardWarehouse(0, chosen_model_id, "", userInfo.getId(), userInfo.getNickname(), UCW_time, CW_name, 0, cards.size(), CW_intro, CW_detail, training_time);
                            controller.uploadWarehouse(new_house, cover, cards);
                            //TODO: show wait modal dialog
                            progressDialog = new ProgressDialog(context);
                            progressDialog.setTitle("上传仓库中");
                            progressDialog.setMessage("等待中...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                        }
                    }
                    else{
                        Toast.makeText(context, "未添加卡片！", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(context, "未上传封面！", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.ANW_add_card);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewWarehouseActivity.this, AddCardActivity.class);
                intent.putExtra("CT_id", chosen_model_id);
                startActivityForResult(intent, ADD_CARD);
            }
        });
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else
                    Toast.makeText(this, "无相册权限！", Toast.LENGTH_SHORT);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    handleImageOnKitKat(data);
                }
            case ADD_CARD:{
                Model now_model = null;
                for(CardModel model:models){
                    if(model.getCT_id() == chosen_model_id) {
                        now_model = new Model(model.getCT_context());
                        break;
                    }
                }
                if(now_model != null){
                    int num = data.getIntExtra("num", 0);
                    Card tmp = new Card();
                    for(int i = 0; i < num; i++){
                        String name = data.getStringExtra("name" + String.valueOf(i));
                        String entry_data = data.getStringExtra("data" + String.valueOf(i));
                        Card.CardItem item = new Card.CardItem(now_model.getEntry(name), entry_data);
                        tmp.addCardItem(item);
                    }
                    cards.add(tmp);
                }
            }
        }
    }

    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        cover = createCircleImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static Bitmap createCircleImage(String pic_path) {
        Bitmap source = BitmapFactory.decodeFile(pic_path);
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
