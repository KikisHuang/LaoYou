package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ImUtils.CreateUserIm;
import static laoyou.com.laoyou.utils.ImUtils.getImIdentifier;
import static laoyou.com.laoyou.utils.ImUtils.getImUserSig;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.validPhoneNumber;
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
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:
                try {
                    String key = getJsonSring(response);
                    SPreferences.saveUserToken(key);
                    //REQUEST2
                    getImIdentifier(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                listener.onSucceed();

                break;
            case Fields.REQUEST2:
                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                SPreferences.saveUserId(ub.getId());
                faceUrl = ub.getHeadImgUrl();
                name = ub.getName();

                if (ub.getCloudTencentAccount() != null && !ub.getCloudTencentAccount().isEmpty()) {
                    Log.i(TAG, "详情获得的id ===" + ub.getCloudTencentAccount());
                    SPreferences.saveIdentifier(ub.getCloudTencentAccount());
                    //REQUEST4
                    getImUserSig(ub.getCloudTencentAccount(), this);
                } else
                    CreateUserIm(this);
                break;
            case Fields.REQUEST4:
                Log.i(TAG, "user sig ===" + response);
                SPreferences.saveUserSig(getJsonSring(response));
                listener.onSucceed();
                break;
            case Fields.REQUEST6:
                String id = getJsonSring(response);
                if (!id.isEmpty()) {
                    SPreferences.saveIdentifier(id);
                    Log.i(TAG, "创建获得的id ===" + id);
                    getImUserSig(id, this);
//                  ImportImInfo(id, name, faceUrl, this);
                }
                break;

            case Fields.REQUEST3:
                Log.i(TAG, "server key === " + getJsonSring(response));
                SPreferences.saveUserToken(getJsonSring(response));
                //REQUEST2
                getImIdentifier(this);
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

        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, url, null, null);
    }


    public void Login(String phone, String pass) {
        if (!phone.isEmpty() && !pass.isEmpty() && validPhoneNumber(phone)) {
            Map<String, String> map = getParamsMap();
            map.put("account", phone);
            map.put("password", pass);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST3, Interface.URL + Interface.LOGIN, null, null);
        } else if (phone.isEmpty())
            listener.onFailed(gets(R.string.phonenullmsg));
        else if (!validPhoneNumber(phone))
            listener.onFailed(gets(R.string.phoneuncorrectmsg));
        else if (pass.isEmpty())
            listener.onFailed(gets(R.string.passnullmsg));

    }
}
