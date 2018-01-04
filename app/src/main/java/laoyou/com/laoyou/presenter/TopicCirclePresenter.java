package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.TopicCircleListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/18.
 */
public class TopicCirclePresenter implements HttpResultListener {

    private static final String TAG = "TopicCirclePresenter";
    private TopicCircleListener listener;
    public int page = 0;
    private boolean isRefresh = true;
    private List<TopicBean> list;

    public TopicCirclePresenter(TopicCircleListener listener) {
        this.listener = listener;
        list = new ArrayList<>();
        getMyFollowChatType();
    }

    public void getMyFollowChatType() {

        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYFOLLOWCHATTYPE);

    }

    public void getTopicDataList(boolean b) {
        isRefresh = b;
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page += 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.TOPICGETBYPAGE);

    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                JSONArray ar = getJsonAr(response);
                if (isRefresh)
                    list.clear();

                if (ar.length() > 0) {
                    for (int i = 0; i < ar.length(); i++) {
                        TopicBean tb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), TopicBean.class);
                        list.add(tb);
                    }
                    listener.onTopicTypeList(list);
                } else if (isRefresh)
                    listener.onFailedMsg(gets(R.string.nodata));
                else if (!isRefresh)
                    listener.onFailedMsg(gets(R.string.nomore));
                break;

            case Fields.REQUEST2:
                JSONArray myar = getJsonAr(response);
                if (myar.length() > 0)
                    listener.onHeadViewShwoOfHide(true);
                else
                    listener.onHeadViewShwoOfHide(false);
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }
}
