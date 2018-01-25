package laoyou.com.laoyou.presenter;

import android.util.Log;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.ChangePassListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/10/25.
 */
public class ChangePassPresenter implements HttpResultListener {
    private static final String TAG = "ChangePassPresenter";
    private ChangePassListener listener;

    public ChangePassPresenter(ChangePassListener listener) {
        this.listener = listener;

    }

    public void Presenter() {

    }

    public void ChangePassWord(String old, String news) {
        if (!old.isEmpty() && !news.isEmpty())
            Commit(old, news);
        else if (old.isEmpty())
            listener.VerifyFailed(gets(R.string.codenullmsg));
        else if (news.isEmpty())
            listener.VerifyFailed(gets(R.string.newpassnullmsg));

    }

    private void Commit(String old, String news) {

        Map<String, String> map = getKeyMap();
        map.put("password", old);
        map.put("newPassword", news);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.MODIFYPASSWORD);
    }

    @Override
    public void onSucceed(String response, int tag) {
        listener.onChangeSucceed();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onErrorMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }
}
