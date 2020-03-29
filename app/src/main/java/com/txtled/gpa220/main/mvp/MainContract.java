package com.txtled.gpa220.main.mvp;


import android.view.View;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void onViewClick(android.view.View v);
    }
}
