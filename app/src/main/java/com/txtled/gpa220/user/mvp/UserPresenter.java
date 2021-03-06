package com.txtled.gpa220.user.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class UserPresenter extends RxPresenter<UserContract.View> implements UserContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public UserPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public UserData getData(int position) {
        return dataManagerModel.getUserData().get(position);
    }

    @Override
    public void deleteUser(int position) {
        dataManagerModel.deleteUserData(getData(position));
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
