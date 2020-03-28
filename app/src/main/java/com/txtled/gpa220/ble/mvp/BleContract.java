package com.txtled.gpa220.ble.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public interface BleContract {
    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }
}
