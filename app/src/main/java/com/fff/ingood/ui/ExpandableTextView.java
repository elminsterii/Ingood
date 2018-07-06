package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fff.ingood.R;

/**
 * Created by ElminsterII on 2018/6/26.
 */
@SuppressLint("AppCompatCustomView")
public class ExpandableTextView extends TextView implements View.OnClickListener {

    private static final int DEFAULT_EXPAND_ICON = R.drawable.ic_arrow_drop_down;
    private static final int DEFAULT_COLLAPSED__ICON = R.drawable.ic_arrow_drop_up;
    private static final int DEFAULT_MAX_LINES = 4;

    private int iMaxLines = DEFAULT_MAX_LINES;
    private int mResExpandIconResId = DEFAULT_EXPAND_ICON;
    private int mResCollapsedIconResId = DEFAULT_COLLAPSED__ICON;

    public ExpandableTextView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public void setExpandIcon(int resExpandIconResId) {
        mResExpandIconResId = resExpandIconResId;
    }

    public void setCollapsedIcon(int resCollapsedIconResId) {
        mResCollapsedIconResId = resCollapsedIconResId;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        post(new Runnable() {
            public void run() {
                if (getLineCount() > iMaxLines)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, mResExpandIconResId);
                else
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                setMaxLines(iMaxLines);
            }
        });
    }

    public void setMaxLine(int maxLines) {
        iMaxLines = maxLines;
    }

    @Override
    public void onClick(View v) {
        if (getMaxLines() == Integer.MAX_VALUE) {
            super.setMaxLines(iMaxLines);
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, mResExpandIconResId);
        }
        else {
            super.setMaxLines(Integer.MAX_VALUE);
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, mResCollapsedIconResId);
        }
    }
}