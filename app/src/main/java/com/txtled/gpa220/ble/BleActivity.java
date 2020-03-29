package com.txtled.gpa220.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleConnectInfo;
import com.txtled.gpa220.ble.mvp.BleContract;
import com.txtled.gpa220.ble.mvp.BlePresenter;
import com.txtled.gpa220.broadcast.BlueToothStateReceiver;
import com.txtled.gpa220.utils.BleUtils;
import com.txtled.gpa220.widget.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BleActivity extends MvpBaseActivity<BlePresenter> implements BleContract.View,
        BlueToothStateReceiver.OnBlueToothStateListener, BleAdapter.OnBleItemClickListener {
    @BindView(R.id.sh_ble)
    SwitchCompat shBle;
    @BindView(R.id.rl_ble_switch)
    RelativeLayout rlBleSwitch;
    @BindView(R.id.ctv_search_name)
    CustomTextView ctvSearchName;
    @BindView(R.id.rlv_ble_list)
    RecyclerView rlvBleList;

    private BleAdapter bleAdapter;
    private List<BluetoothDevice> data;

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
        rlvBleList.setHasFixedSize(true);
        rlvBleList.setLayoutManager(new LinearLayoutManager(this));
        bleAdapter = new BleAdapter(this,this);
        rlvBleList.setAdapter(bleAdapter);
        data = new ArrayList<>(blueAdapter.getBondedDevices());
        bleAdapter.setData(data);

        shBle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                blueAdapter.enable();
                data = new ArrayList<>(blueAdapter.getBondedDevices());
            }else {
                blueAdapter.disable();
                bleAdapter.removeAll();
            }
        });

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

    /**
     * 列表点击事件
     * @param info
     */
    @Override
    public void onBleClick(BluetoothDevice info) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return onExitActivity(keyCode,event);
    }
}
