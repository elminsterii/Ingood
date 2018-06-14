package com.fff.ingood.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.fff.ingood.R;
import com.fff.ingood.ui.CircleProgressBarDialog;

public class StartActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start_animation);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        initImage();
        //mWaitingDialog.show(getSupportFragmentManager(), MainActivity.class.getName());
        //FlowManager.getInstance().goLoginFlow(this);
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private void initImage() {
        ImageView iv_start = (ImageView) findViewById(R.id.iv_start);
        iv_start.setImageResource(R.drawable.landingpage_bg);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnimation);
    }

    private void startActivity() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
