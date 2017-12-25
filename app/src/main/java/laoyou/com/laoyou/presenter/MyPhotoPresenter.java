package laoyou.com.laoyou.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
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
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/8.
 */
public class MyPhotoPresenter implements HttpResultListener, OnCompressListener {

    private MyPhotoListener listener;
    public int page = 0;

    public MyPhotoPresenter(MyPhotoListener listener) {
        this.listener = listener;
        getPhotoListData();
    }

    public void getPhotoListData() {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETPHOTOBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) {

        switch (tag) {
            case Fields.REQUEST1:
                try {
                    JSONArray ar = getJsonAr(response);

                    if (ar.length() > 0) {
                        List<PhotoBean> list = new ArrayList<>();
                        for (int i = 0; i < ar.length(); i++) {
                            PhotoBean pb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), PhotoBean.class);
                            list.add(pb);
                        }
                        listener.onPhotoList(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case Fields.REQUEST2:
                page = 0;
                getPhotoListData();
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
        listener.onFailedMsg(gets(R.string.parse_error));
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
}
