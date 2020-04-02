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
     * 3：重连
     * 4：单个数据
     * 5：全部数据
     * 6：错误
     */
    private int bleConnType;
    private List<SearchResult> data;
    private float temp;
    private List<Float> allTemp;

    public BleControlEvent(int bleConnType) {
        this.bleConnType = bleConnType;
    }

    public BleControlEvent(int bleConnType, float temp) {
        this.bleConnType = bleConnType;
        this.temp = temp;
    }

    public BleControlEvent(int bleConnType, List<SearchResult> data) {
        this.bleConnType = bleConnType;
        this.data = data;
    }

    public BleControlEvent(int bleConnType, float temp, List<Float> allTemp) {
        this.bleConnType = bleConnType;
        this.temp = temp;
        this.allTemp = allTemp;
    }

    public List<SearchResult> getData() {
        return data;
    }

    public void setData(List<SearchResult> data) {
        this.data = data;
    }

    public int getBleConnType() {
        return bleConnType;
    }

    public void setBleConnType(int bleConnType) {
        this.bleConnType = bleConnType;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public List<Float> getAllTemp() {
        return allTemp;
    }

    public void setAllTemp(List<Float> allTemp) {
        this.allTemp = allTemp;
    }
}
