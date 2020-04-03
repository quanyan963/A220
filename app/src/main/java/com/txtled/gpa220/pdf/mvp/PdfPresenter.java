package com.txtled.gpa220.pdf.mvp;

import com.txtled.gpa220.base.RxPresenter;
import com.txtled.gpa220.model.DataManagerModel;

import javax.inject.Inject;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class PdfPresenter extends RxPresenter<PdfContract.View> implements PdfContract.Presenter {
    private DataManagerModel dataManagerModel;

    @Inject
    public PdfPresenter(DataManagerModel dataManagerModel) {
        this.dataManagerModel = dataManagerModel;
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
