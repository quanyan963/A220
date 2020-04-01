package com.txtled.gpa220.ble;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inuker.bluetooth.library.search.SearchResult;
import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.ble.mvp.BleContract;
import com.txtled.gpa220.ble.mvp.BlePresenter;
import com.txtled.gpa220.ble.service.BleBindInterface;
import com.txtled.gpa220.ble.service.BleService;
import com.txtled.gpa220.broadcast.BlueToothStateReceiver;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.BleUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.FINISH_SEARCH;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BleActivity extends MvpBaseActivity<BlePresenter> implements BleContract.View,
        BlueToothStateReceiver.OnBlueToothStateListener, BleAdapter.OnBleItemClickListener {
    //    @BindView(R.id.sh_ble)
//    SwitchCompat shBle;
//    @BindView(R.id.rl_ble_switch)
//    RelativeLayout rlBleSwitch;
//    @BindView(R.id.ctv_search_name)
//    CustomTextView ctvSearchName;
    @BindView(R.id.rlv_ble_list)
    RecyclerView rlvBleList;
    @BindView(R.id.srl_ble_refresh)
    SwipeRefreshLayout srlBleRefresh;

    private BleAdapter bleAdapter;
    private List<SearchResult> data;
    private Intent service;
    private BleBindInterface bindInterface;
    private BindService bindService;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.ble);
        BleUtils.getInstance().registerBlueToothStateReceiver(this, this);
        setNavigationIcon(true);

        presenter.checkBle(this);
        rlvBleList.setHasFixedSize(true);
        rlvBleList.setLayoutManager(new LinearLayoutManager(this));
        bleAdapter = new BleAdapter(this, this);
        rlvBleList.setAdapter(bleAdapter);

        bleAdapter.setData(data);

//        ctvSearchName.setText(String.format(getResources().getString(R.string.find_by),
//                BluetoothAdapter.getDefaultAdapter().getName()));
//        shBle.setChecked(blueAdapter.enable());
//        shBle.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked){
//                blueAdapter.enable();
//                data = new ArrayList<>(blueAdapter.getBondedDevices());
//            }else {
//                blueAdapter.disable();
//                bleAdapter.removeAll();
//            }
//        });

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
        if (presenter.turnOnBluetooth()) {
            hideSnackBar();
        } else {
            showSnackBar(srlBleRefresh, R.string.ble_open_failed, R.string.go,
                    v -> startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS)));
        }
        //shBle.setChecked(false);
    }

    @Override
    public void onStateOn() {
        hideSnackBar();
        //shBle.setChecked(true);
    }

    @Override
    public void onTvRightClick() {

    }

    @Override
    public void onDestroy() {
        unbindService(bindService);
        BleUtils.getInstance().unregisterBlueToothStateReceiver(this);
        super.onDestroy();
    }

    /**
     * 列表点击事件
     *
     * @param info
     */
    @Override
    public void onBleClick(SearchResult info) {

    }

    @Override
    public void showPermissionHint() {
        AlertUtils.showAlertDialog(this, R.string.permission_fail,
                (dialog, which) -> BleActivity.this.finish());
    }

    @Override
    public void startBleService() {
        bindService = new BindService();
        service = new Intent(this, BleService.class);
        startService(service);
        bindService(service,bindService,BIND_AUTO_CREATE);
    }

    private class BindService implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bindInterface = (BleBindInterface) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    public void onEventServiceThread(BleControlEvent event) {
        if (event.getBleConnType() == FINISH_SEARCH){
            Set<SearchResult> before = new LinkedHashSet<>(event.getData());
            data = new ArrayList<>(before);
            runOnUiThread(() -> bleAdapter.setData(data));
        }
        super.onEventServiceThread(event);
    }
}
