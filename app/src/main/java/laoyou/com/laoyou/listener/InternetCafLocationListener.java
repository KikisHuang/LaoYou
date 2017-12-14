package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/12/14.
 */
public interface InternetCafLocationListener {

    void onSucceed();

    void onFailesMsg(String msg);

    void onInternetCafDataList();
}
