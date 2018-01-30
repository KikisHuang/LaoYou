package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.TIMCallBack;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.MainListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.UserInfo;
import laoyou.com.laoyou.tencent.presentation.business.InitBusiness;
import laoyou.com.laoyou.tencent.presentation.business.LoginBusiness;
import laoyou.com.laoyou.tencent.presentation.event.FriendshipEvent;
import laoyou.com.laoyou.tencent.presentation.event.GroupEvent;
import laoyou.com.laoyou.tencent.presentation.event.RefreshEvent;
import laoyou.com.laoyou.tencent.service.TlsBusiness;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/10/25.
 */
public class MainPresenter implements HttpResultListener {
    private static final String TAG = "MainPresenter";
    private MainListener listener;

    public MainPresenter(MainListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
        listener.onCheckePermission();
        listener.onInitFragment();

    }

    public void CheckLikeGames() {
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(10));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.GETMYGAME);
    }


    public void UpLoadLocation(double latitude, double longitude, String address) {
        Map<String, String> map = getKeyMap();
        map.put("latitude", String.valueOf(latitude));
        map.put("longitude", String.valueOf(longitude));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GPSLOCATION);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST2:

                List<GameBean> games = GsonUtil.jsonToList(getJsonSring(response), GameBean.class);
                if (games != null && games.size() > 0) {

                } else
                    listener.goFirstaddLikeGames();
                break;
        }

        Log.i(TAG, "GPS定位上传成功！");
    }

    @Override
    public void onError(Request request, Exception e) {
        Log.i(TAG, "onError ==" + e);
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "Parse Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        Log.i(TAG, "Failed ==" + response);
    }

    public void IMinit(TIMCallBack call) {
        //初始化IMSDK
        InitBusiness.start(MyApplication.getContext());
        //初始化TLS
        TlsBusiness.init(MyApplication.getContext());
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

        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), call);
    }
}
