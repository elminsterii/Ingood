package com.fff.ingood.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fff.ingood.global.PersonManager;
import com.fff.ingood.task.wrapper.PersonLogoutTaskWrapper;

/**
 * Created by ElminsterII on 2018/7/26.
 */
public class IngoodService extends Service implements PersonLogoutTaskWrapper.PersonLogoutTaskWrapperCallback {
    private static final int DELAY_FOR_LOGOUT_TASK_MS = 3 * 1000;

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        PersonManager.getInstance().setLoginSuccess(false);
        PersonLogoutTaskWrapper logoutWrapper = new PersonLogoutTaskWrapper(this);
        logoutWrapper.execute(PersonManager.getInstance().getPerson());

        try {
            Thread.sleep(DELAY_FOR_LOGOUT_TASK_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLogoutSuccess() {

    }

    @Override
    public void onLogoutFailure(Integer iStatusCode) {

    }
}
