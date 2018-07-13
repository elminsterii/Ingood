package com.fff.ingood.logic;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public abstract class Logic {

    public interface LogicCaller {
        void returnStatus(Integer iStatusCode);
    }

    LogicCaller mCaller;

    Logic(LogicCaller caller) {
        mCaller = caller;
    }

    protected abstract void doLogic();
}
