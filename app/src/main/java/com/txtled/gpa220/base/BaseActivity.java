package com.txtled.gpa220.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.snackbar.Snackbar;
import com.txtled.gpa220.R;
import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.widget.CustomTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.RECONN;


public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public CustomTextView tvTitle;
    private CustomTextView tvRight;
    public boolean isBack = true;
    public boolean changeColor = true;
    private long mExitTime;
    private MyApplication mApplication;
    public Toolbar toolbar;
    public Snackbar snackbar;
    private ImageView ivRight,ivRightSecond;

    public abstract void init();

    public abstract int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        beforeContentView();
        setContentView(getLayout());
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mApplication = MyApplication.getInstance();
        addActivity();
        onCreateView();
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected abstract void beforeContentView();

    public void onCreateView() {

    }

    public void setRightText(int str){
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(str);
    }

    public void changeRightTextColor(int color){
        tvRight.setTextColor(getResources().getColor(color));
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            tvTitle = (CustomTextView) findViewById(R.id.tv_title);
            ivRight = (ImageView) findViewById(R.id.iv_right);
            tvRight = (CustomTextView) findViewById(R.id.tv_right);
            ivRightSecond = (ImageView) findViewById(R.id.iv_right_second);
            setSupportActionBar(toolbar);
            setTitle("");

            toolbar.setOnMenuItemClickListener(onMenuItemClick);
            toolbar.setNavigationOnClickListener(v -> {
                if (isBack){
                    onBackPressed();
                }else {
                    onLeftClick();
                }

            });
            tvRight.setOnClickListener(v -> onTvRightClick());
        }
    }

    public void onTvRightClick(){

    }

    public void onLeftClick() {
    }

    public void setSecondImage(boolean isClosed){
        ivRightSecond.setVisibility(View.VISIBLE);
        Drawable drawable = ivRightSecond.getDrawable();
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, ContextCompat.getColor(this,isClosed ?
                R.color.blue : R.color.gray));
        ivRightSecond.setImageDrawable(drawable);
    }

    public void setNavigationIcon(boolean isBack) {
        this.isBack = isBack;
        if (isBack) {
            toolbar.setNavigationIcon(R.mipmap.back_btnxhdpi);
        } else {
            toolbar.setNavigationIcon(R.mipmap.home_addxhdpi);
        }

    }

    public void setRightImg(boolean isShow, @Nullable Drawable drawable, View.OnClickListener listener) {
        if (isShow) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageDrawable(drawable);
            ivRight.setOnClickListener(listener);
        } else {
            ivRight.setVisibility(View.GONE);
        }

    }

    public Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            OnMenuItemClick(menuItem.getItemId());
            return true;
        }
    };

    public void OnMenuItemClick(int itemId) {

    }

    public boolean onExitActivity(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_program_hint,
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                removeAllActivity();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void addActivity() {
        mApplication.addActivity(this);
    }


    public void removeAllActivity() {
        mApplication.removeAllActivity();
    }

    public void showSnackBar(View view, int str) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, str, Snackbar.LENGTH_INDEFINITE);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        snackbar.show();
    }

    public void showSnackBar(View view, int str, int btnStr, View.OnClickListener listener) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, str, Snackbar.LENGTH_INDEFINITE).setAction(btnStr,listener);
            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        snackbar.show();
    }

    public void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

    @Subscribe
    public void onEventMainThread(UserData str) {

    }

    @Subscribe
    public void onEventMainThread(BleControlEvent event) {

    }
}
