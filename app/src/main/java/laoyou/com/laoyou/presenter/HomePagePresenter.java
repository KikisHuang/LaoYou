package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.AttentionGameBean;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/7.
 */
public class HomePagePresenter implements HttpResultListener {
    private static final String TAG = "HomePagePresenter";
    private HomePageListener listener;

    public HomePagePresenter(HomePageListener listener) {
        this.listener = listener;

    }

    /**
     * 获取他人的信息;
     */
    public void getOthersDetails(String id) {
        getMyHeartNum(id);
        getAttentGames(id);
    }

    /**
     * 获取我的个人信息;
     */
    public void getMyDetails() {
        getUseDetails();
        getMyHeartNum(null);
        getAttentGames(null);
        getMyPhotoList(null);
//        CheckID();
    }

    /**
     * 获取相册  userId String 字符串用户id(为空则取key对应的用户的id)
     *
     * @param str
     */
    private void getMyPhotoList(String str) {

        Map<String, String> map = getKeyMap();
        if (str != null)
            map.put("userId", str);

        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST5, Interface.URL + Interface.GETPHOTOBYPAGE);
    }

    /**
     * 获取关注的游戏  userId String 字符串用户id(为空则取key对应的用户的id)
     *
     * @param str
     */
    private void getAttentGames(String str) {
        Map<String, String> map = getKeyMap();
        if (str != null)
            map.put("userId", str);

        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(4));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST4, Interface.URL + Interface.GETMYGAME);
    }

    /**
     * 我的心动值数量;
     *
     * @param str
     */
    private void getMyHeartNum(String str) {
        Map<String, String> map = getKeyMap();
        if (str != null)
            map.put("toUserId", str);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYHEARTNUMBER);
    }


    /**
     * 获取详情、查询实名;
     */
    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.MYINFODETAILS);
    }

//    /**
//     * 实名认证状态查询;
//     */
//    public void CheckID() {
//        Map<String, String> m = getKeyMap();
//        httpUtils.OkHttpsGet(m, this, Fields.REQUEST3, Interface.URL + Interface.GETAPPLYQUERY);
//    }

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
            case Fields.REQUEST3:
                try {

                    JSONObject ob = getJsonOb(response);
                    CheckStatusBean cb = new Gson().fromJson(String.valueOf(ob), CheckStatusBean.class);
                    listener.onCertificaTion(cb.getStatus());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case Fields.REQUEST2:

                try {
                    listener.onMyHeartValue(getJsonSring(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case Fields.REQUEST4:
                List<AttentionGameBean> ll = new ArrayList<>();
                JSONArray ar = getJsonAr(response);
                for (int i = 0; i < ar.length(); i++) {
                    AttentionGameBean agb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), AttentionGameBean.class);
                    ll.add(agb);
                }
                listener.onAttentGames(ll);

                break;

            case Fields.REQUEST5:
                JSONArray p = getJsonAr(response);
                List<PhotoBean> photos = new ArrayList<>();
                for (int i = 0; i < p.length(); i++) {
                    PhotoBean pb = new Gson().fromJson(String.valueOf(p.optJSONObject(i)), PhotoBean.class);
                    photos.add(pb);
                }
                listener.onPhotoList(photos);
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMSg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        listener.onFailedMSg(gets(R.string.parse_error));
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMSg(response);
    }
}
