package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/26.
 */
public interface LoginListener {
    void onClear();

    void onCommit();

    void onSucceed();

    void onFailed(String msg);

    void onError(String msg);

    void onImFailed(String msg);

}
