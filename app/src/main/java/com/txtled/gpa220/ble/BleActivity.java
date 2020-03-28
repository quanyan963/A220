package com.txtled.gpa220.ble;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.ble.mvp.BleContract;
import com.txtled.gpa220.ble.mvp.BlePresenter;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BleActivity extends MvpBaseActivity<BlePresenter> implements BleContract.View {
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(R.color.black_bg));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ble;
    }

    @Override
    protected void beforeContentView() {
        //setTheme(R.style.BlackTheme);
    }
}
