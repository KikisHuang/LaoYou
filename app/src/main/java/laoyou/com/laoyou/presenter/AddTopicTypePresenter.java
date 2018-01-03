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
import laoyou.com.laoyou.bean.AddTopicTypeBean;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.AddTopicTypeListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/28.
 */
public class AddTopicTypePresenter implements HttpResultListener {

    private static final String TAG = getTAG(AddTopicTypePresenter.class);
    private AddTopicTypeListener listener;

    public AddTopicTypePresenter(AddTopicTypeListener listener) {
        this.listener = listener;
        getTopicTypeDataList();
    }

    private void getTopicTypeDataList() {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(6));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.TOPICGETBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                JSONArray ar = getJsonAr(response);
                if (ar.length() > 0) {
                    List<TopicBean> list = new ArrayList<>();
                    for (int i = 0; i < ar.length(); i++) {
                        TopicBean tb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), TopicBean.class);
                        list.add(tb);
                    }
                    listener.onTopicTypeList(list);
                }
                break;
            case Fields.REQUEST2:
                Log.i(TAG, "添加类型成功回调 == " + response);
                JSONObject ob = getJsonOb(response);
                AddTopicTypeBean attb = new Gson().fromJson(String.valueOf(ob), AddTopicTypeBean.class);
                listener.onSucceed(attb);

                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMsg(response);
    }

    /**
     * 新增类型;
     *
     * @param name
     */
    public void AddTopicType(String name) {

        Map<String, String> map = getParamsMap();
        map.put("name", name);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.ADDCHATTYPE);
    }
}
