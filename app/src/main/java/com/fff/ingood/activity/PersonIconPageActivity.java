package com.fff.ingood.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.*;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.task.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Response.*;

public class PersonIconPageActivity extends BaseActivity {

    private Button mButton_upLoad;
    private Button mButton_get;
    private Person mUser = new Person();
    private ImageView ivShow;
    private Bitmap photoBmp;
    private EditText mEditText_Account;
    private String url ;
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
        mButton_upLoad = findViewById(R.id.button_upload);
        mButton_get = findViewById(R.id.btn_get);
        ivShow = findViewById(R.id.showimg);
    }

    @Override
    protected void initData(){
        super.initData();
        mUser = (Person)getIntent().getSerializableExtra("user");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x1111 && data != null) {
            uri = data.getData();
            try {
                photoBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivShow.setImageBitmap(photoBmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImg(String account){

        url = HttpProxy.HTTP_POST_API_PERSON_ACCESS_ICON+"/"+account+"/icon01";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString(response);
                        Log.d("response", Response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", "icon01");
                params.put("image", imageTobstring(photoBmp));
                return params;
            }
        };
        MySingleton.getInstance(PersonIconPageActivity.this).addToRequestQueue(stringRequest);
    }

    private String imageTobstring(Bitmap bmp){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebyte, Base64.DEFAULT);
    }

    @Override
    protected void initListener(){
        super.initListener();
        mButton_upLoad.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0x1111);
                String email = mEditText_Account.getText().toString();
                uploadImg(email);
            }
        });

        mButton_get.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
