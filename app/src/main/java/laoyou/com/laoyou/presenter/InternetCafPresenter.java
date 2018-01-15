package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CafBean;
import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.InternetCapListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/12.
 */
public class InternetCafPresenter implements HttpResultListener {
    private static final String TAG = "InternetCafPresenter";
    private InternetCapListener listener;
    private String id = "";
    public int page = 0;
    private List<CafCommentBean> list;
    public boolean isRefresh;

    public InternetCafPresenter(InternetCapListener listener, String caf_id) {
        this.listener = listener;
        this.id = caf_id;
        this.list = new ArrayList<>();
        getCafDetails();
        getCatComment(true);
    }

    private void getCafDetails() {
        Map<String, String> map = getParamsMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETCAFDETAILS);
    }

    public void getCatComment(boolean t) {
        isRefresh = t;
        Map<String, String> map = getParamsMap();
        map.put("id", id);
        if (t)
            map.put("page", String.valueOf(0));
        else
            map.put("page", String.valueOf(page));

        map.put("pageSize", String.valueOf(page + 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETCAFCOMMENT);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:

                CafBean ub = GsonUtil.GsonToBean(getJsonSring(response), CafBean.class);
                listener.onInternetCafDetails(ub);

                break;
            case Fields.REQUEST2:
                List<CafCommentBean> list = GsonUtil.jsonToList(getJsonSring(response), CafCommentBean.class);
                if (list.size() > 0)
                    listener.onInternetCafComment(list);
                else if (!isRefresh)
                    listener.onEndBottom();

                break;
            case Fields.REQUEST3:
                Log.i(TAG, "Comment result == " + response);
                listener.onSucceed();
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

    public void SenCafComment(String caf_id, String content) {
        if (!caf_id.isEmpty() && content.length() > 0) {

            Map<String, String> map = getKeyMap();
            map.put("internetBarId", id);
            map.put("content", content);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST3, Interface.URL + Interface.CAFCOMMENTSEND, null, null);

        } else if (caf_id.isEmpty())
            listener.onFailedMsg(gets(R.string.caf_id_null));
        else if (content.length() <= 0)
            listener.onFailedMsg(gets(R.string.comment_content_null));
    }
}
