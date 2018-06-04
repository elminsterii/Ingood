package com.fff.ingood.flow;

public class RegisterFlowLogic extends FlowLogic {
    public static final int REGISTER_FLOW_PRIMARY = 0;
    public static final int REGISTER_FLOW_VERIFY = 1;
    public static final int REGISTER_FLOW_LOCATION = 2;
    public static final int REGISTER_FLOW_INTERESTS = 3;
    @Override
    public FlowLogic.FLOW decideFlow() {
        FlowLogic.FLOW fRes;

        switch (PreferenceManager.getInstance().getRegisterCurFlow()){
            case REGISTER_FLOW_PRIMARY:{
                fRes = FLOW.FLOW_REGISTER_VERIFY;
                break;
            }
            case REGISTER_FLOW_VERIFY:{
                fRes = FLOW.FLOW_REGISTER_LOCATION;
                break;
            }
            case REGISTER_FLOW_LOCATION:{
                fRes = FLOW.FLOW_REGISTER_INTERESTS;
                break;
            }
            case REGISTER_FLOW_INTERESTS:{
                fRes = FLOW.FLOW_HOME;
                break;
            }
            default:{
                fRes = FLOW.FLOW_REGISTER_PRIMARY;
                break;
            }
        }
        return fRes;
    }
}