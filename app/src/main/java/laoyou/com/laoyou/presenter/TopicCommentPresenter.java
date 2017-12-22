package laoyou.com.laoyou.presenter;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TopicCommentBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/22.
 */
public class TopicCommentPresenter implements HttpResultListener {

    private TopicCommentListener listener;

    public TopicCommentPresenter(TopicCommentListener listener) {
        this.listener = listener;
    }

    public void getLikeStatus(String id) {

        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.COMMENTLIKECHATTHEME);
    }

    public void getTopicDetails(String id) {

        Map<String, String> map = getParamsMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.TOPICGETDETAILS);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                int status = Integer.parseInt(getJsonSring(response));
                listener.onLikeStatus(status == 1 ? true : false);
                break;
            case Fields.REQUEST2:
                JSONObject ob = getJsonOb(response);
                TopicCommentBean tcb = new Gson().fromJson(String.valueOf(ob), TopicCommentBean.class);
                listener.onThemeDetails(tcb);

                break;
            case Fields.REQUEST3:
                if (Integer.valueOf(getJsonSring(response)) == 1)
                    listener.onSucceed();
                else
                    listener.onFailedMsg(gets(R.string.comment_failed));
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        listener.onFailedMsg(gets(R.string.parse_error));
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }

    public void LikeTopic(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.ADDCOMMENTLIKECHATTHEME);
    }

    public void SendComment(String id, String userId, String content, File file) {
        if (content != null) {
            Map<String, String> map = getKeyMap();
            map.put("messageContent", content);
            map.put("chatThemeId", id);
            if (!userId.isEmpty())
                map.put("reMessageId", userId);
            Map<String, File> files = null;
            if (file != null) {
                files = new HashMap<>();
                files.put("files", file);
            }

            httpUtils.OkHttpsPost(map, this, Fields.REQUEST3, Interface.URL + Interface.CHATMESSAGE, null, files);
        } else
            listener.onFailedMsg(gets(R.string.comment_content_null));
    }
}
