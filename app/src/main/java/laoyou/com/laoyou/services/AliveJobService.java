package laoyou.com.laoyou.services;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;

import laoyou.com.laoyou.activity.MainActivity;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.UserInfo;
import laoyou.com.laoyou.tencent.utils.Foreground;
import laoyou.com.laoyou.tencent.utils.PushUtil;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.SynUtils.LogOut;

/**
 * Created by lian on 2018/1/9.
 */
public class AliveJobService extends JobService implements TIMCallBack {
    private final static String TAG = "KeepAliveService";
    // 告知编译器，这个变量不能被优化
    private volatile static Service mKeepAliveService = null;

    public static boolean isJobServiceAlive() {
        return mKeepAliveService != null;
    }

    private static final int MESSAGE_ID_TASK = 0x01;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (!Foreground.get().isForeground())
                if (SPreferences.getUserSig() != null && SPreferences.getUserSig().isEmpty())
                    rejectInit();
            // 具体任务逻辑

            if (!ActivityCollector.isActivityExist(MainActivity.class)) {
                if (SPreferences.getUserSig() != null && !SPreferences.getUserSig().isEmpty())
                    IMInit();
                Log.d(TAG, "APP被杀死，重启...");
            }
            // 通知系统任务执行结束
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }
    });

    private void rejectInit() {
        //互踢下线逻辑
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                LogOut(SPreferences.context, false);
                Log.d(TAG, "被踢掉线了...");
            }

            @Override
            public void onUserSigExpired() {
                LogOut(SPreferences.context, false);
            }
        });
    }


    private void IMInit() {
        //初始化IMSDK
        InitBusiness.start(SPreferences.context);
        //初始化TLS
        TlsBusiness.init(SPreferences.context);
        //设置刷新监听
        RefreshEvent.getInstance();

//      UserInfo.getInstance().setId("nnmcw");
//      UserInfo.getInstance().setUserSig("eJx1kEFPgzAYhu-8iqZXjaOFiph4AOl0G4iObTFeGgIFvmyrDVTBGP*7C5rIxff6PMmTvJ8WQghv4uwiL4rXN2WE*dASo2uEPZ-i8z*sNZQiN8JpyxET1z6NMMeZWHLQ0EqRV0a2o0WZT0-aRIFSKgMV-ApKHYt*grtyL8bW-5EO6hEmfHu7eIoeqB3N1NweQu4uK2go3dRpu2ycbP8eqWAVgFw-Bnc6ZfWijg9JnOTPpN*lhxnT0c72mlUz79IzLnXG19XA-ZcwCfv77c0kaeD4cwphhBD3yqOX2PqyvgEKZlXE");

        Log.i(TAG, "Identifier == " + SPreferences.getIdentifier() + " UserSig ==" + SPreferences.getUserSig());
        UserInfo.getInstance().setId(SPreferences.getIdentifier());
        UserInfo.getInstance().setUserSig(SPreferences.getUserSig());

        //登录之前要初始化群和好友关系链缓存
        FriendshipEvent.getInstance().init();
        GroupEvent.getInstance().init();

        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        if (Fields.DEBUG)
            Log.d(TAG, "KeepAliveService----->JobService服务被启动...");
        mKeepAliveService = this;
        // 返回false，系统假设这个方法返回时任务已经执行完毕；
        // 返回true，系统假定这个任务正要被执行
        Message msg = Message.obtain(mHandler, MESSAGE_ID_TASK, params);
        mHandler.sendMessage(msg);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mHandler.removeMessages(MESSAGE_ID_TASK);
        if (Fields.DEBUG)
            Log.d(TAG, "KeepAliveService----->JobService服务被关闭");
        return false;
    }

    @Override
    public void onError(int i, String s) {
        Log.i(TAG, "登录失败 Error Code ==" + i);
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, "登录成功");
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
    }
}