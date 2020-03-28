package com.txtled.gpa220.start;

import android.content.Intent;
import android.view.WindowManager;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.ble.BleActivity;
import com.txtled.gpa220.main.MainActivity;
import com.txtled.gpa220.start.mvp.StartContract;
import com.txtled.gpa220.start.mvp.StartPresenter;
import com.txtled.gpa220.utils.AlertUtils;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class StartActivity extends MvpBaseActivity<StartPresenter> implements StartContract.View,
        AlertUtils.OnConfirmClickListener {
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        AlertUtils.setListener(this);
        AlertUtils.showHintDialog(this,R.layout.alert_ble);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void beforeContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onOk() {
        startActivity(new Intent(this, BleActivity.class));
    }

    @Override
    public void onCancel() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
