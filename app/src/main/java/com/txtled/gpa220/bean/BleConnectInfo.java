package com.txtled.gpa220.bean;

import java.io.Serializable;

public class BleConnectInfo implements Serializable {
    private String bleName;
    private String address;
    private boolean isConn;

    public BleConnectInfo() {
    }

    public BleConnectInfo(String bleName, String address, boolean isConn) {
        this.bleName = bleName;
        this.address = address;
        this.isConn = isConn;
    }

    public String getBleName() {
        return bleName;
    }

    public void setBleName(String bleName) {
        this.bleName = bleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isConn() {
        return isConn;
    }

    public void setConn(boolean conn) {
        isConn = conn;
    }
}
