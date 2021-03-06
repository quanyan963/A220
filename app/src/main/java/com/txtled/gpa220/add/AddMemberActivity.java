package com.txtled.gpa220.add;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.txtled.gpa220.R;
import com.txtled.gpa220.add.mvp.AddContract;
import com.txtled.gpa220.add.mvp.AddPresenter;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.widget.CustomButton;
import com.txtled.gpa220.widget.CustomEditText;
import com.txtled.gpa220.widget.CustomTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.OK;
import static com.txtled.gpa220.utils.Constants.POSITION;
import static com.txtled.gpa220.utils.Constants.RECONN;

public class AddMemberActivity extends MvpBaseActivity<AddPresenter> implements AddContract.View {
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
    CustomTextView cetAddBirth;
    @BindView(R.id.cbt_add_commit)
    CustomButton cbtAddCommit;

    private boolean woman;
    private int position;
    private UserData data;

    /**
     * 注解activity
     */
    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    /**
     * 做初始化操作，在getLayout()方法执行完后执行
     * 主要做控件初始化，设置监听
     */
    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        Intent intent = getIntent();
        setSecondImage(presenter.isClosed());
        AlphaAnimation hid = new AlphaAnimation(1f, 0.3f);
        hid.setDuration(200);
        hid.setFillAfter(true);
        imgWoman.startAnimation(hid);
        position = intent.getIntExtra(POSITION, -1);
        if (position == -1) {
            tvTitle.setText(R.string.new_manber);
        } else {
            tvTitle.setText(R.string.edit);
            data = presenter.getUserData(position);
            if (data.getSex() == 1) {
                woman = true;
                setAnimation(imgWoman, imgMan);
            } else {
                woman = false;
                setAnimation(imgMan, imgWoman);
            }
            cetAddName.setText(data.getUserName());
            cetAddPost.setText(data.getPost());
            cetAddCode.setText(data.getPostCode());
            cetAddBirth.setText(data.getBirth());
        }
        if (cetAddName.getText().toString().trim().isEmpty()) {
            cbtAddCommit.setEnabled(false);
        } else {
            cbtAddCommit.setEnabled(true);
        }

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

        //弹出选择日期控件
        cetAddBirth.setOnClickListener(v ->
                AlertUtils.showSheetDialog(AddMemberActivity.this,
                date -> cetAddBirth.setText(date)));

        //监听输入名称控件
        cetAddName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    cbtAddCommit.setEnabled(false);
                } else {
                    cbtAddCommit.setEnabled(true);
                }
            }
        });

        cbtAddCommit.setOnClickListener(v -> {
            if (this.data != null) {
                this.data.setSex(woman ? 1 : 0);
                this.data.setUserName(cetAddName.getText().toString());
                this.data.setPost(cetAddPost.getText().toString());
                this.data.setPostCode(cetAddCode.getText().toString());
                this.data.setBirth(cetAddBirth.getText().toString());
                presenter.update(this.data);
            } else {
                UserData data = new UserData(cetAddName.getText().toString(), cetAddPost.getText().toString(),
                        cetAddCode.getText().toString(), cetAddBirth.getText().toString(), woman ? 1 : 0);
                presenter.insertData(data);
                EventBus.getDefault().post(data);
            }
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

    @Override
    public void onEventMainThread(BleControlEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getBleConnType() == CONN){
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
