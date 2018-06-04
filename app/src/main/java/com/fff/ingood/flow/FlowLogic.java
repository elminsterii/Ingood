package com.fff.ingood.flow;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public abstract class FlowLogic {

    public enum FLOW {
        FL_UNKNOWN
        ,FL_LOGIN
        ,FL_HOME
        ,FL_REGISTER_PRIMARY
        ,FL_REGISTER_VERIFY
        ,FL_REGISTER_LOCATION
        ,FL_REGISTER_INTEREST
    }

    public interface FlowLogicCaller {
        void returnFlow(boolean bSuccess, FLOW flow, Class<?> clsFlow);
    }

    FlowLogicCaller mCaller;

    FlowLogic(FlowLogicCaller caller) {
        mCaller = caller;
    }

    protected abstract FLOW doLogic();
}
