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
import laoyou.com.laoyou.listener.MyNoticeListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/23.
 */
public class MyNoticePresenter implements HttpResultListener {

    private static final String TAG = "MyNoticePresenter";

    private MyNoticeListener listener;
    private boolean isMore;

    public MyNoticePresenter(MyNoticeListener listener) {
        this.listener = listener;
        getMyNoticeDataList(true);
    }

    public void getMyNoticeDataList(boolean b) {
        isMore = b;
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETMYFOLLOWCHATTYPE);
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
                } else if (!isMore)
                    listener.onFailedsMsg(gets(R.string.nomore));
                else
                    listener.onFailedsMsg(gets(R.string.nodata));

                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMsg(response);
    }
}
