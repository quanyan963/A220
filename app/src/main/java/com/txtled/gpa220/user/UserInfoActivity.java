package com.txtled.gpa220.user;

import android.graphics.Point;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.user.mvp.UserContract;
import com.txtled.gpa220.user.mvp.UserPresenter;
import com.txtled.gpa220.widget.CustomTextView;
import com.txtled.gpa220.widget.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.lv_user_chart)
    LineChartView lvUserChart;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        tvTitle.setText(R.string.user_info);
        ViewGroup.LayoutParams params = lvUserChart.getLayoutParams();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        params.height = point.y / 3;
        lvUserChart.setLayoutParams(params);
        List<Float> data = new ArrayList<>();
        data.add(35.6f);
        data.add(36.4f);
        data.add(38.6f);
        data.add(35.8f);
        data.add(38.6f);
        data.add(35.9f);
        data.add(36.6f);
        data.add(37.6f);

        lvUserChart.setData(data);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void beforeContentView() {

    }
}
