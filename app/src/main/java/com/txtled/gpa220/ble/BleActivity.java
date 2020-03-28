package com.txtled.gpa220.ble;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.ble.mvp.BleContract;
import com.txtled.gpa220.ble.mvp.BlePresenter;
import com.txtled.gpa220.broadcast.BlueToothStateReceiver;
import com.txtled.gpa220.utils.BleUtils;
import com.txtled.gpa220.widget.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BleActivity extends MvpBaseActivity<BlePresenter> implements BleContract.View,
        BlueToothStateReceiver.OnBlueToothStateListener {
    @BindView(R.id.sh_ble)
    SwitchCompat shBle;
    @BindView(R.id.rl_ble_switch)
    RelativeLayout rlBleSwitch;
    @BindView(R.id.ctv_search_name)
    CustomTextView ctvSearchName;
    @BindView(R.id.rlv_ble_list)
    RecyclerView rlvBleList;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.ble);
        BleUtils.getInstance().registerBlueToothStateReceiver(this,this);
        setNavigationIcon(true);
        ctvSearchName.setText(String.format(getResources().getString(R.string.find_by),
                BluetoothAdapter.getDefaultAdapter().getName()));
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        shBle.setChecked(blueAdapter.enable());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ble;
    }

    @Override
    protected void beforeContentView() {
        //setTheme(R.style.BlackTheme);
    }

    @Override
    public void onStateOff() {
        shBle.setChecked(false);
    }

    @Override
    public void onStateOn() {
        shBle.setChecked(true);
    }
}
