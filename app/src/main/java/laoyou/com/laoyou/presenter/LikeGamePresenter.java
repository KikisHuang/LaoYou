package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LikeGameListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/15.
 */
public class LikeGamePresenter implements HttpResultListener {

    private static final String TAG = "LikeGamePresenter";
    private LikeGameListener listener;
    public int page = 0;
    private String id = "";

    public LikeGamePresenter(LikeGameListener listener, String id) {
        this.listener = listener;
        this.id = id;
        getMyLikeGameDataList(id);
    }

    /**
     * 获取我喜欢的游戏列表;
     *
     * @param id
     */
    public void getMyLikeGameDataList(String id) {

        Map<String, String> map = getKeyMap();
        if (!id.isEmpty())
            map.put("userId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETMYGAME);
    }

    /**
     * 添加或取消喜欢的游戏游戏;
     *
     * @param id
     */
    public void FollowGameOfCancle(String id) {

        Map<String, String> map = getKeyMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.FOLLOWGAME);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:

                List<GameBean> games = GsonUtil.jsonToList(getJsonSring(response), GameBean.class);
                listener.onGamesInfo(games);

                break;
            case Fields.REQUEST2:
                int f = Integer.parseInt(getJsonSring(response));
                getMyLikeGameDataList(id);
                if (f == 0)
                    listener.onSucceed(gets(R.string.delete_succ));
                else
                    listener.onSucceed(gets(R.string.add_succ));
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMSg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Cancle();
        Log.e(TAG, "Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMSg(response);
        Cancle();
    }
}
