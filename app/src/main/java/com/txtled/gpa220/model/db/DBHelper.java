package com.txtled.gpa220.model.db;



import com.txtled.gpa220.bean.UserData;

import java.util.List;

/**
 * Created by Mr.Quan on 2018/4/17.
 */

public interface DBHelper {
    void setUserData(UserData data);

    List<UserData> getUserData();

    void deleteUserData(UserData data);

    void updateUserData(UserData data);

}
