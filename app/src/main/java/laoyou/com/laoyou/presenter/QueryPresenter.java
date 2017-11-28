package laoyou.com.laoyou.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.QueryListener;
import laoyou.com.laoyou.utils.CheckThread;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.CheckThread.ThreadInstance;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/10/25.
 */
public class QueryPresenter implements HttpResultListener {
    private static final String TAG = "QueryPresenter";
    private QueryListener listener;
    private Handler handler;

    private RotateAnimation animation = null;

    public QueryPresenter(QueryListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
        handInit();
//        BannerHideOfShow();
        IsLogin();
    }

    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.MYINFODETAILS);

        CheckID();
    }

    public void CheckID() {
        Map<String, String> m = getKeyMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST3, Interface.URL + Interface.GETAPPLYQUERY);
    }


    public void handInit() {
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1:

                            break;
                        case 11:
                            int time = msg.getData().getInt("time");
                            listener.onTime(time);
                            break;

                    }
                }
            };
        }
    }

    @Override
    public void onSucceed(String response, int tag) {
        switch (tag) {
            case Fields.REQUEST1:

                break;
            case Fields.REQUEST2:
                try {
                    JSONObject ob = getJsonOb(response);
                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                    listener.onDetails(ub);
                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }

                break;
            case Fields.REQUEST4:
                CheckID();
                break;
            case Fields.REQUEST5:

                break;
            case Fields.REQUEST3:
                try {
                    JSONObject ob = getJsonOb(response);
                    CheckStatusBean cb = new Gson().fromJson(String.valueOf(ob), CheckStatusBean.class);
//                    listener.onCheckStatus(cb.getStatus());
                    //	status  审核状态 0未审核，1已通过，-1已拒绝，2密码错误要求复检,3重新上传
                    switch (cb.getStatus()) {
                        case 0:
                            listener.unCheck();
                            QueryPassData();
                            break;
                        case 1:
                            CloseQueryThread();
                            listener.CheckOK(cb.getPassword());
                            break;
                        case -1:
                            CloseQueryThread();
                            listener.Refuse(cb.getFailReasons());
                            break;
                        case 2:
                            QueryPassData();
                            listener.unCheck();
                            break;
                        case 3:
                            QueryPassData();
                            listener.unCheck();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public void CloseQueryThread() {
        if (ThreadInstance() != null)
            ThreadInstance().ClearThread();
        Fields.CHECKFLAG = false;
    }

    /**
     * 启动线程查询审核数据;
     */
    private void QueryPassData() {
        Fields.CHECKFLAG = true;
        if (ThreadInstance() == null) {
            CheckThread check = new CheckThread(handler);
            check.start();
        }
    }


    @Override
    public void onError(Request request, Exception e) {
        listener.onError(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        //未实名认证;
        if (tag == Fields.REQUEST3 && code == 0)
            listener.onCertificaTion();
        else
            listener.onFailed(response);
    }

    public void IsLogin() {
        if (LoginStatusQuery()) {
            getUseDetails();
            listener.IsLogin(true);
        } else
            listener.IsLogin(false);
    }

    public void RegainPassWord() {
        Map<String, String> m = getKeyMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST4, Interface.URL + Interface.FEEDBACKPASSWORDERROR);
    }

    /**
     * 等待条旋转动画
     */
    public void startAnima(ImageView refresh_img) {
        if (animation == null) {
            /** 设置旋转动画 */
            animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2500);//设置动画持续时间
            /** 常用方法 */
            animation.setRepeatCount(9999);//设置重复次数
            //animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
            //animation.setStartOffset(long startOffset);//执行前的等待时间
            refresh_img.setAnimation(animation);
        }
    }

    public void stopAnima(ImageView refresh_img) {
        if (animation != null) {
            refresh_img.clearAnimation();
            animation.cancel();
            animation = null;
        }
    }

    /**
     * 密码长度判断，以防密码过长;
     *
     * @param len
     * @param passwd
     */
    public void checkPassLength(int len, String passwd) {
        if (len > 10)
            listener.onMinPassSize(passwd);
        else
            listener.onNormalPassSize(passwd);
    }

    public void BannerHideOfShow() {
        httpUtils.OkHttpsGet(null, this, Fields.REQUEST5, Interface.URL + Interface.GETSETTING);
    }
}
