package com.txtled.gpa220.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.main.mvp.MainContract;
import com.txtled.gpa220.main.mvp.MainPresenter;


public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.View {

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText("登录");

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }
}
