package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.PageTopBannerAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.AppBarStateChangeListener;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.listener.VersionListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.VersionUpUtils;
import laoyou.com.laoyou.utils.homeViewPageUtils;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;
import static laoyou.com.laoyou.utils.SynUtils.fileIsExists;
import static laoyou.com.laoyou.utils.SynUtils.getVersionCode;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.saveImage;
import static laoyou.com.laoyou.utils.SynUtils.startPlay;
import static laoyou.com.laoyou.utils.SynUtils.stopPlay;
import static laoyou.com.laoyou.utils.VideoUtils.createVideoThumbnail;

/**
 * Created by lian on 2017/10/25.
 */
public class HomePresenter extends AppBarStateChangeListener implements HttpResultListener, VersionListener {
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
    public int page = 0;
    private boolean RefreshFlag;
    private List<TopicTypeBean> Nblist;
    public SpringListener springlistener;

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
        BannerHideOfShow();
        IsLogin();
//        getPeopleNearby(true);
    }

    /**
     * 获取在意的人;
     *
     * @param flag
     */
    public void getPeopleNearby(boolean flag) {

        RefreshFlag = flag;
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + Fields.SIZE));
        map.put("followFlag", String.valueOf(0));

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST6, Interface.URL + Interface.GETCAREBYPAGE);
    }

    /**
     * 获取详情、查询实名(废弃);
     */
    public void getUseDetails() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.MYINFODETAILS);

//        CheckID();
    }

    /**
     * 获取申请结果
     */
    public void CheckID() {
        Map<String, String> m = getKeyMap();
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST3, Interface.URL + Interface.GETAPPLYQUERY);
    }

    public void getBanner() {
        Map<String, String> map = getParamsMap();
        map.put("showPosition", "0");
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETBANNER);
//        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, "http://fns.mozu123.com:8080/mcFnsInterface/" + Interface.GETBANNER);
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
                    }
                }
            };
        }
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
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
                    } else if (ar.length() > 0) {
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
                    SPreferences.saveMyNickName(ub.getName());
                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
                break;

            case Fields.REQUEST5:
                try {
                    JSONObject ob = getJsonOb(response);
                    int flag = ob.optInt("hideBannerFlag");

                    VersionUpUtils.VersionCheck(ob.optInt("androidVersion"), this);

                    if (flag == 0)
                        listener.BannerShow();
                    else if (flag == 1)
                        listener.BannerHide();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST6:
                //需要封装;
                try {
                    JSONArray ar = getJsonAr(response);
                    if (RefreshFlag)
                        Nblist = new ArrayList<>();

                    if (ar.length() > 0) {
                        for (int i = 0; i < ar.length(); i++) {
                            TopicTypeBean ttb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), TopicTypeBean.class);

                            if (ttb.getReChatMessages() != null) {
                                JSONArray tta = new JSONArray(ttb.getReChatMessages().replace(" ", ""));

                                Gson gson = new Gson();
                                String[][] ss = gson.fromJson(String.valueOf(tta), new TypeToken<String[][]>() {
                                }.getType());
                                List<List<String>> outlist = new ArrayList<>();
                                for (String[] strings : ss) {
                                    List<String> inlist = null;
                                    for (String string : strings) {
                                        if (inlist == null)
                                            inlist = new ArrayList<>();
                                        inlist.add(string);
                                    }
                                    outlist.add(inlist);
                                }
                                ttb.setComments(outlist);
                            }
                            if (ttb.getVideos() != null) {
                                if (fileIsExists(ttb.getVideos()))
                                    ttb.setVideoCover(saveImage(null, ttb.getVideos()));
                                else {
                                    Bitmap bitmap = createVideoThumbnail(ttb.getVideos(), DeviceUtils.getWindowWidth(SPreferences.context), (int) (DeviceUtils.getWindowWidth(SPreferences.context) * 0.8 / 1));
                                    ttb.setVideoCover(saveImage(bitmap, ttb.getVideos()));
                                }
                            }

                            if (ttb.getImgs() != null) {
                                String b[] = ttb.getImgs().split("[,]");
                                if (b != null && b.length > 0) {
                                    List<String> list = new ArrayList<>();
                                    for (String str : b) {
                                        list.add(str);
                                    }
                                    ttb.setPhotos(list);
                                }
                            }
                            Nblist.add(ttb);
                        }

                        listener.RefreshRecyclerView(Nblist);

                    } else if (RefreshFlag)
                        listener.onFailed(gets(R.string.nodata));
                    else if (!RefreshFlag)
                        listener.onBottom();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "解析异常 Error ===" + e);
                }
                break;
            case Fields.REQUEST3:
               /* try {
                    JSONObject ob = getJsonOb(response);
                    CheckStatusBean cb = new Gson().fromJson(String.valueOf(ob), CheckStatusBean.class);
                    listener.onCheckStatus(cb.getStatus());

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                RefreshLikeThme(true);
                break;

            case Fields.REQUEST4:

                listener.onDownload(getJsonSring(response));
                break;
        }
    }


    /**
     * 数据、圆点初始化;
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
        listener.onError(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        //未实名认证;
        /*if (tag == Fields.REQUEST3 && code == 0)
            listener.onCheckStatus(0);
        else*/
        listener.onFailed(response);
    }

    /**
     * 开启广告自动滚动：
     */
    public void start() {
        if (!ONEIMGFLAG)
            startPlay(handler, mViewPager, 0);
    }

    /**
     * 关闭广告自动滚动：
     */
    public void stop() {
        if (!ONEIMGFLAG)
            stopPlay();
    }

    /**
     * 广告标点递增;
     */
    public void current() {
        if (!ONEIMGFLAG)
            currentPosition++;
    }

    /**
     * 登录判断;
     */
    public void IsLogin() {
        if (LoginStatusQuery()) {
            getPeopleNearby(true);
            getUseDetails();
        } else
            listener.onForbidSlide();
    }

    /**
     * 广告隐藏或显示的事件监听;
     */
    public void BannerHideOfShow() {
        httpUtils.OkHttpsGet(null, this, Fields.REQUEST5, Interface.URL + Interface.GETSETTING);
    }
