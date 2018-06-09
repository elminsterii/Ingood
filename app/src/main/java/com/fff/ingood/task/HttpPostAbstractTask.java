package com.fff.ingood.task;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by yoie7 on 2018/5/16.
 */


public abstract class HttpPostAbstractTask<VIN, VOUT> extends AsyncTask<VIN,Void,VOUT> {

    Activity mActivity;
    private AsyncResponder<VOUT> mResponder;

    public HttpPostAbstractTask() {}

    public HttpPostAbstractTask(Activity activity){
        mActivity = activity;
    }

    public HttpPostAbstractTask(AsyncResponder<VOUT> responder) {
        mResponder = responder;
    }

    public HttpPostAbstractTask(Activity activity, AsyncResponder<VOUT> responder) {
        mActivity = activity;
        mResponder = responder;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public VOUT doInBackground(VIN... arg0) {

        return parseFromResponse(access(mActivity, arg0[0]));
    }

    @Override
    public void onPostExecute(VOUT result) {
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

    protected abstract String access(Activity activity, VIN value);
    protected abstract VOUT parseFromResponse(String response);

}