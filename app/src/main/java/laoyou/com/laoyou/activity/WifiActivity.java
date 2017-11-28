package laoyou.com.laoyou.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.WifiAdapter;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.WifiListener;
import laoyou.com.laoyou.presenter.WifiPresenter;
import laoyou.com.laoyou.receiver.WifiReceiver;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;

/**
 * Created by lian on 2017/11/4.
 */
public class WifiActivity extends InitActivity implements View.OnClickListener, WifiListener {

    private static final String TAG = "TestActivity";
    private ListView listView;
    private List<ScanResult> list;

    private WifiManager wifiManager;
    private WifiReceiver receiver;
    private static WifiActivity activity;

    private String ssid = "";
    private FrameLayout mFlContainer;

    private ImageView wifi_icon;
    private TextView wifi_name, wifi_state;
    private Button connetion_bt;

    private WifiPresenter wp;

    @Override
    protected void click() {
        mFlContainer.setOnClickListener(this);
        connetion_bt.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             /*   Show(WifiActivity.this, "正在自动获取Wifi", true, null);
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
                Log.d("Kikis - Test", "reconnect: " + reconnect);*/

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

        listView = f(R.id.listView);

        wifi_icon = f(R.id.wifi_icon);
        wifi_name = f(R.id.wifi_name);
        wifi_state = f(R.id.wifi_state);
        connetion_bt = f(R.id.connetion_bt);

        list = new ArrayList<>();
        registerWiFiReceiver();
        wp = new WifiPresenter(this);

        mFlContainer = f(R.id.mFlContainer);

        LayoutHideOfShow(true);
    }

    private void LayoutHideOfShow(boolean flag) {
        if(flag){
            wifi_icon.setVisibility(View.GONE);
            wifi_name.setVisibility(View.GONE);
            wifi_state.setVisibility(View.GONE);
            connetion_bt.setVisibility(View.GONE);
        }else {
            wifi_icon.setVisibility(View.VISIBLE);
            wifi_name.setVisibility(View.VISIBLE);
            wifi_state.setVisibility(View.VISIBLE);
            connetion_bt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        wp.WifiConnected(this);
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
        WifiListInit();
    }

    private void WifiListInit() {
        list = wifiManager.getScanResults();
        Log.i(TAG, " wifi info Size  ===" + list.size());
        if (list == null) {
            ToastUtil.toast2_bottom(this, "wifi未打开！");
        } else {
            if (list.size() > 0)
                listView.setAdapter(new WifiAdapter(this, list));
            else
                ToastUtil.toast2_bottom(this, "没有搜索到附近Wifi...");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connetion_bt:

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

    @Override
    public void onConnected() {
        LayoutHideOfShow(false);
        connetion_bt.setVisibility(View.GONE);
        Glide.with(WifiActivity.this).load(R.mipmap.wifi_connect_icon).into(wifi_icon);
//        wifi_state.setText(Fields.WIFICONNECTED);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        wifi_name.setText(wifiInfo.getSSID());
    }

    @Override
    public void onUnConnected() {
        LayoutHideOfShow(false);
        wifi_name.setVisibility(View.GONE);
        Glide.with(WifiActivity.this).load(R.mipmap.exclamation_mark).into(wifi_icon);
//        wifi_state.setText(Fields.WIFIUNCONNECTED);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
        if (receiver != null)
            unregisterReceiver(receiver);
    }
}
