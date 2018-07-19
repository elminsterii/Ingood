package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.PersonIconGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconGetListLogic extends Logic implements PersonIconGetListTaskWrapper.PersonIconGetListTaskWrapperCallback {

    public interface PersonIconGetListLogicCaller extends LogicCaller {
        void returnPersonIconsName(List<String> lsIconsName);
        void returnStatus(Integer iStatusCode);
    }

    private PersonIconGetListLogicCaller mCaller;
    private String m_strEmailOrId;

    PersonIconGetListLogic(PersonIconGetListLogicCaller caller, String strEmailOrId) {
        super(caller);
        mCaller = caller;
        m_strEmailOrId = strEmailOrId;
    }

    @Override
    protected void doLogic() {
        PersonIconGetListTaskWrapper task = new PersonIconGetListTaskWrapper(this);
        task.execute(m_strEmailOrId);
    }

    @Override
    public void onGetIconListSuccess(String strIconsName) {
        List<String> lsIconsName = StringTool.arrayStringToListString(strIconsName.split(","));
        mCaller.returnPersonIconsName(lsIconsName);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onGetIconListFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
