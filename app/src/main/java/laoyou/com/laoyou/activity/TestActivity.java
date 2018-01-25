package laoyou.com.laoyou.activity;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.ThumbnailListener;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener, ThumbnailListener {

    private static final String TAG = "TestActivity";

    private Button bt;

    @Override
    protected void click() {
        bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        bt = f(R.id.bt);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt:
                String name = getCacheFilePath("tempAudio");
                Log.e(TAG, "name ==" + name);
                break;
        }
    }

    private static String pathDiv = "/";
    private static File cacheDir = !isExternalStorageWritable() ? MyApplication.getContext().getFilesDir() : MyApplication.getContext().getExternalCacheDir();

    /**
     * 判断外部存储是否可用
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        Log.e(TAG, "ExternalStorage not mounted");
        return false;
    }

    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath(String fileName) {
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onThumbnailResult(List<TopicTypeBean> list) {

    }
}
