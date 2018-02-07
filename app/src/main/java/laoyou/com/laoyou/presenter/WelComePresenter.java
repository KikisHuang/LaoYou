package laoyou.com.laoyou.presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.DeviceUtils.getIMEI;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;

/**
 * Created by lian on 2018/2/7.
 */
public class WelComePresenter implements HttpResultListener {

    private static final String TAG = "WelComePresenter";

    public WelComePresenter() {
        UpLoadingDvInfo();
    }

    private void UpLoadingDvInfo() {
        String imei = getIMEI();
        String dvname = android.os.Build.BRAND + "  " + android.os.Build.MODEL;

        Map<String, String> map = getParamsMap();
        map.put("deviceNumber", imei);
        map.put("deviceName", dvname);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.INSTALL);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        Log.i(TAG, "设备号提交成功");
    }

    @Override
    public void onError(Request request, Exception e) {
        Log.e(TAG, "设备号提交异常 == " + e);
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "设备号提交解析异常 == " + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        Log.i(TAG, "设备号提交失败  状态码 == " + code+" resp == "+response);
    }
}
