package com.txtled.gpa220.ble.mvp;

import android.app.Activity;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public interface BleContract {
    interface View extends BaseView{

        void showPermissionHint();

        void startBleService();
    }

    interface Presenter extends BasePresenter<View>{

        boolean turnOnBluetooth();

        void checkBle(Activity activity);

        void reScan();
    }
}
