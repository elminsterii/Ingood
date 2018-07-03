package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.DeemInfoManager;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.logic.ActivityDeemLogic;
import com.fff.ingood.logic.ActivityLogicExecutor;
import com.fff.ingood.logic.ActivityQueryLogic;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonQueryLogic;
import com.fff.ingood.task.wrapper.ActivityDeemTaskWrapper;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.ExpandableTextView;

import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityDetailActivity extends BaseActivity implements
        PersonQueryLogic.PersonQueryLogicCaller
        , ActivityDeemLogic.ActivityDeemLogicCaller
        , ActivityQueryLogic.ActivityQueryLogicCaller {

    private ImageButton mImageViewBack;
    private ImageView mImageViewIgActivityMain;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;
    private TextView mTextViewIgPublisherName;
    private FrameLayout mLayoutPublisherIcon;
    private LinearLayout mLayoutAttendeesIcons;
    private ExpandableTextView mTextViewDescription;
    private LinearLayout mLayoutTagBar;
    private TextView mTextViewAttention;
    private ImageView mBtnDeemGood;
    private ImageView mBtnDeemBad;
    private TextView mTextViewDeemGood;
    private TextView mTextViewDeemBad;

    private IgActivity mIgActivity;
    private Person mPublisher;

    private int mTagBarWidth;
    private boolean m_bIsMakeTags = false;
    private DeemInfoManager.DEEM_INFO mCurDeemInfo;

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
        mImageViewIgActivityMain = findViewById(R.id.imageViewIgActivityMain);
        mTextViewTitle = findViewById(R.id.textViewIgActivityTitle);
        mTextViewDate = findViewById(R.id.textViewIgActivityDate);
        mTextViewLocation = findViewById(R.id.textViewIgActivityLocation);
        mTextViewIgPublisherName = findViewById(R.id.textViewIgActivityPublisherName);
        mLayoutPublisherIcon = findViewById(R.id.layoutIgActivityPublisherThumbnail);
        mLayoutAttendeesIcons = findViewById(R.id.layoutIgActivityAttendeesIcons);
        mTextViewDescription = findViewById(R.id.textViewIgActivityDescription);
        mLayoutTagBar = findViewById(R.id.layoutIgActivityTags);
        mTextViewAttention = findViewById(R.id.textViewIgActivityAttention);
        mBtnDeemGood = findViewById(R.id.btnIgActivityDeemGood);
        mBtnDeemBad = findViewById(R.id.btnIgActivityDeemBad);
        mTextViewDeemGood = findViewById(R.id.textViewIgActivityDeemGood);
        mTextViewDeemBad = findViewById(R.id.textViewIgActivityDeemBad);
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

        setUiIgActivityImageByIgActivity(mIgActivity);
        setUiPublisherByIgActivity(mIgActivity);
        setUiAttentionByIgActivity(mIgActivity);
        setUiDeemInfoByIgActivity(mIgActivity);
        setUiDeemPeopleByIgActivity(mIgActivity);
    }

    @Override
    protected void initListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mLayoutTagBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!m_bIsMakeTags) {
                    mTagBarWidth = mLayoutTagBar.getWidth();

                    String[] arrTags = mIgActivity.getTags().split(",");
                    List<String> lsTags = StringTool.arrayStringToListString(arrTags);

                    int iRemainTags = arrTags.length;
                    Integer resIdPreLayout = null;

                    while(iRemainTags > 0) {
                        RelativeLayout layout = makeTagBarLayout(mLayoutTagBar, resIdPreLayout);

                        int iShowTags = TagManager.getInstance().makeTagsInLayout(layout, lsTags.toArray(new String[lsTags.size()]), mTagBarWidth);
                        if(iShowTags == 0)
                            break;

                        iRemainTags -= iShowTags;
                        resIdPreLayout = layout.getId();

                        for(int i=0; i<iShowTags; i++)
                            lsTags.remove(0);
                    }

                    m_bIsMakeTags = true;
                }
            }
        });

        mBtnDeemGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(IgActivityDetailActivity.class.getName());
                DeemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_GOOD);
            }
        });

        mBtnDeemBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(IgActivityDetailActivity.class.getName());
                DeemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_BAD);
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGDETAIL).setSystemUI(this);
    }

    private void refreshUI(IgActivity activity) {
        setUiDeemPeopleByIgActivity(activity);
    }

    private RelativeLayout makeTagBarLayout(ViewGroup parent, Integer resIdBelowView) {

        RelativeLayout layout = new RelativeLayout(parent.getContext());
        layout.setId(View.generateViewId());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(resIdBelowView != null) {
            int iGapPx = parent.getResources().getDimensionPixelSize(R.dimen.gap_tags_row_ig_activity);
            params.addRule(RelativeLayout.BELOW, resIdBelowView);
            params.setMargins(0, iGapPx, 0, 0);
        }

        layout.setLayoutParams(params);
        parent.addView(layout);

        return layout;
    }

    private void setUiIgActivityImageByIgActivity(IgActivity activity) {
        mImageViewIgActivityMain.setImageResource(R.drawable.sample_activity);
    }

    private void setUiPublisherByIgActivity(IgActivity activity) {
        if(activity == null)
            return;

        String strPublisherEmail = activity.getPublisherEmail();

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonQuery(this, strPublisherEmail, true);

        showWaitingDialog(IgActivityDetailActivity.class.getName());
    }

    private void setPublisherIconByPerson(Person person) {
        ImageView imageViewIcon = (ImageView)mLayoutPublisherIcon.getChildAt(0);
        imageViewIcon.setImageResource(R.drawable.sample_activity);
    }

    private void setAttendeesIconByPerson(Person person) {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") FrameLayout layout = (FrameLayout)inflater.inflate(R.layout.layout_person_thumbnail, null, false);
        ImageView imageViewIcon = (ImageView)layout.getChildAt(0);
        imageViewIcon.setImageResource(R.drawable.sample_activity);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.gap_icons_attendees_ig_activity));
        layout.setLayoutParams(params);

        mLayoutAttendeesIcons.addView(layout);
    }

    private void setUiAttentionByIgActivity(IgActivity activity) {
        if(activity == null)
            return;

        String strAttention1 = mActivity.getResources().getText(R.string.activity_detail_attendees_count_1).toString();
        String strAttention2 = mActivity.getResources().getText(R.string.activity_detail_attendees_count_2).toString();
        String strAttention;

        int iAttention = Integer.parseInt(activity.getAttention());
        strAttention = strAttention1;
        strAttention += iAttention;
        strAttention += strAttention2;

        mTextViewAttention.setText(strAttention);

        for(int i=0; i<iAttention; i++)
            setAttendeesIconByPerson(mPublisher);
    }

    private void DeemIgActivity(DeemInfoManager.DEEM_INFO deemInfo) {
        Person person = PersonManager.getInstance().getPerson();
        ActivityLogicExecutor executor = new ActivityLogicExecutor();

        ActivityDeemTaskWrapper.DEEM_VALUE dvValue;
        boolean bIsDeemRollBack;

        if(deemInfo == DeemInfoManager.DEEM_INFO.DEEM_GOOD)
            if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_GOOD) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_NONE;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = true;
            }
            else if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_NONE) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_GOOD;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = false;
            } else {
                executor.doDeemActivity(this, person.getEmail(), person.getPassword()
                        , mIgActivity.getId(), ActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD, true);

                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_GOOD;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = false;
            }
        else
            if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_BAD) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_NONE;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
                bIsDeemRollBack = true;
            }
            else if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_NONE) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_BAD;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
                bIsDeemRollBack = false;
            } else {
                executor.doDeemActivity(this, person.getEmail(), person.getPassword()
                        , mIgActivity.getId(), ActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD, true);

                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_BAD;
                dvValue = ActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
                bIsDeemRollBack = false;
            }

        executor.doDeemActivity(this, person.getEmail(), person.getPassword()
                , mIgActivity.getId(), dvValue, bIsDeemRollBack);
    }

    private void setUiDeemInfoByIgActivity(IgActivity activity) {
        mCurDeemInfo = PreferenceManager.getInstance().getDeemInfo(activity.getId());
        setUiDeemInfoByEnum(mCurDeemInfo);
    }

    private void setUiDeemInfoByEnum(DeemInfoManager.DEEM_INFO deemInfo) {
        switch(deemInfo) {
            case DEEM_GOOD :
                mBtnDeemGood.setImageResource(R.drawable.good_d);
                mBtnDeemBad.setImageResource(R.drawable.bad_n);
                mTextViewDeemGood.setTextColor(getResources().getColor(R.color.colorDeemGood));
                mTextViewDeemBad.setTextColor(getResources().getColor(R.color.colorTextHint));
                break;

            case DEEM_BAD :
                mBtnDeemGood.setImageResource(R.drawable.good_n);
                mBtnDeemBad.setImageResource(R.drawable.bad_d);
                mTextViewDeemGood.setTextColor(getResources().getColor(R.color.colorTextHint));
                mTextViewDeemBad.setTextColor(getResources().getColor(R.color.colorDeemBad));
                break;

            case DEEM_NONE :
                mBtnDeemGood.setImageResource(R.drawable.good_n);
                mBtnDeemBad.setImageResource(R.drawable.bad_n);
                mTextViewDeemGood.setTextColor(getResources().getColor(R.color.colorTextHint));
                mTextViewDeemBad.setTextColor(getResources().getColor(R.color.colorTextHint));
                break;
        }
    }

    private void setUiDeemPeopleByIgActivity(IgActivity activity) {
        String strDeemGoodFullText;
        String strDeemBadFullText;
        String strDeemGoodPeople = activity.getGood();
        String strDeemBadPeople = activity.getNoGood();

        strDeemGoodFullText = strDeemGoodPeople + getResources().getText(R.string.activity_detail_deem_good_people).toString();
        strDeemBadFullText = strDeemBadPeople + getResources().getText(R.string.activity_detail_deem_bad_people).toString();

        mTextViewDeemGood.setText(strDeemGoodFullText);
        mTextViewDeemBad.setText(strDeemBadFullText);
    }

    @Override
    public void returnPersons(List<Person> lsPersons) {
        hideWaitingDialog();

        if(lsPersons != null && lsPersons.size() > 0) {
            mPublisher = lsPersons.get(0);
            mTextViewIgPublisherName.setText(mPublisher.getName());
            setPublisherIconByPerson(mPublisher);
        }
    }

    @Override
    public void returnDeemSuccess() {
        hideWaitingDialog();

        ActivityLogicExecutor executor = new ActivityLogicExecutor();
        executor.doGetActivitiesData(this, mIgActivity.getId());

        setUiDeemInfoByEnum(mCurDeemInfo);
        PreferenceManager.getInstance().setDeemInfo(mIgActivity.getId(), mCurDeemInfo);

        Toast.makeText(mActivity, getResources().getText(R.string.deem_activity_has_been_sent), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        hideWaitingDialog();

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnActivities(List<IgActivity> lsActivities) {
        if(lsActivities != null && lsActivities.size() > 0) {
            mIgActivity = lsActivities.get(0);
            refreshUI(mIgActivity);
        }
    }

    @Override
    public void returnActivitiesIds(String strActivitiesIds) {

    }
}
