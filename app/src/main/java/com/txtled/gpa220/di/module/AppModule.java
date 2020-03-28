package com.txtled.gpa220.di.module;

import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.db.DBHelper;
import com.txtled.gpa220.model.db.DBHelperImpl;
import com.txtled.gpa220.model.net.NetHelper;
import com.txtled.gpa220.model.net.NetHelperImpl;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.model.operate.OperateHelperImpl;
import com.txtled.gpa220.model.prefs.PreferencesHelper;
import com.txtled.gpa220.model.prefs.PreferencesHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by KomoriWu
 * on 2017/9/15.
 */
@Module
public class AppModule {
    private MyApplication myApplication;

    public AppModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplication() {
        return myApplication;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(DBHelperImpl dbHelper) {
        return dbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesHelperImpl preferencesHelper) {
        return preferencesHelper;
    }

    @Provides
    @Singleton
    NetHelper provideNetHelper(NetHelperImpl netHelper) {
        return netHelper;
    }

    @Provides
    @Singleton
    OperateHelper provideOperateHelper(OperateHelperImpl operateHelper) {
        return operateHelper;
    }

    @Provides
    @Singleton
    DataManagerModel provideDataManagerModel(DBHelperImpl dbHelper,
                                             PreferencesHelperImpl preferencesHelper,
                                             NetHelperImpl netHelper, OperateHelperImpl operateHelper) {
        return new DataManagerModel(dbHelper, preferencesHelper,netHelper,operateHelper);
    }
}
