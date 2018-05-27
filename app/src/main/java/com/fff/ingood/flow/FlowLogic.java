package com.fff.ingood.flow;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public abstract class FlowLogic {
    public enum FLOW {
        FLOW_LOGIN
        , FLOW_HOME
        , FLOW_REGISTER
        , FLOW_UNKNOWN
    }

    public abstract FLOW decideFlow();
}
