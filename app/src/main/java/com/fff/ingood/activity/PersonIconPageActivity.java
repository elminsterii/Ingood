package com.fff.ingood.activity;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonDeleteIconTask;
import com.fff.ingood.task.DoPersonGetIconListTask;
import com.fff.ingood.task.DoPersonGetIconTask;
import com.fff.ingood.task.DoPersonUploadIconTask;
import com.fff.ingood.task.HttpProxy;
import com.fff.ingood.tools.ParserUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;


public class PersonIconPageActivity extends BaseActivity {

    private Button mButton_upLoad;
    private Button mButton_get, mButton_choose, mButton_getList, mButton_delete;
    private ImageView ivShow;
    private Bitmap photoBmp;
    private EditText mEditText_Account;
    private EditText mEditText_Pwd;
    private String img_src;
    private String account, password;
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
        mEditText_Pwd = findViewById(R.id.edit_pwd);
        mButton_upLoad = findViewById(R.id.btn_upload);
        mButton_choose = findViewById(R.id.btn_choose);
        mButton_get = findViewById(R.id.btn_get);
        mButton_getList = findViewById(R.id.btn_getlist);
        mButton_delete = findViewById(R.id.btn_delete);
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
                    img_src = cursor.getString(column_index); // actual image path
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

                DoPersonUploadIconTask task = new DoPersonUploadIconTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {

                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).contains("0")) {
                                    Toast.makeText(PersonIconPageActivity.this, "doUpload OK", Toast.LENGTH_SHORT).show();

                                    PreferenceManager.getInstance().setLoginSuccess(true);
                                    PreferenceManager.getInstance().setKeepLogin(true);

                                    Class<?> clsFlow = FlowManager.getInstance().goHomeFlow();

                                    if(clsFlow != null) {
                                        Intent intent = new Intent(mActivity, clsFlow);

                                        Bundle bundle = new Bundle();
                                        bundle.putString("personData", strResponse);
                                        //bundle.putString("pwd", mEditText_Password.getText().toString());

                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                    Toast.makeText(PersonIconPageActivity.this, "doUpload Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                task.execute(mEditText_Account.getText().toString()+"&"+img_src);
            }
        });

        mButton_get.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImage().execute(String.valueOf(HttpProxy.HTTP_GET_API_PERSON_ACCESS_ICON) + "/"+ mEditText_Account.getText().toString() + "/icon01.jpg");
            }
        });

        mButton_getList.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoPersonGetIconListTask task = new DoPersonGetIconListTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).contains("0")) {
                                    Toast.makeText(PersonIconPageActivity.this, "doGetlist OK", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(PersonIconPageActivity.this, "doGetlist Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                task.execute(mEditText_Account.getText().toString());
            }
        });

        mButton_delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person user = new Person();
                user.setEmail(mEditText_Account.getText().toString());
                user.setPassword(mEditText_Pwd.getText().toString());
                DoPersonDeleteIconTask task = new DoPersonDeleteIconTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).contains("0")) {
                                    Toast.makeText(PersonIconPageActivity.this, "doDelete OK", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(PersonIconPageActivity.this, "doDelete Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                task.execute(user);
            }
        });
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];
            Bitmap bitmap = null;

            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                ivShow.setDrawingCacheEnabled(true);
                bitmap = BitmapFactory.decodeStream(input);
                bitmap = ivShow.getDrawingCache();
                File file = new File(Environment.getExternalStorageDirectory(),"NEW_Icon.jpg");
                if (file.exists()){
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
                Log.v("mmp","File Length:"+file.length());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ivShow.setImageBitmap(result);
        }
    }
}