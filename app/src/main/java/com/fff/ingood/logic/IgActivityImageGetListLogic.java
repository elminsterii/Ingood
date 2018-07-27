package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.IgActivityImageGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageGetListLogic extends Logic
        implements IgActivityImageGetListTaskWrapper.IgActivityImageGetListTaskWrapperCallback {

    public interface IgActivityImageGetListLogicCaller extends LogicCaller {
        void returnIgActivityImagesName(List<String> lsImagesName);
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImageGetListLogicCaller mCaller;
    private String m_strIgActivityId;

    IgActivityImageGetListLogic(IgActivityImageGetListLogicCaller caller, String strIgActivityId) {
        super(caller);
        mCaller = caller;
        m_strIgActivityId = strIgActivityId;
    }

    @Override
    protected void doLogic() {
        IgActivityImageGetListTaskWrapper task = new IgActivityImageGetListTaskWrapper(this);
        task.execute(m_strIgActivityId);
    }

    @Override
    public void onGetIgActivitiesImageListSuccess(String strIgActivityImagesName) {
        List<String> lsImagesName = StringTool.arrayStringToListString(strIgActivityImagesName.split(","));
        mCaller.returnIgActivityImagesName(lsImagesName);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onGetIgActivitiesImageListFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
