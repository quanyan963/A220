package com.txtled.gpa220.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.txtled.gpa220.R;
import com.txtled.gpa220.widget.CustomButton;

import java.lang.reflect.Field;


public class AlertUtils {
    //private static OnCreateThingListener thingListener;
    private static boolean canClose = false;
    public static OnConfirmClickListener clickListener;
    public static OnBottomSheetClickListener bottomSeetListener;

    public static void showErrorMessage(Context context, int titleRes,
                                        String errorCode, DialogInterface.OnClickListener listener) {
//        if (!((Activity) context).isFinishing()) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                    .setMessage(context.getResources().getIdentifier("ERROR_CODE_" + errorCode,
//                            "string", context.getPackageName()));
//            if (titleRes != 0) {
//                builder.setTitle(titleRes);
//            }
//            if (listener == null) {
//                builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//            } else {
//                builder.setNegativeButton(R.string.ok, listener);
//            }
//            Dialog dialog = builder.create();
//            dialog.setCancelable(true);
//            dialog.show();
//        }
    }

    public static void showErrorMessage(Context context, String errorCode) {
        showErrorMessage(context, 0, errorCode, null);
    }

    public static void showErrorMessage(Context context,
                                        String errorCode, DialogInterface.OnClickListener listener) {
        showErrorMessage(context, 0, errorCode, listener);
    }

    public static void showAlertDialog(Context context, String message,
                                       DialogInterface.OnClickListener listener0,
                                       DialogInterface.OnClickListener listener1) {
//        if (!((Activity) context).isFinishing()) {
//            AlertDialog dialog = new AlertDialog.Builder(context)
//                    .setMessage(message)
//                    .setNegativeButton(R.string.cancel, listener0)
//                    .setPositiveButton(R.string.confirm, listener1)
//                    .create();
//            dialog.setCancelable(true);
//            dialog.show();
//        }
    }

    public interface OnConfirmClickListener{
        void onOk();
        void onCancel();
    }

    public static void setListener(OnConfirmClickListener listener){
        clickListener = listener;
    }

//    public static OnCreateThingListener getThingListener(){
//        return thingListener;
//    }

    public static int width;

    public static void showHintDialog(Context context, int viewId){
        if (!((Activity) context).isFinishing()){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(viewId,null);
            CustomButton abtOk = view.findViewById(R.id.abt_ble_ok);
            CustomButton abtCanael = view.findViewById(R.id.abt_ble_cancel);
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .create();
            dialog.setCancelable(false                    );
            dialog.show();
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.dialogWindowAnimInToOut);
            window.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.background_gray));
            abtOk.setOnClickListener(v -> {
                dialog.dismiss();
                clickListener.onOk();
            });
            abtCanael.setOnClickListener(v -> {
                dialog.dismiss();
                clickListener.onCancel();
            });
        }
    }

//    public static AlertDialog showProgressDialog(Context context, String wifiName, String pass){
//        if (!((Activity) context).isFinishing()){
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View config = inflater.inflate(R.layout.alert_configure,null);
//            ArialRoundTextView atvWifi = config.findViewById(R.id.atv_wifi);
//            ArialRoundTextView atvPass = config.findViewById(R.id.atv_pass);
//            atvWifi.setText(context.getString(R.string.config_wifi,wifiName));
//            atvPass.setText(pass);
//            AlertDialog configDialog = new AlertDialog.Builder(context,R.style.TransparentDialog)
//                    .setView(config)
//                    .create();
//            configDialog.setCancelable(false);
//            configDialog.show();
//            Window cWindow = configDialog.getWindow();
//
//            ViewTreeObserver vto = atvWifi.getViewTreeObserver();
//            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    atvWifi.getViewTreeObserver().removeOnPreDrawListener(this);
//                    width = atvWifi.getMeasuredWidth();
//                    return true;
//                }
//            });
//
//            ViewTreeObserver passVto = atvPass.getViewTreeObserver();
//            passVto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    atvPass.getViewTreeObserver().removeOnPreDrawListener(this);
//                    width = atvPass.getMeasuredWidth() > width ?
//                            atvPass.getMeasuredWidth() : width;
//                    cWindow.setLayout(width+60, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    return true;
//                }
//            });
//            cWindow.setWindowAnimations(R.style.dialogWindowAnimInToOut);
//            cWindow.setBackgroundDrawable(context.getResources()
//                    .getDrawable(R.drawable.background_gray));
//            return configDialog;
//        }else {
//            return null;
//        }
//    }

