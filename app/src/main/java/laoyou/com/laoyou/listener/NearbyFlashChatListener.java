package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.GroupBean;

/**
 * Created by lian on 2017/12/5.
 */
public interface NearbyFlashChatListener {
    void onSucceed(List<GroupBean> list);

    void onFailedMsg(String msg);
}
