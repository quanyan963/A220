package com.txtled.gpa220.model.ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.utils.BleUtils;
import com.txtled.gpa220.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static com.txtled.gpa220.utils.BleUtils.Notify;
import static com.txtled.gpa220.utils.BleUtils.Read;
import static com.txtled.gpa220.utils.BleUtils.Write;

/**
 * Created by Mr.Quan on 2018/4/17.
 */

public class BleHelperImpl implements BleHelper {
    public static final String TAG = BleHelperImpl.class.getSimpleName();
    public static final int DURATION = 1000;
    public static final int TIMES = 1000;
    private BluetoothClient mBleClient;
    private SearchRequest mRequest;
    private BleConnectOptions mOptions;
    private String mAddress;
    private UUID mServiceUUID;
    private UUID mSendCharacterUUID;
    private UUID mNotifyCharacterUUID;
    private BleConnectStatusListener listener;
    private boolean conn;
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothSocket socket;

    @Inject
    public BleHelperImpl() {
        mBleClient = new BluetoothClient(MyApplication.getInstance());
        mRequest = new SearchRequest.Builder()
                .searchBluetoothLeDevice(DURATION, TIMES).build();
        mOptions = new BleConnectOptions.Builder()
                .setConnectRetry(TIMES)   // 连接如果失败重试2次
                .setConnectTimeout(DURATION)   // 连接超时5s
                .setServiceDiscoverRetry(TIMES)  // 发现服务如果失败重试2次
                .setServiceDiscoverTimeout(DURATION)  // 发现服务超时5s
                .build();
    }