//    public static AlertDialog showProgressDialog(Context context){
//        if (!((Activity) context).isFinishing()){
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View config = inflater.inflate(R.layout.alert_configure,null);
//            ArialRoundTextView atvWifi = config.findViewById(R.id.atv_wifi);
//            ArialRoundTextView atvPass = config.findViewById(R.id.atv_pass);
//            atvWifi.setText(R.string.uploading);
//            atvPass.setVisibility(View.INVISIBLE);
//            AlertDialog configDialog = new AlertDialog.Builder(context,R.style.TransparentDialog)
//                    .setView(config)
//                    .create();
//            configDialog.setCancelable(false);
//            configDialog.show();
//            Window cWindow = configDialog.getWindow();
//
//            ViewTreeObserver vto = atvWifi.getViewTreeObserver();
//            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    atvWifi.getViewTreeObserver().removeOnPreDrawListener(this);
//                    width = atvWifi.getMeasuredWidth();
//                    cWindow.setLayout(width+60, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    return true;
//                }
//            });
//            cWindow.setWindowAnimations(R.style.dialogWindowAnimInToOut);
//            cWindow.setBackgroundDrawable(context.getResources()
//                    .getDrawable(R.drawable.background_yellow));
//            return configDialog;
//        }else {
//            return null;
//        }
//    }

    private static void setAlphaAnimation(View view){
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(500);
        view.setAnimation(animation);
        animation.start();
    }

    public static AlertDialog showLoadingDialog(Context context){
        if (!((Activity) context).isFinishing()) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.alert_progress, null);
            AlertDialog dialog = new AlertDialog.Builder(context, R.style.TransparentDialog)
                    .setView(view)
                    .create();
            dialog.setCancelable(false);
            return dialog;
        }else {
            return null;
        }
    }

    public static void showAlertDialog(Context context, int title, int message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public static void showAlertDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, (dialog1, which) -> dialog1.dismiss())
                    .create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public static void showAlertDialog(Context context, int messageRes) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(messageRes)
                    .setPositiveButton(R.string.ok, (dialog1, which) -> dialog1.dismiss())
                    .create();
            dialog.setCancelable(true);
            if (!dialog.isShowing()) {
                dialog.show();
                Window window = dialog.getWindow();
                window.setWindowAnimations(R.style.dialogWindowAnimInToOut);
                window.setBackgroundDrawable(context.getResources()
                        .getDrawable(R.drawable.background_gray));
            }

            try {
                //获取mAlert对象
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);

                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                mMessage.setAccessible(true);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                mMessageView.setTextColor(context.getResources().getColor(R.color.white));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showAlertDialog(Context context, int messageRes,
                                       DialogInterface.OnClickListener listener) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(messageRes)
                    .setPositiveButton(R.string.conform, listener)
                    .create();
            dialog.setCancelable(false);
            dialog.show();
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.dialogWindowAnimInToOut);
            window.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.background_gray));

            try {
                //获取mAlert对象
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);

                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                mMessage.setAccessible(true);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                mMessageView.setTextColor(context.getResources().getColor(R.color.white));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
    }

    public static void showAlertDialog(Context context, int messageRes, int positive, int negative,
                                       DialogInterface.OnClickListener listener1) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(messageRes)
                    .setPositiveButton(positive, listener1)
                    .setNegativeButton(negative, (dialog1, which) -> dialog1.dismiss())
                    .create();
            dialog.setCancelable(true);
            dialog.show();
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.dialogWindowAnimInToOut);
            window.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.background_gray));

            try {
                //获取mAlert对象
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);

                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                mMessage.setAccessible(true);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                mMessageView.setTextColor(context.getResources().getColor(R.color.white));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
    }

    public interface OnBottomSheetClickListener{
        void onOkClick(String date);
    }

    public static void showSheetDialog(Context context,OnBottomSheetClickListener listener){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = View.inflate(context,R.layout.bottom_dialog,null);
        DatePicker datePicker = view.findViewById(R.id.dp_date);
        CustomButton cbtOk = view.findViewById(R.id.cbt_dialog_ok);
        CustomButton cbtcancel = view.findViewById(R.id.cbt_dialog_cancel);
        cbtOk.setOnClickListener(v -> {
            listener.onOkClick(datePicker.getYear() + "/" +
                    String.format("%02d",datePicker.getMonth()+1) + "/" +
                    String.format("%02d",datePicker.getDayOfMonth()));
            dialog.dismiss();
        });
        cbtcancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    public static void showProgressDialog(Context context, int id) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(id));
        progressDialog.show();
    }
}
