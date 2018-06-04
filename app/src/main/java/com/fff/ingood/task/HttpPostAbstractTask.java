package com.fff.ingood.task;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by yoie7 on 2018/5/16.
 */


public abstract class HttpPostAbstractTask<Object> extends AsyncTask<Object,Void,String> {

    Activity mActivity;
    private AsyncResponder<String> mResponder;

    public HttpPostAbstractTask() {}

    public HttpPostAbstractTask(Activity activity){
        mActivity = activity;
    }

    public HttpPostAbstractTask(AsyncResponder<String> responder) {
        mResponder = responder;
    }

    public HttpPostAbstractTask(Activity activity, AsyncResponder<String> responder) {
        mActivity = activity;
        mResponder = responder;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public String doInBackground(Object... arg0) {

        return access(mActivity, arg0[0]);
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result != null) {
            if (mResponder != null)
                mResponder.onSuccess(result);
        }
        else {
            if (mResponder != null)
                mResponder.onFailure();
        }
    }

    protected abstract String access(Activity activity, Object value);
}