package com.fff.ingood.task;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public abstract class AsyncHttpDownloadResponder<VOUT_STATUS, VOUT_DATA> {
    private VOUT_STATUS mStatus = null;
    private VOUT_DATA mData = null;

    public abstract void onSuccess(VOUT_DATA data);
    public abstract void onFailure(VOUT_STATUS status);

    public VOUT_STATUS getStatus() {
        return mStatus;
    }

    public void setStatus(VOUT_STATUS mStatus) {
        this.mStatus = mStatus;
    }

    public VOUT_DATA getData() {
        return mData;
    }

    public void setData(VOUT_DATA data) {
        mData = data;
    }
}
