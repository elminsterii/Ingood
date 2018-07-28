package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.ImageCache;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.IgActivityCreateLogic;
import com.fff.ingood.logic.IgActivityImagesUploadLogic;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityUpdateLogic;
import com.fff.ingood.tools.ImageHelper;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.tools.TimeHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.GlobalProperty.IGACTIVITY_IMAGE_HEIGHT;
import static com.fff.ingood.global.GlobalProperty.IGACTIVITY_IMAGE_UPLOAD_UPPER_LIMIT;
import static com.fff.ingood.global.GlobalProperty.IGACTIVITY_IMAGE_WIDTH;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityPublishActivity extends BaseActivity implements
        IgActivityCreateLogic.IgActivityCreateLogicCaller
        , IgActivityUpdateLogic.IgActivityUpdateLogicCaller, IgActivityImagesUploadLogic.IgActivityImagesUploadLogicCaller {

    private static final int RESULT_CODE_PICK_IMAGE = 1;

    private Button mBtnLeftBottom;
    private Button mBtnRightBottom;
    private ImageButton mBtnBack;
    private TextView mTextViewTitle;
    private TextView mTextViewPublisherName;
    private RelativeLayout mLayoutIgActivityPublishImages;
    private ImageButton mBtnImageUpload;
    private ImageButton mBtnStartDatePicker;
    private ImageButton mBtnStartTimePicker;
    private TextView mTextViewStartDateDescription;
    private TextView mTextViewStartTimeDescription;
    private TextView mTextViewEndDateDescription;
    private TextView mTextViewEndTimeDescription;
    private ImageButton mBtnEndDatePicker;
    private ImageButton mBtnEndTimePicker;
    private EditText mEditTextIgActivityName;
    private EditText mEditTextIgActivityMaxAttention;
    private EditText mEditTextIgActivityLocation;
    private EditText mEditTextIgActivityDescription;
    private LinearLayout mLayoutIgActivityTags;

    private boolean m_bEditMode = false;
    private IgActivity m_igActivity;

    private List<EditText> m_lsTagsInput;
    private ImageButton m_preBtnOfTagAdd;

    private List<Bitmap> m_lsUploadImages;

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
        mBtnBack = findViewById(R.id.imgBackIgActivityPublish);
        mTextViewTitle = findViewById(R.id.textViewIgActivityPublishTitle);
        mTextViewPublisherName = findViewById(R.id.textViewIgActivityPublisherName);
        mLayoutIgActivityPublishImages = findViewById(R.id.layoutIgActivityPublishImages);
        mBtnImageUpload = findViewById(R.id.imageBtnIgActivityPublishImageUpload);
        mBtnStartDatePicker = findViewById(R.id.btnIgActivityPublishStartDatePicker);
        mBtnStartTimePicker = findViewById(R.id.btnIgActivityPublishStartTimePicker);
        mTextViewStartDateDescription = findViewById(R.id.textViewIgActivityPublishStartDateDescription);
        mTextViewStartTimeDescription = findViewById(R.id.textViewIgActivityPublishStartTimeDescription);
        mBtnEndDatePicker = findViewById(R.id.btnIgActivityPublishEndDatePicker);
        mBtnEndTimePicker = findViewById(R.id.btnIgActivityPublishEndTimePicker);
        mTextViewEndDateDescription = findViewById(R.id.textViewIgActivityPublishEndDateDescription);
        mTextViewEndTimeDescription = findViewById(R.id.textViewIgActivityPublishEndTimeDescription);
        mEditTextIgActivityName = findViewById(R.id.editTextIgActivityPublishName);
        mEditTextIgActivityMaxAttention = findViewById(R.id.editTextIgActivityPublishMaxAttention);
        mEditTextIgActivityLocation = findViewById(R.id.editTextIgActivityPublishLocation);
        mEditTextIgActivityDescription = findViewById(R.id.editTextIgActivityPublishDescription);
        mLayoutIgActivityTags = findViewById(R.id.layoutIgActivityPublishTags);
    }

    @Override
    protected void initData() {
        m_lsUploadImages = new ArrayList<>();
        m_lsTagsInput = new ArrayList<>();
        initUIData(m_igActivity, m_bEditMode);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

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

                if(isDataValid()) {
                    Person person = PersonManager.getInstance().getPerson();
                    m_igActivity.setPublisherEmail(person.getEmail());
                    m_igActivity.setPublisherPwd(person.getPassword());
                    m_igActivity.setName(mEditTextIgActivityName.getText().toString());
                    m_igActivity.setLocation(mEditTextIgActivityLocation.getText().toString());
                    m_igActivity.setDescription(mEditTextIgActivityDescription.getText().toString());
                    m_igActivity.setMaxAttention(mEditTextIgActivityMaxAttention.getText().toString());
                    setIgActivityTags(m_igActivity);
                    setIgActivityTime(m_igActivity);

                    if(!m_bEditMode) {
                        m_igActivity.setLargeActivity("0");
                        createIgActivity(m_igActivity);
                    }
                    else
                        updateIgActivity(m_igActivity);

                    showWaitingDialog(IgActivityPublishActivity.class.getName());
                }
            }
        });

        mBtnImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageByGallery();
            }
        });

        View.OnClickListener clickListenerDatePickerStartDate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance(Locale.getDefault());
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(IgActivityPublishActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String strStartDate = setDateFormat(year, month+1, day);
                        mTextViewStartDateDescription.setText(strStartDate);

                    }
                }, mYear, mMonth, mDay).show();
            }
        };

        mBtnStartDatePicker.setOnClickListener(clickListenerDatePickerStartDate);
        mTextViewStartDateDescription.setOnClickListener(clickListenerDatePickerStartDate);

        View.OnClickListener clickListenerTimePickerStartTime = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(IgActivityPublishActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        String strStartTime = setTimeFormat(hour, minute);
                        mTextViewStartTimeDescription.setText(strStartTime);
                    }
                }, hour, minute, false).show();
            }
        };

        mBtnStartTimePicker.setOnClickListener(clickListenerTimePickerStartTime);
        mTextViewStartTimeDescription.setOnClickListener(clickListenerTimePickerStartTime);

        View.OnClickListener clickListenerDatePickerEndDate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(IgActivityPublishActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String strEndDate = setDateFormat(year, month+1, day);
                        mTextViewEndDateDescription.setText(strEndDate);
                    }
                }, mYear, mMonth, mDay).show();
            }
        };

        mBtnEndDatePicker.setOnClickListener(clickListenerDatePickerEndDate);
        mTextViewEndDateDescription.setOnClickListener(clickListenerDatePickerEndDate);

        View.OnClickListener clickListenerTimePickerEndTime = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(IgActivityPublishActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        String strEndTime = setTimeFormat(hour, minute);
                        mTextViewEndTimeDescription.setText(strEndTime);
                    }
                }, hour, minute, false).show();
            }
        };

        mBtnEndTimePicker.setOnClickListener(clickListenerTimePickerEndTime);
        mTextViewEndTimeDescription.setOnClickListener(clickListenerTimePickerEndTime);
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGPUBLISH).setSystemUI(this);
    }

    private void initUIData(IgActivity activity, boolean bEditMode) {
        if(bEditMode) {
            if(activity != null) {
                mBtnRightBottom.setText(R.string.activity_publish_edit_update);
                mTextViewTitle.setText(R.string.activity_edit);
                mTextViewPublisherName.setText(PersonManager.getInstance().getPerson().getName());
                mEditTextIgActivityName.setText(activity.getName());
                mEditTextIgActivityMaxAttention.setText(activity.getMaxAttention());
                mEditTextIgActivityLocation.setText(activity.getLocation());
                mEditTextIgActivityDescription.setText(activity.getDescription());
                mEditTextIgActivityMaxAttention.setText(activity.getMaxAttention());
                setDateByIgActivity(activity);
                setTagsByIgActivity(activity);

                if(ImageCache.getInstance().isCacheExist()) {
                    m_lsUploadImages = ImageCache.getInstance().getCacheImages();
                    makeUploadImageLayout(m_lsUploadImages);
                }
            }
        }
        else {
            mBtnRightBottom.setText(R.string.dialog_publish);
            mTextViewTitle.setText(R.string.activity_publish);
            mTextViewPublisherName.setText(PersonManager.getInstance().getPerson().getName());
            addNewEmptyTag();
        }
    }

    //"yyyy-MM-dd HH:mm:ss";
    private String setDateFormat(int year, int month, int day) {
        return String.valueOf(year) + "-" + month + "-" + day;
    }

    private String setTimeFormat(int hour, int minute) {
        return String.valueOf(hour) + ":" + minute;
    }

    private IgActivity genEmptyIgActivity() {
        return new IgActivity();
    }

    private void setIgActivityTime(IgActivity activity) {
        if(activity == null)
            return;

        String strStartDate = mTextViewStartDateDescription.getText().toString();
        String strStartTime = mTextViewStartTimeDescription.getText().toString();
        String strEndDate = mTextViewEndDateDescription.getText().toString();
        String strEndTime = mTextViewEndTimeDescription.getText().toString();

        String strStartDateTime = TimeHelper.makeIgActivityDateStringByUI(strStartDate + " " + strStartTime);
        String strEndDateTime = TimeHelper.makeIgActivityDateStringByUI(strEndDate + " " + strEndTime);
        activity.setDateBegin(strStartDateTime);
        activity.setDateEnd(strEndDateTime);
        activity.setPublishBegin(strStartDateTime);
        activity.setPublishEnd(strEndDateTime);
    }

    private void setIgActivityTags(IgActivity activity) {
        if(m_lsTagsInput.size() > 0) {
            String strTags = StringTool.listEditTextToString(m_lsTagsInput, ',');
            activity.setTags(strTags);
        }
    }

    private EditText addNewEmptyTag() {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.layout_add_tag_in_puglish_igactivity, null, false);
        EditText editTextTagInput = (EditText)layout.getChildAt(0);
        m_lsTagsInput.add(editTextTagInput);

        ImageButton btnTagAdd = (ImageButton)layout.getChildAt(1);

        btnTagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmptyTag();
            }
        });

        mLayoutIgActivityTags.addView(layout);

        if(m_preBtnOfTagAdd != null)
            m_preBtnOfTagAdd.setVisibility(View.INVISIBLE);
        m_preBtnOfTagAdd = btnTagAdd;

        return editTextTagInput;
    }

    private void setTagsByIgActivity(IgActivity activity) {
        if(activity == null)
            return;

        String strTags = activity.getTags();
        if(StringTool.checkStringNotNull(strTags)) {
            String[] arrTags = strTags.split(",");
            for(String strTag : arrTags) {
                EditText editText = addNewEmptyTag();
                editText.setText(strTag);
            }
        }
    }

    private void setDateByIgActivity(IgActivity activity) {
        String strDateBegin = activity.getDateBegin();
        String strDateEnd = activity.getDateEnd();

        if(StringTool.checkStringNotNull(strDateBegin)) {
            String[] arrDateBegin = strDateBegin.split(" ");
            if(arrDateBegin.length > 1) {
                mTextViewStartDateDescription.setText(arrDateBegin[0]);
                mTextViewStartTimeDescription.setText(arrDateBegin[1]);
            }
        }

        if(StringTool.checkStringNotNull(strDateEnd)) {
            String[] arrDateEnd = strDateEnd.split(" ");
            if(arrDateEnd.length > 1) {
                mTextViewEndDateDescription.setText(arrDateEnd[0]);
                mTextViewEndTimeDescription.setText(arrDateEnd[1]);
            }
        }
    }

    private void createIgActivity(IgActivity activity) {
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doCreateIgActivity(this, activity);
    }

    private void updateIgActivity(IgActivity activity) {
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doUpdateIgActivity(this, activity);
    }

    private void uploadIgActivityImage(String strIgActivityId, List<Bitmap> lsIgActivityImages) {
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doIgActivityImagesUploadAll(this, strIgActivityId, lsIgActivityImages);
    }

    private void pickImageByGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, getResources().getText(R.string.activity_publish_images_select));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, RESULT_CODE_PICK_IMAGE);
    }

    private void addImageIntoLayout(Bitmap bm) {
        if(bm == null)
            return;

        m_lsUploadImages.add(bm);
        makeUploadImageLayout(m_lsUploadImages);
    }

    private void makeUploadImageLayout(List<Bitmap> lsBitmaps) {
        mLayoutIgActivityPublishImages.removeAllViews();

        final int SIZE_DP = 71;
        final int MARGIN_DP = 15;
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIZE_DP, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIZE_DP, getResources().getDisplayMetrics());
        int iLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_DP, getResources().getDisplayMetrics());

        ImageView preImageViewImageUpload = null;
        for(int i=0; i<lsBitmaps.size(); i++) {
            Bitmap bm = lsBitmaps.get(i);
            ImageView imageView = new ImageView(this);
            imageView.setId(View.generateViewId());
            imageView.setTag(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bm);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index =  (int)v.getTag();
                    m_lsUploadImages.remove(index);
                    makeUploadImageLayout(m_lsUploadImages);
                }
            });

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            if(preImageViewImageUpload != null) {
                params.addRule(RelativeLayout.END_OF, preImageViewImageUpload.getId());
                params.setMargins(iLeftMargin, 0, 0, 0);
            }
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            imageView.setLayoutParams(params);
            mLayoutIgActivityPublishImages.addView(imageView);
            preImageViewImageUpload = imageView;
        }
        checkUpperLimitUploadImages();
    }

    private void checkUpperLimitUploadImages() {
        if(m_lsUploadImages.size() >= IGACTIVITY_IMAGE_UPLOAD_UPPER_LIMIT)
            mBtnImageUpload.setVisibility(View.INVISIBLE);
        else
            mBtnImageUpload.setVisibility(View.VISIBLE);
    }

    private boolean isDataValid() {
        return checkIgActivityNameValid()
                && checkIgActivityAttentionValid()
                && checkIgActivityLocationValid()
                && checkIgActivityDescriptionValid()
                && checkIgActivityTagsValid()
                && checkIgActivityDateAndTimeValid()
                && checkIgActivityImagesValid();
    }

    private boolean checkIgActivityImagesValid() {
        boolean bRes = false;

        if(m_lsUploadImages != null && m_lsUploadImages.size() >= 1)
            bRes = true;
        else
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_images_format_wrong), Toast.LENGTH_SHORT).show();
        return bRes;
    }

    private boolean checkIgActivityDateAndTimeValid() {
        boolean bRes = false;

        String strStartDate = mTextViewStartDateDescription.getText().toString();
        String strStartTime = mTextViewStartTimeDescription.getText().toString();
        String strEndDate = mTextViewEndDateDescription.getText().toString();
        String strEndTime = mTextViewEndTimeDescription.getText().toString();

        strStartDate = strStartDate.contentEquals(getResources().getText(R.string.activity_publish_date_description)) ? "" : strStartDate;
        strStartTime = strStartTime.contentEquals(getResources().getText(R.string.activity_publish_time_description)) ? "" : strStartTime;
        strEndDate = strEndDate.contentEquals(getResources().getText(R.string.activity_publish_date_description)) ? "" : strEndDate;
        strEndTime = strEndTime.contentEquals(getResources().getText(R.string.activity_publish_time_description)) ? "" : strEndTime;

        if(StringTool.checkStringNotNull(strStartDate)
                && StringTool.checkStringNotNull(strStartTime)
                && StringTool.checkStringNotNull(strEndDate)
                && StringTool.checkStringNotNull(strEndTime)) {
            String strStartDateTime = TimeHelper.makeIgActivityDateStringByUI(strStartDate + " " + strStartTime);
            String strEndDateTime = TimeHelper.makeIgActivityDateStringByUI(strEndDate + " " + strEndTime);

            if(TimeHelper.checkBeginTimeBeforeEndTime(strStartDateTime, strEndDateTime))
                bRes = true;
            else
                Toast.makeText(this, getResources().getText(R.string.publish_igactivity_begin_end_time_wrong), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_time_format_wrong), Toast.LENGTH_SHORT).show();
        }
        return bRes;
    }

    private boolean checkIgActivityNameValid() {
        boolean bRes = false;
        String strIgActivityName = mEditTextIgActivityName.getText().toString();

        if(StringTool.checkStringNotNull(strIgActivityName)) {
            setViewUnderlineColor(mEditTextIgActivityName, getResources().getColor(R.color.colorTextHint));
            bRes = true;
        } else {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_name_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextIgActivityName, getResources().getColor(R.color.colorWarningRed));
        }
        return bRes;
    }

    private boolean checkIgActivityAttentionValid() {
        boolean bRes = false;
        String strMaxAttention = mEditTextIgActivityMaxAttention.getText().toString();

        if(StringTool.checkStringNotNull(strMaxAttention)) {
            int iMaxAttention = Integer.parseInt(strMaxAttention);
            if(iMaxAttention <= 0) {
                Toast.makeText(this, getResources().getText(R.string.publish_igactivity_max_attention_zero), Toast.LENGTH_SHORT).show();
                setViewUnderlineColor(mEditTextIgActivityMaxAttention, getResources().getColor(R.color.colorWarningRed));
            } else {
                setViewUnderlineColor(mEditTextIgActivityMaxAttention, getResources().getColor(R.color.colorTextHint));
                bRes = true;
            }
        } else {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_max_attention_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextIgActivityMaxAttention, getResources().getColor(R.color.colorWarningRed));
        }
        return bRes;
    }

    private boolean checkIgActivityLocationValid() {
        boolean bRes = false;
        String strIgActivityLocation = mEditTextIgActivityLocation.getText().toString();

        if(StringTool.checkStringNotNull(strIgActivityLocation)) {
            setViewUnderlineColor(mEditTextIgActivityLocation, getResources().getColor(R.color.colorTextHint));
            bRes = true;
        } else {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_name_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextIgActivityLocation, getResources().getColor(R.color.colorWarningRed));
        }
        return bRes;
    }

    private boolean checkIgActivityDescriptionValid() {
        boolean bRes = false;
        String strIgActivityDescription = mEditTextIgActivityDescription.getText().toString();

        if(StringTool.checkStringNotNull(strIgActivityDescription)) {
            setViewUnderlineColor(mEditTextIgActivityDescription, getResources().getColor(R.color.colorTextHint));
            bRes = true;
        } else {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_description_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextIgActivityDescription, getResources().getColor(R.color.colorWarningRed));
        }
        return bRes;
    }

    private boolean checkIgActivityTagsValid() {
        boolean bRes = false;
        TextView textViewFocusAndHighLight = null;

        if(m_lsTagsInput != null && m_lsTagsInput.size() >= 1) {
            for (TextView textView : m_lsTagsInput) {
                setViewUnderlineColor(textView, getResources().getColor(R.color.colorTextHint));

                String strTag = textView.getText().toString();
                if (StringTool.checkStringNotNull(strTag)) {
                    bRes = true;
                    break;
                } else {
                    if(textViewFocusAndHighLight == null)
                        textViewFocusAndHighLight = textView;
                }
            }
        }
        if(!bRes) {
            Toast.makeText(this, getResources().getText(R.string.publish_igactivity_tags_format_wrong), Toast.LENGTH_SHORT).show();
            if(textViewFocusAndHighLight != null)
                setViewUnderlineColor(textViewFocusAndHighLight, getResources().getColor(R.color.colorWarningRed));
        }
        return bRes;
    }

    private void setViewUnderlineColor(View view, int iColor) {
        ColorStateList colorStateList = ColorStateList.valueOf(iColor);
        ViewCompat.setBackgroundTintList(view, colorStateList);
    }

    @Override
    public void returnUploadIgActivityImagesSuccess() {
        hideWaitingDialog();
        Toast.makeText(mActivity, getResources().getText(R.string.activity_has_been_published), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        hideWaitingDialog();

        if(iStatusCode != null && !iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnUpdateIgActivitySuccess() {
        hideWaitingDialog();
        Toast.makeText(mActivity, getResources().getText(R.string.activity_has_been_updated), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @SuppressLint("ShowToast")
    @Override
    public void onCreateIgActivitySuccess(String strId) {
        uploadIgActivityImage(strId, m_lsUploadImages);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RESULT_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(Objects.requireNonNull(inputStream));
                    Bitmap bm = BitmapFactory.decodeStream(bufferedInputStream);
                    bm = ImageHelper.makeBitmapCorrectOrientation(bm, data.getData(), this);
                    bm = ImageHelper.resizeBitmap(bm, IGACTIVITY_IMAGE_WIDTH, IGACTIVITY_IMAGE_HEIGHT);

                    inputStream.close();
                    addImageIntoLayout(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