/*
    @Override
    public void IsonRefresh(int init) {
        page = 0;
        getPeopleNearby(true);
    }

    @Override
    public void IsonLoadmore(int move) {
        page += move;
        getPeopleNearby(false);
    }*/

    /**
     * AppBarLayout状态事件监听,设置SpringView的enable，防止滑动事件冲突;
     *
     * @param appbar_layout
     */
    public void setAppBarLayoutStateChangeListener(AppBarLayout appbar_layout) {
        appbar_layout.addOnOffsetChangedListener(this);
    }

    /**
     * 移除监听;
     *
     * @param appbar_layout
     */
    public void removeAppBarLayoutStateChangeListener(AppBarLayout appbar_layout) {
        appbar_layout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onStateChanged(AppBarLayout appBarLayout, State state, int i) {
        switch (state) {
            //展开状态;
            case EXPANDED:
                listener.onEnable(false);
                break;
            //折叠状态;
            case COLLAPSED:
                listener.onEnable(true);
                break;
            //中间状态;
            default:
                listener.onEnable(false);
                break;
        }
    }

    /**
     * 点赞页面刷新;
     *
     * @param flag
     */
    private void RefreshLikeThme(boolean flag) {
        RefreshFlag = flag;
        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(0));
        map.put("pageSize", String.valueOf(page));
        map.put("followFlag", String.valueOf(0));

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST6, Interface.URL + Interface.GETCAREBYPAGE);
    }

    @Override
    public void onVersionUp() {
        Map<String, String> m = getKeyMap();
        m.put("version", String.valueOf(getVersionCode(context)));
        m.put("channelCode", MyApplication.CHANNEL);
        httpUtils.OkHttpsGet(m, this, Fields.REQUEST4, Interface.URL + Interface.GETDOWNLOADPATH);
    }

    @Override
    public void onVersionMatching() {
        Log.i(TAG, gets(R.string.version_matching));
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void LikeChatTheme(String id) {
        Map<String, String> map = getKeyMap();
        map.put("chatThemeId", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST3, Interface.URL + Interface.LIKECHATTHEME);
    }
}
