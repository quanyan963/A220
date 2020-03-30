package com.txtled.gpa220.main.mvp;


import android.view.View;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;
import com.txtled.gpa220.bean.UserData;

import java.util.List;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public interface MainContract {

    interface View extends BaseView {

        void showLeftView();

        void closeLeftView();
    }

    interface Presenter extends BasePresenter<View> {

        void onViewClick(android.view.View v);

        List<UserData> getUserData();

        void setUserPosition(int position);

        void insertData(UserData str);

        void update(int mPosition, UserData str);
    }
}
