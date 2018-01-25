package laoyou.com.laoyou.utils;

import com.tencent.TIMCallBack;

import java.util.Map;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;

/**
 * Created by lian on 2017/11/29.
 */
public class ImUtils {
    /**
     * 获取IM UserSig;
     *
     * @param id
     */
    public static void getImUserSig(String id, HttpResultListener listener) {
        Map<String, String> map = getParamsMap();
        map.put("identifier", id);
        httpUtils.OkHttpsPost(map, listener, Fields.REQUEST4, Interface.URL + Interface.GETUSERSIG, null, null);
    }

    /**
     * 获取腾讯Im Identifier;
     */
    public static void getImIdentifier(HttpResultListener listener) {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, listener, Fields.REQUEST2, Interface.URL + Interface.MYINFODETAILS, null, null);
    }

    /**
     * 用户没有绑定Im,为用户重新创建;
     */
    public static void CreateUserIm(HttpResultListener listener) {

        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, listener, Fields.REQUEST6, Interface.URL + Interface.CREATECLOUDTENCENTACCOUNT);
    }

    /**
     * 导入账号至腾讯云;
     *
     * @param id
     */
    public static void ImportImInfo(String id, String name, String face, HttpResultListener listener) {
        Map<String, String> map = getParamsMap();
        map.put("account", id);
        map.put("nick", name);
        map.put("faceUrl", face);

        httpUtils.OkHttpsPost(map, listener, Fields.REQUEST5, Interface.URL + Interface.ACCOUNTIMPORT, null, null);
    }

    public static void JoinGroup(String identify, String info, TIMCallBack listener) {
        GroupManagerPresenter.applyJoinGroup(identify, info, listener);
    }
}
