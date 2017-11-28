package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/11/11.
 */
public interface WifiListener {
    void onConnectionFailed(String ssid);

    void onConnectionSucceed(String ssid);

    void onConnected();
    void onUnConnected();
}
