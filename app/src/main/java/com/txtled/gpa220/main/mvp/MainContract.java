package com.txtled.gpa220.main.mvp;


import android.app.Activity;
import android.view.View;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.main.MainActivity;

import java.util.List;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public interface MainContract {

    interface View extends BaseView {

        void showLeftView();

        void closeLeftView();

        void toBleView();

        void refreshView();

        void showLoadingView();

        void toPdfView();

        void showSyncSuccess();

        void hidSnack();

        void toSettingView();

        void toLoginView();

        void hidLoading();

        void showPermissionHint();

        void showUnConnView();
    }

    interface Presenter extends BasePresenter<View> {

        void onViewClick(android.view.View v);

        List<UserData> getUserData();

        void setUserPosition(int position);

        void insertData(UserData str);

        void update(int mPosition, UserData str);

        void setTempData(int mPosition, float temp);

        void setAllTempData(int mPosition, List<Float> allTemp);

        void setClosed(boolean closed);

        boolean isClosed();

        void showSyncSuccess();

        void init(Activity activity);

        void hidDelay();
    }
}
