package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fff.ingood.R;

import java.util.Objects;

/**
 * Created by ElminsterII on 2018/5/28.
 */
public class WarningDialog extends DialogFragment {

    private String m_strMessage;
    private boolean m_bClickOutsideCancel;
    private WarningDialogEvent m_eventCB = null;

    public interface WarningDialogEvent {
        void onPositiveClick(DialogInterface dialog);
        void onNegativeClick(DialogInterface dialog);
    }

    private void initialize(WarningDialogEvent cb, String strMessage, boolean bClickOutsideCancel) {
        m_eventCB = cb;
        m_strMessage = strMessage;
        m_bClickOutsideCancel = bClickOutsideCancel;
    }

    public static WarningDialog newInstance(WarningDialogEvent cb, String strMessage, boolean bClickOutsideCancel) {
        WarningDialog dialog = new WarningDialog();
        dialog.initialize(cb, strMessage, bClickOutsideCancel);
        return dialog;
    }

    public static WarningDialog newInstance(WarningDialogEvent cb, String strMessage) {
        WarningDialog dialog = new WarningDialog();
        dialog.initialize(cb, strMessage, true);
        return dialog;
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setMessage(m_strMessage)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(m_eventCB != null)
                            m_eventCB.onPositiveClick(dialog);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(m_eventCB != null)
                            m_eventCB.onNegativeClick(dialog);
                    }
                });
        // Create the AlertDialog object and return it
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(m_bClickOutsideCancel);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
