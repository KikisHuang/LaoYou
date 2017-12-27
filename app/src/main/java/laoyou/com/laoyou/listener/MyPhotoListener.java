package laoyou.com.laoyou.listener;

import java.io.File;
import java.util.List;

import laoyou.com.laoyou.bean.PhotoBean;

/**
 * Created by lian on 2017/12/8.
 */
public interface MyPhotoListener {
    void onFailedMsg(String msg);

    void onSucceed();

    void onUpLoadFile(File f);

    void onAddPhoto();

    void onItemClick(int pos);

    void onDeletePhoto(String url);

    void onPhotoList(List<PhotoBean> list);

    void DeleteSucceed();
}