    @Override
    public void scanBle(boolean isSpecified, OnScanBleListener onScanBleListener) {
        if (mBleClient.isBleSupported()) {
            if (mBleClient.isBluetoothOpened()) {
                try {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    //得到BluetoothAdapter的Class对象
                    Class<BluetoothAdapter> bluetoothAdapterClass = BluetoothAdapter.class;
                    Method method = null;
                    method = bluetoothAdapterClass.getDeclaredMethod("getConnectionState",
                            (Class[]) null);
                    //打开权限
                    method.setAccessible(true);
                    int state = (int) method.invoke(adapter, (Object[]) null);

                    searchBleByAddress("", onScanBleListener);
//                    if (mAddress != null){
//                        conn = false;
//                        searchBleByAddress(mAddress, onScanBleListener);
//                    } else {
//                        //是否需要连接到指定的设备
//                        if (isSpecified) {
//                            onScanBleListener.onDisOpenDevice();
//                        } else {
//                            searchBleByAddress("", onScanBleListener);
//                        }
//                    }
//                    if (state == BluetoothAdapter.STATE_CONNECTED) {
//                        Set<BluetoothDevice> devices = adapter.getBondedDevices();
//                        for (BluetoothDevice device : devices) {
//                            if (device.getName().contains(BleUtils.BLE_NAME)) {
//                                @SuppressLint("PrivateApi")
//                                Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod(
//                                        "isConnected", (Class[]) null);
//                                method.setAccessible(true);
//                                boolean isConnected = (boolean) isConnectedMethod.invoke(device, (
//                                        Object[]) null);
//                                if (isConnected) {
//                                    Log.d(TAG, "phone connected ble mac:" + device.getAddress());
//                                    searchBleByAddress(device.getAddress(), onScanBleListener);
//                                }
//                            }
//                        }
//                    }
                } catch (NoSuchMethodException | IllegalAccessException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                onScanBleListener.onDisOpenBle();
            }
        } else {
            onScanBleListener.onDisSupported();
        }
    }

    private void searchBleByAddress(final String address, final OnScanBleListener onScanBleListener) {
        if (!conn) {
            mBleClient.search(mRequest, new SearchResponse() {
                @Override
                public void onSearchStarted() {
                    onScanBleListener.onStart();
                }

                @Override
                public void onDeviceFounded(final SearchResult device) {
                    if (!TextUtils.isEmpty(address)) {
                        if (device.getAddress().equalsIgnoreCase(address)) {
                            mBleClient.stopSearch();
                            mAddress = device.getAddress();

                            onScanBleListener.onSuccess(device);
                        }
                    } else if (device.getName().contains(BleUtils.BLE_NAME)) {
                        //mBleClient.stopSearch();
                        mAddress = device.getAddress();
                        onScanBleListener.onSuccess(device);
                    }
                }

                @Override
                public void onSearchStopped() {
                    onScanBleListener.onScanFailure();
                }

                @Override
                public void onSearchCanceled() {

                }
            });
        } else {
            mBleClient.stopSearch();
        }
    }

    private String getMacAddress(String macAddress) {
        String oldChar = macAddress.substring(macAddress.length() - 5, macAddress.length() - 3);
        String newChar = Integer.toHexString(Integer.parseInt(oldChar, 16) + 1);
        Utils.Logger(TAG, "newMac", macAddress.replace(oldChar, newChar));
        return macAddress.replace(oldChar, newChar);
    }

    @Override
    public void connBle(String result, final OnConnBleListener onConnBleListener) {
        if (result != null){
            mAddress = result;
        }
        mBleClient.connect(mAddress, mOptions, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                if (code == REQUEST_SUCCESS) {
                    List<BleGattService> serviceList = profile.getServices();
                    for (BleGattService service : serviceList) {
                        Utils.Logger("service", "UUID", service.getUUID().toString());
                        List<BleGattCharacter> characters = service.getCharacters();
                        for (BleGattCharacter character : characters) {
                            //save uuid                       判断ble端口权限
                            if (character.getProperty() == (Read + Write + Notify)){
                                mServiceUUID = service.getUUID();
                                mSendCharacterUUID = character.getUuid();
                                mNotifyCharacterUUID = character.getUuid();
                                onConnBleListener.onSuccess();
                                break;
                            }
                        }
//                        if (service.getUUID().toString().contains(BleUtils.SERVICE)) {
//
//                        }
                    }
                } else {
                    onConnBleListener.onFailure();
                }
            }
        });
    }

    @Override
    public void writeCommand(String command) {
//        if (mServiceUUID != null && mSendCharacterUUID != null) {
//            divideFrameBleSendData(command.getBytes(),context);
//        }
        divideFrameBleSendData(command.getBytes());
    }

    @Override
    public void writeCommand(byte[] command) {
        divideFrameBleSendData(command);
    }

    //分包

    private void divideFrameBleSendData(byte[] data) {
        Utils.Logger(TAG, "BLE Write Command", new String(data));
        int tmpLen = data.length;
        int start = 0;
        int end = 0;
        while (tmpLen > 0) {
            byte[] sendData = new byte[21];
            if (tmpLen >= 20) {
                end += 20;
                sendData = Arrays.copyOfRange(data, start, end);
                start += 20;
                tmpLen -= 20;
            } else {
                end += tmpLen;
                sendData = Arrays.copyOfRange(data, start, end);
                tmpLen = 0;
            }

            mBleClient.write(mAddress, mServiceUUID, mSendCharacterUUID, sendData,
                    new BleWriteResponse() {
                        @Override
                        public void onResponse(int code) {

                        }
                    });

        }

        //spp
//        Intent actIntent = new Intent(BluetoothTools.ACTION_DATA_TO_GAME);
//        actIntent.putExtra("editViewData",data);
//        context.sendBroadcast(actIntent);
    }

    @Override
    public void notifyBle(final OnReadListener readListener) {
        if (mServiceUUID != null && mNotifyCharacterUUID != null) {
            mBleClient.notify(mAddress, mServiceUUID, mNotifyCharacterUUID, new BleNotifyResponse() {
                @Override
                public void onNotify(UUID service, UUID character, byte[] value) {
                    Utils.Logger(TAG, "BLE Notify", new String(value));
                    readListener.onRead(value);
                }

                @Override
                public void onResponse(int code) {
                    Utils.Logger(TAG, "BLE Notify code", code + "");
                }
            });
        }
    }

    @Override
    public void isBleConnected(final BleConnListener bleListener) {
        listener = new BleConnectStatusListener(){

            @Override
            public void onConnectStatusChanged(String mac, int status) {
                if (status == STATUS_CONNECTED){
                    bleListener.onConn();
                }else if (status == STATUS_DISCONNECTED){
                    bleListener.onDisConn();
                }
            }
        };
        mBleClient.registerConnectStatusListener(mAddress,listener);
    }

    @Override
    public void readCommand(final OnReadListener readListener) {
        mBleClient.read(mAddress, mServiceUUID, mSendCharacterUUID, (code, data) -> {
            if (code == REQUEST_SUCCESS) {
                readListener.onRead(data);
            }
        });

    }

    @Override
    public void unRegisterConn() {
        if (mAddress != null){
            mBleClient.unregisterConnectStatusListener(mAddress, listener);
            mBleClient.unnotify(mAddress, mServiceUUID, mSendCharacterUUID, code -> {

            });
            mBleClient.disconnect(mAddress);
            listener = null;
            conn = false;
        }
    }

    @Override
    public void stopSearch() {
        mBleClient.stopSearch();
    }
}