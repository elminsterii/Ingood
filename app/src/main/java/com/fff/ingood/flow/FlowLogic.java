package com.fff.ingood.flow;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public abstract class FlowLogic {
    public enum FLOW {
        FLOW_LOGIN
        , FLOW_HOME
        , FLOW_REGISTER_PRIMARY
        , FLOW_REGISTER_LOCATION
        , FLOW_REGISTER_INTERESTS
        , FLOW_UNKNOWN
    }

    FlowLogic.FLOW mCurflow;

    FlowLogic(FLOW curflow) {
        this.mCurflow = curflow;
    }

    public abstract FLOW nextFlow();
}
