package com.txtled.gpa220.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.txtled.gpa220.R;
import com.txtled.gpa220.add.mvp.AddConteact;
import com.txtled.gpa220.add.mvp.AddPresenter;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.utils.Utils;
import com.txtled.gpa220.widget.CustomButton;
import com.txtled.gpa220.widget.CustomEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.OK;
import static com.txtled.gpa220.utils.Constants.TYPE;

public class AddMenberActivity extends MvpBaseActivity<AddPresenter> implements AddConteact.View {
    @BindView(R.id.img_man)
    ImageView imgMan;
    @BindView(R.id.img_woman)
    ImageView imgWoman;
    @BindView(R.id.cet_add_name)
    CustomEditText cetAddName;
    @BindView(R.id.cet_add_post)
    CustomEditText cetAddPost;
    @BindView(R.id.cet_add_code)
    CustomEditText cetAddCode;
    @BindView(R.id.cet_add_birth)
    CustomEditText cetAddBirth;
    @BindView(R.id.cbt_add_commit)
    CustomButton cbtAddCommit;

    private boolean woman;
    private int type;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE,0);
        if (type == 0){
            tvTitle.setText(R.string.new_manber);
        }else {
            tvTitle.setText(R.string.edit);
        }
        cbtAddCommit.setEnabled(false);
        AlphaAnimation hid = new AlphaAnimation(1f, 0.3f);
        hid.setDuration(200);
        hid.setFillAfter(true);
        imgWoman.startAnimation(hid);
        imgMan.setOnClickListener(v -> {
            if (woman) {
                setAnimation(v, imgWoman);
                woman = false;
            }
        });
        imgWoman.setOnClickListener(v -> {
            if (!woman) {
                setAnimation(v, imgMan);
                woman = true;
            }
        });

        cetAddName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    cbtAddCommit.setEnabled(false);
                }else {
                    cbtAddCommit.setEnabled(true);
                }
            }
        });

        cbtAddCommit.setOnClickListener(v -> {
            UserData data = new UserData(cetAddName.getText().toString(),cetAddPost.getText().toString(),
                    cetAddCode.getText().toString(),cetAddBirth.getText().toString(),woman ? 1 : 0);
            presenter.insertData(data);
            EventBus.getDefault().post(data);
            this.setResult(OK);
            this.finish();
        });
    }

    public void setAnimation(View s, View h) {
        AlphaAnimation show = new AlphaAnimation(0.3f, 1f);
        show.setDuration(200);
        show.setFillAfter(true);
        s.startAnimation(show);
        AlphaAnimation hid = new AlphaAnimation(1f, 0.3f);
        hid.setDuration(200);
        hid.setFillAfter(true);
        h.startAnimation(hid);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected void beforeContentView() {

    }
}
