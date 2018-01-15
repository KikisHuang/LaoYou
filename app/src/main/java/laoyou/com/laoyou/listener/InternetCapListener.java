package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.CafBean;
import laoyou.com.laoyou.bean.CafCommentBean;

/**
 * Created by lian on 2017/12/12.
 */
public interface InternetCapListener {
    void onSucceed();

    void onFailedMsg(String msg);

    void onInternetCafDetails(CafBean cb);

    void onInternetCafComment(List<CafCommentBean> list);

    void onEndBottom();
}
