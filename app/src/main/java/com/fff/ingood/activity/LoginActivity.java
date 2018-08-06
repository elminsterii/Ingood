package com.fff.ingood.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.PersonCheckExistLogic;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.third_party.GoogleSignInManager;
import com.fff.ingood.tools.ImageHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_HEIGHT;
import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_WIDTH;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_GOOGLE_SIGNIN_FAIL;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class LoginActivity extends BaseActivity implements PersonCheckExistLogic.PersonCheckExistLogicCaller, ImageHelper.loadBitmapFromURLEvent {

    private static final int REQUEST_CODE_GOOGLE_SINGIN = 1011;

    private Button mButton_SignIn;
    private Button mButton_Register;
    private ImageButton mButtonGoogleSignIn;

    private LoginActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.signin_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void preInit() {
        mActivity = this;
    }

    @Override
    protected void initView(){
        mButton_SignIn = findViewById(R.id.btn_signin);
        mButton_Register = findViewById(R.id.btn_register);
        mButtonGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initListener(){
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, LoginAccountActivity.class));
            }
        });

        mButton_Register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, RegistrationFragmentActivity.class));
            }
        });

        mButtonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(LoginActivity.class.getName());
                googleSignIn();
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_LOGIN).setSystemUI(this);
    }

    private void googleSignIn() {
        Intent signInIntent = GoogleSignInManager.getInstance().geGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SINGIN);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null) {
                GoogleSignInManager.getInstance().setGoogleSignInAccount(account);
                PreferenceManager.getInstance().setLoginByGoogle(true);

                Person person = new Person();
                person.setEmail(account.getEmail());
                checkPersonExist(person);
            }
        } catch (ApiException e) {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(STATUS_CODE_GOOGLE_SIGNIN_FAIL), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPersonExist(Person person) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonCheckExist(this, person);
    }

    private void loadBitmapFromURL(String strURL) {
        ImageHelper.loadBitmapFromURL(strURL, this);
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        hideWaitingDialog();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && clsFlow != LoginActivity.class) {
                if(clsFlow == HomeActivity.class)
                    Toast.makeText(mActivity, getResources().getText(R.string.login_success), Toast.LENGTH_SHORT).show();

                mActivity.startActivity(new Intent(this, clsFlow));
                mActivity.finish();
            }
        } else {
            PreferenceManager.getInstance().setLoginByGoogle(false);
            PreferenceManager.getInstance().setLoginByFacebook(false);
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GOOGLE_SINGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(iStatusCode != null) {
            if(iStatusCode.equals(STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT)) {
                boolean bByGoogleSignIn = PreferenceManager.getInstance().getLoginByGoogle();
                GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();

                if(bByGoogleSignIn && googleSignInAccount != null) {
                    Person person = new Person();
                    person.setEmail(googleSignInAccount.getEmail());
                    person.setPassword(googleSignInAccount.getId());
                    FlowManager.getInstance().goLoginFlow(this, person);
                }
            }
        }
    }

    @Override
    public void onPersonNotExist() {
        boolean bByGoogleSignIn = PreferenceManager.getInstance().getLoginByGoogle();
        GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();

        if(bByGoogleSignIn && googleSignInAccount != null) {
            if(googleSignInAccount.getPhotoUrl() != null)
                loadBitmapFromURL(googleSignInAccount.getPhotoUrl().toString());
            else {
                Person personNew = new Person();
                personNew.setEmail(googleSignInAccount.getEmail());
                personNew.setPassword(googleSignInAccount.getId());
                personNew.setName(googleSignInAccount.getDisplayName());
                personNew.setAge("10");              //change by user in person data page.
                personNew.setGender("F");           //change by user in person data page.
                personNew.setVerifyCode("5454");
                FlowManager.getInstance().goRegistrationFlow(this, personNew);
            }
        }
    }

    @Override
    public void returnBitmap(Bitmap bm) {
        if (bm != null) {
            bm = ImageHelper.resizeBitmap(bm, PERSON_ICON_WIDTH, PERSON_ICON_HEIGHT);

            GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();
            Person personNew = new Person();
            personNew.setEmail(googleSignInAccount.getEmail());
            personNew.setPassword(googleSignInAccount.getId());
            personNew.setName(googleSignInAccount.getDisplayName());
            personNew.setAge("10");              //change by user in person data page.
            personNew.setGender("F");           //change by user in person data page.
            personNew.setVerifyCode("5454");
            FlowManager.getInstance().goRegistrationFlow(this, personNew, bm);
        }
    }
}
