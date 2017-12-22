package laoyou.com.laoyou.listener;

import android.support.design.widget.AppBarLayout;

import java.util.List;

import laoyou.com.laoyou.bean.ActiveUserBean;
import laoyou.com.laoyou.bean.TopicTypeBean;

/**
 * Created by lian on 2017/12/19.
 */
public interface TopicTypeDetailsListener {
    void onFailedsMsg(String msg);
    void onSucceed();

    void onShowDetailsInfo(List<TopicTypeBean> list);

    void onStateChange(AppBarLayout appBarLayout, AppBarStateChangeListener.State state, int i);

    void onRefresh();

    void IsFollowChat(boolean b);

    void GetActiveUserData(List<ActiveUserBean> aub);
}
