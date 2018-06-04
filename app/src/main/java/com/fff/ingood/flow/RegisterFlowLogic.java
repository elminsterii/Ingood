package com.fff.ingood.flow;

public class RegisterFlowLogic extends FlowLogic {

    RegisterFlowLogic(FLOW curflow) {
        super(curflow);
    }

    @Override
    public FlowLogic.FLOW nextFlow() {
        FlowLogic.FLOW fRes;

        switch (mCurflow) {
            case FLOW_REGISTER_PRIMARY :
                fRes = FLOW.FLOW_REGISTER_LOCATION;
                break;
            case FLOW_REGISTER_LOCATION :
                fRes = FLOW.FLOW_REGISTER_INTERESTS;
                break;
            case FLOW_REGISTER_INTERESTS :
                fRes = FLOW.FLOW_HOME;
                break;
            default :
                fRes = FLOW.FLOW_REGISTER_PRIMARY;
                break;
        }
        return fRes;
    }
}