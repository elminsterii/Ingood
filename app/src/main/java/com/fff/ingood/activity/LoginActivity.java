package com.fff.ingood.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_GOOGLE_SIGNIN_FAIL;import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */


public class LoginActivity extends BaseActivity implements PersonCheckExistLogic.PersonCheckExistLogicCaller, ImageHelper.loadBitmapFromURLEvent {

    private static final int REQUEST_CODE_GOOGLE_SINGIN = 1011;

    private Button mButton_SignIn;
    private Button mButton_Register;
    private ImageButton mButtonGoogleSignIn;

    private ImageButton mImageButton_FBLogin;

    private LoginActivity mActivity;

    // FB
    private LoginManager mLoginManager;
    private CallbackManager mCallbackManager;

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
        mImageButton_FBLogin = findViewById(R.id.btn_facebook);
        mButtonGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
    }

    @Override
    protected void initData(){
        // init facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        // init LoginManager & CallbackManager
        mLoginManager = LoginManager.getInstance();
        mCallbackManager = CallbackManager.Factory.create();    }

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

		mImageButton_FBLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFB();
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
    private void LogOutFB(){
        // Facebook Logout
        mLoginManager.logOut();
        }

	private void LoginFB() {
        // 設定FB login的顯示方式 ; 預設是：NATIVE_WITH_FALLBACK
        /**
         * 1. NATIVE_WITH_FALLBACK
         * 2. NATIVE_ONLY
         * 3. KATANA_ONLY
         * 4. WEB_ONLY
         * 5. WEB_VIEW_ONLY
         * 6. DEVICE_AUTH
         */
        mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        // 設定要跟用戶取得的權限，以下3個是基本可以取得，不需要經過FB的審核
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_friends");
                                // 設定要讀取的權限
                                mLoginManager.logInWithReadPermissions(this, permissions);
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (response.getConnection().getResponseCode() == 200) {
                                long id = object.getLong("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                Log.d("yoie", "Facebook id:" + id);
                                Log.d("yoie", "Facebook name:" + name);
                                Log.d("yoie", "Facebook email:" + email);
                                // 此時如果登入成功，就可以順便取得用戶大頭照
                                Profile profile = Profile.getCurrentProfile();
                                // 設定大頭照大小
                                Uri userPhoto = profile.getProfilePictureUri(300, 300);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // https://developers.facebook.com/docs/android/graph?locale=zh_TW
                // 如果要取得email，需透過添加參數的方式來獲取(如下)
                // 不添加只能取得id & name
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // 用戶取消
                Log.d("yoie", "Facebook onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                // 登入失敗
                Log.d("yoie", "Facebook onError:" + error.toString());
            }
        });
    }
}
