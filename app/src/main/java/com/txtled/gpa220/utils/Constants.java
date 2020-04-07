package com.txtled.gpa220.utils;

import android.Manifest;

/**
 * Created by Mr.Quan on 2020/3/25.
 */
public class Constants {
    public static String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
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
    public static final int UNKNOWN = 2002;
    public static final int OK = 200;

    public static final String TYPE = "type";
    public static final String SUPER_ACCOUNT = "13888888888";
    public static final String POSITION = "position";
    public static final String UNKNOWN_NAME = "未分组";
    public static final String PDF_NAME = "gp220instructions.pdf";
    public static final String IDENTITY_POOL_ID = "us-east-1:a7fd6e3b-f444-41e7-ae9b-38f8ccef53cb";
    //ddb
    public static final String DB_NAME = "TEMP_USER";
    public static final String USER_ID = "UserId";
    public static final String PASSWORD = "Password";
}
