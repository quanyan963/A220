package com.txtled.gpa220.setting.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class SetPresenter extends RxPresenter<SetContract.View> implements SetContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public SetPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public boolean isClosed() {
        return dataManagerModel.isClosed();
    }

    @Override
    public void setClosed(boolean b) {
        dataManagerModel.setClosed(b);
    }
}
