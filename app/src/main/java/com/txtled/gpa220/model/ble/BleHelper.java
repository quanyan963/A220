package com.txtled.gpa220.model.ble;

import android.app.Activity;
import android.content.Context;

import com.inuker.bluetooth.library.search.SearchResult;


/**
 * Created by Mr.Quan on 2018/4/17.
 */

public interface BleHelper {
    void scanBle(boolean isSpecified, OnScanBleListener onScanBleListener);

    void connBle(SearchResult result, OnConnBleListener onConnBleListener);

    void writeCommand(String command);

    void notifyBle(OnReadListener readListener);

    void isBleConnected(BleConnListener bleListener);

    void readCommand(OnReadListener readListener);

    void unRegisterConn();

    void stopSearch();

    interface OnScanBleListener {
        void onStart();

        void onSuccess(SearchResult device);

        void onScanFailure();

        void onDisOpenDevice();

        void onDisOpenBle();

        void onDisOpenGPS();

        void onDisSupported();
    }

    interface OnConnBleListener {
        void onSuccess();

        void onFailure();
    }

    interface OnReadListener{
        void onRead(byte[] data);
    }

    interface BleConnListener{
        void onConn();
        void onDisConn();
    }
}