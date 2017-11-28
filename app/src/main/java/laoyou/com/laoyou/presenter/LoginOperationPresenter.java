package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.tpartyLoginUtils.getUMAuthListener;

/**
 * Created by lian on 2017/10/25.
 */
public class LoginOperationPresenter implements HttpResultListener {
    private static final String TAG = "LoginOperationPresenter";
    private LoginOperationListener listener;
    private SHARE_MEDIA platform;

    public LoginOperationPresenter(LoginOperationListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
    }

    @Override
    public void onSucceed(String response, int tag) {
        try {
            String key = getJsonSring(response);
            SPreferences.saveUserToken(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onSucceed();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailed(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "Parse Error ===" + e);
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }

    public void getWeChatInfo(LoginOperationActivity ac, UMShareAPI mShareAPI) {
        platform = SHARE_MEDIA.WEIXIN;
        mShareAPI.isInstall(ac, platform);
        mShareAPI.doOauthVerify(ac, platform, getUMAuthListener(listener, Interface.URL + Interface.WECHATLogin));
    }

    public void WeChatLogin(String accessToken, String openid, String url) {
        //useInvitationCode(邀请码),channelCode(渠道号)
        Map<String, String> map = getParamsMap();
        map.put("accessToken", accessToken);
        map.put("openId", openid);
//      map.put("channelCode", null);
//      map.put("useInvitationCode", null);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, url);
    }
}
