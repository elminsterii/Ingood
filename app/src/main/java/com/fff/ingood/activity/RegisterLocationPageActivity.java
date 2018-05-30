package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.tools.SerializableHashMap;

import java.util.HashMap;


/**
 * Created by yoie7 on 2018/5/21.
 */

public class RegisterLocationPageActivity extends BaseActivity {

    private HashMap<String, Object> mRegisterList = new HashMap<>();
    private Button mButton_Next;
    private Spinner mSpinner_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_location_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(){
        super.initView();
        mButton_Next = findViewById(R.id.btn_next);
        mSpinner_Location = findViewById(R.id.spinner_location);
    }

    @Override
    protected void initData(){
        super.initData();
        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("mapList");
        mRegisterList = serializableHashMap.getObjectItems();
    }

    @Override
    protected void initListener(){
        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpinner_Location.getSelectedItemPosition() != 0){
                    mRegisterList.put(Person.ATTRIBUTES_PERSON_LOCATION, mSpinner_Location.getSelectedItem().toString());
                    Intent intent = new Intent(mActivity, RegisterInterestPageActivity.class);
                    SerializableHashMap hashMapList = new SerializableHashMap();
                    hashMapList.setObjectItems(mRegisterList);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mapList", hashMapList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterLocationPageActivity.this, "Please choose Your Location!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
