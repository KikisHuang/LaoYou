package laoyou.com.laoyou.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.ui.NotifyDialog;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.fragment.HomeFragment;
import laoyou.com.laoyou.listener.MainListener;
import laoyou.com.laoyou.presenter.MainPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.ui.ConversationFragment;
import laoyou.com.laoyou.tencent.ui.SettingFragment;
import laoyou.com.laoyou.tencent.ui.customview.DialogActivity;
import laoyou.com.laoyou.tencent.utils.PushUtil;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.save.SPreferences.saveIdentifier;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.SynUtils.LogOut;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainListener, AMapLocationListener, TIMCallBack {
    private static final String TAG = "MainActivity";

    private LinearLayout home_ll, my_ll, msg_ll;
    private FragmentManager fm = getSupportFragmentManager();
    private HomeFragment homeFragment = null;
    private SettingFragment myFragment = null;
    private ConversationFragment msgFragment = null;
    private FragmentTransaction ft;
    private ImageView my_img, home_img, msg_img;
    private TextView my_tv, home_tv, msg_tv;
    private MainPresenter mp;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static MainActivity activity;
    @Override
    protected void click() {
        home_ll.setOnClickListener(this);
        my_ll.setOnClickListener(this);
        msg_ll.setOnClickListener(this);
    }
    public static MainActivity MainInstance(){
        if(activity==null)
            activity= new MainActivity();
        return activity;
    }
    @Override
    protected void init() {
        clearNotification();
        setContentView(R.layout.activity_main);
        mp = new MainPresenter(this);
        activity = this;
        home_ll = f(R.id.home_ll);
        home_img = f(R.id.home_img);
        home_tv = f(R.id.home_tv);

        msg_ll = f(R.id.msg_ll);
        msg_img = f(R.id.msg_img);
        msg_tv = f(R.id.msg_tv);

        my_ll = f(R.id.my_ll);
        my_tv = f(R.id.my_tv);
        my_img = f(R.id.my_img);
        mp.Presenter();
        initLocation();
    }

    public void IMInit() {
        mp.IMinit(this);
        rejectInit();
    }

    private void rejectInit() {
        //互踢下线逻辑
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                Log.d(TAG, "receive force offline message");
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要重新登录
                new NotifyDialog().show(getString(R.string.tls_expire), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogOut(MainActivity.this);
                    }
                });
            }
        });
    }

  /*  public void logout() {

        TlsBusiness.logout(UserInfo.getInstance().getId());
        UserInfo.getInstance().setId(null);
        MessageEvent.getInstance().clear();
        FriendshipInfo.getInstance().clear();
        GroupInfo.getInstance().clear();
        Intent intent = new Intent(MainActivity.this, LoginOperationActivity.class);
        startActivity(intent);
    }*/

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(60000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void Home() {
//        ObjectAnimator anima = ShakeAnima(home_img);
//        anima.start();
        // 提交事务
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            ft.add(R.id.main_layout, homeFragment).show(homeFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(homeFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void My() {
//        ObjectAnimator anima = ShakeAnima(my_img);
//        anima.start();
        if (myFragment == null) {
            myFragment = new SettingFragment();
            ft.add(R.id.main_layout, myFragment).show(myFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(myFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void Msg() {
//        ObjectAnimator anima = ShakeAnima(msg_img);
//        anima.start();
        if (msgFragment == null) {
            msgFragment = new ConversationFragment();
            ft.add(R.id.main_layout, msgFragment).show(msgFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(msgFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void setSelected(LinearLayout ll) {

        if (ll.equals(my_ll)) {
            my_tv.setTextColor(getRouColors(R.color.blue8));
            home_tv.setTextColor(getRouColors(R.color.gray4));
            msg_tv.setTextColor(getRouColors(R.color.gray4));

            my_img.setImageResource(R.mipmap.my_page_icon);
            home_img.setImageResource(R.mipmap.home_page_unicon);
            msg_img.setImageResource(R.mipmap.msg_page_unicon);

        } else if (ll.equals(home_ll)) {
            my_tv.setTextColor(getRouColors(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_page_unicon);

            home_tv.setTextColor(getRouColors(R.color.blue8));
            home_img.setImageResource(R.mipmap.home_page_icon);

            msg_tv.setTextColor(getRouColors(R.color.gray4));
            msg_img.setImageResource(R.mipmap.msg_page_unicon);
        } else {
            my_tv.setTextColor(getRouColors(R.color.blue8));
            msg_img.setImageResource(R.mipmap.msg_page_icon);

            my_tv.setTextColor(getRouColors(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_page_unicon);

            home_tv.setTextColor(getRouColors(R.color.gray4));
            home_img.setImageResource(R.mipmap.home_page_unicon);
        }

        if (homeFragment != null) {
            // 隐藏fragment
            ft.hide(homeFragment);
        }
        if (myFragment != null) {
            ft.hide(myFragment);
        }
        if (msgFragment != null) {
            ft.hide(msgFragment);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.home_ll:
                setSelected(home_ll);
                Home();
                break;
            case R.id.my_ll:
                if (LoginStatusQuery()) {
                    setSelected(my_ll);
                    My();
                } else {
                    ToastUtil.toast2_bottom(MainActivity.this, "请先登录！");
                    goLoginOperPage(MainActivity.this);
                }
                break;
            case R.id.msg_ll:
                if (LoginStatusQuery()) {
                    setSelected(msg_ll);
                    Msg();
                } else {
                    ToastUtil.toast2_bottom(MainActivity.this, "请先登录！");
                    goLoginOperPage(MainActivity.this);
                }
                break;
        }
    }

    /**
     * 申请6.0权限
     * 最新版本检查;
     */
    @Override
    public void onCheckePermission() {
        final List<String> permissionsList = new ArrayList<>();
//        if (ConnectionResult.SUCCESS != GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this))
//            Log.i(TAG,getString(R.string.google_service_not_available));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionsList.size() == 0) {
//                init();
            } else
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_PHONE_PERMISSIONS);

        }
//            init();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET1 || resultCode == Fields.ACRESULET3)
            if (getHomeInstance() != null)
                getHomeInstance().onRefresh();
    }

    @Override
    public void onInitFragment() {
        ft = fm.beginTransaction();
        setSelected(home_ll);
        Home();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                if (LoginStatusQuery()) {
                    Log.i(TAG, "Longitude  ===" + amapLocation.getLongitude() + " Latitude ===" + amapLocation.getLatitude() + " 地址 ===" + amapLocation.getAddress());
                    //可在其中解析amapLocation获取相应内容。
                    SPreferences.saveLatitud(String.valueOf(amapLocation.getLatitude()));
                    SPreferences.saveLongitude(String.valueOf(amapLocation.getLongitude()));
                    SPreferences.saveAddress(amapLocation.getAddress());

                    mp.UpLoadLocation(amapLocation.getLatitude(), amapLocation.getLongitude(), amapLocation.getAddress());
                }

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 清楚所有通知栏通知
     */

    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        MiPushClient.clearNotification(getApplicationContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this, getString(R.string.need_permission), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "login error : code " + i + " " + s);
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogOut(MainActivity.this);
                    }
                });
                break;
            case 6200:
                SPreferences.saveUserToken("");
                Toast.makeText(this, getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.i(TAG, "Error Code ==" + i);
                SPreferences.saveUserToken("");
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSuccess() {

        saveIdentifier("nnmcw");
        Log.i(TAG, "登录成功");
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = android.os.Build.MANUFACTURER;
   /*     //注册小米和华为推送
        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
        } else if (deviceMan.equals("HUAWEI")) {
            PushManager.requestToken(this);
        }*/

        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };

        Logger.setLogger(this, newLogger);
        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
