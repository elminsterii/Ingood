package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ElminsterII on 2018/6/26.
 */
@SuppressLint("AppCompatCustomView")
public class ExpandableTextView extends TextView implements View.OnClickListener {

    private static final int MAX_LINES = 5;
    private int currentMaxLines = Integer.MAX_VALUE;
    private int mResExpandIconResId;

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

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        post(new Runnable() {
            public void run() {
                if (getLineCount() > MAX_LINES)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, mResExpandIconResId);
                else
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                setMaxLines(MAX_LINES);
            }
        });
    }

    @Override
    public void setMaxLines(int maxLines) {
        currentMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    /* Custom method because standard getMaxLines() requires API > 16 */
    public int getMaxLines() {
        return currentMaxLines;
    }

    @Override
    public void onClick(View v) {
        /* Toggle between expanded collapsed states */
        if (getMaxLines() == Integer.MAX_VALUE)
            setMaxLines(MAX_LINES);
        else
            setMaxLines(Integer.MAX_VALUE);
    }
}