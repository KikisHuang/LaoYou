package laoyou.com.laoyou.presenter;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.utils.Interface;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.tpartyLoginUtils.getUMAuthListener;

/**
 * Created by lian on 2017/10/25.
 */
public class LoginOperationPresenter implements HttpResultListener {
    private LoginOperationListener listener;
    private SHARE_MEDIA platform;

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

    public void WechatLogin(LoginOperationActivity ac, UMShareAPI mShareAPI) {
        platform = SHARE_MEDIA.WEIXIN;
        mShareAPI.isInstall(ac, platform);
        mShareAPI.doOauthVerify(ac, platform, getUMAuthListener(listener, Interface.WECHATLogin));
    }
}
