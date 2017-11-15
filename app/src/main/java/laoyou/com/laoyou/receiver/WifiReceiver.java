package laoyou.com.laoyou.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import static laoyou.com.laoyou.activity.WifiActivity.WifiInstance;


/**
 * Created by lian on 2017/11/10.
 */
public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = "WifiReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG + " ==== ", intent.getAction());
        //wifi状态，是否连上，密码
        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
            Log.i(TAG, " wifi状态，是否连上，密码");
        //是不是正在获得IP地址
        if (intent.getAction().equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)) {

            Log.i(TAG, " 正在获得IP地址");
            int linkWifiResult = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 123);
            if (linkWifiResult == WifiManager.ERROR_AUTHENTICATING) {
                Log.i(TAG, "密码错误");
                WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();
                Log.i(TAG, "Wifi自动连接的id  ==" + info.getSSID());
                if (WifiInstance() != null)
                    WifiInstance().onConnectionFailed(info.getSSID());
            }
        }

    //连上与否
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo.isConnected()) {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo()
                        .getSSID();
                Log.i(TAG, " 成功连接");
                WifiInstance().onConnectionSucceed(wifiSSID);
            }
        }
    }
}
