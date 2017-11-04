package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/26.
 */
public interface SendCodeListener {
    void onError();

    void onSucceed();

    void onFailed(String msg);
}
