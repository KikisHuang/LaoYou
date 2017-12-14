package laoyou.com.laoyou.listener;

import java.io.File;

/**
 * Created by lian on 2017/12/8.
 */
public interface MyPhotoListener {
    void onFailedMsg(String msg);

    void onSucceed();

    void onTest(File f);

    void onAddPhoto();

    void onItemClick(int pos);

    void onDeletePhoto(String url);
}
