package com.txtled.gpa220.unknown;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.unknown.mvp.UnknownContract;
import com.txtled.gpa220.unknown.mvp.UnknownPresenter;
import com.txtled.gpa220.widget.CustomButton;
import com.txtled.gpa220.widget.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.RECONN;
import static com.txtled.gpa220.utils.Constants.SINGLE_DATA;

public class UnknownActivity extends MvpBaseActivity<UnknownPresenter> implements UnknownContract.View,
        DataAdapter.OnDataClickListener, UserAdapter.OnUserClickListener {
    @BindView(R.id.rlv_unknown_list)
    RecyclerView rlvUnknownList;
    @BindView(R.id.cbt_bind)
    CustomButton cbtBind;
    @BindView(R.id.rlv_unknown_user)
    RecyclerView rlvUnknownUser;

    private DataAdapter dataAdapter;
    private UserAdapter userAdapter;
    private int position;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        setSecondImage(presenter.isClosed());
        cbtBind.setEnabled(false);
        rlvUnknownUser.setHasFixedSize(true);
        rlvUnknownUser.setLayoutManager(new GridLayoutManager(this,3));
        rlvUnknownUser.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.BOTH_SET,
                getResources().getDimensionPixelSize(R.dimen.dp_16_x),
                Color.TRANSPARENT));
        userAdapter = new UserAdapter(this, this);

        userAdapter.setData(presenter.getUserData());
        rlvUnknownUser.setAdapter(userAdapter);

        tvTitle.setText(R.string.unbind_data);
        //rlvUnknownList.setHasFixedSize(true);
        rlvUnknownList.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this,this);
        rlvUnknownList.setAdapter(dataAdapter);
        dataAdapter.setData(presenter.getUnknownData());

        cbtBind.setOnClickListener(v -> {
            if (rlvUnknownUser.getVisibility() == View.GONE){
                showUserAnimation(true);
            }else {
                presenter.setData(position,dataAdapter.getChecked());
            }
        });
        rlvUnknownList.scrollToPosition(dataAdapter.getItemCount() - 1);
    }

    private void showUserAnimation(boolean isShow) {
        AlphaAnimation show =new AlphaAnimation(0,1);
        show.setFillAfter(true);
        show.setDuration(200);
        AlphaAnimation hid =new AlphaAnimation(1,0);
        hid.setFillAfter(true);
        hid.setDuration(200);
        hid.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShow){
                    rlvUnknownUser.startAnimation(show);
                    rlvUnknownUser.setVisibility(View.VISIBLE);
                    //将控件显示在最上层
                    rlvUnknownUser.bringToFront();
                    cbtBind.setText(R.string.bind);
                    cbtBind.startAnimation(show);

                }else {
                    rlvUnknownList.startAnimation(show);
                    rlvUnknownList.setVisibility(View.VISIBLE);
                    //将控件显示在最上层
                    rlvUnknownList.bringToFront();
                    cbtBind.setText(R.string.bind_user);
                    cbtBind.startAnimation(show);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (isShow){
            tvTitle.setText(R.string.select_member);
            rlvUnknownList.startAnimation(hid);
            rlvUnknownList.setVisibility(View.INVISIBLE);
            cbtBind.startAnimation(hid);
        }else {
            tvTitle.setText(R.string.unbind_data);

            rlvUnknownUser.startAnimation(hid);
            rlvUnknownUser.setVisibility(View.GONE);
            cbtBind.startAnimation(hid);
        }
    }

    @Override
    public void onBackPressed() {
        if (rlvUnknownUser.getVisibility() == View.VISIBLE){
            showUserAnimation(false);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_unknown;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public void onDataClick() {
        cbtBind.setEnabled(true);
    }

    @Override
    public void onUserClick(int position) {
        this.position = position;
    }

    @Override
    public void showFinish() {
        hidSnack();
        showUserAnimation(false);
        showSnackBar(cbtBind,R.string.bind_success);
    }

    @Override
    public void hidSnack() {
        hideSnackBar();
    }

    @Override
    public void onEventMainThread(BleControlEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getBleConnType() == SINGLE_DATA){
                    if (dataAdapter.getItemCount() == 0){
                        dataAdapter.setData(presenter.getUnknownData());
                    }else {
                        dataAdapter.insertData(event.getTemp());
                    }
                    rlvUnknownList.scrollToPosition(dataAdapter.getItemCount() - 1);
                }else if (event.getBleConnType() == CONN){
                    setSecondImage(true);
                    presenter.setClosed(true);
                }else if (event.getBleConnType() == DISCONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }else if (event.getBleConnType() == RECONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }
            }
        });
    }
}
