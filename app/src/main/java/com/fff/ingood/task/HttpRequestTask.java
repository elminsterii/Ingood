package com.fff.ingood.task;

import android.os.AsyncTask;

/**
 * Created by ElminsterII on 2018/6/11.
 */


public abstract class HttpRequestTask<VIN, VOUT_STATUS, VOUT_DATA> extends AsyncTask<VIN, Void, String> {

    private AsyncHttpRequestResponder<VOUT_STATUS, VOUT_DATA> mResponder;

    HttpRequestTask(AsyncHttpRequestResponder<VOUT_STATUS, VOUT_DATA> responder) {
        mResponder = responder;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
    }

    @SafeVarargs
    @Override
    public final String doInBackground(VIN... arg0) {
        return access(arg0[0]);
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result != null && mResponder.parseResponse(result))
            mResponder.onSuccess(mResponder.getData());
        else
            mResponder.onFailure(mResponder.getStatus());
    }

    protected abstract String access(VIN value);
}