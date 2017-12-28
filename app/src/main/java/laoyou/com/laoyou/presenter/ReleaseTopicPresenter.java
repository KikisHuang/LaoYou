package laoyou.com.laoyou.presenter;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.bean.FilesBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.ReleaseTopicListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/12/28.
 */
public class ReleaseTopicPresenter implements HttpResultListener {

    private static final String TAG = "ReleaseTopicPresenter";
    private ReleaseTopicListener listener;

    public ReleaseTopicPresenter(ReleaseTopicListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:
                listener.onSucceed();
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
     * 发布话题圈动态;
     */
    public void IssueTopic(String id, String content, List<File> files, File video) {

        Map<String, String> map = getKeyMap();
        if (!id.isEmpty())
            map.put("chatTypeId", String.valueOf(id));

        map.put("content", content);
        FilesBean fb = null;
        if (files != null) {
            fb = new FilesBean();
            HashMap<String, File> m = new HashMap<>();
            for (File f : files) {
                m.put(f.getName(), f);
            }
            fb.setKey("files");
            fb.setMaps(m);
        }
        Map<String, File> videos = null;
        if (video != null) {
            videos = new HashMap<>();
            videos.put("video", video);
        }
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.CHATTHEME, fb, videos);
    }
}
