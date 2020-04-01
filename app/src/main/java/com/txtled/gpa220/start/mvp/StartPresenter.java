package com.txtled.gpa220.start.mvp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.format.Time;
import android.widget.Toast;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.CommonSubscriber;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.BleUtils;
import com.txtled.gpa220.utils.Constants;
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
    public void checkBle(Activity activity) {
        if (dataManagerModel.isFirstIn()){
            UserData data = new UserData(UNKNOWN,"","","",2);
            dataManagerModel.setUserData(data);
            dataManagerModel.setFirstIn(false);
        }
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        addSubscribe(Flowable.timer(2, TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Long>(view) {

                    @Override
                    public void onNext(Long aLong) {
                        String[] permissions = {Constants.permissions[0], Constants.permissions[1]};
                        if (!checkPermission(activity,permissions)){
                            AlertUtils.showAlertDialog(activity, R.string.permissions_hint,
                                    (dialog, which) -> dataManagerModel
                                            .requestPermissions(activity, permissions,
                                                    new OperateHelper.OnPermissionsListener() {
                                                        @Override
                                                        public void onSuccess(String name) {
                                                            if (name.equals(Constants.permissions[1])) {
                                                                checkBle(blueAdapter);
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure() {
                                                            view.showPermissionHint();
                                                        }

                                                        @Override
                                                        public void onAskAgain() {

                                                        }
                                                    }));
                        }else {
                            checkBle(blueAdapter);
                        }
                    }
                }));
    }

    private void checkBle(BluetoothAdapter blueAdapter){
        //支持蓝牙模块
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {

                view.toMain();
            } else {
                turnOnBluetooth();
            }
        } else {//不支持蓝牙模块
            view.notHaveBle();
        }
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

    public static boolean checkPermission(Context context, String[] permissions) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        for (String permission : permissions) {
            int per = packageManager.checkPermission(permission, packageName);
            if (PackageManager.PERMISSION_DENIED == per) {
                return false;
            }
        }
        return true;
    }
}
