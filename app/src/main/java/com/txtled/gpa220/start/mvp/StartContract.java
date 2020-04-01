package com.txtled.gpa220.start.mvp;

import android.app.Activity;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public interface StartContract {
    interface View extends BaseView{

        void toMain();

        void notHaveBle();

        //void showAlert();

        void showPermissionHint();
    }

    interface Presenter extends BasePresenter<View>{

        void checkBle(Activity activity);

        boolean turnOnBluetooth();
    }
}
