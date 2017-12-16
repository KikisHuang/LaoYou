package laoyou.com.laoyou.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import laoyou.com.laoyou.application.MyApplication;
import okhttp3.Call;

import static laoyou.com.laoyou.dialog.PhotoProgress.LoadingCancle;
import static laoyou.com.laoyou.dialog.PhotoProgress.SettingRate;
import static laoyou.com.laoyou.utils.SynUtils.getNameFromUrl;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/7/7.
 * 下载版本apk通用方法;
 */
public class DownLoadUtils {
    private static final String TAG = getTAG(DownLoadUtils.class);
    private Context context;
    private Handler handler;
    private String path = "";

    public DownLoadUtils(Context context) {
        this.context = context;
        handlerInit();
    }

    private void handlerInit() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        SettingRate(msg.arg1);
//                        Log.i(TAG, "进度 =======" + msg.arg1);
                        break;
                    case 3:
//                        ToastUtil.toast2_bottom(context, "下载完成!");
                        LoadingCancle();
                        break;
                    case 2:
                        try {
                            ToastUtil.toast2_bottom(context, "下载失败!");
                            LoadingCancle();
                        } catch (Exception e) {
                            LoadingCancle();
                            Log.e(TAG, "Error ===" + e);
                        }
                        break;
                }
            }
        };
    }

    public void download(final String url) {

        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wangquan/", getNameFromUrl(url)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                        Log.i(TAG, "下载失败");
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        openFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wangquan/" + getNameFromUrl(url)));
                        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wangquan/" + getNameFromUrl(url);
                        Message msg = new Message();
                        msg.what = 3;
                        handler.sendMessage(msg);
                        Log.i(TAG, "下载成功");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        Message msg = new Message();
                        msg.arg1 = (int) (progress * 100);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                });
//        requestUtils.requestDownFile(new HttpUtil.OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess(String savePath, String nameFromUrl) {
//                openFile(new File(savePath + "/" + nameFromUrl));
//                path = savePath + "/" + nameFromUrl;
//                Message msg = new Message();
//                msg.what = 3;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onDownloading(int progress) {
//                Log.i(TAG, "进度 ==" + progress);
//                Message msg = new Message();
//                msg.arg1 = progress;
//                msg.what = 1;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onDownloadFailed() {
//                Message msg = new Message();
//                msg.what = 2;
//                handler.sendMessage(msg);
//            }
//        }, context, url, Environment.getDataDirectory().getAbsolutePath() + "/yougirl/");
    }

    //打开APK
    public void openFile(File file) {
        try {
            // TODO Auto-generated method stub
            Log.e("OpenFile", file.getName());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                //provider authorities
                Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                //Granting Temporary Permissions to a URI
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            if(MyApplication.crashHandler!=null)
                MyApplication.crashHandler.uncaughtException(new Thread(), e);
        }
    }
}
