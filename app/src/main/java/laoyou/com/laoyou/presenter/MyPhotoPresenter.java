package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.FilesBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyPhotoListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/8.
 */
public class MyPhotoPresenter implements HttpResultListener, OnCompressListener {

    private static final String TAG = "MyPhotoPresenter";
    private MyPhotoListener listener;
    public int page = 0;

    public MyPhotoPresenter(MyPhotoListener listener) {
        this.listener = listener;
    }

    public void getPhotoListData(String id) {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        if (!id.isEmpty())
            map.put("toUserId", String.valueOf(id));

        map.put("pageSize", String.valueOf(page + 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETPHOTOBYPAGE);
    }

    public void RefreshPhotoListData(String id) {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(0));
        if (!id.isEmpty())
            map.put("toUserId", String.valueOf(id));

        map.put("pageSize", String.valueOf(page));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETPHOTOBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:

                List<PhotoBean> ar = GsonUtil.jsonToList(getJsonSring(response), PhotoBean.class);
                if (ar.size() > 0)
                    listener.onPhotoList(ar);

                break;
            case Fields.REQUEST2:
                page = 0;
                getPhotoListData("");
                break;

            case Fields.REQUEST3:
                listener.DeleteSucceed();
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
        Cancle();
    }

    @Override
    public void onStart() {

    }

    public void AddPhoto(List<File> files) {
        FilesBean fb = new FilesBean();
        HashMap<String, File> m = new HashMap<>();
        for (File f : files) {
            m.put(f.getName(), f);
        }
        fb.setKey("files");
        fb.setMaps(m);

        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.PHOTO, fb, null);
    }

    /**
     * 压缩成功回调;
     *
     * @param file
     */
    @Override
    public void onSuccess(File file) {
        listener.onUpLoadFile(file);
    }

    @Override
    public void onError(Throwable e) {
        listener.onFailedMsg(gets(R.string.compress_error));
    }

    public void ComPressFile(Context context, ArrayList<String> p, int i) {
        Compress(context, p, this, i);
    }

    public void DeletePhoto(String s) {

        Map<String, String> map = getKeyMap();
        map.put("id", s);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST3, Interface.URL + Interface.DELETEPHOTO);
    }
}
