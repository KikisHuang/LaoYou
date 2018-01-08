package laoyou.com.laoyou.activity;

import android.util.Log;
import android.view.View;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import laoyou.com.laoyou.view.CustomImageView;
import laoyou.com.laoyou.view.NineGridlayout;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener, RecyclerViewOnItemClickListener, HttpResultListener {

    private static final String TAG = "TestActivity";
    private NineGridlayout nine_gd_layout;
    private CustomImageView customImageView;

    @Override
    protected void click() {

    }


    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        nine_gd_layout = f(R.id.nine_gd_layout);
        customImageView = f(R.id.customImageView);
    }

    @Override
    protected void initData() {


        Map<String, String> map = getParamsMap();
        map.put("showPosition", "0");
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETBANNER);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void RcOnItemClick(int pos, List<String> list) {
        Log.i(TAG, "点击的 Postion  ==" + pos);
    }

    @Override
    public void LikeClick(String id) {

    }

    @Override
    public void GoPageHome(String userId) {

    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {

    }

    @Override
    public void GoVideoPage(String url, String videoCover) {

    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {


    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }
}
