package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.LikeListBean;

/**
 * Created by lian on 2017/12/23.
 */
public interface ParticipationListener {
    void onFailedsMsg(String msg);

    void onSucceed();

    void LikeListData(List<LikeListBean> li);

    void NoData();
}
