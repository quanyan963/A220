package com.txtled.gpa220.model.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import javax.inject.Inject;

import static com.txtled.gpa220.utils.Constants.LOGIN_URL;
import static com.txtled.gpa220.utils.Constants.URL_PASSWORD;
import static com.txtled.gpa220.utils.Constants.URL_USER_ID;


/**
 * Created by Mr.Quan on 2018/11/12.
 */

public class NetHelperImpl implements NetHelper {



    @Inject
    public NetHelperImpl() {
    }

    @Override
    public void getHttp(String userName, String pass, StringCallback callback) {
        OkGo.<String>get(LOGIN_URL)//+"?"+URL_USER_ID+"="+userName+"&"+URL_PASSWORD+"="+pass
                .params(URL_USER_ID,userName)
                .params(URL_PASSWORD,pass)
                .execute(callback);
    }
}
