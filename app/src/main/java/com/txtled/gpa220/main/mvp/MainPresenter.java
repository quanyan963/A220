package com.txtled.gpa220.main.mvp;

import android.view.View;

import com.txtled.gpa220.R;
import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2019/12/9.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManagerModel mDataManagerModel;

    @Inject
    public MainPresenter(DataManagerModel mDataManagerModel) {
        this.mDataManagerModel = mDataManagerModel;
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_right:

                break;
        }
    }
}
