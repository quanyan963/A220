package com.txtled.gpa220.ble.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BlePresenter extends RxPresenter<BleContract.View> implements BleContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public BlePresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }
}
