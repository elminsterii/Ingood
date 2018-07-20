package com.fff.ingood.logic;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public abstract class Logic {

    public interface LogicCaller {
        void returnStatus(Integer iStatusCode);
    }

    private String m_strTag;
    protected String getTag() {
        return m_strTag;
    }

    LogicCaller mCaller;
    Logic(LogicCaller caller) {
        mCaller = caller;
    }
    Logic(LogicCaller caller, String strTag) {
        mCaller = caller;
        m_strTag = strTag;
    }

    protected abstract void doLogic();
}
