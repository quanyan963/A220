package com.txtled.gpa220.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.login.mvp.LoginContract;
import com.txtled.gpa220.login.mvp.LoginPresenter;
import com.txtled.gpa220.main.MainActivity;
import com.txtled.gpa220.register.RegisterActivity;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.Utils;
import com.txtled.gpa220.widget.CustomButton;
import com.txtled.gpa220.widget.CustomEditText;
import com.txtled.gpa220.widget.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Quan on 2020/4/7.
 */
public class LoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginContract.View,
        View.OnClickListener {
    @BindView(R.id.cet_login_pass)
    CustomEditText cetLoginPass;
    @BindView(R.id.cet_login_number)
    CustomEditText cetLoginNumber;
    @BindView(R.id.cbt_login)
    CustomButton cbtLogin;
    @BindView(R.id.cbt_sign)
    CustomButton cbtSign;
    @BindView(R.id.ctv_forget)
    CustomTextView ctvForget;
    @BindView(R.id.ctv_change_pass)
    CustomTextView ctvChangePass;

    private boolean numberIsNull = true, passIsNull = true;
    private AlertDialog dialog;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        presenter.init();
        tvTitle.setText(R.string.login);
        cbtLogin.setOnClickListener(this);
        cbtSign.setOnClickListener(this);
        ctvForget.setOnClickListener(this);
        ctvChangePass.setOnClickListener(this);
        cbtLogin.setEnabled(false);
        dialog = AlertUtils.showLoadingDialog(this);
        cetLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    numberIsNull = true;
                } else if (s.length() == 11) {
                    if (Utils.isMobileNO(s.toString().trim())) {
                        numberIsNull = false;
                        hidSnack();
                    } else {
                        numberIsNull = true;
                        showPhoneError();
                    }
                } else {
                    numberIsNull = true;
                }
                setLoginBtnColor();
            }
        });
        cetLoginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.toString().length() < 6) {
                    passIsNull = true;
                } else {
                    passIsNull = false;
                }
                setLoginBtnColor();
            }
        });
    }

    private void setLoginBtnColor() {
        if (passIsNull == false && numberIsNull == false) {
            cbtLogin.setEnabled(true);
            cbtLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            cbtLogin.setTextColor(getResources().getColor(R.color.white));
        } else {
            cbtLogin.setEnabled(false);
            cbtLogin.setBackgroundColor(getResources().getColor(R.color.line_bg));
            cbtLogin.setTextColor(getResources().getColor(R.color.gray));
        }


    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return onExitActivity(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbt_login:
                dialog.show();
                presenter.checkLogin(cetLoginNumber.getText().toString(),
                        cetLoginPass.getText().toString());
                break;
            case R.id.cbt_sign:
                startActivity(new Intent(this,RegisterActivity.class));
//                if (!Utils.isMobileNO(cetLoginNumber.getText().toString().trim())) {
//                    showPhoneError();
//                    presenter.hidDelay();
//                    break;
//                }
//                if (cetLoginPass.getText().toString().isEmpty() ||
//                        cetLoginPass.getText().toString().length() < 6) {
//                    showPhoneLength();
//                    presenter.hidDelay();
//                    break;
//                }
//                dialog.show();
//                presenter.signIn(cetLoginNumber.getText().toString().trim(), cetLoginPass.getText().toString());
                break;
            case R.id.ctv_forget:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.ctv_change_pass:
                dialog.show();
                presenter.checkLogin(cetLoginNumber.getText().toString(),
                        cetLoginPass.getText().toString());
                break;
        }

    }

    private void showPhoneLength() {
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.pass_length);
    }

    @Override
    public void toMain() {
        hidLoading();
        dialog = null;
        hideSnackBar();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void Sign() {

    }

    @Override
    public void showPhoneError() {
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.phone_error);
    }

    @Override
    public void hidSnack() {
        hideSnackBar();
    }

    @Override
    public void showPassError() {
        hidLoading();
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.pass_error);
    }

    @Override
    public void showNoUser() {
        hidLoading();
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.no_user);
    }

    @Override
    public void hidLoading() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void showError() {
        hidLoading();
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.net_error);
    }

    @Override
    public void showUserExist() {
        hidLoading();
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.user_exist);
    }

    @Override
    public void showSuccess() {
        hidLoading();
        hideSnackBar();
        showSnackBar(cbtLogin, R.string.sign_success);
    }

    @Override
    public void showMsg(String result) {
        hidLoading();
        hideSnackBar();
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
    }
}
