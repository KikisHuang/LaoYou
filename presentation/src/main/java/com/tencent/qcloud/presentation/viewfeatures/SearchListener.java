package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.qcloud.sdk.SearchBean;

import java.util.List;

/**
 * Created by lian on 2017/12/4.
 */
public interface SearchListener {
    void onShowInfo(List<SearchBean> list);
}
