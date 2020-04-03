package com.txtled.gpa220.setting;

import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.setting.mvp.SetContract;
import com.txtled.gpa220.setting.mvp.SetPresenter;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class SettingActivity extends MvpBaseActivity<SetPresenter> implements SetContract.View {
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
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
