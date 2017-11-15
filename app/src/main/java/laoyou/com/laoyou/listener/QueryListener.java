package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/10/25.
 */
public interface QueryListener {
    void onFailed(String msg);

    void onError(String msg);

    void onDetails(UserInfoBean ub);

    void onCertificaTion();

    void IsLogin(boolean b);

    void unCheck();

    void CheckOK(String passwd);

    void Refuse(String msg);

    void onTime(int time);


    void onNormalPassSize(String passwd);

    void onMinPassSize(String passwd);

}
