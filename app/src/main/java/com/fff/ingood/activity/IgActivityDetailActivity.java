package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Comment;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.DeemInfoManager;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.global.TagManager;
import com.fff.ingood.logic.CommentCreateLogic;
import com.fff.ingood.logic.CommentLogicExecutor;
import com.fff.ingood.logic.CommentQueryLogic;
import com.fff.ingood.logic.IgActivityAttendLogic;
import com.fff.ingood.logic.IgActivityDeemLogic;
import com.fff.ingood.logic.IgActivityDeleteLogic;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityQueryLogic;
import com.fff.ingood.logic.PersonIconComboLogic_MultiPersonMainIconsDownload;
import com.fff.ingood.logic.PersonIconComboLogic_PersonMainIconDownload;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonQueryLogic;
import com.fff.ingood.logic.PersonSaveIgActivityLogic;
import com.fff.ingood.task.wrapper.IgActivityAttendTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityDeemTaskWrapper;
import com.fff.ingood.task.wrapper.PersonSaveIgActivityTaskWrapper;
import com.fff.ingood.tools.DateHelper;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.CommentPublishDialog;
import com.fff.ingood.ui.ExpandableTextView;
import com.fff.ingood.ui.WarningDialog;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class IgActivityDetailActivity extends BaseActivity implements
        PersonQueryLogic.PersonQueryLogicCaller
        , PersonSaveIgActivityLogic.PersonSaveIgActivityLogicCaller
        , PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller
        , PersonIconComboLogic_MultiPersonMainIconsDownload.MultiPersonMainIconsDownloadLogicCaller
        , IgActivityDeemLogic.IgActivityDeemLogicCaller
        , IgActivityQueryLogic.IgActivityQueryLogicCaller
        , IgActivityAttendLogic.IgActivityAttendLogicCaller
        , IgActivityDeleteLogic.IgActivityDeleteLogicCaller
        , CommentQueryLogic.CommentQueryLogicCaller
        , CommentCreateLogic.CommentCreateLogicCaller {

    private enum UPDATE_IGACTIVITY_UI_SECTION {
        uiSecAll, uiSecBasic, uiSecDeem, uiSecAttendees
    }

    private ImageButton mImageViewBack;
    private ImageView mImageViewShare;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;
    private TextView mTextViewIgPublisherName;
    private FrameLayout mLayoutPublisherIcon;
    private LinearLayout mLayoutAttendeesIcons;
    private ExpandableTextView mTextViewDescription;
    private LinearLayout mLayoutTagBar;
    private TextView mTextViewAttention;
    private ImageView mImageViewDeemGood;
    private ImageView mImageViewDeemBad;
    private TextView mTextViewDeemGood;
    private TextView mTextViewDeemBad;
    private LinearLayout mLayoutComments;
    private ImageView mImageViewSaveIgActivity;
    private TextView mTextViewPublishComment;

    private Button mBtnLeftBottom;
    private Button mBtnRightBottom;

    private IgActivity mIgActivity;
    private ImageView mImageViewIgActivityMain;

    private ImageView mImageViewPublisherIcon;

    private List<ImageView> m_lsImageViewAttendeeIcons;
    private List<ImageView> m_lsImageViewCommentIcons;

    private int mTagBarWidth;
    private boolean m_bIsGetTagBarWidth = false;
    private DeemInfoManager.DEEM_INFO mCurDeemInfo;
    private boolean m_bIsIgActivityOwner;
    private boolean m_bIsAttended;
    private boolean m_bIsSave;

    private static final String LOGIC_TAG_DOWNLOAD_ATTENDEES_ICONS = "attendees_icons_download";
    private static final String LOGIC_TAG_DOWNLOAD_COMMENT_PUBLISHER_ICONS = "comment_publisher_icons_download";

    private UPDATE_IGACTIVITY_UI_SECTION m_updateUiScetion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ig_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        showWaitingDialog(HomeActivity.class.getName());
        refresh(UPDATE_IGACTIVITY_UI_SECTION.uiSecAll);
    }

    @Override
    protected void preInit() {
        mIgActivity = (IgActivity)getIntent().getSerializableExtra(TAG_IGACTIVITY);

        Person person = PersonManager.getInstance().getPerson();
        m_bIsIgActivityOwner = mIgActivity.getPublisherEmail().equals(person.getEmail());
    }

    @Override
    protected void initView() {
        mImageViewBack = findViewById(R.id.imageViewBack);
        mImageViewShare = findViewById(R.id.imageViewShare);
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
        mImageViewDeemGood = findViewById(R.id.btnIgActivityDeemGood);
        mImageViewDeemBad = findViewById(R.id.btnIgActivityDeemBad);
        mTextViewDeemGood = findViewById(R.id.textViewIgActivityDeemGood);
        mTextViewDeemBad = findViewById(R.id.textViewIgActivityDeemBad);
        mLayoutComments = findViewById(R.id.layoutComments);
        mImageViewSaveIgActivity = findViewById(R.id.imageViewIgActivitySave);
        mTextViewPublishComment = findViewById(R.id.textViewIgActivityPublishComment);

        mBtnLeftBottom = findViewById(R.id.btnIgActivityLeftBottom);
        mBtnRightBottom = findViewById(R.id.btnIgActivityRightBottom);
    }

    @Override
    protected void initData() {
        if(mIgActivity == null)
            return;

        setUiPublisherDefaultIcon();
        setUiIgActivityDefaultImage();

        showWaitingDialog(IgActivityDetailActivity.class.getName());
        getPublisherInfo();
    }

    @Override
    protected void initListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mImageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLayoutTagBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!m_bIsGetTagBarWidth) {
                    mTagBarWidth = mLayoutTagBar.getWidth();
                    m_bIsGetTagBarWidth = true;
                }
            }
        });

        mImageViewDeemGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!m_bIsIgActivityOwner) {
                    showWaitingDialog(IgActivityDetailActivity.class.getName());
                    deemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_GOOD);
                } else
                    Toast.makeText(mActivity, getResources().getText(R.string.deem_fail_in_your_own_igactivity), Toast.LENGTH_SHORT).show();
            }
        });

        mImageViewDeemBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!m_bIsIgActivityOwner) {
                    showWaitingDialog(IgActivityDetailActivity.class.getName());
                    deemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_BAD);
                } else
                    Toast.makeText(mActivity, getResources().getText(R.string.deem_fail_in_your_own_igactivity), Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewDeemGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!m_bIsIgActivityOwner) {
                    showWaitingDialog(IgActivityDetailActivity.class.getName());
                    deemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_GOOD);
                } else
                    Toast.makeText(mActivity, getResources().getText(R.string.deem_fail_in_your_own_igactivity), Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewDeemBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!m_bIsIgActivityOwner) {
                    showWaitingDialog(IgActivityDetailActivity.class.getName());
                    deemIgActivity(DeemInfoManager.DEEM_INFO.DEEM_BAD);
                } else
                    Toast.makeText(mActivity, getResources().getText(R.string.deem_fail_in_your_own_igactivity), Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewPublishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentPublishDialog.newInstance(new CommentPublishDialog.CommentPublishDialogEvent() {
                    @Override
                    public void onPublishClick(String strCommentContent) {
                        showWaitingDialog(IgActivityDetailActivity.class.getName());
                        publishComment(strCommentContent);
                    }
                }).show(getSupportFragmentManager(), IgActivityDetailActivity.class.getName());
            }
        });

        View.OnClickListener leftClickBtnListener;
        View.OnClickListener rightClickBtnListener;

        if(m_bIsIgActivityOwner) {
            leftClickBtnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WarningDialog.newInstance(new WarningDialog.WarningDialogEvent() {
                        @Override
                        public void onPositiveClick(DialogInterface dialog) {
                            showWaitingDialog(IgActivityDetailActivity.class.getName());
                            deleteIgActivity();
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    }, getResources().getString(R.string.dialog_delete_confirm_message)).show(getSupportFragmentManager(), IgActivityDetailActivity.class.getName());
                }
            };
            rightClickBtnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, IgActivityPublishActivity.class);
                    intent.putExtra(TAG_IGACTIVITY, mIgActivity);
                    mActivity.startActivity(intent);
                }
            };
        } else {
            leftClickBtnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO - report button
                }
            };
            rightClickBtnListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IgActivityAttendTaskWrapper.ATTEND_VALUE avAttend;

                    if(m_bIsAttended)
                        avAttend = IgActivityAttendTaskWrapper.ATTEND_VALUE.AV_CANCEL_ATTEND;
                    else
                        avAttend = IgActivityAttendTaskWrapper.ATTEND_VALUE.AV_ATTEND;

                    attendIgActivity(avAttend);
                }
            };
        }

        mBtnLeftBottom.setOnClickListener(leftClickBtnListener);
        mBtnRightBottom.setOnClickListener(rightClickBtnListener);

        mImageViewSaveIgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(IgActivityDetailActivity.class.getName());

                if(m_bIsSave)
                    saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE.SV_CANCEL_SAVE);
                else
                    saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE.SV_SAVE);
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_IGDETAIL).setSystemUI(this);
    }

    private void refresh(UPDATE_IGACTIVITY_UI_SECTION updateUiSection) {
        m_updateUiScetion = updateUiSection;
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doGetIgActivitiesData(this, mIgActivity.getId());
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

    private void getPublisherInfo() {
        if(mIgActivity == null)
            return;

        String strPublisherEmail = mIgActivity.getPublisherEmail();

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonQuery(this, strPublisherEmail, true);
    }

    private void setUiIgActivityDefaultImage() {
        mImageViewIgActivityMain.setImageResource(R.drawable.sample_activity);
    }

    private void setUiPublisherDefaultIcon() {
        mImageViewPublisherIcon = (ImageView)mLayoutPublisherIcon.getChildAt(0);
        mImageViewPublisherIcon.setImageResource(R.drawable.sample_activity);
    }

    private void downloadIcon_IgActivityPublisher(Person igActivityPublisher) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonMainIconDownload(this, igActivityPublisher.getEmail());
    }

    private void setUiAttendeesDefaultIcons(int iAttention) {
        mLayoutAttendeesIcons.removeAllViews();

        if(m_lsImageViewAttendeeIcons == null)
            m_lsImageViewAttendeeIcons = new ArrayList<>();
        m_lsImageViewAttendeeIcons.clear();

        for(int i=0; i<iAttention; i++)
            setAttendeesDefaultIcons();
    }

    private void downloadIcon_IgActivityAttendees(IgActivity activity) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doMultiPersonMainIconsDownload(this, activity.getAttendees(), LOGIC_TAG_DOWNLOAD_ATTENDEES_ICONS);
    }

    private void downloadIcon_IgActivityCommentPublishers(List<Comment> lsComments) {
        if(lsComments != null && lsComments.size() > 0) {
            StringBuilder strBuilder = new StringBuilder();

            for(Comment comment : lsComments) {
                strBuilder.append(comment.getPublisherEmail());
                strBuilder.append(",");
            }

            if(strBuilder.length() > 0)
                strBuilder.deleteCharAt(strBuilder.length()-1);

            PersonLogicExecutor executor = new PersonLogicExecutor();
            executor.doMultiPersonMainIconsDownload(this, strBuilder.toString(), LOGIC_TAG_DOWNLOAD_COMMENT_PUBLISHER_ICONS);
        }
    }

    private void setAttendeesDefaultIcons() {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") FrameLayout layout = (FrameLayout)inflater.inflate(R.layout.layout_person_thumbnail, null, false);
        ImageView imageViewIcon = (ImageView)layout.getChildAt(0);
        imageViewIcon.setImageResource(R.drawable.sample_activity);
        m_lsImageViewAttendeeIcons.add(imageViewIcon);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.gap_icons_attendees_ig_activity));
        layout.setLayoutParams(params);

        mLayoutAttendeesIcons.addView(layout);
    }

    private void setUiAttendeesWithDefaultIconByIgActivity(IgActivity activity) {
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

        setUiAttendeesDefaultIcons(iAttention);
    }

    private void addCommentInLayoutWithDefaultIcon(Comment comment) {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.layout_igactivity_comment, null, false);
        FrameLayout frameLayout = layout.findViewById(R.id.layoutCommentPublisherThumbnail);
        TextView textViewCommentPublisherName = layout.findViewById(R.id.textViewCommentPublisherName);
        TextView textViewCommentPublishDate = layout.findViewById(R.id.textViewCommentPublishDate);
        TextView textViewCommentContent = layout.findViewById(R.id.textViewCommentContent);

        ImageView imageViewIcon = (ImageView)frameLayout.getChildAt(0);
        imageViewIcon.setImageResource(R.drawable.sample_activity);
        m_lsImageViewCommentIcons.add(imageViewIcon);

        textViewCommentPublisherName.setText(comment.getDisplayName());
        textViewCommentPublishDate.setText(DateHelper.gmtToLocalTime(comment.getTs()));
        textViewCommentContent.setText(comment.getContent());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.gap_comment_comments_ig_activity));
        layout.setLayoutParams(params);

        mLayoutComments.addView(layout);
    }

    private void setUiBasicInfoByIgActivity(IgActivity activity) {
        String strDate = IgActivityHelper.makeDateStringByIgActivity(activity);

        mTextViewTitle.setText(activity.getName());
        mTextViewDate.setText(strDate);
        mTextViewLocation.setText(activity.getLocation());
        mTextViewDescription.setText(activity.getDescription());

        if(m_bIsGetTagBarWidth)
            setUiTagsByIgActivity(activity);
    }

    private void setUiDeemInfoByIgActivity(IgActivity activity) {
        mCurDeemInfo = PreferenceManager.getInstance().getDeemInfo(activity.getId());
        setUiDeemInfoByEnum(mCurDeemInfo);
    }

    private void setUiDeemInfoByEnum(DeemInfoManager.DEEM_INFO deemInfo) {
        switch(deemInfo) {
            case DEEM_GOOD :
                mImageViewDeemGood.setImageResource(R.drawable.good_d);
                mImageViewDeemBad.setImageResource(R.drawable.bad_n);
                mTextViewDeemGood.setTextColor(getResources().getColor(R.color.colorSlave));
                mTextViewDeemBad.setTextColor(getResources().getColor(R.color.colorSlave));
                break;

            case DEEM_BAD :
                mImageViewDeemGood.setImageResource(R.drawable.good_n);
                mImageViewDeemBad.setImageResource(R.drawable.bad_d);
                mTextViewDeemGood.setTextColor(getResources().getColor(R.color.colorSlave));
                mTextViewDeemBad.setTextColor(getResources().getColor(R.color.colorSlave));
                break;

            case DEEM_NONE :
                mImageViewDeemGood.setImageResource(R.drawable.good_n);
                mImageViewDeemBad.setImageResource(R.drawable.bad_n);
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

    private void setUiTagsByIgActivity(IgActivity activity) {
        mLayoutTagBar.removeAllViews();

        String[] arrTags = activity.getTags().split(",");
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
    }

    private void setUiBottomButtons() {
        m_bIsAttended = isAttended(mIgActivity, PersonManager.getInstance().getPerson());

        if(m_bIsIgActivityOwner) {
            mBtnLeftBottom.setText(getResources().getText(R.string.activity_action_delete));
            mBtnRightBottom.setText(getResources().getText(R.string.activity_action_edit));
        } else {
            mBtnLeftBottom.setText(getResources().getText(R.string.activity_action_report));

            if(m_bIsAttended)
                mBtnRightBottom.setText(getResources().getText(R.string.activity_action_no_attend));
            else
                mBtnRightBottom.setText(getResources().getText(R.string.activity_action_attend));
        }
    }

    private void getCommentsByIgActivity(IgActivity activity) {
        if(activity == null)
            return;

        String strActivityId = activity.getId();
        Comment comment = new Comment();
        comment.setActivityId(strActivityId);

        CommentLogicExecutor executor = new CommentLogicExecutor();
        executor.doSearchCommentsIds(this, comment);
    }

    private boolean isAttended(IgActivity activity, Person person) {
        boolean bRes = false;

        String strAttendeesId = activity.getAttendees();
        String strPersonId = person.getId();

        String[] arrIds = strAttendeesId.split(",");
        for(String strId : arrIds) {
            if(strId.equals(strPersonId)) {
                bRes = true;
                break;
            }
        }

        return bRes;
    }

    private void setUiSaveIgActivityByPerson(Person person) {
        String strSaveActivities = person.getSaveActivities();

        if(!StringTool.checkStringNotNull(strSaveActivities)) {
            String[] arrSaveActivities = strSaveActivities.split(",");
            for(String strActivityId : arrSaveActivities) {
                if(strActivityId.equals(mIgActivity.getId()))
                    setUiComponentSaveIgActivity(true);
                else
                    setUiComponentSaveIgActivity(false);
            }
        }
    }

    private void setUiComponentSaveIgActivity(boolean bSave) {
        if(bSave)
            mImageViewSaveIgActivity.setImageResource(R.drawable.bookmark_d_65);
        else
            mImageViewSaveIgActivity.setImageResource(R.drawable.bookmark_n_65);

        m_bIsSave = bSave;
    }

    private void attendIgActivity(IgActivityAttendTaskWrapper.ATTEND_VALUE avAttend) {
        Person person = PersonManager.getInstance().getPerson();
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();

        executor.doAttendIgActivity(this, person.getId(), person.getEmail()
                , person.getPassword(), mIgActivity.getId(), avAttend);
    }

    private void saveIgActivity(PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE svValue) {
        Person person = PersonManager.getInstance().getPerson();
        PersonLogicExecutor executor = new PersonLogicExecutor();

        executor.doSaveIgActivity(this, person.getEmail(), mIgActivity.getId(), svValue);
    }

    private void deemIgActivity(DeemInfoManager.DEEM_INFO deemInfo) {
        Person person = PersonManager.getInstance().getPerson();
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();

        IgActivityDeemTaskWrapper.DEEM_VALUE dvValue;
        boolean bIsDeemRollBack;

        if(deemInfo == DeemInfoManager.DEEM_INFO.DEEM_GOOD)
            if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_GOOD) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_NONE;
                dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = true;
            }
            else if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_NONE) {
                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_GOOD;
                dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = false;
            } else {
                executor.doDeemIgActivity(this, person.getEmail(), person.getPassword()
                        , mIgActivity.getId(), IgActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD, true);

                mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_GOOD;
                dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD;
                bIsDeemRollBack = false;
            }
        else
        if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_BAD) {
            mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_NONE;
            dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
            bIsDeemRollBack = true;
        }
        else if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_NONE) {
            mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_BAD;
            dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
            bIsDeemRollBack = false;
        } else {
            executor.doDeemIgActivity(this, person.getEmail(), person.getPassword()
                    , mIgActivity.getId(), IgActivityDeemTaskWrapper.DEEM_VALUE.DV_GOOD, true);

            mCurDeemInfo = DeemInfoManager.DEEM_INFO.DEEM_BAD;
            dvValue = IgActivityDeemTaskWrapper.DEEM_VALUE.DV_BAD;
            bIsDeemRollBack = false;
        }

        executor.doDeemIgActivity(this, person.getEmail(), person.getPassword()
                , mIgActivity.getId(), dvValue, bIsDeemRollBack);
    }

    private void deleteIgActivity() {
        Person personOwner = PersonManager.getInstance().getPerson();
        IgActivityLogicExecutor executor = new IgActivityLogicExecutor();
        executor.doDeleteIgActivity(this, mIgActivity.getId(), personOwner.getEmail(), personOwner.getPassword());
    }

    private void publishComment(String strCommentContent) {
        Person personPublisher = PersonManager.getInstance().getPerson();
        CommentLogicExecutor executor = new CommentLogicExecutor();
        executor.doCreateComment(this, personPublisher.getEmail(), personPublisher.getName(), mIgActivity.getId(), strCommentContent);
    }

    @Override
    public void returnPersons(List<Person> lsPersons) {
        hideWaitingDialog();

        if(lsPersons != null && lsPersons.size() > 0) {
            Person publisher = lsPersons.get(0);
            mTextViewIgPublisherName.setText(publisher.getName());

            setUiSaveIgActivityByPerson(publisher);
            setUiBottomButtons();

            downloadIcon_IgActivityPublisher(publisher);
        }
    }

    @Override
    public void returnDeemIgActivitySuccess() {
        hideWaitingDialog();

        refresh(UPDATE_IGACTIVITY_UI_SECTION.uiSecDeem);

        setUiDeemInfoByEnum(mCurDeemInfo);
        PreferenceManager.getInstance().setDeemInfo(mIgActivity.getId(), mCurDeemInfo);

        if(mCurDeemInfo == DeemInfoManager.DEEM_INFO.DEEM_NONE)
            Toast.makeText(mActivity, getResources().getText(R.string.deem_activity_has_been_recover), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mActivity, getResources().getText(R.string.deem_activity_has_been_sent), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnPersonMainIcon(Bitmap bmPersonIcon) {
        if(bmPersonIcon != null)
            mImageViewPublisherIcon.setImageBitmap(bmPersonIcon);
    }

    @Override
    public void returnPersonMainIcons(Bitmap[] arrPersonMainIcons, String strTag) {
        if(StringTool.checkStringNotNull(strTag)) {
            if(strTag.equals(LOGIC_TAG_DOWNLOAD_ATTENDEES_ICONS)) {
                if (arrPersonMainIcons.length == m_lsImageViewAttendeeIcons.size()) {
                    for (int i = 0; i < arrPersonMainIcons.length; i++) {
                        Bitmap bitmap = arrPersonMainIcons[i];
                        if (bitmap != null)
                            m_lsImageViewAttendeeIcons.get(i).setImageBitmap(bitmap);
                    }
                }
            } else if(strTag.equals(LOGIC_TAG_DOWNLOAD_COMMENT_PUBLISHER_ICONS)) {
                if (arrPersonMainIcons.length == m_lsImageViewCommentIcons.size()) {
                    for (int i = 0; i < arrPersonMainIcons.length; i++) {
                        Bitmap bitmap = arrPersonMainIcons[i];
                        if (bitmap != null)
                            m_lsImageViewCommentIcons.get(i).setImageBitmap(bitmap);
                    }
                }
            }
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(iStatusCode == null
                || iStatusCode.equals(STATUS_CODE_FAIL_COMMENT_NOT_FOUND_INT)
                || iStatusCode.equals(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT))
            return;

        hideWaitingDialog();

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateCommentSuccess(String strId) {
        getCommentsByIgActivity(mIgActivity);
        Toast.makeText(mActivity, getResources().getText(R.string.comment_has_been_published), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnDeleteIgActivitySuccess() {
        hideWaitingDialog();
        Toast.makeText(mActivity, getResources().getText(R.string.dialog_delete_igactivity_done_message), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void saveIgActivitySuccess() {
        hideWaitingDialog();
        setUiComponentSaveIgActivity(!m_bIsSave);
    }

    @Override
    public void returnComments(List<Comment> lsComments) {
        if(lsComments != null && lsComments.size() > 0) {
            mLayoutComments.removeAllViews();

            if(m_lsImageViewCommentIcons == null)
                m_lsImageViewCommentIcons = new ArrayList<>();
            m_lsImageViewCommentIcons.clear();

            for (Comment comment : lsComments)
                addCommentInLayoutWithDefaultIcon(comment);

            downloadIcon_IgActivityCommentPublishers(lsComments);
        }
    }

    @Override
    public void returnCommentsIds(String strCommentsIds) {
        CommentLogicExecutor executor = new CommentLogicExecutor();
        executor.doSearchComments(this, strCommentsIds);
    }

    @Override
    public void returnAttendIgActivitySuccess() {
        hideWaitingDialog();

        if(m_bIsAttended)
            Toast.makeText(mActivity, getResources().getText(R.string.no_attend_activity_success), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mActivity, getResources().getText(R.string.attend_activity_success), Toast.LENGTH_SHORT).show();

        refresh(UPDATE_IGACTIVITY_UI_SECTION.uiSecAttendees);
    }

    @Override
    public void returnIgActivities(List<IgActivity> lsActivities) {
        if(lsActivities != null && lsActivities.size() > 0) {
            mIgActivity = lsActivities.get(0);

            switch(m_updateUiScetion) {
                case uiSecAll :
                    setUiBasicInfoByIgActivity(mIgActivity);
                    setUiDeemInfoByIgActivity(mIgActivity);
                    setUiDeemPeopleByIgActivity(mIgActivity);
                    setUiAttendeesWithDefaultIconByIgActivity(mIgActivity);
                    downloadIcon_IgActivityAttendees(mIgActivity);
                    getCommentsByIgActivity(mIgActivity);
                    break;

                case uiSecBasic :
                    setUiBasicInfoByIgActivity(mIgActivity);
                    break;

                case uiSecDeem :
                    setUiDeemInfoByIgActivity(mIgActivity);
                    setUiDeemPeopleByIgActivity(mIgActivity);
                    break;

                case uiSecAttendees :
                    setUiAttendeesWithDefaultIconByIgActivity(mIgActivity);
                    downloadIcon_IgActivityAttendees(mIgActivity);
                    break;
            }
        }
        setUiBottomButtons();
    }

    @Override
    public void returnIgActivitiesIds(String strActivitiesIds) {
        //do not enter.
    }
}
