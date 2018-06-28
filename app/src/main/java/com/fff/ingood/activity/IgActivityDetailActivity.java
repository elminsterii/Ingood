package com.fff.ingood.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonQueryLogic;
import com.fff.ingood.ui.ExpandableTextView;
import com.fff.ingood.ui.HeadZoomScrollView;

import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityDetailActivity extends BaseActivity implements PersonQueryLogic.PersonQueryLogicCaller {

    private AppCompatImageView mImageViewBack;
    private HeadZoomScrollView mZoomView;
    private ImageView mImageViewIgActivityMain;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;
    private TextView mTextViewIgPublisherName;
    private ExpandableTextView mTextViewDescription;

    private IgActivity mIgActivity;
    private Person mPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ig_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void preInit() {
        mIgActivity = (IgActivity)getIntent().getSerializableExtra(TAG_IGACTIVITY);
    }

    @Override
    protected void initView() {
        mImageViewBack = findViewById(R.id.imageViewBack);
        mZoomView = findViewById(R.id.zoomViewIgActivity);
        mImageViewIgActivityMain = findViewById(R.id.imageViewIgActivityMain);
        mTextViewTitle = findViewById(R.id.textViewIgActivityTitle);
        mTextViewDate = findViewById(R.id.textViewIgActivityDate);
        mTextViewLocation = findViewById(R.id.textViewIgActivityLocation);
        mTextViewIgPublisherName = findViewById(R.id.textViewIgActivityPublisherName);
        mTextViewDescription = findViewById(R.id.textViewIgActivityDescription);
    }

    @Override
    protected void initData() {
        if(mIgActivity == null)
            return;

        String strDate = IgActivityHelper.makeDateStringByIgActivity(mIgActivity);

        mTextViewTitle.setText(mIgActivity.getName());
        mTextViewDate.setText(strDate);
        mTextViewLocation.setText(mIgActivity.getLocation());

        mTextViewDescription.setCollapsedIcon(R.drawable.ic_arrow_drop_up);
        mTextViewDescription.setExpandIcon(R.drawable.ic_arrow_drop_down);
        mTextViewDescription.setMaxLine(4);
        mTextViewDescription.setText(mIgActivity.getDescription());

        getPublisherByIgActivity(mIgActivity);
    }

    @Override
    protected void initListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getPublisherByIgActivity(IgActivity activity) {
        if(activity == null)
            return;

        String strPublisherEmail = activity.getPublisherEmail();

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonQuery(this, strPublisherEmail, true);

        showWaitingDialog(IgActivityDetailActivity.class.getName());
    }

    @Override
    public void returnPersons(List<Person> lsPersons) {
        hideWaitingDialog();

        if(lsPersons != null && lsPersons.size() > 0) {
            mPublisher = lsPersons.get(0);
            mTextViewIgPublisherName.setText(mPublisher.getName());
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        hideWaitingDialog();

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }
}
