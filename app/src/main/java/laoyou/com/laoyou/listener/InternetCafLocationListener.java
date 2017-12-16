package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.MarkerBean;

/**
 * Created by lian on 2017/12/14.
 */
public interface InternetCafLocationListener {

    void onSucceed();

    void onFailesMsg(String msg);

    void onInternetCafDataList(List<MarkerBean> list);
}
