package com.txtled.gpa220.main.mvp;

import android.view.View;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;

import org.reactivestreams.Subscription;

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
                view.showLoadingView();
                mDataManagerModel.writeCommand(SYNC);
                break;
            case R.id.ll_inst:
                view.toPdfView();
                break;
            case R.id.ll_setting:
                view.toSettingView();
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
}
