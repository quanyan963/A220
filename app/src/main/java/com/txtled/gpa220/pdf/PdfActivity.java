package com.txtled.gpa220.pdf;

import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;
import com.txtled.gpa220.R;
import com.txtled.gpa220.base.MvpBaseActivity;
import com.txtled.gpa220.bean.BleControlEvent;
import com.txtled.gpa220.pdf.mvp.PdfContract;
import com.txtled.gpa220.pdf.mvp.PdfPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.txtled.gpa220.utils.Constants.CONN;
import static com.txtled.gpa220.utils.Constants.DISCONN;
import static com.txtled.gpa220.utils.Constants.PDF_NAME;
import static com.txtled.gpa220.utils.Constants.RECONN;
import static com.txtled.gpa220.utils.Constants.SINGLE_DATA;

/**
 * Created by Mr.Quan on 2020/4/3.
 */
public class PdfActivity extends MvpBaseActivity<PdfPresenter> implements PdfContract.View {
    @BindView(R.id.v_pdf)
    PDFView vPdf;

    @Override
    public void setInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void init() {
        initToolbar();
        setNavigationIcon(true);
        tvTitle.setText(R.string.instructions);
        setSecondImage(presenter.isClosed());
        vPdf.fromAsset(PDF_NAME)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    public void onEventServiceThread(BleControlEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getBleConnType() == RECONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }else if (event.getBleConnType() == CONN){
                    setSecondImage(true);
                    presenter.setClosed(true);
                }else if (event.getBleConnType() == DISCONN){
                    setSecondImage(false);
                    presenter.setClosed(false);
                }
            }
        });
    }
}
