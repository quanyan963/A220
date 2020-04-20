package com.txtled.gpa220.login.mvp;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.lzy.okgo.OkGo;
import com.txtled.gpa220.R;
import com.txtled.gpa220.application.MyApplication;
import com.txtled.gpa220.base.CommonSubscriber;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;
import com.txtled.gpa220.utils.Constants;
import com.txtled.gpa220.utils.RxUtil;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.txtled.gpa220.utils.Constants.DB_NAME;
import static com.txtled.gpa220.utils.Constants.PASSWORD;
import static com.txtled.gpa220.utils.Constants.SUPER_ACCOUNT;
import static com.txtled.gpa220.utils.Constants.USER_ID;

/**
 * Created by Mr.Quan on 2020/4/7.
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private DataManagerModel dataManagerModel;
    private static CognitoCachingCredentialsProvider provider;
    private AmazonDynamoDB client;
    private HashMap<String, AttributeValue> key;

    @Inject
    public LoginPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
    }

    @Override
    public void init() {
        provider = MyApplication.getCredentialsProvider();
        client = new AmazonDynamoDBClient(provider);
        key = new HashMap<>();
    }

    @Override
    public void showErrorPhone() {
        addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        view.showPhoneError();
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        view.hidSnack();
                    }
                }));
    }

    @Override
    public void checkLogin(String number, String pass) {
        //OkGo.<String>get()
        key.put(USER_ID, new AttributeValue().withS(number));
        if (number.equals(SUPER_ACCOUNT)){
            view.toMain();
        }else {
            addSubscribe(Flowable.create((FlowableOnSubscribe<String>) e -> {
                try {
                    //获取数据
                    GetItemResult itemResult = client.getItem(new GetItemRequest()
                            .withTableName(DB_NAME).withKey(key));
                    if (itemResult.getItem() == null) {
                        e.onNext("no_user");
                    } else if (itemResult.getItem().get(PASSWORD).getS().equals(pass)) {
                        e.onNext("success");
                    } else {
                        e.onNext("pass_error");
                    }
                } catch (Exception e1) {
                    e.onNext("error");
                }
            }, BackpressureStrategy.BUFFER)
                    .compose(RxUtil.rxSchedulerHelper())
                    .subscribeWith(new CommonSubscriber<String>(view) {

                        @Override
                        public void onNext(String s) {
                            switch (s) {
                                case "success":
                                    view.toMain();
                                    break;
                                case "pass_error":

                                    addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                            .observeOn(Schedulers.io())
                                            .subscribeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(subscription -> view.showPassError())
                                            .subscribe(aLong -> view.hidSnack()));
                                    break;
                                case "error":
                                    addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                            .observeOn(Schedulers.io())
                                            .subscribeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(subscription -> view.showError())
                                            .subscribe(aLong -> view.hidSnack()));
                                    break;
                                case "no_user":
                                    addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                            .observeOn(Schedulers.io())
                                            .subscribeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(subscription -> view.showNoUser())
                                            .subscribe(aLong -> view.hidSnack()));
                                    break;
                            }
                        }
                    }));
        }
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
    public void signIn(String number, String pass) {
        key.put(USER_ID, new AttributeValue().withS(number));
        addSubscribe(Flowable.create((FlowableOnSubscribe<String>) e -> {
            try {
                //获取数据
                GetItemResult itemResult = client.getItem(new GetItemRequest()
                        .withTableName(DB_NAME).withKey(key));
                if (itemResult.getItem() == null) {
                    PutItemRequest request = new PutItemRequest();
                    request.withTableName(Constants.DB_NAME);
                    request.addItemEntry(USER_ID, new AttributeValue().withS(number));
                    request.addItemEntry(PASSWORD, new AttributeValue().withS(pass));
                    client.putItem(request);
                    e.onNext("success");
                }else {
                    e.onNext("user_exist");
                }
            } catch (Exception e1) {
                e.onNext("error");
            }
        }, BackpressureStrategy.BUFFER)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<String>(view) {

                    @Override
                    public void onNext(String s) {
                        switch (s) {
                            case "success":
                                addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                        .observeOn(Schedulers.io())
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe(subscription -> view.showSuccess())
                                        .subscribe(aLong -> view.toMain()));
                                break;
                            case "user_exist":
                                addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                        .observeOn(Schedulers.io())
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe(subscription -> view.showUserExist())
                                        .subscribe(aLong -> view.hidSnack()));
                                break;
                            case "error":
                                addSubscribe(Flowable.timer(3, TimeUnit.SECONDS)
                                        .observeOn(Schedulers.io())
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe(subscription -> view.showError())
                                        .subscribe(aLong -> view.hidSnack()));
                                break;
                        }
                    }
                }));
    }
}
