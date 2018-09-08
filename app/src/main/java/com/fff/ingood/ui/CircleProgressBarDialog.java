package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fff.ingood.R;

/**
 * Created by ElminsterII on 2018/5/28.
 */
public class CircleProgressBarDialog extends DialogFragment {

    private Dialog m_dialog;
    private long m_timeout;

    private static final int DEFAULT_TIMEOUT_MS = 20 * 1000;

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.ui_progress_bar_loading_circle, null));

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        m_dialog = dialog;

        setTimeout(DEFAULT_TIMEOUT_MS);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(m_timeout > 0)
            applyTimeout(m_timeout);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setTimeout(long millis) {
        m_timeout = millis;
    }

    private void applyTimeout(long millis) {
        if(m_dialog == null)
            return;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                m_dialog.dismiss();
            }
        }, millis);
    }
}
