package com.txtled.gpa220.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.ble.BleActivity;
import com.txtled.gpa220.broadcast.BlueToothStateReceiver;
import com.txtled.gpa220.main.MainActivity;
import com.txtled.gpa220.start.mvp.StartContract;
import com.txtled.gpa220.start.mvp.StartPresenter;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.BleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class StartActivity extends MvpBaseActivity<StartPresenter> implements StartContract.View,
        AlertUtils.OnConfirmClickListener, BlueToothStateReceiver.OnBlueToothStateListener {
    @BindView(R.id.cl_start)
    ConstraintLayout clStart;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        BleUtils bleUtils = BleUtils.getInstance();
        bleUtils.registerBlueToothStateReceiver(this,this);
        presenter.checkBle();
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
        if (!presenter.turnOnBluetooth()){
            hideSnackBar();
            showSnackBar(clStart,R.string.ble_open_fail);
        }
    }

    @Override
    public void onCancel() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void toMain() {
        onCancel();
    }

    @Override
    public void notHaveBle() {
        hideSnackBar();
        showSnackBar(clStart, R.string.not_have_ble, R.string.exit, v ->
                StartActivity.this.finish());
    }

    @Override
    public void showAlert() {
        AlertUtils.setListener(this);
        AlertUtils.showHintDialog(this, R.layout.alert_ble);
    }

    @Override
    public void onStateOff() {

    }

    @Override
    public void onStateOn() {
        startActivity(new Intent(this, BleActivity.class));
        this.finish();
    }

    @Override
    public void onDestroy() {
        BleUtils.getInstance().unregisterBlueToothStateReceiver(this);
        super.onDestroy();
    }
}
