package com.txtled.gpa220.di.component;

import android.app.Activity;


import com.txtled.gpa220.di.module.FragmentModule;
import com.txtled.gpa220.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by KomoriWu
 * on 2017-09-01.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    //void inject(IntroductionFragment introductionFragment);
}
