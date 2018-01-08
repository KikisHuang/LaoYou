package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.FindSonListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.StatusPaser;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/11.
 */
public class FindSonPresenter implements HttpResultListener {

    private static final String TAG = "FindSonPresenter";
    private FindSonListener listener;
    private boolean RefreshFlag;
    public int page = 0;

    public FindSonPresenter(FindSonListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    List<NearbyBean> ar = GsonUtil.jsonToList(getJsonSring(response), NearbyBean.class);
                    if (ar.size() > 0) {
                        for (NearbyBean nbb : ar) {
                            if (nbb.getCloud_tencent_account() != null && !nbb.getCloud_tencent_account().isEmpty()) {
                                if (nbb.getGameImgs() != null && !nbb.getGameImgs().isEmpty()) {
                                    String b[] = nbb.getGameImgs().split("[,]");
                                    if (b != null && b.length > 0) {
                                        List<String> list = new ArrayList<>();
                                        for (String str : b) {
                                            list.add(str);
                                        }
                                        nbb.setImgsList(list);
                                    }
                                }
                            }
                        }
                        listener.RefreshNearby(ar);
                    } else if (RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nodata));
                    else if (!RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nomore));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST2:
                try {
                    JSONArray ar = getJsonAr(response);
                    List<TopicTypeBean> toppic = new ArrayList<>();
                    StatusPaser(ar, toppic);
                    if (ar.length() > 0)
                        listener.RefreshNewWonders(toppic);
                    else if (RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nodata));
                    else if (!RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nomore));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST3:
                int data = Integer.parseInt(getJsonSring(response));
                listener.onRefresh();
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "parse Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }

    /**
     * 附近的人数据获取;
     */
    public void getNearbyData(boolean flag) {
        /**
         * 如果没有经纬度，不能获取附近的人信息;
         */
        if (SPreferences.getLatitud() != null && Double.parseDouble(SPreferences.getLatitud()) > 0) {
            RefreshFlag = flag;
            Map<String, String> map = getParamsMap();
            map.put("page", String.valueOf(page));
            map.put("pageSize", String.valueOf(page + Fields.SIZE));
            map.put("latitude", String.valueOf(SPreferences.getLatitud()));
            map.put("longitude", String.valueOf(SPreferences.getLongitude()));

            httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETNEARBYUSER);
        }
    }

    /**
     * 新事件数据获取;
     */
    public void getNewIncident(boolean b) {
        RefreshFlag = b;

        Map<String, String> map = getKeyMap();
        map.put("model", String.valueOf(0));
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + Fields.SIZE));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETTOPICTYPEDETAILS);


    }

    //界面操作刷新;
    public void getHottestAndNewestRefresh() {
        Map<String, String> map = getKeyMap();
        map.put("model", String.valueOf(0));
        map.put("page", String.valueOf(0));
        if (page <= 0)
            page += 10;
        map.put("pageSize", String.valueOf(page));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETTOPICTYPEDETAILS);
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void LikeChatTheme(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST3, Interface.URL + Interface.LIKECHATTHEME);
    }
}
