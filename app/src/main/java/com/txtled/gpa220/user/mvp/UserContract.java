package com.txtled.gpa220.user.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;
import com.txtled.gpa220.bean.UserData;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public interface UserContract {
    interface View extends BaseView{

    }
    interface Presenter extends BasePresenter<View>{

        UserData getData(int position);
    }
}
