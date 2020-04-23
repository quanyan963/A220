package com.txtled.gpa220.setting;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.setting.mvp.SetContract;
import com.txtled.gpa220.setting.mvp.SetPresenter;
import com.txtled.gpa220.widget.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.RECONN;

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
        setNavigationIcon(true);
        tvTitle.setText(R.string.setting);
        setSecondImage(presenter.isClosed());
        try {
            ctvSettingVersion.setText(getPackageManager()
                    .getPackageInfo(getPackageName(),0).versionName);
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

    @Override
    public void onEventMainThread(BleControlEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getBleConnType() == RECONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }else if (event.getBleConnType() == CONN){
                    setSecondImage(true);
                    presenter.setClosed(true);
                }else if (event.getBleConnType() == DISCONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }
            }
        });

    }
}
