package com.txtled.gpa220.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.navigation.NavigationView;
import com.txtled.gpa220.R;
import com.txtled.gpa220.add.AddMemberActivity;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.ble.BleActivity;
import com.txtled.gpa220.ble.service.BleService;
import com.txtled.gpa220.login.LoginActivity;
import com.txtled.gpa220.main.mvp.MainContract;
import com.txtled.gpa220.main.mvp.MainPresenter;
import com.txtled.gpa220.pdf.PdfActivity;
import com.txtled.gpa220.setting.SettingActivity;
import com.txtled.gpa220.unknown.UnknownActivity;
import com.txtled.gpa220.user.UserInfoActivity;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.widget.DividerItemDecoration;

import butterknife.BindView;

import static com.txtled.gpa220.utils.Constants.ADD;
import static com.txtled.gpa220.utils.Constants.ALL_DATA;
import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DELETE;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.OK;
import static com.txtled.gpa220.utils.Constants.POSITION;
import static com.txtled.gpa220.utils.Constants.RECONN;
import static com.txtled.gpa220.utils.Constants.SINGLE_DATA;
import static com.txtled.gpa220.utils.Constants.UNKNOWN;
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
    private LinearLayout llBle, llSync, llExport, llInst, llSetting, llLogOut;
    private MemberAdapter adapter;
    private int type, mPosition,bleType;
    private AlertDialog dialog;

    @Override
    public void init() {
        initToolbar();
        tvTitle.setText(R.string.member);
        setNavigationIcon(false);

        presenter.init(this);
        setSecondImage(presenter.isClosed());
        setRightImg(true, getResources().getDrawable(R.mipmap.home_infoxhdpi), this);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        View view = nvMainLeft.getHeaderView(0);
        llBle = view.findViewById(R.id.ll_ble_conn);
        llSync = view.findViewById(R.id.ll_data_sync);
        llExport = view.findViewById(R.id.ll_data_export);
        llInst = view.findViewById(R.id.ll_inst);
        llSetting = view.findViewById(R.id.ll_setting);
        llLogOut = view.findViewById(R.id.ll_logout);
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
        llLogOut.setOnClickListener(this);

        rlvMember.setHasFixedSize(true);
        rlvMember.setLayoutManager(new GridLayoutManager(this, 3));
        rlvMember.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.BOTH_SET,
                getResources().getDimensionPixelSize(R.dimen.dp_16_x),
                Color.TRANSPARENT));
        adapter = new MemberAdapter(this,this);
        rlvMember.setAdapter(adapter);
        adapter.setData(presenter.getUserData());
        ((SimpleItemAnimator)rlvMember.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    public void onLeftClick() {
        type = 0;
        startActivityForResult(new Intent(this, AddMemberActivity.class), ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD) {
            if (resultCode == OK) {
                //adapter.setData(presenter.getUserData());
            }
        }else if (requestCode == USER){
            if (resultCode == DELETE){
                adapter.deleteData(mPosition);
            }else if(resultCode == OK){
                adapter.notifyTrueItem(mPosition);
            }
        }else if (requestCode == UNKNOWN){
            if (resultCode == OK){
                adapter.notifyTrueItem(0);
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
            presenter.insertData(str);
            adapter.insertData();
            mPosition = presenter.getDataSize() - 1;

        }else if (type == 1){
            presenter.update(mPosition,str);
            adapter.notifyTrueItem(mPosition);
        }

        super.onEventMainThread(str);
    }


    @Override
    public void onEventServiceThread(BleControlEvent event) {
        bleType = event.getBleConnType();
        runOnUiThread(() -> {
            if (event.getBleConnType() == RECONN){
                setSecondImage(false);
                presenter.setClosed(false);
                if (dialog != null){
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
            }else if (event.getBleConnType() == SINGLE_DATA){
                presenter.setTempData(mPosition,event.getTemp());
            }else if (event.getBleConnType() == ALL_DATA){
                presenter.setAllTempData(mPosition,event.getAllTemp());
                if (dialog != null){
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                presenter.showSyncSuccess();
            }else if (event.getBleConnType() == CONN){
                setSecondImage(true);
                presenter.setClosed(true);
            }else if (event.getBleConnType() == DISCONN){
                setSecondImage(false);
                presenter.setClosed(false);
            }
        });
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
    public void toBleView() {
        startActivity(new Intent(this, BleActivity.class));
    }

    @Override
    public void refreshView() {
        adapter.notifyTrueItem(mPosition);
        //adapter.update(mPosition,str);
    }

    @Override
    public void showLoadingView() {
        dialog = AlertUtils.showLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void toPdfView() {
        startActivity(new Intent(this, PdfActivity.class));
    }

    @Override
    public void showSyncSuccess() {
        hideSnackBar();
        showSnackBar(rlvMember,R.string.sync_success);
    }

    @Override
    public void hidSnack() {
        hideSnackBar();
    }

    @Override
    public void toSettingView() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void toLoginView() {
        AlertUtils.showAlertDialog(this, R.string.sign_out_check,R.string.conform,R.string.no,
                (dialog, which) -> {
                    dlMain.closeDrawer(nvMainLeft);
                    startActivity(new Intent(MainActivity.this,
                            LoginActivity.class));
                });
    }

    @Override
    public void hidLoading() {
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showPermissionHint() {
        hidSnack();
        showSnackBar(rlvMember,R.string.no_export);
        presenter.hidDelay();
    }

    @Override
    public void showUnConnView() {
        hidSnack();
        AlertUtils.showAlertDialog(this, R.string.ble_unconn_hint, R.string.conn,
                R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toBleView();
            }
        });
    }

    @Override
    public void onOnceClick(int position) {
        mPosition = position;
        presenter.setUserPosition(position);
    }

    @Override
    public void onTwiceClick(int position,int userType) {
        type = 1;
        if (mPosition == position){
            if (userType == 2){
                startActivityForResult(new Intent(this, UnknownActivity.class),UNKNOWN);
            }else {
                startActivityForResult(new Intent(this, UserInfoActivity.class)
                        .putExtra(POSITION,position),USER);
            }
        }
        mPosition = position;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return onExitActivity(keyCode, event);
    }

    @Override
    public void onDestroy() {
        presenter.setClosed(false);
        stopService(new Intent(this, BleService.class));
        super.onDestroy();
    }
}
