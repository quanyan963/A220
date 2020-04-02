package com.txtled.gpa220.utils;

import android.Manifest;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class Constants {
    public static String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE };
    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;

    //ble连接状态
    public static final int DISCONN = 0;
    public static final int FINISH_SEARCH = 1;
    public static final int CONN = 2;
    public static final int RECONN = 3;
    public static final int SINGLE_DATA = 4;
    public static final int ALL_DATA = 5;
    public static final int ERROR = 6;

    //跳转界面
    public static final int ADD = 2000;
    public static final int USER = 2001;
    public static final int OK = 200;

    public static final String TYPE = "type";
    public static final String POSITION = "position";
    public static final String UNKNOWN = "未分组";
}
