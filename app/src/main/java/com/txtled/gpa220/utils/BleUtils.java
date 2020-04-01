package com.txtled.gpa220.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;

import com.txtled.gpa220.broadcast.BlueToothStateReceiver;

public class BleUtils {
    private static BleUtils INSTANCE;
    public static final String BLE_NAME = "GP-A220";
    public static final String BLE_NAME_MAC = "mi";
    public static final String SEND_CHARACTERS = "1161";
    public static final String NOTIFY_CHARACTERS = "1162";
    public static final String SERVICE = "ffe0";
    public static final String SEMICOLON = ";";
    public static final String HEAD = "A005+";//AT
    public static final String END = "\\r\\n";//
    public static final String OPEN_CLOSE = "A"; //开关彩灯
    public static final String LIGHT = "B";//亮度调节
    public static final String POWER = "P";//火苗大小
    public static final String POWER_REQ = "K";//火苗大小
    public static final String SPEED = "S";//彩灯速度
    //    public static final String BLE_OPEN_CLOSE = "I";
    public static final String SOUND = "J";//音量
    public static final String REQUEST = "DT";//发送命令返回状态
    public static final String REQUEST_REQ = "D";//发送命令返回状态
    public static final String TO_MUSIC = "T";//发送命令返回状态

    public static String getLightSwitch(boolean b) {
        int state = b ? 1 : 0;
        return HEAD + OPEN_CLOSE + Utils.formatHex(state) + END;
    }

//    public static String getLightSwitchAll(Flame flame) {
//        //return HEAD + LIGHT + Utils.formatHex(progress+1) + END;
//        return HEAD + "CO" + "{" + Utils.formatHex(flame.getLightStatue())
//                +Utils.formatHex(flame.getLight()+1)+Utils.formatHex(flame
//                .getPower()+1) + Utils.formatHex(flame.getToMusic())
//                + Utils.formatHex(flame.getSpeed()+1) + "}" + END;
//    }

    public static String getLight(int statue) {
        return HEAD + LIGHT + Utils.formatHex(statue) + END;
    }



    public static synchronized BleUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BleUtils();
        }
        return INSTANCE;
    }

    BlueToothStateReceiver blueToothStateReceiver;

    //注册广播接收器，用于监听蓝牙状态变化
    public void registerBlueToothStateReceiver(Activity activity, BlueToothStateReceiver
            .OnBlueToothStateListener listener) {
        //注册广播，蓝牙状态监听
        blueToothStateReceiver = new BlueToothStateReceiver();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        activity.registerReceiver(blueToothStateReceiver, filter);
        blueToothStateReceiver.setOnBlueToothStateListener(listener);
    }

    public void unregisterBlueToothStateReceiver(Activity activity) {
        activity.unregisterReceiver(blueToothStateReceiver);
    }
}
