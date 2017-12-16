package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LikeGameListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/15.
 */
public class LikeGamePresenter implements HttpResultListener {

    private static final String TAG = "LikeGamePresenter";
    private LikeGameListener listener;

    public LikeGamePresenter(LikeGameListener listener) {
        this.listener = listener;
        getMyLikeGameDataList();
    }

    private void getMyLikeGameDataList() {

        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETMYGAME);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:

                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedsMSg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedsMSg(response);
    }
}
