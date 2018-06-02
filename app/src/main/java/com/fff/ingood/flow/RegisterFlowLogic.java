package com.fff.ingood.flow;

public class RegisterFlowLogic extends FlowLogic {
    @Override
    public FlowLogic.FLOW decideFlow() {
        FlowLogic.FLOW fRes;

        switch (PreferenceManager.getInstance().getRegisterCurFlow()){
            case 0:{
                fRes = FLOW.FLOW_REGISTER_LOCATION;
                break;
            }
            case 1:{
                fRes = FLOW.FLOW_REGISTER_INTERESTS;
                break;
            }
            case 2:{
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