package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.MarkerBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.InternetCafLocationListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/14.
 */
public class InternetCafLocationPresenter implements HttpResultListener {

    private static final String TAG = "InternetCafLocationPresenter";

    private InternetCafLocationListener listener;
    private int page = 0;

    public InternetCafLocationPresenter(InternetCafLocationListener listener) {
        this.listener = listener;
    }

    public void getInternetCafData(double latitude, double longitude) {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(9999));
        map.put("latitude", String.valueOf(latitude));
        map.put("longitude", String.valueOf(longitude));
        map.put("orderType", String.valueOf(1));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.INTERNETBAR);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                List<MarkerBean> list = GsonUtil.jsonToList(getJsonSring(response), MarkerBean.class);
                if (list.size() > 0)
                    listener.onInternetCafDataList(list);
                else
                    listener.onFailesMsg(gets(R.string.no_bar_data));
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailesMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailesMsg(response);
    }
}
