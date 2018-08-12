package com.fff.ingood.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.fff.ingood.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ElminsterII on 2018/8/12.
 */
public class FileHelper {
    public static Uri createUriFromProvider(Context context, String strName) {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", createFile(context, strName));
    }

    public static Uri createUri(Context context, String strName) {
        return Uri.fromFile(createFile(context, strName));
    }

    public static Uri bitmapToUri(Context context, Bitmap bm) {
        final String TEMP_BITMAP_NAME = "ingood_temp_bm_to_uri.png";
        writeBitmapToFile(context, bm, TEMP_BITMAP_NAME);
        return readFileToUri(context, TEMP_BITMAP_NAME);
    }

    private static Uri readFileToUri(Context context, String strName) {
        File fileDir = context.getExternalFilesDir(null);
        File file = new File(fileDir, strName);
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
    }

    private static void writeBitmapToFile(Context context, Bitmap bm, String strName) {
        File fileBitmap = createFile(context, strName);
        try {
            FileOutputStream fos = new FileOutputStream(fileBitmap);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createFile(Context context, String strName) {
        File fileDir = context.getExternalFilesDir(null);
        File fileNew = null;
        try {
            fileNew = new File(fileDir, strName);
            if (!fileNew.exists())
                fileNew.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNew;
    }
}
