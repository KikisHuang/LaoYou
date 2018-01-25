package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyNoticeListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
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

                List<TopicBean> ar = GsonUtil.jsonToList(getJsonSring(response), TopicBean.class);
                if (ar.size() > 0)
                    listener.onTopicTypeList(ar);
                else if (!isMore)
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
