package laoyou.com.laoyou.presenter;

import android.util.Log;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.SendCodeListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;


/**
 * Created by lian on 2017/10/26.
 */
public class SendCodePresenter implements HttpResultListener {
    private static final String TAG = "SendCodePresenter";
    private SendCodeListener listener;

    public SendCodePresenter(SendCodeListener listener) {
        this.listener = listener;
    }

    public void SendCode(String phone) {

        if (phone.length() != 11)
            listener.onFailed(gets(R.string.phoneuncorrectmsg));
        else {
            Map<String, String> map = getParamsMap();
            map.put("phone", phone);
            httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETREPASSWORDCODE);
        }
    }

    @Override
    public void onSucceed(String response, int tag) {
        listener.onSucceed();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onError();
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }
}
