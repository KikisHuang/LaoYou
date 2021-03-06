package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.ProvinceBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyHomePageListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.JsonUtils.getFileMap;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
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
     * @param name       昵称 ;
     * @param sex        性别;
     * @param sig        个性签名;
     * @param height     身高;
     * @param hometown   家乡;
     * @param birthday   生日;
     * @param love_state 恋爱状态;
     * @param backFile
     * @param address
     */
    public void ChangeInfo(File file, String name, int sex, String sig, String height, String hometown, String birthday, String love_state, File backFile, String address) {
        Map<String, String> map = getKeyMap();
        map.put("name", name);
        map.put("sex", String.valueOf(sex));
        if (hometown != null)
            map.put("hometown", String.valueOf(hometown));
        if (birthday != null)
            map.put("birthday", String.valueOf(birthday));

        map.put("height", String.valueOf(height));

        map.put("autograph", String.valueOf(sig));

        map.put("loveStatus", String.valueOf(love_state));

        map.put("address", String.valueOf(address));

        if (file != null || backFile != null) {
            Map<String, File> f = getFileMap();
            if (file != null)
                f.put("file", file);

            if (backFile != null)
                f.put("background", backFile);

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

    /**
     * 查询实名;
     */
    public void CheckID() {
        Map<String, String> m = getKeyMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST3, Interface.URL + Interface.GETAPPLYQUERY);
    }

    /**
     * 获取详情;
     */
    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.MYINFODETAILS);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                listener.onShowUserInfo(ub);
                break;
            case Fields.REQUEST3:
                CheckStatusBean cb = GsonUtil.GsonToBean(getJsonSring(response), CheckStatusBean.class);
                listener.onCertificaTion(cb.getStatus());
                break;
            case Fields.REQUEST2:

                List<ProvinceBean> list = GsonUtil.jsonToList(getJsonSring(response), ProvinceBean.class);
                listener.onProvinceInfo(list);
                /**
                 * 获取市级数据;
                 */
                Map<String, String> ms = getParamsMap();
                httpUtils.OkHttpsGet(ms, this, Fields.REQUEST4, Interface.URL + Interface.GETCITYBYPAGE);

                break;
            case Fields.REQUEST4:
                List<ProvinceBean> lis = GsonUtil.jsonToList(getJsonSring(response), ProvinceBean.class);
                listener.onCityInfo(lis);
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
