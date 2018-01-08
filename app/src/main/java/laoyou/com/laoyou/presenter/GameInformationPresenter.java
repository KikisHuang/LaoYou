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
import laoyou.com.laoyou.bean.FlashTypeIconBean;
import laoyou.com.laoyou.bean.GameInfoBean;
import laoyou.com.laoyou.bean.GameTypeBean;
import laoyou.com.laoyou.listener.GameInformationListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.ArrayIsNull;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/12.
 */
public class GameInformationPresenter implements HttpResultListener {

    private static final String TAG = "GameInformationPresenter";
    private GameInformationListener listener;
    public int page = 0;
    private boolean InfoRefresh;
    private List<GameInfoBean> li;

    public GameInformationPresenter(GameInformationListener listener) {
        this.listener = listener;
        getGameInforType();
    }

    /**
     * 获取游戏资讯类型：
     */
    private void getGameInforType() {

        Map<String, String> map = getParamsMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETNEWSTYPE);
    }

    /**
     * 获取游戏资讯列表：
     *
     * @param id
     */
    public void getGameInfo(String id, boolean f) {
        InfoRefresh = f;

        Map<String, String> map = getParamsMap();
        map.put("typeId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:

                List<GameTypeBean> list = GsonUtil.jsonToList(getJsonSring(response), GameTypeBean.class);
                if (list.size() > 0)
                    listener.onGameTypeInforMation(list);
                else
                    listener.onFailedMsg(gets(R.string.nodata));
                break;
            case Fields.REQUEST2:

                if (InfoRefresh)
                    li = new ArrayList<>();

                li = GsonUtil.jsonToList(getJsonSring(response), GameInfoBean.class);

                if (li.size() > 0)
                    listener.onGameInfor(li);
                else if (InfoRefresh)
                    listener.onFailedMsg(gets(R.string.nodata));
                else
                    listener.onFailedMsg(gets(R.string.The_bottom));
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
}
