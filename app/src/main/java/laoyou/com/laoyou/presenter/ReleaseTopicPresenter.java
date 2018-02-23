package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.FilesBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.ReleaseTopicListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/28.
 */
public class ReleaseTopicPresenter implements HttpResultListener {

    private static final String TAG = "ReleaseTopicPresenter";
    private ReleaseTopicListener listener;

    public ReleaseTopicPresenter(ReleaseTopicListener listener) {
        this.listener = listener;
        getUserInfo();
    }

    private void getUserInfo() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.MYINFODETAILS, null, null);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:
                listener.onSucceed();
                Cancle();
                break;
            case Fields.REQUEST2:
                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                SPreferences.saveMyNickName(ub.getName());
                listener.ShowUserInfo(ub);
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFaileds(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFaileds(response);
        Cancle();
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
