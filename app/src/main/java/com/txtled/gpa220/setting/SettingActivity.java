package com.txtled.gpa220.setting;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.setting.mvp.SetContract;
import com.txtled.gpa220.setting.mvp.SetPresenter;
import com.txtled.gpa220.widget.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class SettingActivity extends MvpBaseActivity<SetPresenter> implements SetContract.View {
    @BindView(R.id.ctv_setting_version)
    CustomTextView ctvSettingVersion;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.setting);
        try {
            ctvSettingVersion.setText(getPackageManager().getPackageInfo(getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void beforeContentView() {

    }
}
