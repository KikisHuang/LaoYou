package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.AddTopicTypeBean;
import laoyou.com.laoyou.bean.TopicBean;

/**
 * Created by lian on 2017/12/28.
 */
public interface AddTopicTypeListener {
    void onFailedsMsg(String msg);

    void onSucceed(AddTopicTypeBean attb);

    void onTopicTypeList(List<TopicBean> list);
}
