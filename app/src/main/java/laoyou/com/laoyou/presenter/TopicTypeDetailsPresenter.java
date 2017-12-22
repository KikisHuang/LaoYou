package laoyou.com.laoyou.presenter;

import android.support.design.widget.AppBarLayout;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.ActiveUserBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.AppBarStateChangeListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.TopicTypeDetailsListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/19.
 */
public class TopicTypeDetailsPresenter extends AppBarStateChangeListener implements HttpResultListener {
    private TopicTypeDetailsListener listener;
    public int page = 0;
    private boolean isRefresh;
    private List<TopicTypeBean> list;

    public TopicTypeDetailsPresenter(TopicTypeDetailsListener listener) {
        this.listener = listener;
        list = new ArrayList<>();
    }

    /**
     * 检测关注;
     *
     * @param id
     */
    public void getFollowChatType(String id) {

        Map<String, String> map = getKeyMap();
        map.put("chatTypeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST4, Interface.URL + Interface.FOLLOWCHATTYPE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                if (isRefresh)
                    list.clear();
                JSONArray ar = getJsonAr(response);
                if (ar.length() > 0) {
                    for (int i = 0; i < ar.length(); i++) {
                        TopicTypeBean tb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), TopicTypeBean.class);
                        list.add(tb);
                    }
                    listener.onShowDetailsInfo(list);
                } else if (isRefresh)
                    listener.onFailedsMsg(gets(R.string.nodata));
                else if (!isRefresh)
                    listener.onFailedsMsg(gets(R.string.nomore));
                break;
            //活跃用户列表;
            case Fields.REQUEST2:
                List<ActiveUserBean> aub = new ArrayList<>();
                JSONArray a = getJsonAr(response);
                for (int i = 0; i < a.length(); i++) {
                    ActiveUserBean ab = new Gson().fromJson(String.valueOf(a.optJSONObject(i)), ActiveUserBean.class);
                    aub.add(ab);
                }
                listener.GetActiveUserData(aub);
                break;
            //点赞;
            case Fields.REQUEST3:
                int data = Integer.parseInt(getJsonSring(response));
                listener.onRefresh();
                break;
            //检测关注
            case Fields.REQUEST4:
                listener.IsFollowChat(getJsonSring(response).equals("1") ? true : false);
                break;
            //关注回调;
            case Fields.REQUEST5:
                listener.IsFollowChat(getJsonSring(response).equals("1") ? true : false);
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMsg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        listener.onFailedsMsg(gets(R.string.parse_error));
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMsg(response);
        Cancle();
    }

    /**
     * 关注话题圈类型;
     *
     * @param id
     */
    public void AttentionTopic(String id) {

        Map<String, String> map = getKeyMap();
        map.put("chatTypeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST5, Interface.URL + Interface.ATTENTIONTOPIC);
    }

    //最热;
    public void getHottestAndNewestData(String id, boolean b, int model, int i) {
        if (b)
            page = i;
        else
            page += i;

        isRefresh = b;
        Map<String, String> map = getKeyMap();
        map.put("model", String.valueOf(model));
        map.put("chatTypeId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page += 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETTOPICTYPEDETAILS);
    }

    //界面操作刷新;
    public void getHottestAndNewestRefresh(String id, boolean b, int model) {
        isRefresh = b;
        Map<String, String> map = getKeyMap();
        map.put("model", String.valueOf(model));
        map.put("chatTypeId", id);
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(page += 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETTOPICTYPEDETAILS);
    }

    /**
     * 获取活跃用户;
     *
     * @param id
     */
    public void getActiveUserData(String id) {

        Map<String, String> map = getParamsMap();
        map.put("chatTypeId", id);
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(6));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETACTIVEUSER);
    }

    @Override
    public void onStateChanged(AppBarLayout appBarLayout, State state, int i) {
        listener.onStateChange(appBarLayout, state, i);
    }

    public void setAppBarLayoutStateChangeListener(AppBarLayout appbar_layout) {
        appbar_layout.addOnOffsetChangedListener(this);
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

    /**
     * 移除监听;
     *
     * @param appbar_layout
     */
    public void removeAppBarLayoutStateChangeListener(AppBarLayout appbar_layout) {
        appbar_layout.removeOnOffsetChangedListener(this);
    }

    /**
     * 刷新;
     */
    public void OnRefresh() {
        listener.onRefresh();
    }
}