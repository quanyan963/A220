package com.txtled.gpa220.model;


import android.app.Activity;
import android.content.Context;

import com.txtled.gpa220.model.ble.BleHelper;
import com.txtled.gpa220.model.db.DBHelper;
import com.txtled.gpa220.model.net.NetHelper;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.model.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Mr.Quan on 2018/4/17.
 */

public class DataManagerModel implements DBHelper, PreferencesHelper, NetHelper, OperateHelper, BleHelper {
    private DBHelper mDBDbHelper;
    private PreferencesHelper mPreferencesHelper;
    private NetHelper mNetHelper;
    private OperateHelper mOperateHelper;
    private BleHelper mBleHelper;

    public DataManagerModel(DBHelper mDBDbHelper, PreferencesHelper
            mPreferencesHelper, NetHelper mNetHelper, OperateHelper mOperateHelper, BleHelper mBleHelper) {
        this.mDBDbHelper = mDBDbHelper;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mNetHelper = mNetHelper;
        this.mOperateHelper = mOperateHelper;
        this.mBleHelper = mBleHelper;
    }

    @Override
    public int getPlayPosition() {
        return 0;
    }

    @Override
    public void setPlayPosition(int position) {

    }

    @Override
    public boolean isFirstIn() {
        return mPreferencesHelper.isFirstIn();
    }

    @Override
    public void setFirstIn(boolean first) {
        mPreferencesHelper.setFirstIn(first);
    }

    @Override
    public void requestPermissions(Activity activity, String[] permissions, OnPermissionsListener permissionsListener) {
        mOperateHelper.requestPermissions(activity, permissions, permissionsListener);
    }

    @Override
    public void scanBle(Activity activity, boolean isSpecified, OnScanBleListener onScanBleListener, OnConnBleListener onConnBleListener) {
        mBleHelper.scanBle(activity, isSpecified, onScanBleListener, onConnBleListener);
    }

    @Override
    public void connBle(OnConnBleListener onConnBleListener) {
        mBleHelper.connBle(onConnBleListener);
    }

    @Override
    public void writeCommand(String command) {
        mBleHelper.writeCommand(command);
    }

    @Override
    public void notifyBle(OnReadListener readListener) {
        mBleHelper.notifyBle(readListener);
    }

    @Override
    public void readCommand(OnReadListener readListener) {
        mBleHelper.readCommand(readListener);
    }

    @Override
    public void unRegisterConn() {
        mBleHelper.unRegisterConn();
    }
}