package com.fff.ingood.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fff.ingood.R;

import java.util.Objects;

/**
 * Created by ElminsterII on 2018/5/28.
 */
public class ShowBitmapDialog extends DialogFragment {

    private String m_strTextTitle;
    private Bitmap m_bm;

    private void initialize(String strTextTitle, Bitmap bm) {
        m_strTextTitle = strTextTitle;
        m_bm = bm;
    }

    public static ShowBitmapDialog newInstance(String strTextTitle, Bitmap bm) {
        ShowBitmapDialog dialog = new ShowBitmapDialog();
        dialog.initialize(strTextTitle, bm);
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
        return inflater.inflate(R.layout.layout_show_bitmap_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView imageViewShowBitmap = view.findViewById(R.id.imageViewShowBitmap);
        if(m_bm != null)
            imageViewShowBitmap.setImageBitmap(m_bm);

        TextView textViewTextContentTitle = view.findViewById(R.id.textViewTextContentTitle);
        textViewTextContentTitle.setText(m_strTextTitle);

        view.findViewById(R.id.btnTextContentRightBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
