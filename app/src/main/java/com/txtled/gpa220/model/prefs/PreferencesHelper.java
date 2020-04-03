package com.txtled.gpa220.model.prefs;

/**
 * Created by Mr.Quan on 2018/4/17.
 */

public interface PreferencesHelper {
    int getBlePosition();

    void setBlePosition(int position);

    boolean isFirstIn();

    void setFirstIn(boolean first);

    String getBleAddress();

    void setBleAddress(String address);

    boolean isClosed();

    void setClosed(boolean closed);
}
