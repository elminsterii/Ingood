package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonUpdateLogic;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.data.Person.TAG_PERSON;

public class PersonDataActivity extends BaseActivity implements PersonUpdateLogic.PersonUpdateLogicCaller{

    private ImageView mImageViewEditName;
    private ImageView mImageViewEditDescription;
    private ImageView mImageViewEditPhoto;
    private TextView mTextViewPersonName;
    private TextView mTextViewPersonDescription;
    private TextView mTextViewChangePwd;
    private TextView mTextViewEmail;

    private Button mBtnSave;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerGender;
    private Spinner mSpinnerLocation;
    private ImageView mImageViewPersonIcon;

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
        mTextViewPersonName.setText(mPerson.getName());
        mTextViewEmail.setText(mPerson.getEmail());
        mTextViewPersonDescription.setText(mPerson.getDescription());
        mTextViewChangePwd.setClickable(true);

        String[] arrAges = getResources().getStringArray(R.array.user_age_list);
        ArrayAdapter<String> spinnerAgeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, arrAges);
        spinnerAgeAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerAge.setAdapter(spinnerAgeAdapter);

        String[] arrGender = getResources().getStringArray(R.array.user_gender_list);
        ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, arrGender);
        spinnerGenderAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerGender.setAdapter(spinnerGenderAdapter);

        String[] arrLocation = getResources().getStringArray(R.array.user_location_list);
        ArrayAdapter<String> spinnerLocationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_in_person_page, arrLocation);
        spinnerLocationAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinnerLocation.setAdapter(spinnerLocationAdapter);

        int index = 0;
        String strPersonAge = mPerson.getAge();
        for(int i=0; i<arrAges.length; i++)
            if(strPersonAge.equals(arrAges[i]))
                index = i;
        mSpinnerAge.setSelection(index);

        index = 0;
        String strGender = mPerson.getGender();
        for(int i=0; i<arrGender.length; i++)
            if(strGender.equals(arrGender[i]))
                index = i;
        mSpinnerGender.setSelection(index);

        index = 0;
        String strLocation = mPerson.getLocation();
        for(int i=0; i<arrLocation.length; i++)
            if(strLocation.equals(arrLocation[i]))
                index = i;
        mSpinnerLocation.setSelection(index);
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
                Person person = new Person();
                boolean isGenderChanged = false;
                boolean isAgeChanged = false;
                boolean isLocationChanged = false;
                boolean isDescriptionChanged = false;

                if(!mPerson.getGender().equals(String.valueOf(mSpinnerGender.getSelectedItem()))) {
                    person.setGender(String.valueOf(mSpinnerGender.getSelectedItem()));
                    isGenderChanged = true;
                }

                if(!mPerson.getAge().equals(String.valueOf(mSpinnerAge.getSelectedItem()))) {
                    person.setAge(String.valueOf(mSpinnerAge.getSelectedItem()));
                    isAgeChanged = true;
                }

                if(!mPerson.getLocation().equals(String.valueOf(mSpinnerLocation.getSelectedItem()))) {
                    person.setLocation(String.valueOf(mSpinnerLocation.getSelectedItem()));
                    isLocationChanged = true;
                }

                if(!mPerson.getDescription().equals(String.valueOf(mTextViewPersonDescription.getText()))) {
                    person.setDescription(String.valueOf(mSpinnerAge.getSelectedItem()));
                    isDescriptionChanged = true;
                }

                if(!isAgeChanged && !isGenderChanged && !isLocationChanged && !isDescriptionChanged) {
                }
                else {
                    person.setEmail(mPerson.getEmail());
                    person.setPassword(mPerson.getPassword());
                    updatePerson(person);
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
                    if(mEditTextNewPassword.getText().equals(mEditTextNewPasswordConfirm.getText())){
                        Person person = new Person();
                        person.setEmail(mPerson.getEmail());
                        person.setPassword(mPerson.getPassword());
                        person.setNewPassword(mEditTextNewPassword.getText().toString());
                        updatePerson(person);
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

    @Override
    public void returnStatus(Integer iStatusCode) {

    }

    @Override
    public void returnUpdatePersonSuccess() {
        PersonManager.getInstance().refresh();
    }
}
