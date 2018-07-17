package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.PersonIconGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconGetListLogic extends Logic implements PersonIconGetListTaskWrapper.PersonIconGetListTaskWrapperCallback {

    public interface PersonGetIconListLogicCaller extends LogicCaller {
        void returnPersonIconsName(List<String> lsIconsName);
        void returnStatus(Integer iStatusCode);
    }

    private PersonGetIconListLogicCaller mCaller;
    private String m_strEmail;

    PersonIconGetListLogic(PersonGetIconListLogicCaller caller, String strEmail) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
    }

    @Override
    protected void doLogic() {
        PersonIconGetListTaskWrapper task = new PersonIconGetListTaskWrapper(this);
        task.execute(m_strEmail);
    }

    @Override
    public void onGetIconListSuccess(String strIconsName) {
        if(StringTool.checkStringNotNull(strIconsName)) {
            List<String> lsIconsName = StringTool.arrayStringToListString(strIconsName.split(","));
            mCaller.returnPersonIconsName(lsIconsName);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void onGetIconListFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
