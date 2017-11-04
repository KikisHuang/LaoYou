package laoyou.com.laoyou.presenter;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginOperationListener;
import okhttp3.Request;

/**
 * Created by lian on 2017/10/25.
 */
public class LoginOperationPresenter implements HttpResultListener{
    private LoginOperationListener listener;

    public LoginOperationPresenter(LoginOperationListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
//        getData();
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
