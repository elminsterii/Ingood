package com.fff.ingood.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.fff.ingood.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ElminsterII on 2018/8/12.
 */
public class FileHelper {
    public static boolean isFromContentProvider(Uri uri) {
        final String CONTENT_PROVIDER_NAME = "contentprovider";
        String strPath = uri.toString();
        return strPath.contains(CONTENT_PROVIDER_NAME);
    }

    public static File copyContentProviderDataToFile(Context context, Uri uriContentProvider) {
        File file = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            is = context.getContentResolver().openInputStream(uriContentProvider);
            file = new File(context.getCacheDir(), "cachedImageForCrop");
            os = new FileOutputStream(file);

            if (is != null) {
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static Uri createUriFromFilePath(Context context, String strFilePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return createUriFromProvider(context, strFilePath);
        else
            return createUri(context, strFilePath);
    }

    private static Uri createUriFromProvider(Context context, String strName) {
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
        Uri uri;
        File fileDir = context.getExternalFilesDir(null);
        File file = new File(fileDir, strName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
        else
            uri = Uri.fromFile(file);

        return uri;
    }

    private static void writeBitmapToFile(Context context, Bitmap bm, String strName) {
        File fileBitmap = createFile(context, strName);
        try {
            FileOutputStream fos = new FileOutputStream(fileBitmap);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
