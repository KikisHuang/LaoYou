package laoyou.com.laoyou.presenter;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MyListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/1.
 */
public class MyPresenter implements HttpResultListener {
    private static final String TAG = "MyPresenter";
    private MyListener listener;

    public MyPresenter(MyListener listener) {
        this.listener = listener;
        getUseDetails();
    }


    /**
     * 获取详情、查询实名;
     */
    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.MYINFODETAILS, null, null);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                SPreferences.saveMyNickName(ub.getName());
                listener.ongetDetails(ub);
                break;

        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onErrorMsg(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onErrorMsg(response);
    }
}
