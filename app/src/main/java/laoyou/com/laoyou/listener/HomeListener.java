package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/10/25.
 */
public interface HomeListener {
    void onFailed(String msg);

    void onError(String msg);

    void onOneImg(PageTopBean ptb);

    void onDetails(UserInfoBean ub);

    void onCertificaTion();

    void IsLogin(boolean b);

    void unCheck();

    void CheckOK(String passwd);

    void Refuse(String msg);

    void onTime(int time);

    void onCheckStatus(int status);

    void onLogout();

    void onOutSideGo(String  url);

    void onNormalPassSize(String passwd);

    void onMinPassSize(String passwd);

//    void onStatus(CheckStatusBean csb);
}
