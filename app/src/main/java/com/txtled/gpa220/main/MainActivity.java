package com.txtled.gpa220.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.txtled.gpa220.R;
import com.txtled.gpa220.add.AddMenberActivity;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.main.mvp.MainContract;
import com.txtled.gpa220.main.mvp.MainPresenter;
import com.txtled.gpa220.widget.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.ADD;
import static com.txtled.gpa220.utils.Constants.OK;


public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.View,
        View.OnClickListener {

    @BindView(R.id.nv_main_left)
    NavigationView nvMainLeft;
    @BindView(R.id.rlv_member)
    RecyclerView rlvMember;
    private LinearLayout llBle,llSync,llExport,llInst,llSetting;

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.member);
        setNavigationIcon(false);
        setRightImg(true, getResources().getDrawable(R.mipmap.home_infoxhdpi), this);

        View view = nvMainLeft.getHeaderView(0);
        llBle = view.findViewById(R.id.ll_ble_conn);
        llSync = view.findViewById(R.id.ll_data_sync);
        llExport = view.findViewById(R.id.ll_data_export);
        llInst = view.findViewById(R.id.ll_inst);
        llSetting = view.findViewById(R.id.ll_setting);

        llBle.setOnClickListener(this);
        llSync.setOnClickListener(this);
        llExport.setOnClickListener(this);
        llInst.setOnClickListener(this);
        llSetting.setOnClickListener(this);

        rlvMember.setHasFixedSize(true);
        rlvMember.setLayoutManager(new GridLayoutManager(this,3));
        rlvMember.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.BOTH_SET,
                getResources().getDimensionPixelSize(R.dimen.dp_16_x),
                Color.TRANSPARENT));

    }

    @Override
    public void onLeftClick() {
        startActivityForResult(new Intent(this, AddMenberActivity.class),ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD){
            if (resultCode == OK){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onViewClick(v);
    }
}
