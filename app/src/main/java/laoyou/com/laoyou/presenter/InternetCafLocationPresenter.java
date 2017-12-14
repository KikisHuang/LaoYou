package laoyou.com.laoyou.presenter;

import java.util.Map;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.InternetCafLocationListener;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/12/14.
 */
public class InternetCafLocationPresenter implements HttpResultListener{
    private InternetCafLocationListener listener;

    public InternetCafLocationPresenter(InternetCafLocationListener listener) {
        this.listener = listener;
        getInternetCafData();
    }

    private void getInternetCafData() {
        Map<String, String> map = getKeyMap();
//      httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.MYINFODETAILS);
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
