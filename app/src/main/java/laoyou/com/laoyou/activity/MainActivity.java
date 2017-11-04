package laoyou.com.laoyou.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.fragment.HomeFragment;
import laoyou.com.laoyou.fragment.MyFragment;
import laoyou.com.laoyou.listener.MainListener;
import laoyou.com.laoyou.presenter.MainPresenter;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.utils.AnimationUtil.ShakeAnima;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainListener, AMapLocationListener {
    private static final String TAG = "MainActivity";

    private LinearLayout home_ll, my_ll;
    private FragmentManager fm = getSupportFragmentManager();
    private HomeFragment homeFragment = null;
    private MyFragment myFragment = null;
    private FragmentTransaction ft;
    private ImageView my_img, home_img;
    private TextView my_tv, home_tv;
    private MainPresenter mp;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void click() {
        home_ll.setOnClickListener(this);
        my_ll.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);
        mp = new MainPresenter(this);
        home_ll = f(R.id.home_ll);
        my_ll = f(R.id.my_ll);
        my_tv = f(R.id.my_tv);
        my_img = f(R.id.my_img);
        home_img = f(R.id.home_img);
        home_tv = f(R.id.home_tv);
        mp.Presenter();
        initLocation();
    }

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
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
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
        ObjectAnimator anima = ShakeAnima(home_img);
        anima.start();
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
        ObjectAnimator anima = ShakeAnima(my_img);
        anima.start();
        if (myFragment == null) {
            myFragment = new MyFragment();
            ft.add(R.id.main_layout, myFragment).show(myFragment);
            Log.i(TAG, "add");
        } else {
            ft.show(myFragment);
            Log.i(TAG, "show");
        }
        ft.commit();
    }

    private void setSelected(LinearLayout ll) {
        if (ll.equals(my_ll)) {
            my_tv.setTextColor(getResources().getColor(R.color.red3));
            home_tv.setTextColor(getResources().getColor(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_img);
            home_img.setImageResource(R.mipmap.home_un_img);
        } else {
            my_tv.setTextColor(getResources().getColor(R.color.gray4));
            my_img.setImageResource(R.mipmap.my_un_img);
            home_tv.setTextColor(getResources().getColor(R.color.red3));
            home_img.setImageResource(R.mipmap.home_img);
        }

        if (homeFragment != null) {
            // 隐藏fragment
            ft.hide(homeFragment);
        }
        if (myFragment != null) {
            ft.hide(myFragment);
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
                setSelected(my_ll);
                My();
                break;
        }
    }

    /**
     * 申请6.0权限
     * 最新版本检查;
     */
    @Override
    public void onCheckePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);//自定义的code
        }
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
                Log.i(TAG, "Longitude  ===" + amapLocation.getLongitude() + " Latitude ===" + amapLocation.getLatitude() + " 地址 ===" + amapLocation.getAddress());
                //可在其中解析amapLocation获取相应内容。
                Fields.Latitud = amapLocation.getLatitude();
                Fields.Longitude = amapLocation.getLongitude();
                Fields.address = amapLocation.getAddress();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
