package com.fff.ingood.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.global.SystemUIManager;

import java.util.Calendar;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

public class IgActivityPublishActivity extends BaseActivity {

    private Button mBtnLeftBottom;
    private Button mBtnRightBottom;
    private TextView mTextViewStartDateDescription;
    private TextView mTextViewStartTimeDescription;
    private ImageButton mBtnStartDatePicker;
    private ImageButton mBtnStartTimePicker;

    private boolean m_bEditMode = false;
    private IgActivity m_igActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ig_publish);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void preInit() {
        Intent intent = getIntent();
        if(intent != null) {
            m_igActivity = (IgActivity)intent.getSerializableExtra(TAG_IGACTIVITY);
            if(m_igActivity != null)
                m_bEditMode = true;
        }
    }

    @Override
    protected void initView() {
        mBtnLeftBottom = findViewById(R.id.btnIgActivityPublishLeftBottom);
        mBtnRightBottom = findViewById(R.id.btnIgActivityPublishRightBottom);
        mBtnStartDatePicker = findViewById(R.id.btnIgActivityPublishStartDatePicker);
        mBtnStartTimePicker = findViewById(R.id.btnIgActivityPublishStartTimePicker);
        mTextViewStartDateDescription = findViewById(R.id.textViewIgActivityPublishStartDateDescription);
        mTextViewStartTimeDescription = findViewById(R.id.textViewIgActivityPublishStartTimeDescription);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnLeftBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnRightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnStartDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(IgActivityPublishActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year ,month, day);
                        mTextViewStartDateDescription.setText(format);
                    }
                }, mYear, mMonth, mDay).show();
            }
        });

        mBtnStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(IgActivityPublishActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        String format = setTimeFormat(hour ,minute);
                        mTextViewStartTimeDescription.setText(format);
                    }
                }, hour, minute, false).show();
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGPUBLISH).setSystemUI(this);
    }

    private String setDateFormat(int year, int month, int day) {
        StringBuilder strRes = new StringBuilder();
        strRes.append(year).append("-").append(month).append("-").append(day);
        return strRes.toString();
    }

    private String setTimeFormat(int hour, int minute) {
        StringBuilder strRes = new StringBuilder();
        strRes.append(hour).append(":").append(minute);
        return strRes.toString();
    }
}
