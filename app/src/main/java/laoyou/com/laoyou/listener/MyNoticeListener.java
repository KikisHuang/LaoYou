package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.TopicBean;

/**
 * Created by lian on 2017/12/23.
 */
public interface MyNoticeListener {
    void onFailedsMsg(String msg);
    void onSucceed();

    void onTopicTypeList(List<TopicBean> list);

}
