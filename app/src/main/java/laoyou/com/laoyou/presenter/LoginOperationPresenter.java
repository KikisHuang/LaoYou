package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import com.tencent.qcloud.sdk.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ImUtils.CreateUserIm;
import static laoyou.com.laoyou.utils.ImUtils.getImIdentifier;
import static laoyou.com.laoyou.utils.ImUtils.getImUserSig;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
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
    private String faceUrl = "";
    private String name = "";

    public LoginOperationPresenter(LoginOperationListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
    }

    @Override
    public void onSucceed(String response, int tag) {

        switch (tag) {
            case Fields.REQUEST1:
                try {
                    String key = getJsonSring(response);
                    SPreferences.saveUserToken(key);
                    getImIdentifier(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                listener.onSucceed();

                break;
            case Fields.REQUEST2:
                try {
                    JSONObject ob = getJsonOb(response);
                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                    faceUrl = ub.getHeadImgUrl();
                    name = ub.getName();

                    if (ub.getCloudTencentAccount() != null && !ub.getCloudTencentAccount().isEmpty()) {
                        Log.i(TAG, "详情获得的id ===" + ub.getCloudTencentAccount());
                        SPreferences.saveIdentifier(ub.getCloudTencentAccount());
                        getImUserSig(ub.getCloudTencentAccount(), this);
                    } else
                        CreateUserIm(this);

                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST4:
                Log.i(TAG, "user sig ===" + response);
                try {
                    SPreferences.saveUserSig(getJsonSring(response));
                    listener.onSucceed();
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onImFailed(gets(R.string.getuser_sig_error));
                }
                break;
            case Fields.REQUEST6:
                try {
                    String id = getJsonSring(response);
                    if (!id.isEmpty()) {
                        SPreferences.saveIdentifier(id);
                        Log.i(TAG, "创建获得的id ===" + id);
                        getImUserSig(id, this);
//                        ImportImInfo(id, name, faceUrl, this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

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

    /**
     * 微信登录;
     *
     * @param accessToken
     * @param openid
     * @param url
     */
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
