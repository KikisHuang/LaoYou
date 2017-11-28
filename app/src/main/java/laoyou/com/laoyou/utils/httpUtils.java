package laoyou.com.laoyou.utils;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;

import laoyou.com.laoyou.bean.FilesBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import okhttp3.Call;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getCode;
import static laoyou.com.laoyou.utils.ToastUtil.ErrorMsg;

/**
 * Created by lian on 2017/10/25.
 * 网络请求业务类;
 */
public class httpUtils {
    private static final String TAG = "httpUtils";

    /**
     * get方法;
     *
     * @param params   请求参数;
     * @param listener 回调监听;
     * @param url      路径;
     * @param reqTag   回调标识符;
     */

    public static synchronized  void OkHttpsGet(Map<String, String> params, final HttpResultListener listener, final int reqTag, String url) {
        GetBuilder get = OkHttpUtils.get();
        get.url(url);
        get.params(params);
        RequestCall build = get.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "服务端异常信息 ====" + e);
                listener.onError(call.request(), e);
                Cancle();
            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    int code = getCode(response);
                    if (code == 1) {
                        Log.i(TAG, "成功返回数据 ====" + response);
                        listener.onSucceed(response, reqTag);
                    } else {
                        Log.i(TAG, "失败返回数据 ====" + response);
                        listener.onFailed(ErrorMsg(response, code), code, reqTag);
                    }
                    Cancle();
                } catch (Exception e) {
                    Log.i(TAG, "解析异常信息 ====" + e);
                    listener.onParseError(e);
                    Cancle();
                }
            }
        });
    }

    /**
     * post方法;
     *
     * @param params   请求参数;
     * @param listener 回调监听;
     * @param files    一个参数对应多文件数组上传;
     * @param url      路径;
     * @param file     单个文件对应单个参数上传;
     */
    public static synchronized void OkHttpsPost(Map<String, String> params, final HttpResultListener listener, final int reqTag, String url, FilesBean files, Map<String, File> file) {
        PostFormBuilder post = OkHttpUtils.post();
        post.url(url);
        post.params(params);
        if (files != null)
            post.files(files.getKey(), files.getMaps());

        if (file != null) {
            for (String key : file.keySet()) {
                post.addFile(key, file.get(key).getName(), file.get(key));
            }
        }
        RequestCall build = post.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "服务端异常信息 ====" + e);
                listener.onError(call.request(), e);
                Cancle();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int code = getCode(response);

                    if (code == 1) {
                        Log.i(TAG, "成功返回数据 ====" + response);
                        listener.onSucceed(response, reqTag);
                    } else {
                        Log.i(TAG, "失败返回数据 ====" + response);
                        listener.onFailed(ErrorMsg(response, code), code, reqTag);
                    }
                    Cancle();
                } catch (Exception e) {
                    Log.i(TAG, "解析异常信息 ====" + e);
                    listener.onParseError(e);
                    Cancle();
                }
            }
        });
    }

    /**
     * 非规范get方法;
     *
     * @param params   请求参数;
     * @param listener 回调监听;
     * @param url      路径;
     * @param reqTag   回调标识符;
     */

    public static void OkHttpsCustomGet(Map<String, String> params, final HttpResultListener listener, final int reqTag, String url) {
        GetBuilder get = OkHttpUtils.get();
        get.url(url);
        get.params(params);
        RequestCall build = get.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailed("", 0, reqTag);
            }

            @Override
            public void onResponse(String response, int id) {
                listener.onSucceed(response, reqTag);
            }
        });
    }


}
