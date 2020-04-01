package com.txtled.gpa220.bean;

import com.inuker.bluetooth.library.search.SearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Quan on 2020/4/1.
 */
public class BleControlEvent implements Serializable {
    /**
     * 0：断开
     * 1：蓝牙搜索完毕
     * 2：已连接
     * 3：错误
     */
    private int bleConnType;
    private List<SearchResult> data;

    public BleControlEvent(int bleConnType) {
        this.bleConnType = bleConnType;
    }

    public List<SearchResult> getData() {
        return data;
    }

    public void setData(List<SearchResult> data) {
        this.data = data;
    }

    public BleControlEvent(int bleConnType, List<SearchResult> data) {
        this.bleConnType = bleConnType;
        this.data = data;
    }

    public int getBleConnType() {
        return bleConnType;
    }

    public void setBleConnType(int bleConnType) {
        this.bleConnType = bleConnType;
    }
}
