package com.txtled.gpa220.main.mvp;

import android.app.Activity;
import android.os.Environment;
import android.view.View;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.CommonSubscriber;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.model.operate.OperateHelper;
import com.txtled.gpa220.utils.AlertUtils;
import com.txtled.gpa220.utils.Constants;
import com.txtled.gpa220.utils.ExcelUtils;
import com.txtled.gpa220.utils.RxUtil;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.txtled.gpa220.utils.BleUtils.ALL;
import static com.txtled.gpa220.utils.BleUtils.ALL_RESPONSE;
import static com.txtled.gpa220.utils.BleUtils.END;
import static com.txtled.gpa220.utils.BleUtils.SINGLE;
import static com.txtled.gpa220.utils.BleUtils.SINGLE_RESPONSE;
import static com.txtled.gpa220.utils.BleUtils.SYNC;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManagerModel mDataManagerModel;
    private List<UserData> mData;
    private int mPosition;
    private Activity activity;

    @Inject
    public MainPresenter(DataManagerModel mDataManagerModel) {
        this.mDataManagerModel = mDataManagerModel;
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                view.showLeftView();
                break;
            case R.id.img_menu_close:
                view.closeLeftView();
                break;
            case R.id.ll_ble_conn:
                view.toBleView();
                break;
            case R.id.ll_data_sync:
                if (mDataManagerModel.isClosed()){
                    view.showLoadingView();
                    mDataManagerModel.writeCommand(SYNC);
                }else {
                    view.showUnConnView();
                }
                break;
            case R.id.ll_inst:
                view.toPdfView();
                break;
            case R.id.ll_setting:
                view.toSettingView();
                break;
            case R.id.ll_logout:
                view.toLoginView();
                break;
            case R.id.ll_data_export:
                String[] permissions = {Constants.permissions[2], Constants.permissions[3]};
                mDataManagerModel.requestPermissions(activity, permissions,
                        new OperateHelper.OnPermissionsListener() {
                            @Override
                            public void onSuccess(String name) {
                                if (name.equals(Constants.permissions[3])) {
                                    view.showLoadingView();
                                    String filePath = Environment.getExternalStorageDirectory()  + "/GP-A220";
                                    File file = new File(filePath);
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }

                                    String excelFileName = "/体温数据.xls";
                                    ExcelUtils.initExcel(filePath + excelFileName,
                                            new String[]{"姓名", "出生年月日", "身份证号"});
                                    ExcelUtils.writeObjListToExcel(getUserData(), filePath + excelFileName, activity,
                                            new AlertUtils.OnConfirmClickListener() {
                                                @Override
                                                public void onOk() {
                                                    view.hidLoading();
                                                }

                                                @Override
                                                public void onCancel() {
                                                    view.hidLoading();
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onFailure() {
                                view.showPermissionHint();
                            }

                            @Override
                            public void onAskAgain() {

                            }
                        });

                break;
        }
    }

    @Override
    public List<UserData> getUserData() {
        mData = mDataManagerModel.getUserData();
        return mData;
    }

    @Override
    public void setUserPosition(int position) {
        this.mPosition = position;
    }

    @Override
    public void insertData(UserData str) {
        mData.add(str);
    }

    @Override
    public void update(int position, UserData str) {
        mData.set(position, str);
    }

    @Override
    public void setTempData(int mPosition, float temp) {
        List<Float> tempData = mData.get(mPosition).getData();
        if (tempData != null) {
            tempData.add(temp);
        } else {
            tempData = new ArrayList<>();
            tempData.add(temp);
        }
        mData.get(mPosition).setData(tempData);
        mDataManagerModel.updateUserData(mData.get(mPosition));
        mDataManagerModel.writeCommand(SINGLE);
        view.refreshView();
    }

    @Override
    public void setAllTempData(int mPosition, List<Float> allTemp) {
        List<Float> tempData = mData.get(mPosition).getData();
        if (tempData != null) {
            tempData.addAll(allTemp);
        } else {
            tempData = new ArrayList<>();
            tempData.addAll(allTemp);
        }
        mData.get(mPosition).setData(tempData);
        mDataManagerModel.updateUserData(mData.get(mPosition));
        mDataManagerModel.writeCommand(ALL);
        view.refreshView();
    }

    @Override
    public void setClosed(boolean closed) {
        mDataManagerModel.setClosed(closed);
    }

    @Override
    public boolean isClosed() {
        return mDataManagerModel.isClosed();
    }

    @Override
    public void showSyncSuccess() {
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> view.showSyncSuccess())
                .subscribe(aLong -> view.hidSnack()));
    }

    @Override
    public void init(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void hidDelay() {
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<Long>(view) {
                    @Override
                    public void onNext(Long aLong) {
                        view.hidSnack();
                    }
                }));
    }

    @Override
    public int getDataSize() {
        return mData.size();
    }
}
