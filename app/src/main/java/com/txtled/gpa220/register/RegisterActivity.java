package com.txtled.gpa220.register;

import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.register.mvp.RegisterContract;
import com.txtled.gpa220.register.mvp.RegisterPresenter;

/**
 * Created by Mr.Quan on 2020/4/22.
 */
public class RegisterActivity extends MvpBaseActivity<RegisterPresenter> implements RegisterContract.View {
    @Override
    public void setInject() {

    }

    @Override
    public void init() {

    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    protected void beforeContentView() {

    }
}
