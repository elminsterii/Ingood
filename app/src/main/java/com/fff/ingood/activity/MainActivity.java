package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.CircleProgressBarDialog;

import static com.fff.ingood.global.GlobalProperty.STARTUP_ANIMATION_DURATION;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class MainActivity extends AppCompatActivity implements Flow.FlowLogicCaller {

    private MainActivity mActivity;
    private CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApplication();

        mWaitingDialog = new CircleProgressBarDialog();
        mActivity = this;

        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_MAIN).setSystemUI(this);
    }

    private void initApplication() {
        TagManager.getInstance(this);
        PreferenceManager.getInstance(this);
        ServerResponse.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startStartupAnimation();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        hideWaitingDialog();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(MainActivity.class)) {
                mActivity.startActivity(new Intent(this, clsFlow));
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }

    private void startStartupAnimation() {
        ImageView imgStartupAnimation = findViewById(R.id.imgStartupAnimation);
        imgStartupAnimation.setImageResource(R.drawable.landingpage_logo);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setDuration(STARTUP_ANIMATION_DURATION);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showWaitingDialog(MainActivity.class.getName());
                FlowManager.getInstance().goLoginFlow(mActivity);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgStartupAnimation.startAnimation(scaleAnimation);
    }

    private void showWaitingDialog(final String strTag) {
        if(!StringTool.checkStringNotNull(strTag))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWaitingDialog.show(getSupportFragmentManager(), strTag);
            }
        });
    }

    private void hideWaitingDialog() {
        if(mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();
    }
}