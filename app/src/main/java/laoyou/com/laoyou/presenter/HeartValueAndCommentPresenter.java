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
import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.bean.HeartBean;
import laoyou.com.laoyou.listener.HeartValueAndCommentListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/23.
 */
public class HeartValueAndCommentPresenter implements HttpResultListener {
    private static final String TAG = "HeartValueAndCommentPresenter";
    private HeartValueAndCommentListener listener;
    public int page = 0;
    private boolean IsRefresh;

    public HeartValueAndCommentPresenter(HeartValueAndCommentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    List<HeartBean> hearts = new ArrayList<>();
                    JSONArray ar = getJsonAr(response);
                    for (int i = 0; i < ar.length(); i++) {
                        HeartBean ub = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), HeartBean.class);
                        hearts.add(ub);
                    }
                    listener.onHeartData(hearts);
                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
                break;

            case Fields.REQUEST2:
                try {
                    List<CommentBean> comments = new ArrayList<>();
                    JSONArray ar = getJsonAr(response);
                    if (ar.length() > 0) {
                        for (int i = 0; i < ar.length(); i++) {
                            CommentBean cb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), CommentBean.class);
                            comments.add(cb);
                        }
                        listener.onCommentData(comments);
                    } else if (IsRefresh)
                        listener.onFailedsMsg(gets(R.string.nodata));
                    else if (!IsRefresh)
                        listener.onFailedsMsg(gets(R.string.nomore));

                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    /**
     * 获取我被赠与的心动值;
     */
    public void GetMyHeartNumber(boolean isSend) {
        Map<String, String> map = getKeyMap();
        map.put("isSend", String.valueOf(isSend));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETMYHEARTNUMBERLIST);
    }

    /**
     * 我发出的评论;
     */
    public void GetMyChatMsg(boolean b) {
        IsRefresh = b;
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYCHATMESSAGE);
    }

    /**
     * 我收到的评论;
     */
    public void GetMyReceiverdChatMsg(boolean b) {
        IsRefresh = b;
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYRECEIVEDCHATMESSAGE);
    }


}
