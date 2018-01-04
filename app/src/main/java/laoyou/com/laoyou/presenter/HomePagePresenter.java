package laoyou.com.laoyou.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMDelFriendType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;
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
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.fileIsExists;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.saveImage;
import static laoyou.com.laoyou.utils.VideoUtils.createVideoThumbnail;

/**
 * Created by lian on 2017/12/7.
 */
public class HomePagePresenter implements HttpResultListener {
    private static final String TAG = "HomePagePresenter";
    private HomePageListener listener;
    public int page = 0;

    public HomePagePresenter(HomePageListener listener) {
        this.listener = listener;
    }


    /**
     * 获取他人的信息;
     */
    public void getOthersDetails(String id, boolean isTencent) {
        getOthersUseDetails(id, isTencent);
    }

    /**
     * 获取他人的详情;
     */
    public void getOthersUseDetails(String id, boolean isTencent) {
        Map<String, String> map = getKeyMap();
        if (isTencent)
            map.put("cloudTencentAccount", id);
        else
            map.put("userId", id);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETOTHERSDETAILS);
    }

    /**
     * 获取我的个人信息;
     */
    public void getMyDetails() {
        getUseDetails();
        getMyHeartNum(null);
        getAttentGames(null);
        getMyPhotoList(null);
        getPersonaldynamic(null, false);
//        CheckID();
    }

    /**
     * 获取个人动态(他人动态);
     *
     * @param id
     * @param b
     */
    public void getPersonaldynamic(String id, boolean b) {

        Map<String, String> map = getKeyMap();
        if (id != null)
            map.put("userId", String.valueOf(id));
        if (b)
            map.put("page", String.valueOf(0));
        else
            map.put("page", String.valueOf(page));

        map.put("pageSize", String.valueOf(page += Fields.SIZE));

        httpUtils.OkHttpsGet(map, this, Fields.ACRESULET1, Interface.URL + Interface.GETCAREBYPAGE);
    }

    /**
     * 获取相册  userId String 字符串用户id(为空则取key对应的用户的id)
     *
     * @param str
     */
    public void getMyPhotoList(String str) {

        Map<String, String> map = getKeyMap();
        if (str != null)
            map.put("toUserId", str);

        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST5, Interface.URL + Interface.GETPHOTOBYPAGE);

    }

    /**
     * 获取关注的游戏  userId String 字符串用户id(为空则取key对应的用户的id)
     *
     * @param str
     */
    public void getAttentGames(String str) {
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
    public void getMyHeartNum(String str) {
        Map<String, String> map = getKeyMap();
        if (str != null)
            map.put("toUserId", str);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYHEARTNUMBER);
    }


    /**
     * 获取我的详情、查询实名;
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
            case Fields.REQUEST6:
                listener.onFailedMSg(gets(R.string.give_heart_succ));
                break;

            case Fields.ACRESULET1:
                try {
                    JSONArray status = getJsonAr(response);
                    if (status.length() > 0) {
                        List<TopicTypeBean> Nblist = new ArrayList<>();
                        for (int i = 0; i < status.length(); i++) {
                            TopicTypeBean ttb = new Gson().fromJson(String.valueOf(status.getJSONObject(i)), TopicTypeBean.class);

                            if (ttb.getReChatMessages() != null) {
                                JSONArray tta = new JSONArray(ttb.getReChatMessages().replace(" ", ""));
                                Gson gson = new Gson();
                                String[][] ss = gson.fromJson(String.valueOf(tta), new TypeToken<String[][]>() {
                                }.getType());
                                List<List<String>> outlist = new ArrayList<>();
                                for (String[] strings : ss) {
                                    List<String> inlist = null;
                                    for (String string : strings) {
                                        if (inlist == null)
                                            inlist = new ArrayList<>();
                                        inlist.add(string);
                                    }
                                    outlist.add(inlist);
                                }
                                ttb.setComments(outlist);
                            }
                            if (ttb.getVideos() != null) {
                                if (fileIsExists(ttb.getVideos()))
                                    ttb.setVideoCover(saveImage(null, ttb.getVideos()));
                                else {
                                    Bitmap bitmap = createVideoThumbnail(ttb.getVideos(), DeviceUtils.getWindowWidth(SPreferences.context), (int) (DeviceUtils.getWindowWidth(SPreferences.context) * 0.8 / 1));
                                    ttb.setVideoCover(saveImage(bitmap, ttb.getVideos()));
                                }
                            }

                            if (ttb.getImgs() != null) {
                                String b[] = ttb.getImgs().split("[,]");
                                if (b != null && b.length > 0) {
                                    List<String> list = new ArrayList<>();
                                    for (String str : b) {
                                        list.add(str);
                                    }
                                    ttb.setPhotos(list);
                                }
                            }
                            Nblist.add(ttb);
                        }
                        listener.onStatusInfo(Nblist);
                    } else
                        listener.onBottom();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.ACRESULET2:
                listener.onRefresh();
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMSg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Cancle();
//        listener.onFailedMSg(gets(R.string.parse_error));
        Log.e(TAG, "Parse Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        Cancle();
        listener.onFailedMSg(response);
    }

    public void GiveHeart(String id) {
        if (!id.isEmpty()) {
            Map<String, String> map = getKeyMap();
            map.put("toUserId", id);
            httpUtils.OkHttpsGet(map, this, Fields.REQUEST6, Interface.URL + Interface.GIVEHEARTNUMBER);
        }
    }


    public void LikeChatTheme(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.ACRESULET2, Interface.URL + Interface.LIKECHATTHEME);
    }


    /**
     * 删除好友
     *
     * @param id 删除对象Identify
     */
    public void delFriend(final String id) {
        if (listener == null) return;
        List<TIMAddFriendRequest> reqList = new ArrayList<>();
        TIMAddFriendRequest req = new TIMAddFriendRequest();
        req.setIdentifier(id);
        reqList.add(req);
        TIMFriendshipManager.getInstance().delFriend(TIMDelFriendType.TIM_FRIEND_DEL_BOTH, reqList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                listener.onAddFriend(TIMFriendStatus.TIM_FRIEND_STATUS_UNKNOWN);
                Cancle();
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                for (TIMFriendResult item : timFriendResults) {
                    if (item.getIdentifer().equals(id)) {
                        listener.onDelFriend(item.getStatus());
                    }
                }
                Cancle();
            }
        });
    }
}
