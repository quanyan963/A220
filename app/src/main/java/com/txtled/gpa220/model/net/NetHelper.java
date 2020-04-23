package com.txtled.gpa220.model.net;

import android.content.Context;

import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Mr.Quan on 2018/11/12.
 */

public interface NetHelper {
    void getHttp(String userName, String pass, StringCallback callback);
}
