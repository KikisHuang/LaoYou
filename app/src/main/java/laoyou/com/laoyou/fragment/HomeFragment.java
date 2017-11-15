package laoyou.com.laoyou.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.NearbyAdapter;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.PositionAddListener;
import laoyou.com.laoyou.presenter.HomePresenter;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.utils.ViewPagerScroller;
import laoyou.com.laoyou.view.SlidePopupWindow;

import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.IntentUtils.goQueryPassPage;
import static laoyou.com.laoyou.utils.IntentUtils.goWifiPage;
import static laoyou.com.laoyou.utils.SynUtils.Indevelopment;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;


/**
 * Created by lian on 2017/4/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListener, PositionAddListener {
    private static final String TAG = "HomeFragment";

    private ViewPager mViewPager;
    //flag 0登录,1查询;
    private int flag = 0;
    private HomePresenter hp;
    private LinearLayout mLinearLayoutDot;
    public static PositionAddListener polistener;
    private ImageView banner_img;
    private FrameLayout page_layout, banner_layout;
    private static HomeFragment homeFragment;
    private String name = "";
    private String HeadImg = "";
    private int Status = 100;
    private PageTopBean pb = null;

    private RecyclerView recyclerView;

    private List<String> list;

    private CircleImageView circleimg1, circleimg2, circleimg3, circleimg4, circleimg5, circleimg6, camera_img;

    private LinearLayout dynamic_layout, query_pass_layout, wifi_layout, nearby_wb_layout, player_community_layout;

    private CoordinatorLayout coordinatorlayout;

    private NearbyAdapter adapter;

    @Override
    protected int initContentView() {
        return R.layout.home_fragment;
    }

    @Override
    protected void click() {
        camera_img.setOnClickListener(this);
        banner_img.setOnClickListener(this);

        dynamic_layout.setOnClickListener(this);
        query_pass_layout.setOnClickListener(this);
        wifi_layout.setOnClickListener(this);
        nearby_wb_layout.setOnClickListener(this);
        player_community_layout.setOnClickListener(this);

    }


    @Override
    protected void init() {
        homeFragment = this;
        banner_img = f(R.id.banner_img);
        camera_img = f(R.id.camera_img);
        page_layout = f(R.id.page_layout);

        banner_layout = f(R.id.banner_layout);

        mViewPager = f(R.id.vp_main);
        mLinearLayoutDot = f(R.id.ll_main_dot);

        coordinatorlayout = f(R.id.coordinatorlayout);

        polistener = this;
        circleimg1 = f(R.id.circle_img1);
        circleimg2 = f(R.id.circle_img2);
        circleimg3 = f(R.id.circle_img3);
        circleimg4 = f(R.id.circle_img4);
        circleimg5 = f(R.id.circle_img5);
        circleimg6 = f(R.id.circle_img6);

        dynamic_layout = f(R.id.dynamic_layout);
        query_pass_layout = f(R.id.query_pass_layout);
        wifi_layout = f(R.id.wifi_layout);
        nearby_wb_layout = f(R.id.nearby_wb_layout);
        player_community_layout = f(R.id.player_community_layout);

//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getWindowWidth(getActivity()) / 2.8));

//        page_layout.setLayoutParams(lp);
//        banner_img.setLayoutParams(lp);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒

        recyclerView = f(R.id.recyclerView);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        test();
        hp = new HomePresenter(this, mViewPager, getActivity().getLayoutInflater(), getActivity().getApplicationContext(), mLinearLayoutDot);
    }

    private void test() {
        for (int i = 0; i < 50; i++) {
            list.add("TAG " + i);
        }
        adapter = new NearbyAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
    }

    public static synchronized HomeFragment getHomeInstance() {
        return homeFragment;
    }

    @Override
    protected void initData() {
        hp.Presenter();
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
                case R.id.camera_img:
                    SlidePopupWindow spw = new SlidePopupWindow(getActivity(), HeadImg, name, Status);
                    spw.ScreenPopupWindow();
                    break;
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
                    goWifiPage(getActivity());
                    break;
                case R.id.nearby_wb_layout:
                    Indevelopment(getActivity());
                    break;
                case R.id.player_community_layout:
                    Indevelopment(getActivity());
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
        Glide.with(getActivity()).load(ptb.getImgUrl()).thumbnail(0.2f).centerCrop().into(banner_img);
        this.pb = ptb;
    }

    @Override
    public void onDetails(UserInfoBean ub) {
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(camera_img);

        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg1);
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg2);
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg3);
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg4);
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg5);
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).thumbnail(0.2f).into(circleimg6);

        HeadImg = ub.getHeadImgUrl();
        name = ub.getName();
    }


    @Override
    public void IsLogin(boolean b) {

        if (b) {
            flag = 1;
            camera_img.setVisibility(View.VISIBLE);
        } else {
            flag = 0;
            camera_img.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(R.mipmap.test_icon).into(camera_img);
        }
    }


    @Override
    public void onCheckStatus(int status) {
        Status = status;
    }

    @Override
    public void onLogout() {
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

    public void onRefresh() {
        hp.IsLogin();
    }
}
