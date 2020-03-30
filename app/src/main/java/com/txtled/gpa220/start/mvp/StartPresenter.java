package com.txtled.gpa220.start.mvp;

import android.bluetooth.BluetoothAdapter;
import android.text.format.Time;
import android.widget.Toast;

import com.txtled.gpa220.base.CommonSubscriber;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.utils.BleUtils;
import com.txtled.gpa220.utils.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableElementAt;

import static com.txtled.gpa220.utils.Constants.UNKNOWN;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class StartPresenter extends RxPresenter<StartContract.View> implements StartContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public StartPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public void checkBle() {
        if (dataManagerModel.isFirstIn()){
            UserData data = new UserData(UNKNOWN,"","","",2);
            dataManagerModel.setUserData(data);
            dataManagerModel.setFirstIn(false);
        }
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Long>(view) {

                    @Override
                    public void onNext(Long aLong) {
//支持蓝牙模块
                        if (blueAdapter != null) {
                            if (blueAdapter.isEnabled()) {

                                view.toMain();
                            } else {
                                view.showAlert();
                            }
                        } else {//不支持蓝牙模块
                            view.notHaveBle();
                        }
                    }
                }));
    }

    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    @Override
    public boolean turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }

        return false;
    }
}
