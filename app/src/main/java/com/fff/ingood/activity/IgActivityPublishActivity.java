package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

public class IgActivityPublishActivity extends BaseActivity {

    private Button mBtnLeftBottom;
    private Button mBtnRightBottom;
    private TextView mTextViewPublisherName;
    private TextView mTextViewStartDateDescription;
    private TextView mTextViewStartTimeDescription;
    private ImageButton mBtnStartDatePicker;
    private ImageButton mBtnStartTimePicker;
    private TextView mTextViewEndDateDescription;
    private TextView mTextViewEndTimeDescription;
    private ImageButton mBtnEndDatePicker;
    private ImageButton mBtnEndTimePicker;
    private EditText mEditTextIgActivityName;
    private EditText mEditTextIgActivityLocation;
    private EditText mEditTextIgActivityDescription;
    private LinearLayout mLayoutIgActivityTags;

    private boolean m_bEditMode = false;
    private IgActivity m_igActivity;

    private String m_strStartDate;
    private String m_strStartTime;
    private String m_strEndDate;
    private String m_strEndTime;

    private List<EditText> m_lsTagsInput;
    private ImageButton m_preBtnOfTagAdd;

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
        mTextViewPublisherName = findViewById(R.id.textViewIgActivityPublisherName);
        mBtnStartDatePicker = findViewById(R.id.btnIgActivityPublishStartDatePicker);
        mBtnStartTimePicker = findViewById(R.id.btnIgActivityPublishStartTimePicker);
        mTextViewStartDateDescription = findViewById(R.id.textViewIgActivityPublishStartDateDescription);
        mTextViewStartTimeDescription = findViewById(R.id.textViewIgActivityPublishStartTimeDescription);
        mBtnEndDatePicker = findViewById(R.id.btnIgActivityPublishEndDatePicker);
        mBtnEndTimePicker = findViewById(R.id.btnIgActivityPublishEndTimePicker);
        mTextViewEndDateDescription = findViewById(R.id.textViewIgActivityPublishEndDateDescription);
        mTextViewEndTimeDescription = findViewById(R.id.textViewIgActivityPublishEndTimeDescription);
        mEditTextIgActivityName = findViewById(R.id.editTextIgActivityPublishName);
        mEditTextIgActivityLocation = findViewById(R.id.editTextIgActivityPublishLocation);
        mEditTextIgActivityDescription = findViewById(R.id.editTextIgActivityPublishDescription);
        mLayoutIgActivityTags = findViewById(R.id.layoutIgActivityPublishTags);
    }

    @Override
    protected void initData() {
        m_lsTagsInput = new ArrayList<>();
        mTextViewPublisherName.setText(PersonManager.getInstance().getPerson().getName());
        addNewEmptyTag(m_igActivity);
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
                if(m_igActivity == null)
                    m_igActivity = genEmptyIgActivity();

                setIgActivityTime(m_igActivity);
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
                        m_strStartDate = setDateFormat(year ,month, day);
                        mTextViewStartDateDescription.setText(m_strStartDate);

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
                        m_strStartTime = setTimeFormat(hour ,minute);
                        mTextViewStartTimeDescription.setText(m_strStartTime);
                    }
                }, hour, minute, false).show();
            }
        });

        mBtnEndDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(IgActivityPublishActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        m_strEndDate = setDateFormat(year, month, day);
                        mTextViewEndDateDescription.setText(m_strEndDate);
                    }
                }, mYear, mMonth, mDay).show();
            }
        });

        mBtnEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(IgActivityPublishActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        m_strEndTime = setTimeFormat(hour ,minute);
                        mTextViewEndTimeDescription.setText(m_strEndTime);
                    }
                }, hour, minute, false).show();
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGPUBLISH).setSystemUI(this);
    }

    //"yyyy-MM-dd HH:mm:ss";
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

    private IgActivity genEmptyIgActivity() {
        return new IgActivity();
    }

    private void setIgActivityTime(IgActivity activity) {
        if(activity == null)
            return;

        String strStartDateTime = IgActivityHelper.makeIgActivityDateStringByUI(m_strStartDate + " " + m_strStartTime);
        String strEndDateTime = IgActivityHelper.makeIgActivityDateStringByUI(m_strEndDate + " " + m_strEndTime);
        activity.setDateBegin(strStartDateTime);
        activity.setDateEnd(strEndDateTime);
    }

    private void addNewEmptyTag(IgActivity activity) {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.layout_add_tag_in_puglish_igactivity, null, false);
        EditText editTextTagInput = (EditText)layout.getChildAt(0);
        m_lsTagsInput.add(editTextTagInput);

        ImageButton btnTagAdd = (ImageButton)layout.getChildAt(1);

        btnTagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmptyTag(m_igActivity);
            }
        });

        mLayoutIgActivityTags.addView(layout);

        if(m_preBtnOfTagAdd != null)
            m_preBtnOfTagAdd.setVisibility(View.INVISIBLE);
        m_preBtnOfTagAdd = btnTagAdd;
    }
}
