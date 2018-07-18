package com.fff.ingood.task;

import android.os.AsyncTask;

/**
 * Created by ElminsterII on 2018/6/11.
 */


public abstract class HttpStreamTask<VIN, VOUT_STATUS, VOUT_DATA> extends AsyncTask<VIN, Void, VOUT_DATA> {

    private AsyncHttpStreamResponder<VOUT_STATUS, VOUT_DATA> mResponder;

    HttpStreamTask(AsyncHttpStreamResponder<VOUT_STATUS, VOUT_DATA> responder) {
        mResponder = responder;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @SafeVarargs
    @Override
    public final VOUT_DATA doInBackground(VIN... arg0) {
        return access(arg0[0]);
    }

    @Override
    public void onPostExecute(VOUT_DATA result) {
        super.onPostExecute(result);

        if(result != null)
            mResponder.onSuccess(result);
        else
            mResponder.onFailure(mResponder.getStatus());
    }

    protected abstract VOUT_DATA access(VIN value);
}