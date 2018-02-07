package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2018/2/7.
 */
public interface AdvertisementListener {

    void onSucceed(String welcomeImgUrl,String welcomeHTTPUrl);

    void onFailedMsg(String msg);

}
