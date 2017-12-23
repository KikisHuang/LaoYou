package laoyou.com.laoyou.presenter;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.ParticipationListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/23.
 */
public class ParticipationPresenter implements HttpResultListener {

    private ParticipationListener listener;
    public int page = 0;


    public ParticipationPresenter(ParticipationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST2:
                JSONArray ar = getJsonAr(response);
                if (ar.length() > 0) {
                    List<LikeListBean> li = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        LikeListBean like = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), LikeListBean.class);
                        li.add(like);
                    }
                    listener.LikeListData(li);
                } else
                    listener.NoData();


                break;

            case Fields.REQUEST1:
                JSONArray a = getJsonAr(response);
                if (a.length() > 0) {
                    List<LikeListBean> list = new ArrayList<>();
                    for (int i = 0; i < a.length(); i++) {
                        LikeListBean like = new LikeListBean();
                        JSONObject ob = a.getJSONObject(i);
                        like.setId(ob.optString("userId"));
                        like.setName(ob.optString("userName"));
                        like.setHeadImgUrl(ob.optString("headImgUrl"));
                        list.add(like);
                    }
                    listener.LikeListData(list);
                } else
                    listener.NoData();
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        listener.onFailedsMsg(gets(R.string.parse_error));
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMsg(response);
    }

    /**
     * 获取活跃用户(参与人);
     *
     * @param id
     */
    public void getActiveUserData(String id) {

        Map<String, String> map = getParamsMap();
        map.put("chatTypeId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETACTIVEUSER);
    }


    public void getLikeList(String id) {

        Map<String, String> map = getParamsMap();
        map.put("id", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETLIKEUSERBYPAGE);
    }
}
