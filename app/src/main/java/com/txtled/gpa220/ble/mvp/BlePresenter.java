package com.txtled.gpa220.ble.mvp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.CommonSubscriber;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.ble.service.BleService;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.ble.BleHelper;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.Constants;
import com.txtled.gpa220.utils.RxUtil;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mr.Quan on 2020/3/26.
 */
public class BlePresenter extends RxPresenter<BleContract.View> implements BleContract.Presenter {
    private DataManagerModel dataManagerModel;
    private BluetoothAdapter blueAdapter;
    private Activity activity;

    @Inject
    public BlePresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public boolean turnOnBluetooth() {
        if (blueAdapter != null) {
            return blueAdapter.enable();
        }

        return false;
    }

    @Override
    public void checkBle(Activity activity) {
        this.activity = activity;
        String[] permissions = {Constants.permissions[0], Constants.permissions[1]};
        dataManagerModel.requestPermissions(activity, permissions,
                        new OperateHelper.OnPermissionsListener() {
                            @Override
                            public void onSuccess(String name) {
                                if (name.equals(Constants.permissions[1])) {
                                    if (blueAdapter != null){
                                        if (blueAdapter.enable()){
                                            view.startBleService();
                                        }else {
                                            turnOnBluetooth();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure() {
                                view.showPermissionHint();
                            }

                            @Override
                            public void onAskAgain() {

                            }
                        });
    }

    @Override
    public void showNoData() {

        addSubscribe(Flowable.timer(3,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        view.showNoData();
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        view.hidSnack();
                    }
                }));
    }

    @Override
    public void closeDevice() {
        dataManagerModel.unRegisterConn();
    }

    @Override
    public int getBlePosition() {
        return dataManagerModel.getBlePosition();
    }

    @Override
    public void savePosition(int position) {
        dataManagerModel.setBlePosition(position);
    }

    @Override
    public void setClosed(boolean b) {
        dataManagerModel.setClosed(b);
    }
}
