package com.fff.ingood.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
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

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonUpdateLogic;

public class PersonDataActivity extends BaseActivity implements PersonUpdateLogic.PersonUpdateLogicCaller{

    private ImageView mImageView_EditName;
    private ImageView mImageView_EditDescription;
    private ImageView mImageView_EditPhoto;
    private TextView mTextView_Name;
    private TextView mTextView_Description;
    private TextView mTextView_ChangePwd;
    private TextView mTextView_Mail;

    private Button mButton_Save;
    private Spinner mSpinner_Age;
    private Spinner mSpinner_Gender;
    private Spinner mSpinner_Location;
    private ImageView mImageView_HeadIcon;

    private PersonDataActivity mActivity;

    //for change password dialog
    private boolean mIsOldPwdEyeCheck = true;
    private boolean mIsNewPwdEyeCheck = true;
    private boolean mIsNewPwdConfirmEyeCheck = true;

    private EditText mEditText_OldPassword;
    private EditText mEditText_NewPassword;
    private EditText mEditText_NewPasswordConfirm;

    private ImageButton mImageButton_EyeOldPassword;
    private ImageButton mImageButton_EyeNewPassword;
    private ImageButton mImageButton_EyeNewPasswordConfirm;

    //for edit description dialog
    private EditText mEditText_EditDes;
    private TextView mTextView_DesLimitation;

    private ImageButton mBtnBack;
    private TextView mTextViewTitle;

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void preInit() {

    }

    @Override
    protected void initView(){
        mTextView_Name = findViewById(R.id.textViewPersonName);
        mTextView_Description = findViewById(R.id.textview_content_about_me);
        mTextView_ChangePwd = findViewById(R.id.textview_change_pwd);
        mTextView_Mail = findViewById(R.id.textViewEmail);

        mSpinner_Gender = findViewById(R.id.spinner_gender);
        mSpinner_Age = findViewById(R.id.spinner_age);
        mSpinner_Location = findViewById(R.id.spinner_location);

        mImageView_EditPhoto = findViewById(R.id.imageViewEditPhoto);
        mImageView_EditName = findViewById(R.id.imageViewEditName);
        mImageView_EditDescription = findViewById(R.id.imageview_edit_about_me);
        //mImageView_EditInterests = findViewById(R.id.imageview_edit_tags);


        mButton_Save = findViewById(R.id.btn_save);
        mBtnBack = findViewById(R.id.imgBack);
        mTextViewTitle = findViewById(R.id.textViewTitle);


        FrameLayout frameLayout = findViewById(R.id.layoutPersonThumbnailInPersonPage);
        mImageView_HeadIcon = (ImageView)frameLayout.getChildAt(0);
        mImageView_HeadIcon.setImageResource(R.drawable.ic_person_black_36dp);
    }

    @Override
    protected void initData(){

        Person person = PersonManager.getInstance().getPerson();
        mActivity = this;

        mTextView_Name.setText(person.getName());
        mTextView_Mail.setText(person.getEmail());
        mTextView_Description.setText(person.getDescription());
        mTextView_ChangePwd.setClickable(true);

        String[] arrAges = getResources().getStringArray(R.array.user_age_list);
        ArrayAdapter<String> spinnerAgeAdapter = new ArrayAdapter<>(mActivity, R.layout.spinner_item_in_person_page, arrAges);
        spinnerAgeAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinner_Age.setAdapter(spinnerAgeAdapter);

        String[] arrGender = getResources().getStringArray(R.array.user_gender_list);
        ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<>(mActivity, R.layout.spinner_item_in_person_page, arrGender);
        spinnerGenderAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinner_Gender.setAdapter(spinnerGenderAdapter);

        String[] arrLocation = getResources().getStringArray(R.array.user_location_list);
        ArrayAdapter<String> spinnerLocationAdapter = new ArrayAdapter<>(mActivity, R.layout.spinner_item_in_person_page, arrLocation);
        spinnerLocationAdapter.setDropDownViewResource(R.layout.spinner_item_in_person_page);
        mSpinner_Location.setAdapter(spinnerLocationAdapter);


        int index = 0;
        String strPersonAge = person.getAge();
        for(int i=0; i<arrAges.length; i++)
            if(strPersonAge.equals(arrAges[i]))
                index = i;
        mSpinner_Age.setSelection(index);

        index = 0;
        String strGender = person.getGender();
        for(int i=0; i<arrGender.length; i++)
            if(strGender.equals(arrGender[i]))
                index = i;
        mSpinner_Gender.setSelection(index);

        index = 0;
        String strLocation = person.getLocation();
        for(int i=0; i<arrLocation.length; i++)
            if(strLocation.equals(arrLocation[i]))
                index = i;
        mSpinner_Location.setSelection(index);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTextView_ChangePwd.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdDlg();

            }
        });


        mButton_Save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                boolean isGenderChanged = false;
                boolean isAgeChanged = false;
                boolean isLocationChanged = false;
                boolean isDescriptionChanged = false;

                if(!PersonManager.getInstance().getPerson().getGender().equals(String.valueOf(mSpinner_Gender.getSelectedItem()))){
                    person.setGender(String.valueOf(mSpinner_Gender.getSelectedItem()));
                    isGenderChanged = true;
                }

                if(!PersonManager.getInstance().getPerson().getAge().equals(String.valueOf(mSpinner_Age.getSelectedItem()))){
                    person.setAge(String.valueOf(mSpinner_Age.getSelectedItem()));
                    isAgeChanged = true;
                }

                if(!PersonManager.getInstance().getPerson().getLocation().equals(String.valueOf(mSpinner_Location.getSelectedItem()))){
                    person.setLocation(String.valueOf(mSpinner_Location.getSelectedItem()));
                    isLocationChanged = true;
                }
                if(!PersonManager.getInstance().getPerson().getDescription().equals(String.valueOf(mTextView_Description.getText()))){
                    person.setDescription(String.valueOf(mSpinner_Age.getSelectedItem()));
                    isDescriptionChanged = true;
                }

                if(!isAgeChanged && !isGenderChanged && !isLocationChanged && !isDescriptionChanged)
                    return;
                else{
                    person.setEmail(PersonManager.getInstance().getPerson().getEmail());
                    person.setPassword(PersonManager.getInstance().getPerson().getPassword());
                    PersonLogicExecutor executor = new PersonLogicExecutor();
                    executor.doPersonUpdate(mActivity, person);
                }


            }
        });

        mImageView_EditPhoto.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditName.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditDescription.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescriptionDlg();

            }
        });



