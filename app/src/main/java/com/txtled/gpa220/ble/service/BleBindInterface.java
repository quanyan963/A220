package com.txtled.gpa220.ble.service;

import com.inuker.bluetooth.library.search.SearchResult;

import java.util.List;

/**
 * Created by Mr.Quan on 2020/4/1.
 */
public interface BleBindInterface {
    void searchResult(List<SearchResult> data);

    void connBle(SearchResult bleData);

    void doRefresh();

    void doReConn();
}
