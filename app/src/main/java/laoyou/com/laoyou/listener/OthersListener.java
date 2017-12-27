package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/12/26.
 */
public interface OthersListener {
    void onFailedMsg(String msg);

    void onSucceed();

    void onShowUserInfo(UserInfoBean ub);

    void onIsFinds(boolean isfind);
}
