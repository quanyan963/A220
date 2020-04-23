package com.txtled.gpa220.register;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.register.mvp.RegisterContract;
import com.txtled.gpa220.register.mvp.RegisterPresenter;

/**
 * Created by Mr.Quan on 2020/4/22.
 */
public class RegisterActivity extends MvpBaseActivity<RegisterPresenter> implements RegisterContract.View {
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.register);
        setNavigationIcon(true);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void beforeContentView() {

    }
}
