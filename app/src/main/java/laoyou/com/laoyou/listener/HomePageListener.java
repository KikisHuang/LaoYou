package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/12/7.
 */
public interface HomePageListener {
    void onFailedMSg(String msg);
    void onSucceed();

    void onShowUserInfo(UserInfoBean ub);

    void onCertificaTion(int status);
}
