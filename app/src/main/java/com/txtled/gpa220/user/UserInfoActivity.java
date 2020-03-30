package com.txtled.gpa220.user;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.user.mvp.UserContract;
import com.txtled.gpa220.user.mvp.UserPresenter;
import com.txtled.gpa220.widget.CustomTextView;

import butterknife.BindView;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class UserInfoActivity extends MvpBaseActivity<UserPresenter> implements UserContract.View {
    @BindView(R.id.img_measure)
    ImageView imgMeasure;
    @BindView(R.id.ctv_measure)
    CustomTextView ctvMeasure;
    @BindView(R.id.rl_measure)
    LinearLayout rlMeasure;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.ctv_change)
    CustomTextView ctvChange;
    @BindView(R.id.rl_change)
    LinearLayout rlChange;
    @BindView(R.id.img_export)
    ImageView imgExport;
    @BindView(R.id.ctv_export)
    CustomTextView ctvExport;
    @BindView(R.id.rl_export)
    LinearLayout rlExport;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ctv_delete)
    CustomTextView ctvDelete;
    @BindView(R.id.rl_delete)
    LinearLayout rlDelete;
    @BindView(R.id.rl_user_list)
    RelativeLayout rlUserList;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.ctv_user_name)
    CustomTextView ctvUserName;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        tvTitle.setText(R.string.user_info);


    }

    @Override
    public int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void beforeContentView() {

    }
}
