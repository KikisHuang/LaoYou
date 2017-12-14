package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/10/25.
 */
public interface MyListener {
    void ongetDetails(UserInfoBean ub);

    void onErrorMsg(String msg);
}
