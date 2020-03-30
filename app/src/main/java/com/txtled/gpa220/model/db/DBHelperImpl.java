package com.txtled.gpa220.model.db;

import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.bean.dao.DaoMaster;
import com.txtled.gpa220.bean.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by Mr.Quan on 2018/4/17.
 */

public class DBHelperImpl implements DBHelper {
    private static final String DB_NAME = "a220.db";
    private DaoSession mDaoSession;

    @Inject
    public DBHelperImpl() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.
                getInstance(), DB_NAME);
        Database db = openHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    @Override
    public void setUserData(UserData data) {
        mDaoSession.getUserDataDao().insert(data);
    }

    @Override
    public List<UserData> getUserData() {
        return mDaoSession.getUserDataDao().loadAll();
    }

    @Override
    public void deleteUserData(UserData data) {
        mDaoSession.getUserDataDao().delete(data);
    }

    @Override
    public void updateUserData(UserData data) {
        mDaoSession.getUserDataDao().update(data);
    }
}
