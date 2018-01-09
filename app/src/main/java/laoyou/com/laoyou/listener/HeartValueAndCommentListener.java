package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.bean.HeartBean;

/**
 * Created by lian on 2017/12/23.
 */
public interface HeartValueAndCommentListener {

    void onFailedsMsg(String msg);

    void onSucceed();

    void onHeartData(List<HeartBean> hb);

    void onCommentData(List<CommentBean> comments);

    void GoHomePage(String userId);
}
