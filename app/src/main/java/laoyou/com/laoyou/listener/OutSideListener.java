package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.bean.GamesAdvBean;

/**
 * Created by lian on 2017/11/1.
 */
public interface OutSideListener {

    void onCancleProgress();

    void onShowProgress(int pro);

    void onCommentSucced();

    void onFailedMsg(String gets);

    void onLikeStatus(boolean b);

    void onShowAdv(GamesAdvBean adv);

    void onCommentData(List<CafCommentBean> data);

    void GoHomePage(String id);
}
