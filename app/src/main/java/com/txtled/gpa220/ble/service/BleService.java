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
import com.txtled.gpa220.utils.RxUtil;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.FINISH_SEARCH;

/**
 * Created by Mr.Quan on 2020/4/1.
 */
public class BleService extends Service {
    private DataManagerModel dataManagerModel;
    private List<SearchResult> data;
    @Override
    public void onCreate() {
        super.onCreate();
        dataManagerModel = MyApplication.getAppComponent().getDataManagerModel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = new ArrayList<>();
        Flowable.timer(3, TimeUnit.SECONDS).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> scanBle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (data.size() != 0){
                            dataManagerModel.stopSearch();
                            EventBus.getDefault().post(new BleControlEvent(FINISH_SEARCH,data));
                        }else {
                            scanBle();
                        }
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

    private void scanBle() {
        dataManagerModel.scanBle(false, new BleHelper.OnScanBleListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(SearchResult device) {

                data.add(device);

            }

            @Override
            public void onScanFailure() {

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
            dataManagerModel.connBle(bleData, new BleHelper.OnConnBleListener() {
                @Override
                public void onSuccess() {
                    EventBus.getDefault().post(new BleControlEvent(CONN));
                    dataManagerModel.notifyBle(new BleHelper.OnReadListener() {
                        @Override
                        public void onRead(byte[] data) {

                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
