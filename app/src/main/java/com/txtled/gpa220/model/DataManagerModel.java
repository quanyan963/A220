package com.txtled.gpa220.model;


import android.app.Activity;
import android.content.Context;

import com.inuker.bluetooth.library.search.SearchResult;
import com.lzy.okgo.callback.StringCallback;
import com.txtled.gpa220.bean.UserData;
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
    public int getBlePosition() {
        return mPreferencesHelper.getBlePosition();
    }

    @Override
    public void setBlePosition(int position) {
        mPreferencesHelper.setBlePosition(position);
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
    public String getBleAddress() {
        return mPreferencesHelper.getBleAddress();
    }

    @Override
    public void setBleAddress(String address) {
        mPreferencesHelper.setBleAddress(address);
    }

    @Override
    public boolean isClosed() {
        return mPreferencesHelper.isClosed();
    }

    @Override
    public void setClosed(boolean closed) {
        mPreferencesHelper.setClosed(closed);
    }

    @Override
    public void requestPermissions(Activity activity, String[] permissions, OnPermissionsListener permissionsListener) {
        mOperateHelper.requestPermissions(activity, permissions, permissionsListener);
    }

    @Override
    public void scanBle(boolean isSpecified, OnScanBleListener onScanBleListener) {
        mBleHelper.scanBle(isSpecified, onScanBleListener);
    }

    @Override
    public void connBle(String result, OnConnBleListener onConnBleListener) {
        mBleHelper.connBle(result,onConnBleListener);
    }

    @Override
    public void writeCommand(String command) {
        mBleHelper.writeCommand(command);
    }

    @Override
    public void writeCommand(byte[] command) {
        mBleHelper.writeCommand(command);
    }

    @Override
    public void notifyBle(OnReadListener readListener) {
        mBleHelper.notifyBle(readListener);
    }

    @Override
    public void isBleConnected(BleConnListener bleListener) {
        mBleHelper.isBleConnected(bleListener);
    }

    @Override
    public void readCommand(OnReadListener readListener) {
        mBleHelper.readCommand(readListener);
    }

    @Override
    public void unRegisterConn() {
        mBleHelper.unRegisterConn();
    }

    @Override
    public void stopSearch() {
        mBleHelper.stopSearch();
    }

    @Override
    public void detachListener() {
        mBleHelper.detachListener();
    }

    @Override
    public void setUserData(UserData data) {
        mDBDbHelper.setUserData(data);
    }

    @Override
    public List<UserData> getUserData() {
        return mDBDbHelper.getUserData();
    }

    @Override
    public void deleteUserData(UserData data) {
        mDBDbHelper.deleteUserData(data);
    }

    @Override
    public void updateUserData(UserData data) {
        mDBDbHelper.updateUserData(data);
    }

    @Override
    public void getHttp(String userName, String pass, StringCallback callback) {
        mNetHelper.getHttp(userName, pass, callback);
    }
}
