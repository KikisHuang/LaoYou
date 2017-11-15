package laoyou.com.laoyou.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.WifiListener;
import laoyou.com.laoyou.receiver.WifiReceiver;
import laoyou.com.laoyou.utils.WifiUtils;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.WifiUtils.createWifiConfig;

/**
 * Created by lian on 2017/11/4.
 */
public class WifiActivity extends InitActivity implements View.OnClickListener, WifiListener {

    private static final String TAG = "TestActivity";
    private Button button;
    private ListView listView;
    private List<ScanResult> list;

    private WifiManager wifiManager;
    private WifiReceiver receiver;
    private static WifiActivity activity;

    private String ssid = "";
    private AnimatorSet mRightOutSet, mLeftInSet;
    private FrameLayout mFlContainer,mFlCardBack,mFlCardFront;

    private boolean mIsShowBack;

    @Override
    protected void click() {
        button.setOnClickListener(this);
        mFlContainer.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Show(WifiActivity.this, "正在自动获取Wifi", true, null);
                //createWifiConfig主要用于构建一个WifiConfiguration，代码中的例子主要用于连接不需要密码的Wifi
                //WifiManager的addNetwork接口，传入WifiConfiguration后，得到对应的NetworkId
                int netId = wifiManager.addNetwork(createWifiConfig(list.get(position).SSID, "11223344", WifiUtils.WIFICIPHER_WPA, wifiManager));

                ssid = list.get(position).SSID;
                //WifiManager的enableNetwork接口，就可以连接到netId对应的wifi了
                //其中boolean参数，主要用于指定是否需要断开其它Wifi网络
                boolean enable = wifiManager.enableNetwork(netId, true);
                Log.d("Kikis - Test", "enable: " + enable);

                //可选操作，让Wifi重新连接最近使用过的接入点
                //如果上文的enableNetwork成功，那么reconnect同样连接netId对应的网络
                //若失败，则连接之前成功过的网络
                boolean reconnect = wifiManager.reconnect();
                Log.d("Kikis - Test", "reconnect: " + reconnect);

            }
        });
    }

    public static synchronized WifiActivity WifiInstance() {
        return activity;
    }

    @Override
    protected void init() {
        setContentView(R.layout.wifi_layout);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        activity = this;
        openWifi();
        button = f(R.id.button);
        listView = f(R.id.listView);
        list = new ArrayList<>();
        registerWiFiReceiver();


        mFlContainer = f(R.id.mFlContainer);
        mFlCardBack = f(R.id.mFlCardBack);
        mFlCardFront = f(R.id.mFlCardFront);
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }

    // 设置动画
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlContainer.setClickable(false);
                button.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setClickable(true);
                button.setClickable(true);
            }
        });
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }
    // 翻转卡片
    public void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            mRightOutSet.setTarget(mFlCardFront);
            mLeftInSet.setTarget(mFlCardBack);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;
        } else { // 背面朝上
            mRightOutSet.setTarget(mFlCardBack);
            mLeftInSet.setTarget(mFlCardFront);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        }
    }

    private void registerWiFiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        receiver = new WifiReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
            /*    list = wifiManager.getScanResults();
                Log.i(TAG, " wifi info Size  ===" + list.size());
                if (list == null) {
                    ToastUtil.toast2_bottom(this, "wifi未打开！");
                } else {
                    if (list.size() > 0)
                        listView.setAdapter(new MyAdapter(this, list));
                    else
                        ToastUtil.toast2_bottom(this, "没有搜索到附近Wifi...");
                }*/
                flipCard();
                break;
            case R.id.mFlContainer:
                flipCard();
                break;
        }
    }

    /**
     * 打开WIFI
     */
    private void openWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

    }

    @Override
    public void onConnectionFailed(String id) {
        Log.i(TAG, " 连接的 ssid === " + ssid + " 广播连接的ssid ===" + id.replace("\"", ""));
        if (ssid.equals(id.replace("\"", ""))) {
            Cancle();
            new MyAlertDialog(this).builder().setCancelable(true).setTitle("提示").setMsg("Wifi连接失败").setNegativeButton("确定", null).show();
            wifiManager.disconnect();
            wifiManager.saveConfiguration();
        }
    }

    @Override
    public void onConnectionSucceed(String id) {
        if (ssid.equals(id.replace("\"", ""))) {
            Cancle();
            new MyAlertDialog(this).builder().setCancelable(true).setTitle("提示").setMsg("Wifi连接成功").setNegativeButton("确定", null).show();
        }
    }

    public class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;
        List<ScanResult> list;

        public MyAdapter(Context context, List<ScanResult> list) {
            // TODO Auto-generated constructor stub
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            view = inflater.inflate(R.layout.wifi_item, null);
            ScanResult scanResult = list.get(position);
            TextView textView = (TextView) view.findViewById(R.id.wifi_name);
            textView.setText(scanResult.SSID);
            TextView signalStrenth = (TextView) view.findViewById(R.id.wifi_level);
            signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));

            return view;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
        if (receiver != null)
            unregisterReceiver(receiver);
    }
}
