package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonUpdateLogic;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.ConfirmDialogWithTextContent;

import static com.fff.ingood.data.Person.TAG_PERSON;
import static com.fff.ingood.global.GlobalProperty.AGE_LIMITATION;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class PersonDataActivity extends BaseActivity implements PersonUpdateLogic.PersonUpdateLogicCaller, PersonManager.PersonManagerRefreshEvent {

    private ImageView mImageViewEditName;
    private ImageView mImageViewEditDescription;
    private ImageView mImageViewEditPhoto;
    private TextView mTextViewPersonName;
    private TextView mTextViewPersonDescription;
    private TextView mTextViewChangePwd;
    private TextView mTextViewEmail;
    private TextView mTextViewDeemGood;
    private TextView mTextViewDeemBad;

    private Button mBtnSave;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerGender;
    private Spinner mSpinnerLocation;
    private ImageView mImageViewPersonIcon;

    private String[] m_arrAges;
    private String[] m_arrGender;
    private String[] m_arrLocation;

    //for change password dialog
    private boolean mIsNewPwdEyeCheck = true;
    private boolean mIsNewPwdConfirmEyeCheck = true;

    private EditText mEditTextNewPassword;
    private EditText mEditTextNewPasswordConfirm;

    private ImageButton mImageBtnEyeNewPassword;
    private ImageButton mImageBtnEyeNewPasswordConfirm;

    private ImageButton mBtnBack;
    private TextView mTextViewTitle;

    private Person mPerson;
    private boolean m_bObserverMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.person_form_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void preInit() {
        Intent intent = getIntent();
        if(intent != null) {
            mPerson = (Person)intent.getSerializableExtra(TAG_PERSON);
            if(mPerson != null
                    && !mPerson.getEmail().equals(PersonManager.getInstance().getPerson().getEmail()))
            m_bObserverMode = true;
        }
    }

    @Override
    protected void initView(){
        mTextViewPersonName = findViewById(R.id.textViewPersonName);
        mTextViewPersonDescription = findViewById(R.id.textViewPersonAboutContent);
        mTextViewChangePwd = findViewById(R.id.textViewChangePwd);
        mTextViewEmail = findViewById(R.id.textViewEmail);
        mTextViewDeemGood = findViewById(R.id.textViewPersonDeemGood);
        mTextViewDeemBad = findViewById(R.id.textViewPersonDeemBad);
        mSpinnerGender = findViewById(R.id.spinner_gender);
        mSpinnerAge = findViewById(R.id.spinner_age);
        mSpinnerLocation = findViewById(R.id.spinner_location);
        mImageViewEditPhoto = findViewById(R.id.imageViewEditPhoto);
        mImageViewEditName = findViewById(R.id.imageViewEditName);
        mImageViewEditDescription = findViewById(R.id.imageViewPersonEditAbout);
        mBtnSave = findViewById(R.id.btnPersonSave);
        mBtnBack = findViewById(R.id.imgPersonBack);
        mTextViewTitle = findViewById(R.id.textViewTitle);

        FrameLayout frameLayout = findViewById(R.id.layoutPersonThumbnailInPersonPage);
        if(frameLayout != null) {
            mImageViewPersonIcon = (ImageView) frameLayout.getChildAt(0);
            mImageViewPersonIcon.setImageResource(R.drawable.ic_person_black_36dp);
        }
    }

    @Override
    protected void initData(){
        mTextViewChangePwd.setClickable(true);

        m_arrAges = getResources().getStringArray(R.array.user_age_list);
        ArrayAdapter<String> spinnerAgeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, m_arrAges);
        spinnerAgeAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerAge.setAdapter(spinnerAgeAdapter);

        m_arrGender = getResources().getStringArray(R.array.user_gender_list);
        ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, m_arrGender);
        spinnerGenderAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerGender.setAdapter(spinnerGenderAdapter);

        m_arrLocation = getResources().getStringArray(R.array.user_location_list);
        ArrayAdapter<String> spinnerLocationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, m_arrLocation);
        spinnerLocationAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerLocation.setAdapter(spinnerLocationAdapter);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });

        mTextViewChangePwd.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdDlg();
            }
        });

        mBtnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataValid()) {
                    mPerson.setAge(String.valueOf(mSpinnerAge.getSelectedItemPosition() + AGE_LIMITATION - 1));
                    mPerson.setGender(String.valueOf(mSpinnerGender.getSelectedItem()));
                    mPerson.setLocation(String.valueOf(mSpinnerLocation.getSelectedItem()));

                    showWaitingDialog(PersonDataActivity.class.getName());
                    updatePerson(mPerson);
                }
            }
        });

        mImageViewEditPhoto.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImageViewEditName.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mImageViewEditDescription.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialogWithTextContent.newInstance(new ConfirmDialogWithTextContent.TextContentEventCB() {
                    @Override
                    public void onPositiveClick(String strTextContent) {
                        mPerson.setDescription(strTextContent);
                    }
                }, getResources().getText(R.string.title_about_me).toString(), mPerson.getDescription())
                        .show(getSupportFragmentManager(), IgActivityDetailActivity.class.getName());
            }
        });

        mSpinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_PERSONDATA).setSystemUI(this);
    }

    private boolean isDataValid() {
        return checkGenderValid()
                && checkAgeValid()
                && checkLocationValid();
    }

    private boolean checkGenderValid() {
        if(mSpinnerGender.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getText(R.string.register_gender_choose), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mSpinnerGender, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mSpinnerGender, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkAgeValid() {
        if(mSpinnerAge.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getText(R.string.register_age_choose), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mSpinnerAge, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mSpinnerAge, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkLocationValid() {
        if(mSpinnerLocation.getSelectedItemPosition() == 0) {
            Toast.makeText(this, getResources().getText(R.string.register_location_choose), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mSpinnerLocation, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mSpinnerLocation, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private void refresh() {
        mTextViewPersonName.setText(mPerson.getName());
        mTextViewEmail.setText(mPerson.getEmail());
        mTextViewPersonDescription.setText(mPerson.getDescription());
        setUiDeemPeopleByPerson(mPerson);

        int index = 0;
        String strPersonAge = mPerson.getAge();
        for(int i=0; i<m_arrAges.length; i++)
            if(m_arrAges[i].contains(strPersonAge))
                index = i;
        mSpinnerAge.setSelection(index);

        index = 0;
        String strGender = mPerson.getGender();
        for(int i=0; i<m_arrGender.length; i++)
            if(strGender.equals(m_arrGender[i]))
                index = i;
        mSpinnerGender.setSelection(index);

        index = 0;
        String strLocation = mPerson.getLocation();
        for(int i=0; i<m_arrLocation.length; i++)
            if(strLocation.equals(m_arrLocation[i]))
                index = i;
        mSpinnerLocation.setSelection(index);
    }


    private void changePwdDlg(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View newPlanDialog = layoutInflater.inflate(R.layout.dialog_persondata_change_pwd,null);

        mEditTextNewPassword = newPlanDialog.findViewById(R.id.edit_pwd_new);
        mEditTextNewPasswordConfirm = newPlanDialog.findViewById(R.id.edit_pwd_new_confirm);

        mImageBtnEyeNewPassword = newPlanDialog.findViewById(R.id.pwd_new_eye);
        mImageBtnEyeNewPasswordConfirm = newPlanDialog.findViewById(R.id.pwd_new_confirm_eye);

        mIsNewPwdEyeCheck = true;
        mIsNewPwdConfirmEyeCheck = true;

        mImageBtnEyeNewPassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsNewPwdEyeCheck) {
                    mIsNewPwdEyeCheck = false;
                    mImageBtnEyeNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditTextNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsNewPwdEyeCheck = true;
                    mImageBtnEyeNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditTextNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mImageBtnEyeNewPasswordConfirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsNewPwdConfirmEyeCheck) {
                    mIsNewPwdConfirmEyeCheck = false;
                    mImageBtnEyeNewPasswordConfirm.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditTextNewPasswordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsNewPwdConfirmEyeCheck = true;
                    mImageBtnEyeNewPasswordConfirm.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditTextNewPasswordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        String titleText = getString(R.string.btn_change_pwd);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        StyleSpan span = new StyleSpan(Typeface.BOLD);

        ssBuilder.setSpan(
                span,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setTitle(ssBuilder);
        builder.setView(newPlanDialog);
        builder.setNegativeButton(getString(R.string.btn_text_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(getString(R.string.btn_text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strNewPassword = mEditTextNewPassword.getText().toString();
                String strNewPasswordConfirm = mEditTextNewPasswordConfirm.getText().toString();

                if(StringTool.checkStringNotNull(strNewPassword)
                        && StringTool.checkStringNotNull(strNewPasswordConfirm)) {
                    if(mEditTextNewPassword.getText().toString().equals(mEditTextNewPasswordConfirm.getText().toString())){
                        mPerson.setNewPassword(mEditTextNewPassword.getText().toString());
                    } else
                        Toast.makeText(mActivity, getResources().getText(R.string.register_password_not_consistent), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(mActivity, getResources().getText(R.string.register_password_format_wrong), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void backToHomePage() {
        hideWaitingDialog();
        onBackPressed();
        finish();
    }

    private void updatePerson(Person person) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonUpdate(this, person);
    }

    private void setUiDeemPeopleByPerson(Person person) {
        String strDeemGoodFullText;
        String strDeemBadFullText;
        String strDeemGoodPeople = person.getGood();
        String strDeemBadPeople = person.getNoGood();

        strDeemGoodFullText = strDeemGoodPeople + getResources().getText(R.string.activity_detail_deem_good_people).toString();
        strDeemBadFullText = strDeemBadPeople + getResources().getText(R.string.activity_detail_deem_bad_people).toString();

        mTextViewDeemGood.setText(strDeemGoodFullText);
        mTextViewDeemBad.setText(strDeemBadFullText);
    }

    private void setViewUnderlineColor(View view, int iColor) {
        ColorStateList colorStateList = ColorStateList.valueOf(iColor);
        ViewCompat.setBackgroundTintList(view, colorStateList);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT)) {
            hideWaitingDialog();
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void returnUpdatePersonSuccess() {
        if(StringTool.checkStringNotNull(mPerson.getNewPassword())) {
            PreferenceManager.getInstance().setLoginPassword(mPerson.getNewPassword());
            mPerson.setPassword(mPerson.getNewPassword());
        }

        PersonManager.getInstance().setPerson(mPerson);
        PersonManager.getInstance().refresh(this);
    }

    @Override
    public void onRefreshDone(Person person) {
        hideWaitingDialog();
        Toast.makeText(mActivity, getResources().getText(R.string.person_data_update_success), Toast.LENGTH_SHORT).show();

        mPerson = person;
        refresh();
    }
}
