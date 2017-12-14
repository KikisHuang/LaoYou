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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomeStatusAdapter;
import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.PositionAddListener;
import laoyou.com.laoyou.presenter.HomePresenter;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.utils.ViewPagerScroller;
import laoyou.com.laoyou.view.WrapContentHeightViewPager;

import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.IntentUtils.goFlashChatPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLocationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.IntentUtils.goQueryPassPage;
import static laoyou.com.laoyou.utils.SynUtils.Indevelopment;
import static laoyou.com.laoyou.utils.SynUtils.LogOut;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;


/**
 * Created by lian on 2017/4/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListener, PositionAddListener {
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

    private SpringView springview;

    private AppBarLayout appbar_layout;

    private LinearLayout dynamic_layout, query_pass_layout, wifi_layout, nearby_wb_layout, player_community_layout, topic_circle_layout;

    private CoordinatorLayout coordinatorlayout;

    private HomeStatusAdapter adapter;

    private List<NearbyBean> list;
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
    }


    @Override
    protected void init() {
        homeFragment = this;
        banner_img = f(R.id.banner_img);
        page_layout = f(R.id.page_layout);

        list = new ArrayList<>();

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

        springview = f(R.id.springview);
        appbar_layout = f(R.id.appbar_layout);

        show_hide_img = f(R.id.show_hide_img);

        dynamic_layout = f(R.id.dynamic_layout);
        query_pass_layout = f(R.id.query_pass_layout);
        wifi_layout = f(R.id.wifi_layout);
        player_community_layout = f(R.id.player_community_layout);

        FrameLayout.LayoutParams lp1= new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.CENTER|Gravity.LEFT;
        mViewPager.setLayoutParams(lp1);
        mViewPager.setClipChildren(false);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒

        recyclerView = f(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置ViewPager切换效果，即实现画廊效果
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setPageMargin(0);
        hp = new HomePresenter(this, mViewPager, getActivity().getLayoutInflater(), getActivity().getApplicationContext(), mLinearLayoutDot);
        SpringUtils.SpringViewInit(springview, getActivity(), hp.springlistener);
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
            ImageView im = new ImageView(getActivity());

            im.setLayoutParams(lp);
            Glide.with(getActivity()).load(Fields.Catalina).bitmapTransform(new CenterCrop(getActivity()),new RoundedCornersTransformation(getActivity(), 15, 0, RoundedCornersTransformation.CornerType.ALL)).into(im);
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
        if (LoginStatusQuery()) {
            switch (v.getId()) {
              /*  case R.id.camera_img:
                    if (info != null) {
                        SlidePopupWindow spw = new SlidePopupWindow(getActivity(), info, Status);
                        spw.ScreenPopupWindow();
                    } else
                        SPreferences.saveUserToken("");
                    break;*/
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
                    goLocationPage(getActivity());
                    break;
                case R.id.player_community_layout:
                    Indevelopment(getActivity());
                    break;
                case R.id.show_hide_img:
                    appbar_layout.setExpanded(true);
                    break;
                case R.id.topic_circle_layout:

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
        Glide.with(getActivity()).load(ptb.getImgUrl()).centerCrop().into(banner_img);
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
    public void RefreshRecyclerView(List<NearbyBean> nblist) {
        list = nblist;
        Log.i(TAG, " list size ==" + list.size());
        if (adapter == null) {
            adapter = new HomeStatusAdapter(getActivity(), list);
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
        springview.setEnable(b);
    }

    public void onRefresh() {
        hp.IsLogin();
    }
}
