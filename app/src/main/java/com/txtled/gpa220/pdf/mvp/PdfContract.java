package com.txtled.gpa220.pdf.mvp;

import com.txtled.gpa220.base.BasePresenter;
import com.txtled.gpa220.base.BaseView;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public interface PdfContract {
    interface View extends BaseView{

    }
    interface Presenter extends BasePresenter<View>{

        boolean isClosed();

        void setClosed(boolean b);
    }
}
