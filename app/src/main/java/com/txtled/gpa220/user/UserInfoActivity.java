package com.txtled.gpa220.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.txtled.gpa220.R;
import com.txtled.gpa220.add.AddMemberActivity;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.user.mvp.UserContract;
import com.txtled.gpa220.user.mvp.UserPresenter;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.Utils;
import com.txtled.gpa220.widget.CustomTextView;
import com.txtled.gpa220.widget.LineChartView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.txtled.gpa220.utils.Constants.ADD;
import static com.txtled.gpa220.utils.Constants.ALL_DATA;
import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.OK;
import static com.txtled.gpa220.utils.Constants.POSITION;
import static com.txtled.gpa220.utils.Constants.RECONN;
import static com.txtled.gpa220.utils.Constants.SINGLE_DATA;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class UserInfoActivity extends MvpBaseActivity<UserPresenter> implements UserContract.View {
    @BindView(R.id.img_measure)
    ImageView imgMeasure;
    @BindView(R.id.ctv_measure)
    CustomTextView ctvMeasure;
    @BindView(R.id.ll_measure)
    LinearLayout llMeasure;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.ctv_change)
    CustomTextView ctvChange;
    @BindView(R.id.ll_change)
    LinearLayout llChange;
    @BindView(R.id.img_export)
    ImageView imgExport;
    @BindView(R.id.ctv_export)
    CustomTextView ctvExport;
    @BindView(R.id.ll_export)
    LinearLayout llExport;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ctv_delete)
    CustomTextView ctvDelete;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.rl_user_list)
    RelativeLayout rlUserList;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.ctv_user_name)
    CustomTextView ctvUserName;
    @BindView(R.id.lv_user_chart)
    LineChartView lvUserChart;
    private int position;
    private UserData data;
    private int layoutId = R.id.ll_measure;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        position = intent.getIntExtra(POSITION,0);
        data = presenter.getData(position);
        initToolbar();
        setNavigationIcon(true);
        tvTitle.setText(R.string.user_info);
        ViewGroup.LayoutParams params = lvUserChart.getLayoutParams();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        params.height = point.y / 3;
        lvUserChart.setLayoutParams(params);
        lvUserChart.setAllData(data.getData());
        initUser();

        llMeasure.setOnClickListener(v -> changeColor(v.getId()));
        llChange.setOnClickListener(v -> changeColor(v.getId()));
        llExport.setOnClickListener(v -> changeColor(v.getId()));
        llDelete.setOnClickListener(v -> changeColor(v.getId()));

        Drawable drawable;
        Drawable wrap;
        drawable = imgMeasure.getDrawable();
        wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.blue));
        imgMeasure.setImageDrawable(wrap);

        drawable = imgChange.getDrawable();
        wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
        imgChange.setImageDrawable(wrap);

        drawable = imgExport.getDrawable();
        wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
        imgExport.setImageDrawable(wrap);

        drawable = imgDelete.getDrawable();
        wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
        imgDelete.setImageDrawable(wrap);
    }

    private void initUser() {
        //显示圆形图片
        Bitmap bitmap = Utils.drawableToBitmap(getResources()
                .getDrawable(data.getSex() == 0 ? R.mipmap.home_boyxhdpi : R.mipmap.home_girlxhdpi));
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory
                .create(getResources(),bitmap);
        roundedDrawable.setCircular(true);
        imgUser.setImageDrawable(roundedDrawable);
        imgUser.setBackground(getResources()
                .getDrawable(R.drawable.img_forground));
        ctvUserName.setText(data.getUserName());
    }

    private void changeColor(int id) {
        Drawable drawable;
        Drawable wrap;
        switch (id){
            case R.id.ll_measure:
                drawable = imgMeasure.getDrawable();
                wrap = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.blue));
                imgMeasure.setImageDrawable(wrap);
                ctvMeasure.setTextColor(getResources().getColor(R.color.blue));
                break;
            case R.id.ll_change:
                drawable = imgChange.getDrawable();
                wrap = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.blue));
                imgChange.setImageDrawable(wrap);
                ctvChange.setTextColor(getResources().getColor(R.color.blue));
                startActivityForResult(new Intent(UserInfoActivity.this,
                        AddMemberActivity.class).putExtra(POSITION,position),ADD);
                break;
            case R.id.ll_export:
                drawable = imgExport.getDrawable();
                wrap = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.blue));
                imgExport.setImageDrawable(wrap);
                ctvExport.setTextColor(getResources().getColor(R.color.blue));
                break;
            default:
                drawable = imgDelete.getDrawable();
                wrap = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.blue));
                imgDelete.setImageDrawable(wrap);
                ctvDelete.setTextColor(getResources().getColor(R.color.blue));
                AlertUtils.showAlertDialog(this, R.string.conform_delete,
                        (dialog, which) -> {
                            presenter.deleteUser(position);
                            UserInfoActivity.this.finish();
                        });
                break;
        }
        switch (layoutId){
            case R.id.ll_measure:
                if (layoutId != id){
                    drawable = imgMeasure.getDrawable();
                    wrap = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
                    imgMeasure.setImageDrawable(wrap);
                    ctvMeasure.setTextColor(getResources().getColor(R.color.gray));
                }
                break;
            case R.id.ll_change:
                if (layoutId != id){
                    drawable = imgChange.getDrawable();
                    wrap = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
                    imgChange.setImageDrawable(wrap);
                    ctvChange.setTextColor(getResources().getColor(R.color.gray));
                }
                break;
            case R.id.ll_export:
                if (layoutId != id){
                    drawable = imgExport.getDrawable();
                    wrap = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
                    imgExport.setImageDrawable(wrap);
                    ctvExport.setTextColor(getResources().getColor(R.color.gray));
                }
                break;
            default:
                if (layoutId != id){
                    drawable = imgDelete.getDrawable();
                    wrap = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this,R.color.gray));
                    imgDelete.setImageDrawable(wrap);
                    ctvDelete.setTextColor(getResources().getColor(R.color.gray));
                }
                break;
        }
        layoutId = id;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD){
            if (resultCode == OK){
                this.data = presenter.getData(position);
                initUser();
                this.setResult(OK);
                EventBus.getDefault().post(this.data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public void onEventServiceThread(BleControlEvent event) {
        if (event.getBleConnType() == RECONN){

        }else if (event.getBleConnType() == SINGLE_DATA){
            //presenter.setTempData(mPosition,event.getTemp());
            lvUserChart.setSingleData(event.getTemp());
        }else if (event.getBleConnType() == ALL_DATA){

        }else if (event.getBleConnType() == CONN){

        }
        super.onEventServiceThread(event);
    }
}
