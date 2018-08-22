package com.fff.ingood.tools;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by ElminsterII on 2018/6/20.
 */
public class ImageHelper {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        canvas.drawRect(0, h/2, w/2, h, paint);
        canvas.drawRect(w/2, h/2, w, h, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap resizeBitmap(Bitmap bm, int iWidth, int iHeight) {
        return Bitmap.createScaledBitmap(bm, iWidth, iHeight, false);
    }

    public static Bitmap resizeBitmap(byte[] arrImageBytes, int iWidth, int iHeight) {
        Bitmap b = BitmapFactory.decodeByteArray(arrImageBytes, 0, arrImageBytes.length);
        return Bitmap.createScaledBitmap(b, iWidth, iHeight, false);
    }

    public static Bitmap loadBitmapFromResId(Context context, int iResId) {
        return BitmapFactory.decodeResource(context.getResources(), iResId);
    }

    public static Bitmap loadBitmapFromUri(Context context, Uri uriImage) {
        Bitmap bm = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(Objects.requireNonNull(uriImage));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(Objects.requireNonNull(inputStream));
            bm = BitmapFactory.decodeStream(bufferedInputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Uri genImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "TempImageSave", null);
        return Uri.parse(path);
    }

    public static void saveImageToUri(Context context, Bitmap bm, Uri uriImage)
    {
        String strImagePath = getRealPathFromUri(context, uriImage);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(strImagePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap makeBitmapCorrectOrientation(Bitmap srcBitmap, Uri uri, Context context) {
        Bitmap bmRes = srcBitmap;
        ExifInterface exif = null;
        String strImagePath = getRealPathFromUri(context, uri);

        try {
            exif = new ExifInterface(strImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = ExifInterface.ORIENTATION_NORMAL;

        if (exif != null)
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bmRes = rotateBitmap(srcBitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bmRes = rotateBitmap(srcBitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bmRes = rotateBitmap(srcBitmap, 270);
                break;

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                bmRes = flipBitmap(srcBitmap, true, false);
                break;

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                bmRes = flipBitmap(srcBitmap, false, true);
                break;
        }
        return bmRes;
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        String result = null;
        Cursor cursor = context.getContentResolver().query(uri, null,
                null, null, null);

        if (cursor == null)
            result = uri.getPath();
        else {
            cursor.moveToFirst();
            try {
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.close();
        }

        if (!StringTool.checkStringNotNull(result)) {
            String uriString = uri.toString();
            int index = uriString.lastIndexOf("/");
            String imageName = uriString.substring(index);
            File storageDir;

            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File file = new File(storageDir, imageName);
            if (file.exists()) {
                result = file.getAbsolutePath();
            } else {
                storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file1 = new File(storageDir, imageName);
                result = file1.getAbsolutePath();
            }
        }
        return result;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flipBitmap(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public interface loadBitmapFromURLEvent {
        void returnBitmap(Bitmap bm);
    }

    public static void loadBitmapFromURL(String strURL, final loadBitmapFromURLEvent event) {
        new LoadBitmapFromURLTask(event).execute(strURL);
    }

    private static class LoadBitmapFromURLTask extends AsyncTask<String, Void, Bitmap> {
        private loadBitmapFromURLEvent m_event;

        LoadBitmapFromURLTask(loadBitmapFromURLEvent event) {
            m_event = event;
        }

        protected Bitmap doInBackground(String... strURL) {
            try {
                URL url = new URL(strURL[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        public void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(m_event != null)
                m_event.returnBitmap(result);
        }
    }
}
