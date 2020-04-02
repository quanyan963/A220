package com.txtled.gpa220.di.component;

import android.app.Activity;

import com.txtled.gpa220.add.AddMemberActivity;
import com.txtled.gpa220.ble.BleActivity;
import com.txtled.gpa220.di.module.ActivityModule;
import com.txtled.gpa220.di.scope.ActivityScope;
import com.txtled.gpa220.main.MainActivity;
import com.txtled.gpa220.start.StartActivity;
import com.txtled.gpa220.user.UserInfoActivity;

import dagger.Component;

/**
 * Created by KomoriWu
 * on 2017-09-01.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(StartActivity startActivity);

    void inject(BleActivity bleActivity);

    void inject(AddMemberActivity addMemberActivity);

    void inject(UserInfoActivity userInfoActivity);
}
