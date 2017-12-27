package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/12/27.
 */
public interface ReleaseTopicListener {
    void onFaileds(String msg);

    void onSucceed();

    void onRemove(int pos);

    void AddPhoto();
}
