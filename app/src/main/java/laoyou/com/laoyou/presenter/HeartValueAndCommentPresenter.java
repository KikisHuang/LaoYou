package laoyou.com.laoyou.presenter;


import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.bean.HeartBean;
import laoyou.com.laoyou.listener.HeartValueAndCommentListener;
import laoyou.com.laoyou.listener.HttpResultListener;
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
                List<HeartBean> hearts = GsonUtil.jsonToList(getJsonSring(response), HeartBean.class);
                listener.onHeartData(hearts);

                break;

            case Fields.REQUEST2:
                List<CommentBean> comments = GsonUtil.jsonToList(getJsonSring(response), CommentBean.class);

                if (comments.size() > 0)
                    listener.onCommentData(comments);
                else if (IsRefresh)
                    listener.onFailedsMsg(gets(R.string.nodata));
                else if (!IsRefresh)
                    listener.onFailedsMsg(gets(R.string.nomore));

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
