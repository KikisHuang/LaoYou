package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/30.
 */
public interface CertificationListener {
    void onCheckFailed(String ms);

    void onSucceed();

    void onFailed(String msg);

    void onError(String msg);
    void setIP(String ip);
}
