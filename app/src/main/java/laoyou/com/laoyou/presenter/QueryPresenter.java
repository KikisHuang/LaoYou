package laoyou.com.laoyou.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.QueryListener;
import laoyou.com.laoyou.thread.CheckThread;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.thread.CheckThread.ThreadInstance;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
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
        getBanner();
        IsLogin();
    }

    public void getBanner() {
        Map<String, String> map = getParamsMap();
        map.put("showPosition", "200");
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETBANNER);
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
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                List<PageTopBannerBean> list = GsonUtil.jsonToList(getJsonSring(response), PageTopBannerBean.class);
                if (list.size() > 0)
                    listener.ShowBannerInfo(list);

                break;
            case Fields.REQUEST2:
                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                listener.onDetails(ub);

                break;
            case Fields.REQUEST4:
                CheckID();
                break;
            case Fields.REQUEST5:

                break;
            case Fields.REQUEST3:
                Cancle();

                CheckStatusBean cb = GsonUtil.GsonToBean(getJsonSring(response), CheckStatusBean.class);
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
                break;
        }
    }

    public void CloseQueryThread() {
        if (ThreadInstance() != null)
            ThreadInstance().ClearThread();
        animation = null;
        Fields.CHECKFLAG = false;
    }
    public void RemoveHand(){
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
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
        Cancle();
        listener.onError(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Cancle();
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        Cancle();
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
