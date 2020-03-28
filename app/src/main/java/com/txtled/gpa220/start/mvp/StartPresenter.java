package com.txtled.gpa220.start.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class StartPresenter extends RxPresenter<StartContract.View> implements StartContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public StartPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }
}
