package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonQueryLogic;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.ExpandableTextView;

import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityDetailActivity extends BaseActivity implements PersonQueryLogic.PersonQueryLogicCaller {

    private AppCompatImageView mImageViewBack;
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

    private IgActivity mIgActivity;
    private Person mPublisher;

    private int mTagBarWidth;
    private boolean m_bIsMakeTags = false;

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

        setIgActivityImageByIgActivity(mIgActivity);
        setPublisherByIgActivity(mIgActivity);
        setAttentionByIgActivity(mIgActivity);
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
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGDETAIL).setSystemUI(this);
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

    private void setIgActivityImageByIgActivity(IgActivity activity) {
        mImageViewIgActivityMain.setImageResource(R.drawable.sample_activity);
    }

    private void setPublisherByIgActivity(IgActivity activity) {
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

//        Bitmap bmSample = ImageHelper.loadBitmapFromResId(this, R.drawable.sample_activity);
//        bmSample = ImageHelper.resizeBitmap(bmSample, 100, 100);
//        imageViewIcon.setImageBitmap(bmSample);

        mLayoutAttendeesIcons.addView(layout);
    }

    private void setAttentionByIgActivity(IgActivity activity) {
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
    public void returnStatus(Integer iStatusCode) {
        hideWaitingDialog();

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }
}
