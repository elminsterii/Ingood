package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.IgActivityCreateLogic;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityUpdateLogic;
import com.fff.ingood.tools.ImageHelper;
import com.fff.ingood.tools.StringTool;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.GlobalProperty.HEIGHT_IGACTIVITY_IMAGE;
import static com.fff.ingood.global.GlobalProperty.WIDTH_IGACTIVITY_IMAGE;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityPublishActivity extends BaseActivity implements
        IgActivityCreateLogic.IgActivityCreateLogicCaller
        , IgActivityUpdateLogic.IgActivityUpdateLogicCaller {

    private static final int RESULT_CODE_PICK_IMAGE = 1;
    private static final int UPPER_LIMIT_UPLOAD_IMAGES = 3;

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
        });

        mBtnImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageByGallery();
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
                        String strStartDate = setDateFormat(year ,month, day);
                        mTextViewStartDateDescription.setText(strStartDate);

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
                        String strStartTime = setTimeFormat(hour ,minute);
                        mTextViewStartTimeDescription.setText(strStartTime);
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
                        String strEndDate = setDateFormat(year, month, day);
                        mTextViewEndDateDescription.setText(strEndDate);
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
                        String strEndTime = setTimeFormat(hour ,minute);
                        mTextViewEndTimeDescription.setText(strEndTime);
                    }
                }, hour, minute, false).show();
            }
        });
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

                //TODO - download images and init views.
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

        String strStartDateTime = IgActivityHelper.makeIgActivityDateStringByUI(strStartDate + " " + strStartTime);
        String strEndDateTime = IgActivityHelper.makeIgActivityDateStringByUI(strEndDate + " " + strEndTime);
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
        executor.doUpdatecomIgActivity(this, activity);
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
        if(m_lsUploadImages.size() >= UPPER_LIMIT_UPLOAD_IMAGES)
            mBtnImageUpload.setVisibility(View.INVISIBLE);
        else
            mBtnImageUpload.setVisibility(View.VISIBLE);
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
        hideWaitingDialog();
        Toast.makeText(mActivity, getResources().getText(R.string.activity_has_been_published), Toast.LENGTH_SHORT).show();
        onBackPressed();
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
                    bm = ImageHelper.resizeBitmap(bm, WIDTH_IGACTIVITY_IMAGE, HEIGHT_IGACTIVITY_IMAGE);

                    inputStream.close();
                    addImageIntoLayout(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
