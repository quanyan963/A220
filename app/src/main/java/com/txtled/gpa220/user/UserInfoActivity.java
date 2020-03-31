package com.txtled.gpa220.user;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.user.mvp.UserContract;
import com.txtled.gpa220.user.mvp.UserPresenter;
import com.txtled.gpa220.utils.Utils;
import com.txtled.gpa220.widget.CustomTextView;
import com.txtled.gpa220.widget.LineChartView;

import butterknife.BindView;

import static com.txtled.gpa220.utils.Constants.POSITION;

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
        lvUserChart.setData(data.getData());
        Bitmap bitmap = Utils.drawableToBitmap(getResources()
                .getDrawable(data.getSex() == 0 ? R.mipmap.home_boyxhdpi : R.mipmap.home_girlxhdpi));
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory
                .create(getResources(),bitmap);
        roundedDrawable.setCircular(true);
        imgUser.setImageDrawable(roundedDrawable);
        imgUser.setBackground(getResources()
                .getDrawable(R.drawable.img_forground));
        ctvUserName.setText(data.getUserName());

        llChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgChange.setImageTintMode(PorterDuff.Mode.SRC);
                imgChange.setColorFilter(R.color.blue);
                ctvChange.setTextColor(getResources().getColor(R.color.blue));
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void beforeContentView() {

    }
}
