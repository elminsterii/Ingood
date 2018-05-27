package com.fff.ingood.task;

/**
 * Created by yoie7 on 2018/5/16.
 */

public abstract class AsyncResponder <V> {
    public abstract void onSuccess(V response);

    public void onFailure() {}
}
