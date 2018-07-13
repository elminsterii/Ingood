package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fff.ingood.R;

import java.util.Objects;

/**
 * Created by ElminsterII on 2018/5/28.
 */
public class CommentPublishDialog extends DialogFragment {

    private CommentPublishDialogEvent m_eventCB = null;

    public interface CommentPublishDialogEvent {
        void onPublishClick(String strCommentContent);
    }

    private void initialize(CommentPublishDialogEvent cb) {
        m_eventCB = cb;
    }

    public static CommentPublishDialog newInstance(CommentPublishDialogEvent cb) {
        CommentPublishDialog dialog = new CommentPublishDialog();
        dialog.initialize(cb);
        return dialog;
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
        return inflater.inflate(R.layout.layout_dialog_publish_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editTextCommentContent = view.findViewById(R.id.editTextPublishCommentContent);

        view.findViewById(R.id.btnCommentPublishLeftBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnCommentPublishRightBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_eventCB.onPublishClick(editTextCommentContent.getText().toString());
                dismiss();
            }
        });
    }
}
