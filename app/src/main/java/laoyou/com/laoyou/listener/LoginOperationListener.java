package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/25.
 */
public interface LoginOperationListener {
    void onWechatLoginSucceed(String accessToken, String openid, String url);

    void onWechatLoginFailed();

    void onFailed(String response);

    void onSucceed();
}
