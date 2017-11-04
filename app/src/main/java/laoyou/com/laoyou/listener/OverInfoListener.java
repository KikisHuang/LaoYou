package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/28.
 */
public interface OverInfoListener {
    void onSucced();

    void onFailed(String msg);

    void onClear();

    void onCommit();

    void onErrorMsg(String msg);

    void setHeadImgAndName(String imgPath, String name);
}
