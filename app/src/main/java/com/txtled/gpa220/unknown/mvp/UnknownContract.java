package com.txtled.gpa220.unknown.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;
import com.txtled.gpa220.bean.UserData;

import java.util.List;

public interface UnknownContract {
    interface View extends BaseView{

        void showFinish();

        void hidSnack();
    }

    interface Presenter extends BasePresenter<View>{

        List<Float> getUnknownData();

        List<UserData> getUserData();

        void setData(int position, List<Float> checked);

        boolean isClosed();

        void setClosed(boolean b);
    }
}
