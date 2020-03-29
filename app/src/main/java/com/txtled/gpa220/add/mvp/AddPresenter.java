package com.txtled.gpa220.add.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

public class AddPresenter extends RxPresenter<AddConteact.View> implements AddConteact.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public AddPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }
}
