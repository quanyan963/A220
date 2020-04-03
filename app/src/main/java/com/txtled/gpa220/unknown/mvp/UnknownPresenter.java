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
    private List<UserData> data;

    @Inject
    public UnknownPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
        data = dataManagerModel.getUserData();
    }

    @Override
    public List<Float> getUnknownData() {
        return data.get(0).getData();
    }

    @Override
    public List<UserData> getUserData() {
        return data;
    }

    @Override
    public void setData(int position, List<Float> checked) {
        UserData witch = data.get(position);
        witch.getData().addAll(checked);
        dataManagerModel.updateUserData(witch);
        UserData unknown = data.get(0);
        List<Float> unknownData = unknown.getData();
        for (int i = 0; i < checked.size(); i++) {
            for (int j = 0; j < unknownData.size(); j++) {
                if (checked.get(i) == unknownData.get(j)){
                    unknownData.remove(j);
                    continue;
                }
            }
        }
        unknown.setData(unknownData);
        dataManagerModel.updateUserData(unknown);
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> view.showFinish())
                .subscribe(aLong -> view.hidSnack()));
    }

    @Override
    public boolean isClosed() {
        return dataManagerModel.isClosed();
    }

    @Override
    public void setClosed(boolean b) {
        dataManagerModel.setClosed(b);
    }
}
