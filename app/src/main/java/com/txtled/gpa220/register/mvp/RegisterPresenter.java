package com.txtled.gpa220.register.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/4/22.
 */
public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public RegisterPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }
}
