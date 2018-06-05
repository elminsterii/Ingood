package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.RegisterInterestPageActivity;
import com.fff.ingood.activity.RegisterLocationPageActivity;
import com.fff.ingood.activity.RegisterPrimaryPageActivity;
import com.fff.ingood.activity.RegisterVerifyPageActivity;

import static com.fff.ingood.global.Constants.STATUS_CODE_SUCCESS_INT;

public class RegisterFlowLogic extends FlowLogic {

    private FLOW mFlow;

    RegisterFlowLogic(FlowLogicCaller caller, FLOW flow) {
        super(caller);
        mFlow = flow;
    }

    @Override
    protected FLOW doLogic() {

        switch(mFlow) {
            case FL_REGISTER_PRIMARY:
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_REGISTER_VERIFY, RegisterVerifyPageActivity.class);
                mFlow = FLOW.FL_REGISTER_VERIFY;
                break;
            case FL_REGISTER_VERIFY:
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_REGISTER_LOCATION, RegisterLocationPageActivity.class);
                mFlow = FLOW.FL_REGISTER_LOCATION;
                break;
            case FL_REGISTER_LOCATION:
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_REGISTER_INTEREST, RegisterInterestPageActivity.class);
                mFlow = FLOW.FL_REGISTER_INTEREST;
                break;
            case FL_REGISTER_INTEREST:
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
                mFlow = FLOW.FL_HOME;
                break;
            default :
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_REGISTER_PRIMARY, RegisterPrimaryPageActivity.class);
                mFlow = FLOW.FL_REGISTER_PRIMARY;
                break;
        }
        return mFlow;
    }
}