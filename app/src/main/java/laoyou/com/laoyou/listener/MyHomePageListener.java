package laoyou.com.laoyou.listener;

import java.io.File;
import java.util.List;

import laoyou.com.laoyou.bean.ProvinceBean;
import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/12/6.
 */
public interface MyHomePageListener {
    void onSucceed();

    void onFailedMsg(String msg);

    void onShowUserInfo(UserInfoBean ub);

    void onCertificaTion(int state);

    void onProvinceInfo(List<ProvinceBean> list);

    void onCityInfo(List<ProvinceBean> list);

    void onComPressSucceed(File f);

}
