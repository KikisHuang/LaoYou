package laoyou.com.laoyou.presenter;

import laoyou.com.laoyou.listener.GameInformationListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import okhttp3.Request;

/**
 * Created by lian on 2017/12/12.
 */
public class GameInformationPresenter implements HttpResultListener {

    private static final String TAG = "GameInformationPresenter";
    private GameInformationListener listener;

    public GameInformationPresenter(GameInformationListener listener) {
        this.listener = listener;
    }

    public void getGameTypeDataList() {
        listener.onGameTypeInforMation();
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
