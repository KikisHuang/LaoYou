package laoyou.com.laoyou.presenter;

import android.content.Context;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.WifiListener;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.WifiUtils.isWifiConnected;

/**
 * Created by lian on 2017/10/25.
 */
public class WifiPresenter implements HttpResultListener {

    private static final String TAG = "WifiPresenter";
    private WifiListener listener;

    public WifiPresenter(WifiListener listener) {
        this.listener = listener;
    }

    public void Presenter() {

    }

    @Override
    public void onSucceed(String response, int tag) {

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

    public void WifiConnected(Context context) {
        if (isWifiConnected(context))
            listener.onConnected();
        else
            listener.onUnConnected();
    }
}
