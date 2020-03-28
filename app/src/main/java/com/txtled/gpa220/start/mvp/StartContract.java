package com.txtled.gpa220.start.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public interface StartContract {
    interface View extends BaseView{

        void toMain();

        void notHaveBle();

        void showAlert();
    }

    interface Presenter extends BasePresenter<View>{

        void checkBle();

        boolean turnOnBluetooth();
    }
}