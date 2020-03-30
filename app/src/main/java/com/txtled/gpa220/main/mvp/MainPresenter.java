package com.txtled.gpa220.main.mvp;

import android.view.View;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManagerModel mDataManagerModel;
    private List<UserData> mData;
    private int mPosition;

    @Inject
    public MainPresenter(DataManagerModel mDataManagerModel) {
        this.mDataManagerModel = mDataManagerModel;
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_right:
                view.showLeftView();
                break;
            case R.id.img_menu_close:
                view.closeLeftView();
                break;
        }
    }

    @Override
    public List<UserData> getUserData() {
        mData = mDataManagerModel.getUserData();
        return mData;
    }

    @Override
    public void setUserPosition(int position) {
        this.mPosition = position;
    }

    @Override
    public void insertData(UserData str) {
        mData.add(str);
    }

    @Override
    public void update(int position, UserData str) {
        mData.add(position,str);
    }
}
