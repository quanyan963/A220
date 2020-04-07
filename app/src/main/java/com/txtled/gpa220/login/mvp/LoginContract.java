package com.txtled.gpa220.login.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/4/7.
 */
public interface LoginContract {
    interface View extends BaseView{

        void toMain();

        void Sign();

        void showPhoneError();

        void hidSnack();

        void showPassError();

        void showNoUser();

        void hidLoading();

        void showError();

        void showUserExist();

        void showSuccess();
    }
    interface Presenter extends BasePresenter<View>{

        void init();

        void showErrorPhone();

        void checkLogin(String number, String pass);

        void hidDelay();

        void signIn(String number, String pass);
    }
}
