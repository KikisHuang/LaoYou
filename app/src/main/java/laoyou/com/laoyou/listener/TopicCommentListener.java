package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.ChatMessages;
import laoyou.com.laoyou.bean.LikeListBean;
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

    void LikeListData(List<LikeListBean> li);

    void onLikeNum(String string);

    void onDeleteSucceed();

    void onCommentInfo(List<ChatMessages> cm);

    void onNoMore(String gets);

    void onPhotoCancle();

    void onPhotoImgClick();

    void GoPhotoPage(String url);
}
