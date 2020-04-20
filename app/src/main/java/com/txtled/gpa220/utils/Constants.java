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
    public static final int DELETE = 201;

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

    //url
    //登录
    public static final String LOGIN_URL = "http://8.129.216.236:8080/gpa220/login.ups";
    //注册
    public static final String REGESTRUE_URL = "http://8.129.216.236:8080/gpa220/regestrue.ups";
    //找回密码
    public static final String FORGET_URL = "http://8.129.216.236:8080/gpa220/recoverPass.ups";
    //查询，查重
    public static final String SEARCH_URL = "http://8.129.216.236:8080/gpa220/queryPhone.ups";
    //找回密码获取验证码
    public static final String PASS_GET_CODE_URL = "http://8.129.216.236:8080/gpa220/recoverPassGetValid.ups";
    //修改密码获取验证码
    public static final String CHANGE_PASS_CODE_URL = "http://8.129.216.236:8080/gpa220/changePassGetValid.ups";
    //修改密码
    public static final String CHANGE_PASS_URL = "http://8.129.216.236:8080/gpa220/changePass.ups";
}
