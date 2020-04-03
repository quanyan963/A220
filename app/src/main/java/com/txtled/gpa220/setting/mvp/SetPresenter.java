package com.txtled.gpa220.setting.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class SetPresenter extends RxPresenter<SetContract.View> implements SetContract.View {
    private DataManagerModel dataManagerModel;

    @Inject
    public SetPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }
}
