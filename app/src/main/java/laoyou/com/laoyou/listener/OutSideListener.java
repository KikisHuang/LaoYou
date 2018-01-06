package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/11/1.
 */
public interface OutSideListener {

    void onCancleProgress();

    void onShowProgress(int pro);

    void onCommentSucced();

    void onFailedMsg(String gets);

    void onLikeStatus(boolean b);
}
