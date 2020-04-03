package com.txtled.gpa220.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.txtled.gpa220.application.MyApplication;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2018/4/17.
 */

public class PreferencesHelperImpl implements PreferencesHelper {
    private static final String SP_NAME = "my_sp";
    private SharedPreferences mSharedPreferences;
    public static final String PLAY_POSITION = "play_position";
    public static final String IS_FIRST_APP = "is_first_app";
    public static final String BLE_ADDRESS = "ble_address";
    public static final String CLOSED = "closed";

    @Inject
    public PreferencesHelperImpl() {
        mSharedPreferences = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.
                MODE_PRIVATE);
    }

    @Override
    public int getBlePosition() {
        return mSharedPreferences.getInt(PLAY_POSITION, -1);
    }

    @Override
    public void setBlePosition(int position) {
        mSharedPreferences.edit().putInt(PLAY_POSITION, position).apply();
    }

    @Override
    public boolean isFirstIn() {
        return mSharedPreferences.getBoolean(IS_FIRST_APP,true);
    }

    @Override
    public void setFirstIn(boolean first) {
        mSharedPreferences.edit().putBoolean(IS_FIRST_APP,first).apply();
    }

    @Override
    public String getBleAddress() {
        return mSharedPreferences.getString(BLE_ADDRESS,"");
    }

    @Override
    public void setBleAddress(String address) {
        mSharedPreferences.edit().putString(BLE_ADDRESS,address).apply();
    }

    @Override
    public boolean isClosed() {
        return mSharedPreferences.getBoolean(CLOSED,false);
    }

    @Override
    public void setClosed(boolean closed) {
        mSharedPreferences.edit().putBoolean(CLOSED,closed).apply();
    }
//
//    @Override
//    public void setMainVolume(int progress) {
//        mSharedPreferences.edit().putInt(MAIN_VOLUME,progress).apply();
//    }
//
//    @Override
//    public int getMainVolume() {
//        return mSharedPreferences.getInt(MAIN_VOLUME,0);
//    }
//
//    @Override
//    public void setInitDialog(boolean b) {
//
//    }
//
//    @Override
//    public boolean getInitDialog() {
//        return false;
//    }
//
//    @Override
//    public boolean getIsCycle() {
//        return mSharedPreferences.getBoolean(IS_CYCLE,true);
//    }
//
//    @Override
//    public void setIsCycle(boolean b) {
//        mSharedPreferences.edit().putBoolean(IS_CYCLE,b).apply();
//    }
//
//    @Override
//    public boolean getIsRandom() {
//        return mSharedPreferences.getBoolean(IS_RANDOM,false);
//    }
//
//    @Override
//    public void setIsRandom(boolean b) {
//        mSharedPreferences.edit().putBoolean(IS_RANDOM,b).apply();
//    }
}
