package com.fff.ingood.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.fff.ingood.third_party.FacebookSignInManager;
import com.fff.ingood.third_party.GoogleSignInManager;
import com.fff.ingood.tools.ImageHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_HEIGHT;
import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_WIDTH;
import static com.fff.ingood.global.GlobalProperty.VERIFY_CODE_FOR_FACEBOOK_SIGN;
import static com.fff.ingood.global.GlobalProperty.VERIFY_CODE_FOR_GOOGLE_SIGN;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_GOOGLE_SIGNIN_FAIL;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;
import static com.google.android.gms.common.api.CommonStatusCodes.NETWORK_ERROR;

/**
 * Created by yoie7 on 2018/5/3.
 */


public class LoginActivity extends BaseActivity implements PersonCheckExistLogic.PersonCheckExistLogicCaller
        , ImageHelper.loadBitmapFromURLEvent {

    private static final int REQUEST_CODE_GOOGLE_SIGNIN = 1011;

    private Button mButton_SignIn;
    private Button mButton_Register;
    private ImageButton mButtonGoogleSignIn;
    private ImageButton mButtonFacebookSignIn;

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
        mButtonFacebookSignIn = findViewById(R.id.btn_facebook);
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

		mButtonFacebookSignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(LoginActivity.class.getName());
                facebookSingIn();
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
        Intent signInIntent = GoogleSignInManager.getInstance().geGoogleSignInClient(this).getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGNIN);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null) {
                GoogleSignInManager.getInstance().setGoogleSignInAccount(account);
                PreferenceManager.getInstance().setLoginByGoogle(true);
                PreferenceManager.getInstance().setLoginByFacebook(false);

                Person person = new Person();
                person.setEmail(account.getEmail());
                checkPersonExist(person);
            }
        } catch (ApiException e) {
            if(e.getStatusCode() == NETWORK_ERROR)
                Toast.makeText(mActivity, getResources().getText(R.string.login_fail_nwk_error), Toast.LENGTH_SHORT).show();
            else
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

        if (requestCode == REQUEST_CODE_GOOGLE_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        } else {
            FacebookSignInManager.getInstance().getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(iStatusCode != null) {
            if(iStatusCode.equals(STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT)) {
                boolean bByGoogleSignIn = PreferenceManager.getInstance().getLoginByGoogle();
                boolean bByFaceBookSignIn = PreferenceManager.getInstance().getLoginByFacebook();

                if(bByGoogleSignIn) {
                    GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();

                    if(googleSignInAccount != null) {
                        Person person = new Person();
                        person.setEmail(googleSignInAccount.getEmail());
                        person.setPassword(googleSignInAccount.getId());
                        FlowManager.getInstance().goLoginFlow(this, person);
                    }
                } else if(bByFaceBookSignIn) {
                    Person fbSignInAccount = FacebookSignInManager.getInstance().getFBSignInAccount();

                    if(fbSignInAccount != null)
                        FlowManager.getInstance().goLoginFlow(this, fbSignInAccount);
                }
            }
        }
    }

    @Override
    public void onPersonNotExist() {
        boolean bByGoogleSignIn = PreferenceManager.getInstance().getLoginByGoogle();
        boolean bByFaceBookSignIn = PreferenceManager.getInstance().getLoginByFacebook();

        if(bByGoogleSignIn) {
            GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();

            if(googleSignInAccount != null) {
                if(googleSignInAccount.getPhotoUrl() != null)
                    loadBitmapFromURL(googleSignInAccount.getPhotoUrl().toString());
                else {
                    Person personNew = new Person();
                    personNew.setEmail(googleSignInAccount.getEmail());
                    personNew.setPassword(googleSignInAccount.getId());
                    personNew.setName(googleSignInAccount.getDisplayName());
                    personNew.setVerifyCode(VERIFY_CODE_FOR_GOOGLE_SIGN);
                    FlowManager.getInstance().goRegistrationFlow(this, personNew);
                }
            }
        } else if(bByFaceBookSignIn) {
            Profile profile = Profile.getCurrentProfile();
            if(profile != null
                    && profile.getProfilePictureUri(PERSON_ICON_WIDTH, PERSON_ICON_HEIGHT) != null){
                loadBitmapFromURL(profile.getProfilePictureUri(PERSON_ICON_WIDTH, PERSON_ICON_HEIGHT).toString());
            } else {
                Person fbSignInAccount = FacebookSignInManager.getInstance().getFBSignInAccount();

                if(fbSignInAccount != null) {
                    fbSignInAccount.setVerifyCode(VERIFY_CODE_FOR_FACEBOOK_SIGN);
                    FlowManager.getInstance().goRegistrationFlow(this, fbSignInAccount);
                }
            }
        }
    }

    @Override
    public void returnBitmap(Bitmap bm) {
        boolean bByGoogleSignIn = PreferenceManager.getInstance().getLoginByGoogle();
        boolean bByFaceBookSignIn = PreferenceManager.getInstance().getLoginByFacebook();

        if (bm != null) {
            if (bByGoogleSignIn) {
                bm = ImageHelper.resizeBitmap(bm, PERSON_ICON_WIDTH, PERSON_ICON_HEIGHT);

                GoogleSignInAccount googleSignInAccount = GoogleSignInManager.getInstance().getGoogleSignInAccount();
                Person personNew = new Person();
                personNew.setEmail(googleSignInAccount.getEmail());
                personNew.setPassword(googleSignInAccount.getId());
                personNew.setName(googleSignInAccount.getDisplayName());
                personNew.setVerifyCode(VERIFY_CODE_FOR_GOOGLE_SIGN);
                FlowManager.getInstance().goRegistrationFlow(this, personNew, bm);
            } else if(bByFaceBookSignIn) {
                Person fbSignInAccount = FacebookSignInManager.getInstance().getFBSignInAccount();

                if(fbSignInAccount != null) {
                    fbSignInAccount.setVerifyCode(VERIFY_CODE_FOR_FACEBOOK_SIGN);
                    FlowManager.getInstance().goRegistrationFlow(this, fbSignInAccount, bm);
                }
            }
        }
    }

	private void facebookSingIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn)
            LoginManager.getInstance().logOut();

        LoginManager loginManager = FacebookSignInManager.getInstance().getLoginManager();
        loginManager.setLoginBehavior(LoginBehavior.WEB_ONLY);
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        loginManager.logInWithReadPermissions(this, permissions);
        loginManager.registerCallback(FacebookSignInManager.getInstance().getCallbackManager(), new FacebookCallback<LoginResult>() {
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
                                String pwd = String.valueOf(id);

                                Person personFB = new Person();
                                personFB.setEmail(email);
                                personFB.setName(name);
                                personFB.setPassword(pwd);

                                FacebookSignInManager.getInstance().setFBSignInAccount(personFB);
                                PreferenceManager.getInstance().setLoginByGoogle(false);
                                PreferenceManager.getInstance().setLoginByFacebook(true);
                                checkPersonExist(personFB);
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}
