package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.PageTopBannerAdapter;
import laoyou.com.laoyou.bean.CheckStatusBean;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.CheckThread;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.homeViewPageUtils;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.CheckThread.ThreadInstance;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;
import static laoyou.com.laoyou.utils.SynUtils.startPlay;
import static laoyou.com.laoyou.utils.SynUtils.stopPlay;

/**
 * Created by lian on 2017/10/25.
 */
public class HomePresenter implements HttpResultListener {
    private static final String TAG = "HomePresenter";
    private HomeListener listener;
    private Handler handler;
    //自动切换position;
    private int currentPosition = 1;
    private ViewPager mViewPager;
    //顶部数据集合;
    private List<PageTopBannerBean> toplist;
    //顶部viewPagerImgList;
    private List<PageTopBannerBean> mImageViewList;
    //顶部viewPager圆点ImgList;
    private List<ImageView> mImageViewDotList;
    private LayoutInflater inflater;
    private Context context;
    private int dotPosition = 0;
    private int prePosition = 0;
    private LinearLayout mLinearLayoutDot;
    private boolean ONEIMGFLAG = false;
    private RotateAnimation animation = null;

    public HomePresenter(HomeListener listener, ViewPager mViewPager, LayoutInflater inflater, Context context, LinearLayout mLinearLayoutDot) {
        this.listener = listener;
        this.mViewPager = mViewPager;
        this.mLinearLayoutDot = mLinearLayoutDot;
        toplist = new ArrayList<>();
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList<>();
        this.inflater = inflater;
        this.context = context;
    }

    public void Presenter() {
        handInit();
        getBanner();
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

    public void getBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("showPosition", "0");
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETBANNER);
    }

    public void handInit() {
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1:
                            mViewPager.setCurrentItem(currentPosition);
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
                try {
                    JSONArray ar = getJsonAr(response);
                    if (ar.length() > 1) {
                        toplist.clear();
                        for (int i = 0; i < ar.length(); i++) {
                            PageTopBannerBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), PageTopBannerBean.class);
                            toplist.add(pb);
                        }

                        initPageData();
                        setViewPager();
                        startPlay(handler, mViewPager, 0);
                    } else {
                        ONEIMGFLAG = true;
                        PageTopBean ptb = new Gson().fromJson(String.valueOf(ar.optJSONObject(0)), PageTopBean.class);
                        listener.onOneImg(ptb);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            case Fields.REQUEST3:
                try {
                    JSONObject ob = getJsonOb(response);
                    CheckStatusBean cb = new Gson().fromJson(String.valueOf(ob), CheckStatusBean.class);
                    listener.onCheckStatus(cb.getStatus());
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

    private void CloseQueryThread() {
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

    /**
     * 圆点初始化;
     */
    private void initPageData() {
        mImageViewList.clear();
        mImageViewDotList.clear();
        mImageViewList = homeViewPageUtils.getTopImg(toplist, context, mImageViewList, inflater);
        homeViewPageUtils.setDot(toplist.size(), context, mImageViewDotList, mLinearLayoutDot, dotPosition);
    }

    /**
     * 设置viewPager适配器;
     */
    private void setViewPager() {
        /**
         *top;
         */
        PageTopBannerAdapter adapter = new PageTopBannerAdapter(mImageViewList, 0, listener);

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Fields.TouchTime = System.currentTimeMillis();
                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP:
                        startPlay(handler, mViewPager, 0);
                        break;
                }
                return false;
            }
        });
        //页面改变监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为images.length,即倒数第二个位置，小圆点位置为length-1
                    currentPosition = toplist.size();
                    dotPosition = toplist.size() - 1;
                } else if (position == toplist.size() + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                    currentPosition = 1;
                    dotPosition = 0;
                } else {
                    currentPosition = position;
                    dotPosition = position - 1;
                }
                //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
                mImageViewDotList.get(prePosition).setBackgroundResource(R.drawable.dot_corners_false);
                mImageViewDotList.get(dotPosition).setBackgroundResource(R.drawable.dot_corners_true);
                prePosition = dotPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPager.setCurrentItem(currentPosition, false);
                }
            }
        });
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onError(Fields.NETWORKERROR);
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        //未实名认证;
        if (tag == Fields.REQUEST3)
            listener.onCertificaTion();
        else
            listener.onFailed(response);
    }

    public void start() {
        if (!ONEIMGFLAG)
            startPlay(handler, mViewPager, 0);
    }

    public void stop() {
        if (!ONEIMGFLAG)
            stopPlay();
    }

    public void current() {
        if (!ONEIMGFLAG)
            currentPosition++;
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

    public void startAnima(ImageView refresh_img) {
        if (animation == null) {
            /** 设置旋转动画 */
            animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2000);//设置动画持续时间
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
}
