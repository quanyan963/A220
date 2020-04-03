package com.txtled.gpa220.add.mvp;

import android.view.View;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;
import com.txtled.gpa220.bean.UserData;

public interface AddConteact {
    interface View extends BaseView{

    }
    interface Presenter extends BasePresenter<View>{

        void insertData(UserData data);

        UserData getUserData(int position);

        void update(UserData data);

        boolean isClosed();

        void setClosed(boolean b);
    }
}
