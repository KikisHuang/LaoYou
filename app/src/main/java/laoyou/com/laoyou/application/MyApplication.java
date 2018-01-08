package laoyou.com.laoyou.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TemporaryBean;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.utils.Foreground;
import laoyou.com.laoyou.utils.CrashHandler;
import laoyou.com.laoyou.utils.Fields;
import okhttp3.OkHttpClient;

/**
 * Created by lian on 2017/9/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context context;
    public static List<TemporaryBean> temporary = new ArrayList<>();
    public static String CHANNEL;
    //CrashHandler实例
    public static CrashHandler crashHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MultiDex.install(this);
        Foreground.init(this);
        ErrorCrashInit();
        SPreferences.setContext(getApplicationContext());
        OkHttpInit();
        QBX5Init();
        UmengInt();
        initTecentIM();
    }

    /**
     * 全局异常捕获方法;
     */
    private void ErrorCrashInit() {

//        crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }

    private void initTecentIM() {
        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification timOfflinePushNotification) {
                    if (timOfflinePushNotification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息设置为需要提醒;
                        timOfflinePushNotification.doNotify(getApplicationContext(), R.mipmap.logo_icon);
                        //注册推送服务;
                        registerPush();
                    }
                }
            });
        }
        //初始化SDK基本设置;
//        InitBusiness.start(getApplicationContext());

    }

    private void registerPush() {

    }


    /**
     * 友盟初始化;
     */
    private void UmengInt() {

        UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        //微信
        PlatformConfig.setWeixin(Fields.WECHATAPPID, Fields.WECHATAPPSECRET);
        PlatformConfig.setQQZone(Fields.QQAPPID, Fields.QQAPPSECRET);

        CHANNEL = AnalyticsConfig.getChannel(getApplicationContext());
        Log.i(TAG, "CHANNEL ==========" + CHANNEL);
    }

    /**
     * OkHttp初始化;
     */
    private void OkHttpInit() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 腾讯webview X5内核初始化;
     */
    private void QBX5Init() {

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@", "加载内核是否成功:" + b);
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}
