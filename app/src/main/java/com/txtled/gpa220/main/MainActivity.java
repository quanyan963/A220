package com.txtled.gpa220.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.txtled.gpa220.R;
import com.txtled.gpa220.add.AddMenberActivity;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.main.mvp.MainContract;
import com.txtled.gpa220.main.mvp.MainPresenter;
import com.txtled.gpa220.user.UserInfoActivity;
import com.txtled.gpa220.widget.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.ADD;
import static com.txtled.gpa220.utils.Constants.OK;
import static com.txtled.gpa220.utils.Constants.POSITION;
import static com.txtled.gpa220.utils.Constants.USER;


public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.View,
        View.OnClickListener, MemberAdapter.OnItemClick {

    @BindView(R.id.nv_main_left)
    NavigationView nvMainLeft;
    @BindView(R.id.rlv_member)
    RecyclerView rlvMember;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    private ImageView close;
    private LinearLayout llBle, llSync, llExport, llInst, llSetting;
    private MemberAdapter adapter;
    private int type, mPosition;

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.member);
        setNavigationIcon(false);
        setRightImg(true, getResources().getDrawable(R.mipmap.home_infoxhdpi), this);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        View view = nvMainLeft.getHeaderView(0);
        llBle = view.findViewById(R.id.ll_ble_conn);
        llSync = view.findViewById(R.id.ll_data_sync);
        llExport = view.findViewById(R.id.ll_data_export);
        llInst = view.findViewById(R.id.ll_inst);
        llSetting = view.findViewById(R.id.ll_setting);
        close = view.findViewById(R.id.img_menu_close);
        View line = view.findViewById(R.id.v_menu_line_one);
        ViewGroup.MarginLayoutParams lineParams = (ViewGroup.MarginLayoutParams) line.getLayoutParams();
        ViewGroup.LayoutParams params = nvMainLeft.getLayoutParams();

        //动态设置宽高
        params.width = point.x / 2;
        lineParams.setMargins(0,point.y/2,0,0);
        nvMainLeft.setLayoutParams(params);
        line.setLayoutParams(lineParams);



        close.setOnClickListener(this);
        llBle.setOnClickListener(this);
        llSync.setOnClickListener(this);
        llExport.setOnClickListener(this);
        llInst.setOnClickListener(this);
        llSetting.setOnClickListener(this);

        rlvMember.setHasFixedSize(true);
        rlvMember.setLayoutManager(new GridLayoutManager(this, 3));
        rlvMember.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.BOTH_SET,
                getResources().getDimensionPixelSize(R.dimen.dp_16_x),
                Color.TRANSPARENT));
        adapter = new MemberAdapter(this,this);
        rlvMember.setAdapter(adapter);
        adapter.setData(presenter.getUserData());

    }

    @Override
    public void onLeftClick() {
        type = 0;
        startActivityForResult(new Intent(this, AddMenberActivity.class), ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD) {
            if (resultCode == OK) {
                adapter.setData(presenter.getUserData());
            }
        }else if (requestCode == USER){
            if (requestCode == OK){
                adapter.notifyTrueItem(mPosition);
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
        setTheme(R.style.BlackTheme);
    }

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        presenter.onViewClick(v);
    }

    @Override
    public void onEventMainThread(UserData str) {
        if (type == 0){
            adapter.insertData(str);
            presenter.insertData(str);
        }else if (type == 1){
            adapter.update(mPosition,str);
            presenter.update(mPosition,str);
        }

        super.onEventMainThread(str);
    }

    @Override
    public void showLeftView() {
        dlMain.openDrawer(nvMainLeft);
    }

    @Override
    public void closeLeftView() {
        dlMain.closeDrawer(nvMainLeft);
    }

    @Override
    public void onOnceClick(int position) {
        presenter.setUserPosition(position);
    }

    @Override
    public void onTwiceClick(int position,int userType) {
        type = 1;
        mPosition = position;
        if (userType == 2){
            startActivityForResult(new Intent(this, UserInfoActivity.class)
                    .putExtra(POSITION,position),USER);
        }else {
            startActivityForResult(new Intent(this, UserInfoActivity.class)
                    .putExtra(POSITION,position),USER);
        }
    }
}
