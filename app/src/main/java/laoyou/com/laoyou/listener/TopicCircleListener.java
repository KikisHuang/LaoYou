package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.TopicBean;

/**
 * Created by lian on 2017/12/18.
 */
public interface TopicCircleListener {
    void onFailedMsg(String msg);

    void onSucceed();

    void onTopicTypeList(List<TopicBean> list);

    void onHeadViewShwoOfHide(boolean b);
}
