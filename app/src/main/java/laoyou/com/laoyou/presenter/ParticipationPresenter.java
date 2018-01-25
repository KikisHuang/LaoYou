package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.ParticipationListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/23.
 */
public class ParticipationPresenter implements HttpResultListener {
    private static final String TAG = "ParticipationPresenter";
    private ParticipationListener listener;
    public int page = 0;


    public ParticipationPresenter(ParticipationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST2:

                List<LikeListBean> ar = GsonUtil.jsonToList(getJsonSring(response), LikeListBean.class);
                if (ar.size() > 0)
                    listener.LikeListData(ar);
                else
                    listener.NoData();
                break;

            case Fields.REQUEST1:
                List<LikeListBean> a = GsonUtil.jsonToList(getJsonSring(response), LikeListBean.class);
                if (a.size() > 0)
                    listener.LikeListData(a);
                else
                    listener.NoData();
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMsg(response);
    }

    /**
     * 获取活跃用户(参与人);
     *
     * @param id
     */
    public void getActiveUserData(String id) {

        Map<String, String> map = getParamsMap();
        map.put("chatTypeId", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETACTIVEUSER);
    }


    public void getLikeList(String id) {

        Map<String, String> map = getParamsMap();
        map.put("id", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 20));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETLIKEUSERBYPAGE);
    }
}
