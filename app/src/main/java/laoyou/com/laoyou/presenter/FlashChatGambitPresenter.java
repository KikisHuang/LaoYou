package laoyou.com.laoyou.presenter;

import laoyou.com.laoyou.listener.FlashChatGambitListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import okhttp3.Request;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatGambitPresenter implements HttpResultListener{
    private FlashChatGambitListener listener;
    public FlashChatGambitPresenter(FlashChatGambitListener listener) {
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
