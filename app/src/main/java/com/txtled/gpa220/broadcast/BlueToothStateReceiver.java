package com.txtled.gpa220.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;

import com.txtled.gpa220.utils.Utils;

/**
 * 广播监听蓝牙状态
 */
public class BlueToothStateReceiver extends BroadcastReceiver {
    public static int DEFAULT_VALUE_BULUETOOTH = 1000;
    public OnBlueToothStateListener onBlueToothStateListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, DEFAULT_VALUE_BULUETOOTH);
            switch (state) {
                case BluetoothAdapter.STATE_OFF://蓝牙已关闭
                    onBlueToothStateListener.onStateOff();
                    break;
                case BluetoothAdapter.STATE_ON://蓝牙已开启
                    onBlueToothStateListener.onStateOn();
                    break;
                case BluetoothAdapter.STATE_TURNING_ON://蓝牙正在打开
                    //onBlueToothStateListener.onStateTurningOn();
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF://蓝牙正在关闭
                    //onBlueToothStateListener.onStateTurningOff();
                    break;
                default:
                    Utils.Logger("BroadCast","BlueToothError", "蓝牙状态未知");
            }
        }
    }

    public interface OnBlueToothStateListener {
        void onStateOff();

        void onStateOn();

//        void onStateTurningOn();
//
//        void onStateTurningOff();
    }

    public void setOnBlueToothStateListener(OnBlueToothStateListener onBlueToothStateListener) {
        this.onBlueToothStateListener = onBlueToothStateListener;
    }

}