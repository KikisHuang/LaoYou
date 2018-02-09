package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.ShareMyQrCodeListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2018/2/9.
 */
public class ShareMyQrCodePresenter implements HttpResultListener {
    private static final String TAG = "ShareMyQrCodePresenter";
    private ShareMyQrCodeListener listener;


    public ShareMyQrCodePresenter(ShareMyQrCodeListener listener) {
        this.listener = listener;
        getMyQrCode();
    }

    private void getMyQrCode() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.GETUSERCARDQCURL, null, null);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                listener.ShowMyQrCode(getJsonSring(response));
                break;
            case Fields.REQUEST2:
                listener.onShareQrCodeUrl(getJsonSring(response));
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
    }

    public void ShareMyQrCode() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.GETUSERCARDSHAREIMAGEURL, null, null);

    }
}
