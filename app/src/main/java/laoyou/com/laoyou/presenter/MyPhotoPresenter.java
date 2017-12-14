package laoyou.com.laoyou.presenter;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyPhotoListener;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/8.
 */
public class MyPhotoPresenter implements HttpResultListener, OnCompressListener {

    private MyPhotoListener listener;

    public MyPhotoPresenter(MyPhotoListener listener) {
        this.listener = listener;
        getPhotoListData();
    }

    private void getPhotoListData() {
    }

    @Override
    public void onSucceed(String response, int tag) {

    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    @Override
    public void onStart() {

    }

    /**
     * 压缩成功回调;
     *
     * @param file
     */
    @Override
    public void onSuccess(File file) {
        listener.onTest(file);
    }

    @Override
    public void onError(Throwable e) {
        listener.onFailedMsg(gets(R.string.compress_error));
    }

    public void ComPressFile(Context context, ArrayList<String> p, int i) {
        Compress(context, p, this, i);
    }
}
