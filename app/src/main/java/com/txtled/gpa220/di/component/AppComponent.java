package com.txtled.gpa220.di.component;

import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.di.module.AppModule;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.ble.BleHelper;
import com.txtled.gpa220.model.db.DBHelper;
import com.txtled.gpa220.model.net.NetHelper;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by KomoriWu
 * on 2017-09-01.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    MyApplication getContext();

    DataManagerModel getDataManagerModel();

    DBHelper getDbHelper();

    PreferencesHelper getPreferencesHelper();

    NetHelper getNetHelper();

    OperateHelper getOperateHelper();

    BleHelper getBleHelper();
}
