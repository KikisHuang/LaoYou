package laoyou.com.laoyou.presenter;

import android.util.Log;
import android.view.View;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.ChatMessages;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.bean.TopicCommentBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import laoyou.com.laoyou.view.popup.CommentPhotoPopupWindow;
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

    private static final String TAG = "TopicCommentPresenter";
    private TopicCommentListener listener;

    public TopicCommentPresenter(TopicCommentListener listener) {
        this.listener = listener;
    }

    public int page = 0;
    private CommentPhotoPopupWindow commentPhotoPopupWindow;

    /**
     * 获取点赞状态;
     *
     * @param id
     */
    public void getLikeStatus(String id) {

        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.COMMENTLIKECHATTHEME);
    }

    /**
     * 获取话题圈主题详情;
     *
     * @param id
     */
    public void getTopicDetails(String id) {

        Map<String, String> map = getParamsMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.TOPICGETDETAILS);
    }

    /**
     * 获取话题圈主题的点赞人;
     *
     * @param id
     */
    public void GetLikeUserByPage(String id) {
        Map<String, String> map = getParamsMap();
        map.put("id", id);
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(8));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST4, Interface.URL + Interface.GETLIKEUSERBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                int status = Integer.parseInt(getJsonSring(response));
                listener.onLikeStatus(status == 1 ? true : false);
                break;
            case Fields.REQUEST2:
                TopicCommentBean tcb = GsonUtil.GsonToBean(getJsonSring(response), TopicCommentBean.class);
                if (tcb.getImgs() != null) {
                    String b[] = tcb.getImgs().split("[,]");
                    if (b != null && b.length > 0) {
                        List<String> list = new ArrayList<>();
                        for (String str : b) {
                            list.add(str);
                        }
                        tcb.setPhotos(list);
                    }
                }
                listener.onThemeDetails(tcb);
                break;
            case Fields.REQUEST3:
                if (Integer.valueOf(getJsonSring(response)) == 1)
                    listener.onSucceed();
                else
                    listener.onFailedMsg(gets(R.string.comment_failed));
                break;
            case Fields.REQUEST4:
                List<LikeListBean> ar = GsonUtil.jsonToList(getJsonSring(response), LikeListBean.class);
                listener.LikeListData(ar);
                break;

            case Fields.REQUEST5:
                JSONObject o = getJsonOb(response);

                listener.onLikeNum(o.optString("likeCount"));

                break;
            case Fields.REQUEST6:
                listener.onDeleteSucceed();
                break;

            case Fields.ACRESULET5:
                List<ChatMessages> list = GsonUtil.jsonToList(getJsonSring(response), ChatMessages.class);
                if (list.size() > 0)
                    listener.onCommentInfo(list);
                else
                    listener.onNoMore(gets(R.string.nomore));
                break;
        }

    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
//        listener.onFailedMsg(gets(R.string.parse_error));
        Log.e(TAG, gets(R.string.parse_error) + " ===" + e);

    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }

    /**
     * 点赞;
     *
     * @param id
     */
    public void LikeTopic(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.ADDCOMMENTLIKECHATTHEME);
    }

    /**
     * 发送评论;
     *
     * @param id      主题id
     * @param userId  帖子id（可为空）
     * @param content 内容
     * @param file    图片文件
     */
    public void SendComment(String id, String userId, String content, File file) {
        if (!content.isEmpty() || file != null) {
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
        } else if (content.isEmpty())
            listener.onFailedMsg(gets(R.string.comment_content_null));

    }

    /**
     * 用于刷新赞的数量;
     *
     * @param id
     */
    public void getLikeNum(String id) {
        Map<String, String> map = getParamsMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST5, Interface.URL + Interface.TOPICGETDETAILS);
    }

    /**
     * 删除我的评论;
     *
     * @param id
     */
    public void DeleteMyComment(String id) {
        Map<String, String> map = getKeyMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST6, Interface.URL + Interface.DELETETCHATMESSAGE);
    }

    /**
     * 获取我的评论;
     *
     * @param id
     */
    public void getComment(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 10));
        httpUtils.OkHttpsGet(map, this, Fields.ACRESULET5, Interface.URL + Interface.GECHATMESSAGEBYPAGE);
    }

    public void ShowPhotoPopup(String imgPath, View view) {
        if (commentPhotoPopupWindow == null)
            commentPhotoPopupWindow = new CommentPhotoPopupWindow(listener);
        commentPhotoPopupWindow.ScreenPopupWindow(imgPath, view);
    }

    public void MovePopupWindow(View view) {
        if (commentPhotoPopupWindow != null)
            commentPhotoPopupWindow.MoveWindow(view);
    }

    public void ClosePopupWindow() {
        if (commentPhotoPopupWindow != null)
            commentPhotoPopupWindow.ClosePopup();
    }
}
