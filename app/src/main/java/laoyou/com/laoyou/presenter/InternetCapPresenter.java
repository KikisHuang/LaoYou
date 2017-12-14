package laoyou.com.laoyou.presenter;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.InternetCapListener;
import okhttp3.Request;

/**
 * Created by lian on 2017/12/12.
 */
public class InternetCapPresenter implements HttpResultListener{
    private InternetCapListener listener;

    public InternetCapPresenter(InternetCapListener listener) {
        this.listener = listener;
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
}
