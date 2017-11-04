package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/25.
 */
public interface ChangePassListener {
    void VerifyFailed(String msg);

    void onChangeSucceed();

    void onErrorMsg(String msg);

    void onFailed(String msg);
}
