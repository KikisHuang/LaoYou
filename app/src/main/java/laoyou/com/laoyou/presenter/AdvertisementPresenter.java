package laoyou.com.laoyou.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import laoyou.com.laoyou.listener.AdvertisementListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;

/**
 * Created by lian on 2018/2/7.
 */
public class AdvertisementPresenter implements HttpResultListener {
    private AdvertisementListener listener;

    public AdvertisementPresenter(AdvertisementListener listener) {
        this.listener = listener;
        getAdvInfo();
    }

    private void getAdvInfo() {

        Map<String, String> map = getParamsMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETSETTING);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        JSONObject ob = getJsonOb(response);
//        ob.optString("welcomeHTTPUrl")
        if (listener != null)
            listener.onSucceed(ob.optString("welcomeImgUrl"), "https://www.baidu.com/");
    }

    @Override
    public void onError(Request request, Exception e) {
        if (listener != null)
            listener.onFailedMsg("");
    }

    @Override
    public void onParseError(Exception e) {
        if (listener != null)
            listener.onFailedMsg("");
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        if (listener != null)
            listener.onFailedMsg("");
    }
}
