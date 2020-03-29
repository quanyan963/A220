package com.txtled.gpa220.add;

import com.txtled.gpa220.add.mvp.AddConteact;
import com.txtled.gpa220.add.mvp.AddPresenter;
import com.txtled.gpa220.base.MvpBaseActivity;

public class AddMenberActivity extends MvpBaseActivity<AddPresenter> implements AddConteact.View {
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
