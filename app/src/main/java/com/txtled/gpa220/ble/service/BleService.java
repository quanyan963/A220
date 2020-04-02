package com.txtled.gpa220.ble.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.inuker.bluetooth.library.search.SearchResult;
import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.ble.BleHelper;
import com.txtled.gpa220.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import static com.txtled.gpa220.utils.BleUtils.SINGLE_RESPONSE;
import static com.txtled.gpa220.utils.Constants.ALL_DATA;
import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.FINISH_SEARCH;
import static com.txtled.gpa220.utils.Constants.RECONN;
import static com.txtled.gpa220.utils.Constants.SINGLE_DATA;

/**
 * Created by Mr.Quan on 2020/4/1.
 */
public class BleService extends Service {
    private DataManagerModel dataManagerModel;
    private List<SearchResult> data;
    private int type;
    private boolean isClose;
    private SearchResult deviceData;
    private List<Float> syncData;
    @Override
    public void onCreate() {
        super.onCreate();
        syncData = new ArrayList<>();
        dataManagerModel = MyApplication.getAppComponent().getDataManagerModel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh();
        return super.onStartCommand(intent, flags, startId);
    }

    private void refresh() {
        isClose = true;
        type = DISCONN;
        data = new ArrayList<>();
        Flowable.timer(3, TimeUnit.SECONDS).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> scanBle())
                .subscribe(aLong -> {
                    if (isClose){
                        dataManagerModel.stopSearch();
                        EventBus.getDefault().post(new BleControlEvent(FINISH_SEARCH,data));
                    }

                });
    }

    private void reConn() {
        type = RECONN;
        data = new ArrayList<>();
        Flowable.just(0).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(aLong -> conn(null));
    }

    private void scanBle() {
        dataManagerModel.scanBle(false, new BleHelper.OnScanBleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(SearchResult device) {

                if (type == DISCONN){
                    data.add(device);
                }else {
                    deviceData = device;
                    dataManagerModel.stopSearch();
                    conn(device.getAddress());
                }
            }

            @Override
            public void onScanFailure() {
                if (type == DISCONN){
                    dataManagerModel.stopSearch();
                    EventBus.getDefault().post(new BleControlEvent(FINISH_SEARCH,data));
                }else {
                    reConn();
                }
            }

            @Override
            public void onDisOpenDevice() {

            }

            @Override
            public void onDisOpenBle() {

            }

            @Override
            public void onDisOpenGPS() {

            }

            @Override
            public void onDisSupported() {

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BleBinder();
    }

    private class BleBinder extends Binder implements BleBindInterface{

        @Override
        public void searchResult(List<SearchResult> data) {

        }

        @Override
        public void connBle(SearchResult bleData) {
            conn(bleData.getAddress());
        }

        @Override
        public void doRefresh() {
            refresh();
        }

        @Override
        public void doReConn() {
            reConn();
        }

        @Override
        public void setClosed() {
            isClose = false;
            dataManagerModel.stopSearch();
            dataManagerModel.unRegisterConn();
            if (type == DISCONN){
                EventBus.getDefault().post(new BleControlEvent(FINISH_SEARCH,data));
            }else {
                EventBus.getDefault().post(new BleControlEvent(DISCONN));
            }
        }
    }

    private void conn(String bleData) {

        dataManagerModel.connBle(bleData, new BleHelper.OnConnBleListener() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().post(new BleControlEvent(CONN));
                setListener();
            }

            @Override
            public void onFailure() {
                if (isClose)
                    conn(bleData);
            }
        });
    }

    private void setListener() {
        dataManagerModel.notifyBle(data -> {

            String result = Utils.bytesToHex(data);
            float temp = Integer.parseInt(result.substring(12, 16),16) / 10f;

            if (result.contains(SINGLE_RESPONSE)){
                EventBus.getDefault().post(new BleControlEvent(SINGLE_DATA,temp));
            }else {
                syncData.add(temp);
                if (syncData.size() == 100){
                    Set<Float> before = new LinkedHashSet<>(syncData);
                    syncData = new ArrayList<>(before);
                    EventBus.getDefault().post(new BleControlEvent(ALL_DATA,0,syncData));
                    syncData = new ArrayList<>();
                }
            }
        });
        dataManagerModel.isBleConnected(new BleHelper.BleConnListener() {
            @Override
            public void onConn() {
                EventBus.getDefault().post(new BleControlEvent(CONN));
            }

            @Override
            public void onDisConn() {
                EventBus.getDefault().post(new BleControlEvent(RECONN));
                if (isClose)
                    reConn();
            }
        });
        dataManagerModel.readCommand(new BleHelper.OnReadListener() {
            @Override
            public void onRead(byte[] data) {

            }
        });
    }

    @Override
    public void onDestroy() {
        dataManagerModel.stopSearch();
        dataManagerModel.unRegisterConn();
        super.onDestroy();
    }
}
