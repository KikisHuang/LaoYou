package laoyou.com.laoyou.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import laoyou.com.laoyou.listener.LoginOperationListener;

/**
 * Created by lian on 2017/6/9.
 * 友盟登录、分享工具类;
 */
public class tpartyLoginUtils {
    private static final String TAG = "tpartyLoginUtils";

    /**
     * 登录获取授权;
     *
     * @param listener
     * @return
     */
    public static UMAuthListener getUMAuthListener(final LoginOperationListener listener, final String url) {

        UMAuthListener umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //回调成功，即登陆成功后这里返回Map<String, String> map，map里面就是用户的信息，可以拿出来使用了
                if (map != null) {
                    Log.i(TAG, "授权成功   getting data ====" + map.toString());
                    listener.onWechatLoginSucceed(map.get("access_token"), map.get("openid"), url);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                listener.onWechatLoginFailed();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.i(TAG, "授权取消");
            }
        };
        return umAuthListener;
    }

    /**
     * 获取用户信息;
     *
     * @param context
     * @return
     */
    public static UMAuthListener getUMDataListener(final Context context) {

        UMAuthListener umAuthListener = new UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {

                if (data != null) {
                    Log.i(TAG, "getting data ====" + data.toString());
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(context.getApplicationContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.i(TAG, "取消获取用户信息");

            }
        };

        return umAuthListener;
    }

    /**
     * 分享通用回调方法;
     *
     * @param context
     * @return
     */

    public static UMShareListener getUMShareListener(final Context context) {

        UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {

                Log.d("plat", "platform" + platform);
                if (platform.name().equals("WEIXIN_FAVORITE")) {
                    Toast.makeText(context, platform + " 收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, platform + " 分享成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(context, platform + " 分享失败", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
//                Toast.makeText(context, platform + " 分享取消", Toast.LENGTH_SHORT).show();
                Log.i(TAG, platform + "分享取消");
            }
        };
        return umShareListener;
    }
}
