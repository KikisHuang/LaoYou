package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.ProvinceBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyHomePageListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.JsonUtils.getFileMap;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/6.
 */
public class MyHomePagePresenter implements HttpResultListener, OnCompressListener {

    private static final String TAG = "MyHomePagePresenter";
    private MyHomePageListener listener;

    public MyHomePagePresenter(MyHomePageListener listener) {
        this.listener = listener;
        getUseDetails();
        CheckID();
        getRegionInfo();
    }

    /**
     * 修改用户信息;
     *
     * @param file
     */
    public void ChangeInfo(File file, String name, int sex) {
        Map<String, String> map = getKeyMap();
        map.put("name", name);
        map.put("sex", String.valueOf(sex));

        if (file != null) {
            Map<String, File> f = getFileMap();
            f.put("file", file);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST5, Interface.URL + Interface.MODIFYUSER, null, f);
        } else
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST5, Interface.URL + Interface.MODIFYUSER, null, null);
    }

    private void getRegionInfo() {
        /**
         * 获取省级数据;
         */
        Map<String, String> m = getParamsMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST2, Interface.URL + Interface.GETPROVINCEBYPAGE);
    }

    public void CheckID() {
        Map<String, String> m = getKeyMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST3, Interface.URL + Interface.GETAPPLYQUERY);
    }

    /**
     * 获取详情、查询实名;
     */
    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.MYINFODETAILS);
    }

    @Override
    public void onSucceed(String response, int tag) {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    JSONObject ob = getJsonOb(response);
                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                    listener.onShowUserInfo(ub);
                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST3:
                try {

                    JSONObject ob = getJsonOb(response);
                    CheckStatusBean cb = new Gson().fromJson(String.valueOf(ob), CheckStatusBean.class);
                            listener.onCertificaTion(cb.getStatus());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST2:
                try {
                    List<ProvinceBean> list = new ArrayList<>();
                    JSONArray ar = getJsonAr(response);
                    for (int i = 0; i < ar.length(); i++) {
                        ProvinceBean pb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), ProvinceBean.class);
                        list.add(pb);
                    }
                    listener.onProvinceInfo(list);
                    /**
                     * 获取市级数据;
                     */
                    Map<String, String> ms = getParamsMap();
                    httpUtils.OkHttpsGet(ms, this, Fields.REQUEST4, Interface.URL + Interface.GETCITYBYPAGE);
                } catch (Exception e) {
                    Log.e(TAG, "Error ===" + e);
                    e.printStackTrace();
                }

                break;
            case Fields.REQUEST4:
                try {
                    List<ProvinceBean> list = new ArrayList<>();
                    JSONArray ar = getJsonAr(response);
                    for (int i = 0; i < ar.length(); i++) {
                        ProvinceBean pb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), ProvinceBean.class);
                        list.add(pb);
                    }
                    listener.onCityInfo(list);
                } catch (Exception e) {
                    Log.e(TAG, "Error ===" + e);
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST5:
                listener.onSucceed();
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Cancle();
        Log.i(TAG, "解析异常 ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        Cancle();
        //未实名认证;
        if (tag == Fields.REQUEST3 && code == 0)
            listener.onCertificaTion(99);
        else
            listener.onFailedMsg(response);
    }

    public void CompressFile(Context context, List<String> p, int size) {
        Compress(context, p, this, size);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(File file) {
        Cancle();
    listener.onComPressSucceed(file);
    }

    @Override
    public void onError(Throwable e) {
        listener.onFailedMsg(gets(R.string.compress_error));
    }
}
