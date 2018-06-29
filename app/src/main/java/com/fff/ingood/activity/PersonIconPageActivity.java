package com.fff.ingood.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fff.ingood.R;
import com.fff.ingood.task.HttpProxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class PersonIconPageActivity extends BaseActivity {

    private Button mButton_upLoad;
    private Button mButton_get, mButton_choose;
    private ImageView ivShow;
    private Bitmap photoBmp;
    private EditText mEditText_Account;
    private String img_src;
    URL uploadUrl;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_person_icon_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void initView(){
        super.initView();
        mEditText_Account = findViewById(R.id.edit_account);
        mButton_upLoad = findViewById(R.id.btn_upload);
        mButton_choose = findViewById(R.id.btn_choose);
        mButton_get = findViewById(R.id.btn_get);
        ivShow = findViewById(R.id.showimg);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x1111 && data != null) {
            uri = data.getData();
            assert uri != null;
            img_src = uri.getPath();

            try {
                photoBmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                ivShow.setImageBitmap(photoBmp);
                String[] proj = {MediaStore.Images.Media.DATA};
                CursorLoader loader = new CursorLoader(getApplicationContext(), uri, proj, null, null, null);
                Cursor cursor = loader.loadInBackground();
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    img_src = cursor.getString(column_index);//图片实际路径
                }
                cursor.close();

            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initListener(){
        super.initListener();
        mButton_choose.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0x1111);
            }
        });

        mButton_upLoad.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //convert image to stream
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = new File(img_src);
                            uploadImg(file);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        mButton_get.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void uploadImg(File file) {
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            uploadUrl = new URL(String.valueOf(HttpProxy.HTTP_POST_API_PERSON_ACCESS_ICON) + "/" + mEditText_Account.getText().toString() + "/icon02.jpg");
            HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Charset", "UTF_8");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            if (file != null) {

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                FileInputStream is = new FileInputStream(file);
                int read = 0;
                byte[] bytes = new byte[1024000];
                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                is.close();
                out.flush();
                out.close();

                //response body
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }
            }
        }catch(IOException e){
                e.printStackTrace();
        }
    }

}