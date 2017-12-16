package laoyou.com.laoyou.presenter;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GameInfoBean;
import laoyou.com.laoyou.bean.GameTypeBean;
import laoyou.com.laoyou.listener.GameInformationListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
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
                List<GameTypeBean> list = new ArrayList<>();
                JSONArray ar = getJsonAr(response);
                if (!ArrayIsNull(ar)) {
                    for (int i = 0; i < ar.length(); i++) {
                        GameTypeBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), GameTypeBean.class);
                        list.add(pb);
                    }
                    listener.onGameTypeInforMation(list);
                } else
                    listener.onFailedMsg(gets(R.string.nodata));
                break;
            case Fields.REQUEST2:
                JSONArray a = getJsonAr(response);
                if (InfoRefresh)
                    li = new ArrayList<>();

                if (!ArrayIsNull(a)) {
                    for (int i = 0; i < a.length(); i++) {
                        GameInfoBean gb = new Gson().fromJson(String.valueOf(a.optJSONObject(i)), GameInfoBean.class);
                        li.add(gb);
                    }
                    listener.onGameInfor(li);
                } else if (InfoRefresh)
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
        listener.onFailedMsg(gets(R.string.parse_error));
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }
}
