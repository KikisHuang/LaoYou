package laoyou.com.laoyou.presenter;


import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.bean.FlashTypeIconBean;
import laoyou.com.laoyou.listener.FlashChatGambitListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatGambitPresenter implements HttpResultListener {
    private FlashChatGambitListener listener;

    public FlashChatGambitPresenter(FlashChatGambitListener listener) {
        this.listener = listener;
        getTypeIcon();
    }

    private void getTypeIcon() {

        Map<String, String> map = getParamsMap();
        map.put("type", String.valueOf(0));

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETICON);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                List<FlashTypeIconBean> list = GsonUtil.jsonToList(getJsonSring(response), FlashTypeIconBean.class);
                listener.onIconInfo(list);
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
}
