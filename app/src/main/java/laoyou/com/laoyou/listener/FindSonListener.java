package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.bean.TopicTypeBean;

/**
 * Created by lian on 2017/12/11.
 */
public interface FindSonListener {
    void onSucceed();

    void onFailedMsg(String str);

    void RefreshNearby(List<NearbyBean> nblist);

    void RefreshNewWonders(List<TopicTypeBean> toppic);

    void onRefresh();

    void onNotLatiLongTude();
}
