package com.txtled.gpa220.unknown.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.bean.UserData;
import com.txtled.gpa220.model.DataManagerModel;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UnknownPresenter extends RxPresenter<UnknownContract.View> implements UnknownContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public UnknownPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public List<Float> getUnknownData() {
        return dataManagerModel.getUserData().get(0).getData();
    }

    @Override
    public List<UserData> getUserData() {
        return dataManagerModel.getUserData();
    }

    @Override
    public void setData(int position, List<Float> checked) {
        UserData data = dataManagerModel.getUserData().get(position);
        data.getData().addAll(checked);
        dataManagerModel.updateUserData(data);
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> view.showFinish())
                .subscribe(aLong -> view.hidSnack()));
    }
}
