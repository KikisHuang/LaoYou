package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/25.
 */
public interface ForgetPassListener {
    void onErrorMsg(String msg);

    void onSucceed();

    void onFailed(String msg);
}
