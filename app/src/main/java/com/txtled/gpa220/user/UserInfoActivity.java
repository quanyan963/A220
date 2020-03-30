package com.txtled.gpa220.user;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.user.mvp.UserContract;
import com.txtled.gpa220.user.mvp.UserPresenter;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class UserInfoActivity extends MvpBaseActivity<UserPresenter> implements UserContract.View {
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        tvTitle.setText(R.string.user_info);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void beforeContentView() {

    }
}
