package com.fff.ingood.global;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by ElminsterII on 2018/9/6.
 */
public class PermissionHelper {
    public static boolean requestPermission(Activity activity, int iPermissionCode) {
        boolean bIsDenied = false;
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            String[] arrPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA};

            for (String strPermission : arrPermissions) {
                if (ContextCompat.checkSelfPermission(activity, strPermission) != PackageManager.PERMISSION_GRANTED) {
                    bIsDenied = true;
                    break;
                }
            }
            if(bIsDenied)
                ActivityCompat.requestPermissions(activity, arrPermissions, iPermissionCode);
        }
        return !bIsDenied;
    }
}
