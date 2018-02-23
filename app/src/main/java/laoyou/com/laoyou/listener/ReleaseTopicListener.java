package laoyou.com.laoyou.listener;

import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/12/27.
 */
public interface ReleaseTopicListener {
    void onFaileds(String msg);

    void onSucceed();

    void onRemove(int pos);

    void AddPhoto();

    void ShowUserInfo(UserInfoBean ub);
}
