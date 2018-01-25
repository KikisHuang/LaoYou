package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.TopicTypeBean;

/**
 * Created by lian on 2018/1/20.
 */
public interface ThumbnailListener {
    void onThumbnailResult(List<TopicTypeBean> list);
}
