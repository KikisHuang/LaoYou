package laoyou.com.laoyou.presenter;

import android.util.Log;

import java.util.Map;

import laoyou.com.laoyou.listener.ForgetPassListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.FilesUtil.getParamsMap;

/**
 * Created by lian on 2017/10/25.
 */
public class ForgetPassPresenter implements HttpResultListener {
    private static final String TAG = "ForgetPassPresenter";
    private ForgetPassListener listener;

    public ForgetPassPresenter(ForgetPassListener listener) {
        this.listener = listener;
    }

    public void ForgetPassSend(String pass, String code, String phone) {
        if (!pass.isEmpty() && !code.isEmpty()) {
            Map<String, String> map = getParamsMap();
            map.put("newPassword", pass);
            map.put("phone", phone);
            map.put("code", code);

            httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.REPASSWORD);
        } else if (pass.isEmpty())
            listener.onErrorMsg(Fields.PASSNULLMSG);
        else if (code.isEmpty())
            listener.onErrorMsg(Fields.CODENULLMSG);

    }

    @Override
    public void onSucceed(String response, int tag) {
        listener.onSucceed();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onErrorMsg(Fields.NETWORKERROR);
    }

    @Override
    public void onParseError(Exception e) {
//        listener.onFailed(response);
        Log.i(TAG, "解析异常 Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }
}
