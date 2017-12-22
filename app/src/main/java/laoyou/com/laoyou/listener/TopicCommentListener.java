package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.TopicCommentBean;

/**
 * Created by lian on 2017/12/22.
 */
public interface TopicCommentListener {

    void onFailedMsg(String msg);

    void onSucceed();

    void onLikeStatus(boolean b);

    void onThemeDetails(TopicCommentBean tcb);

    void GoHomePage(String id);
}
