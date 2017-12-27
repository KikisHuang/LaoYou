package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.OthersListener;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/12/26.
 */
public class OthersPresenter implements HttpResultListener {

    private static final String TAG = "OthersPresenter";
    private OthersListener listener;

    public OthersPresenter(OthersListener listener) {
        this.listener = listener;
    }

    /**
     * 获取关注的游戏  userId String 字符串用户id(为空则取key对应的用户的id)
     */
    public void getOthersDataList(String id, boolean isTencent) {
        Map<String, String> map = getKeyMap();
        if (isTencent)
            map.put("cloudTencentAccount", id);
        else
            map.put("userId", id);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETOTHERSDETAILS);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    JSONObject ob = getJsonOb(response);
                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                    listener.onShowUserInfo(ub);
                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    public void FindsWhether(String id) {
        listener.onIsFinds(FriendshipInfo.getInstance().isFriend(id));
    }
}
