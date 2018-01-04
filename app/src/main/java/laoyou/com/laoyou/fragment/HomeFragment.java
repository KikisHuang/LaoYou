package laoyou.com.laoyou.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomeStatusAdapter;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.PositionAddListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.presenter.HomePresenter;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.DownLoadUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.utils.ViewPagerScroller;
import laoyou.com.laoyou.view.RoundAngleImageView;
import laoyou.com.laoyou.view.WrapContentHeightViewPager;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.dialog.PhotoProgress.LoadingCancle;
import static laoyou.com.laoyou.dialog.PhotoProgress.LoadingShow;
import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IPUtils.isWifi;
import static laoyou.com.laoyou.utils.IntentUtils.goFlashChatPage;
import static laoyou.com.laoyou.utils.IntentUtils.goGameInformationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goLocationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goQueryPassPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCirclePage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.Indevelopment;
import static laoyou.com.laoyou.utils.SynUtils.LogOut;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;


/**
 * Created by lian on 2017/4/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListener, PositionAddListener, RecyclerViewOnItemClickListener {
    private static final String TAG = "HomeFragment";

    private WrapContentHeightViewPager mViewPager;
    //flag 0登录,1查询;
    private HomePresenter hp;
    private LinearLayout mLinearLayoutDot;
    public static PositionAddListener polistener;
    private ImageView banner_img, show_hide_img;
    private FrameLayout page_layout, banner_layout;
    private static HomeFragment homeFragment;
    private UserInfoBean info;

    private PageTopBean pb = null;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
//    private SpringView springview;

    private AppBarLayout appbar_layout;

    private LinearLayout dynamic_layout, query_pass_layout, wifi_layout, nearby_wb_layout, player_community_layout, topic_circle_layout;

    private CoordinatorLayout coordinatorlayout;

    private HomeStatusAdapter adapter;

    private TextView flash_more_tv;

    @Override
    protected int initContentView() {
        return R.layout.home_fragment;
    }

    @Override
    protected void click() {
        banner_img.setOnClickListener(this);

        dynamic_layout.setOnClickListener(this);
        query_pass_layout.setOnClickListener(this);
        wifi_layout.setOnClickListener(this);
        nearby_wb_layout.setOnClickListener(this);
        player_community_layout.setOnClickListener(this);
        show_hide_img.setOnClickListener(this);
        topic_circle_layout.setOnClickListener(this);
        flash_more_tv.setOnClickListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    if (!recyclerView.canScrollVertically(1)) {
                        hp.page += 10;
                        hp.getPeopleNearby(false);
                    }

            }
        });

    }


    @Override
    protected void init() {


        homeFragment = this;
        banner_img = f(R.id.banner_img);
        page_layout = f(R.id.page_layout);

        banner_layout = f(R.id.banner_layout);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowWidth(getActivity()) * 2 / 5);
        banner_layout.setLayoutParams(lp);


        mViewPager = f(R.id.vp_main);
        mLinearLayoutDot = f(R.id.ll_main_dot);

        coordinatorlayout = f(R.id.coordinatorlayout);
        topic_circle_layout = f(R.id.topic_circle_layout);

        flash_more_tv = f(R.id.flash_more_tv);
        nearby_wb_layout = f(R.id.nearby_wb_layout);

        polistener = this;

//        springview = f(R.id.springview);
        appbar_layout = f(R.id.appbar_layout);

        show_hide_img = f(R.id.show_hide_img);

        dynamic_layout = f(R.id.dynamic_layout);
        query_pass_layout = f(R.id.query_pass_layout);
        wifi_layout = f(R.id.wifi_layout);
        player_community_layout = f(R.id.player_community_layout);

        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER | Gravity.LEFT;
        mViewPager.setLayoutParams(lp1);
        mViewPager.setClipChildren(false);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒

        recyclerView = f(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //设置ViewPager切换效果，即实现画廊效果
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setPageMargin(0);
        hp = new HomePresenter(this, mViewPager, getActivity().getLayoutInflater(), getActivity().getApplicationContext(), mLinearLayoutDot);
//        SpringUtils.SpringViewInit(springview, getActivity(), hp.springlistener);
        mViewPager.setOffscreenPageLimit(3);
    }


    public static synchronized HomeFragment getHomeInstance() {
        return homeFragment;
    }

    @Override
    protected void initData() {
        hp.Presenter();
        TestData();
    }


    private void TestData() {

        for (int i = 0; i < 3; i++) {

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(getActivity()) * 1 / 3, ViewGroup.LayoutParams.MATCH_PARENT);

            lp.rightMargin = DeviceUtils.dip2px(getActivity(), 10);
            RoundAngleImageView im = new RoundAngleImageView(getActivity());

            im.setLayoutParams(lp);
            Glide.with(getActivity()).load(Fields.Catalina).apply(getGlideOptions()).into(im);
            dynamic_layout.addView(im);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            hp.start();
            polistener = this;
            Log.i(TAG, "onResume");
        } else {
            polistener = null;
            hp.stop();
            Log.i(TAG, "onPauser");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        polistener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        hp.setAppBarLayoutStateChangeListener(appbar_layout);
        Log.i(TAG, "onResume");
        try {
            polistener = this;
            hp.start();
        } catch (Exception e) {
            Log.i(TAG, "Error ==" + e);
        }
    }

    @Override
    public void onPause() {
        hp.removeAppBarLayoutStateChangeListener(appbar_layout);
        super.onPause();
        Log.i(TAG, "onPause");
        try {
            polistener = null;
            hp.stop();
        } catch (Exception e) {
            Log.i(TAG, "Error ==" + e);
        }
    }

    @Override
    public void onClick(View v) {
        if (LoginStatusQuery() || v.getId() == R.id.show_hide_img) {
            switch (v.getId()) {
                case R.id.banner_img:
                    if (pb != null)
                        goOutSidePage(getActivity(), pb.getHttpUrl());
                    break;
                case R.id.dynamic_layout:
                    Indevelopment(getActivity());
                    break;
                case R.id.query_pass_layout:
                    goQueryPassPage(getActivity());
                    break;
                case R.id.wifi_layout:
                    goFlashChatPage(getActivity());
                    break;
                case R.id.nearby_wb_layout:
                    goLocationPage(getActivity(), 0, 0);
                    break;
                case R.id.player_community_layout:
                    goGameInformationPage(getActivity());
                    break;
                case R.id.show_hide_img:
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                    mLayoutManager.setStackFromEnd(true);
                    appbar_layout.setExpanded(true);
                    break;
                case R.id.topic_circle_layout:
                    goTopicCirclePage(getActivity());
                    break;
                case R.id.flash_more_tv:

                    break;
            }
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }
    }


    @Override
    public void onIncrease() {
        hp.current();
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(getActivity(), msg);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.toast2_bottom(getActivity(), msg);
    }

    @Override
    public void onOneImg(PageTopBean ptb) {
        banner_img.setVisibility(View.VISIBLE);
        Glide.with(getActivity()).load(ptb.getImgUrl()).apply(getGlideOptions()).into(banner_img);
        this.pb = ptb;
    }

    @Override
    public void onLogout() {
        LogOut(getActivity());
        ToastUtil.toast2_bottom(getActivity(), "已退出登录");
        hp.Presenter();
    }

    @Override
    public void onOutSideGo(String url) {
        goOutSidePage(getActivity(), url);
    }


    @Override
    public void BannerHide() {
        banner_layout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void BannerShow() {
        hp.getBanner();
    }

    @Override
    public void onForbidSlide() {
        coordinatorlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void RefreshRecyclerView(List<TopicTypeBean> nblist) {

        if (adapter == null) {
            adapter = new HomeStatusAdapter(getActivity(), nblist, this);
            recyclerView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();

    }

    @Override
    public void onEnable(boolean b) {
        if (b)
            showAndHiddenAnimation(show_hide_img, null, AnimationUtil.AnimationState.STATE_SHOW, 500);
        else if (show_hide_img.getVisibility() == View.VISIBLE)
            showAndHiddenAnimation(show_hide_img, null, AnimationUtil.AnimationState.STATE_HIDDEN, 500);

        show_hide_img.setEnabled(b);
//        springview.setEnable(b);
    }

    @Override
    public void onDownload(final String url) {

        LoadingShow(getActivity(), false, "版本升级中请稍后...");
        if (!url.isEmpty()) {
            /**
             * 判断是否Wifi环境;
             */
            if (isWifi(getActivity())) {
                DownLoadUtils du = new DownLoadUtils(getActivity());
                du.download(url);
            } else {
                new MyAlertDialog(getActivity()).builder().setTitle("提示").setCancelable(true).setMsg("检测到您不是Wifi环境,是否还继续下载?").setNegativeButton("下次再说", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadingCancle();
                    }
                }).setPositiveButton("下载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownLoadUtils du = new DownLoadUtils(getActivity());
                        du.download(url);
                    }
                }).show();
            }
        } else {
            Log.i(TAG, "没有获取到新版本下载路径");
            LoadingCancle();
        }
    }

    @Override
    public void onBottom() {

    }

    public void onRefresh() {
        hp.IsLogin();
    }


    @Override
    public void RcOnItemClick(int pos, List<String> imgs) {
        goPhotoViewerPage(getActivity(), imgs, pos, 1);
    }

    @Override
    public void LikeClick(String id) {
        Show(getActivity(), "", true, null);
        hp.LikeChatTheme(id);
    }

    @Override
    public void GoPageHome(String userId) {
        goHomePage(getActivity(), userId, false);
    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {
        goTopicCommentDetailsPage(getActivity(), id, userId, name, content);
    }

    @Override
    public void GoVideoPage(String url, String videoCover) {
        goVideoPlayerPage(getActivity(), url, videoCover);
    }
}
