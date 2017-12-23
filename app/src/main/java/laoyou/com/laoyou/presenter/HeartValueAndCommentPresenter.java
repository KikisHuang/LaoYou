package laoyou.com.laoyou.presenter;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.listener.HeartValueAndCommentListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/12/23.
 */
public class HeartValueAndCommentPresenter implements HttpResultListener {
    private HeartValueAndCommentListener listener;


    public HeartValueAndCommentPresenter(HeartValueAndCommentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

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
     * 获取我被赠与的心动值;
     */
    public void GetMyHeartNumber() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST4, Interface.URL + Interface.GETMYHEARTNUMBERLIST);
    }


}
