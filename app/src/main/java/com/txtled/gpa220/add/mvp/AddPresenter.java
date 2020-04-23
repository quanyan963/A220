package com.txtled.gpa220.add.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

public class AddPresenter extends RxPresenter<AddContract.View> implements AddContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public AddPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public void insertData(UserData data) {
        dataManagerModel.setUserData(data);
    }

    @Override
    public UserData getUserData(int position) {
        return dataManagerModel.getUserData().get(position);
    }

    @Override
    public void update(UserData data) {
        dataManagerModel.updateUserData(data);
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
