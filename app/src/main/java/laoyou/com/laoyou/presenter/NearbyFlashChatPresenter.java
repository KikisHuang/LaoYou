package laoyou.com.laoyou.presenter;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GroupBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.NearbyFlashChatListener;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/5.
 */
public class NearbyFlashChatPresenter implements HttpResultListener {

    private NearbyFlashChatListener listener;
    private long limit = 20;
    private long next;
    private boolean refresh;

    public NearbyFlashChatPresenter(NearbyFlashChatListener listener) {
        this.listener = listener;
        getData(true);
    }

    public void getData(boolean b) {
        if (b)
            next = 0;

        refresh = b;
        Map<String, String> map = getParamsMap();

        map.put("groupType", GroupInfo.publicGroup);
        map.put("next", String.valueOf(next));
        map.put("limit", String.valueOf(limit));
        map.put("officialFlag", String.valueOf(0));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETAPPIDGROUP);
    }

    @Override
    public void onSucceed(String response, int tag) {
        switch (tag) {
            case Fields.REQUEST1:
                List<GroupBean> list = new ArrayList<>();
                try {
                    JSONObject ob = getJsonOb(response);
                    if (ob.length() > 0) {
                        GroupBean gb = new Gson().fromJson(String.valueOf(ob), GroupBean.class);
                        list.add(gb);
                        next = gb.getNext();
                        listener.onSucceed(list);
                    } else if (!refresh) {
                        listener.onFailedMsg(gets(R.string.The_bottom));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }
}