//        mImageView_EditInterests.setOnClickListener(new ImageView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        mSpinner_Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View newPlanDialog = layoutInflater.inflate(R.layout.dialog_persondata_change_pwd,null);

        mEditText_OldPassword = (EditText) newPlanDialog.findViewById(R.id.edit_pwd);
        mEditText_NewPassword = (EditText) newPlanDialog.findViewById(R.id.edit_pwd_new);
        mEditText_NewPasswordConfirm = (EditText) newPlanDialog.findViewById(R.id.edit_pwd_new_confirm);

        mImageButton_EyeOldPassword = (ImageButton) newPlanDialog.findViewById(R.id.pwd_eye);
        mImageButton_EyeNewPassword = (ImageButton) newPlanDialog.findViewById(R.id.pwd_new_eye);
        mImageButton_EyeNewPasswordConfirm = (ImageButton) newPlanDialog.findViewById(R.id.pwd_new_confirm_eye);

        mIsOldPwdEyeCheck = true;
        mIsNewPwdEyeCheck = true;
        mIsNewPwdConfirmEyeCheck = true;

        mImageButton_EyeOldPassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsOldPwdEyeCheck) {
                    mIsOldPwdEyeCheck = false;
                    mImageButton_EyeOldPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditText_OldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsOldPwdEyeCheck = true;
                    mImageButton_EyeOldPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditText_OldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mImageButton_EyeNewPassword.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsNewPwdEyeCheck) {
                    mIsNewPwdEyeCheck = false;
                    mImageButton_EyeNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditText_NewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsNewPwdEyeCheck = true;
                    mImageButton_EyeNewPassword.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditText_NewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mImageButton_EyeNewPasswordConfirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsNewPwdConfirmEyeCheck) {
                    mIsNewPwdConfirmEyeCheck = false;
                    mImageButton_EyeNewPasswordConfirm.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditText_NewPasswordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsNewPwdConfirmEyeCheck = true;
                    mImageButton_EyeNewPasswordConfirm.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditText_NewPasswordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        builder.setPositiveButton(getString(R.string.btn_text_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.btn_text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mEditText_NewPassword.getText().equals(mEditText_NewPasswordConfirm.getText())
                        && mEditText_OldPassword.getText().toString().equals(PersonManager.getInstance().getPerson().getPassword())){
                    Person person = new Person();
                    person.setEmail(PersonManager.getInstance().getPerson().getEmail());
                    person.setPassword(PersonManager.getInstance().getPerson().getPassword());
                    person.setNewPassword(mEditText_NewPassword.getText().toString());
                    PersonLogicExecutor executor = new PersonLogicExecutor();
                    executor.doPersonUpdate(mActivity, person);
                }

            }
        });

        builder.show();
    }

    private void editDescriptionDlg(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View newPlanDialog = layoutInflater.inflate(R.layout.dialog_persondata_edit_description,null);

        mEditText_EditDes = (EditText) newPlanDialog.findViewById(R.id.edit_des);
        mTextView_DesLimitation = (TextView) newPlanDialog.findViewById(R.id.text_edit_limit);

        mEditText_EditDes.setText(mTextView_Description.getText().toString());

        mEditText_EditDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextView_DesLimitation.setText(String.valueOf(150 - mEditText_EditDes.getText().toString().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(newPlanDialog);
        builder.setPositiveButton(getString(R.string.btn_text_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.btn_text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTextView_Description.setText(mEditText_EditDes.getText());
                dialog.dismiss();
            }
        });

        builder.show();
    }



    @Override
    public void returnStatus(Integer iStatusCode) {

    }

    @Override
    public void returnUpdatePersonSuccess() {

    }






}
