package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.tools.StringTool;

import java.util.Objects;

/**
 * Created by ElminsterII on 2018/5/28.
 */
public class ConfirmDialogWithTextContent extends DialogFragment {

    private String m_strTextTitle;
    private String m_strDefaultTextContent;
    private int m_editTextMaxLength = 0;
    private TextContentEventCB m_eventCB = null;

    public interface TextContentEventCB {
        void onPositiveClick(String strTextContent);
    }

    private void initialize(TextContentEventCB cb, String strTextTitle, String strDefaultTextContent, int editTextMaxLength) {
        m_eventCB = cb;
        m_strTextTitle = strTextTitle;
        m_strDefaultTextContent = strDefaultTextContent;
        m_editTextMaxLength = editTextMaxLength;
    }

    public static ConfirmDialogWithTextContent newInstance(TextContentEventCB cb, String strTextTitle, String strDefaultTextContent, int editTextMaxLength) {
        ConfirmDialogWithTextContent dialog = new ConfirmDialogWithTextContent();
        dialog.initialize(cb, strTextTitle, strDefaultTextContent, editTextMaxLength);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //show keyboard always.
        Objects.requireNonNull(getDialog().getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.layout_confirm_dialog_with_text_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editTextContent = view.findViewById(R.id.editTextContent);
        if(m_editTextMaxLength > 0)
            editTextContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(m_editTextMaxLength), StringTool.getInputFilterForEditText()});

        if(StringTool.checkStringNotNull(m_strDefaultTextContent)) {
            editTextContent.setText(m_strDefaultTextContent);
            editTextContent.setSelection(editTextContent.getText().length());
        }

        TextView textViewTextContentTitle = view.findViewById(R.id.textViewTextContentTitle);
        textViewTextContentTitle.setText(m_strTextTitle);

        view.findViewById(R.id.btnTextContentLeftBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnTextContentRightBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_eventCB.onPositiveClick(editTextContent.getText().toString());
                dismiss();
            }
        });
    }
}
