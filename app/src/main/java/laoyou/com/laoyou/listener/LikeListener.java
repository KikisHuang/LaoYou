package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/12/9.
 */
public interface LikeListener {

    void onSucceed();

    void onFailedMsg(String msg);

    void onDeleteGame(int pos);
}
