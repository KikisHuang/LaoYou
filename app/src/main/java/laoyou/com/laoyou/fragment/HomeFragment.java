package laoyou.com.laoyou.fragment;

import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomeStatusAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.ActiveBean;
import laoyou.com.laoyou.bean.AddressBookBean;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.PositionAddListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.presenter.HomePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.tencent.ui.ChatActivity;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.DownLoadUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.utils.ViewPagerScroller;
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
import static laoyou.com.laoyou.utils.SynUtils.LogOut;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;


/**
 * Created by lian on 2017/4/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListener, SwipeRefreshLayout.OnRefreshListener, TIMCallBack, PositionAddListener, RecyclerViewOnItemClickListener {
    private static final String TAG = "HomeFragment";

    private WrapContentHeightViewPager mViewPager;
    //flag 0登录,1查询;
    private HomePresenter hp;
    private LinearLayout mLinearLayoutDot;
    public static PositionAddListener polistener;
    private ImageView banner_img, show_hide_img;
    private FrameLayout page_layout, banner_layout;
    private static HomeFragment homeFragment;
    private PageTopBean pb = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private LinearLayout dynamic_layout, query_pass_layout, wifi_layout, nearby_wb_layout, player_community_layout, topic_circle_layout;

    private HomeStatusAdapter adapter;
    private TextView flash_more_tv, recom_nick_name;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LinearLayout foot_layout, flash_title_layout, foot_recom_layout, recom_layout, foot_tv;
    private FrameLayout head_layout;
    private String groupId = "";
    private TIMCallBack back;
    private HorizontalScrollView dynamic_scroll;
    private List<TopicTypeBean> Nblist;
    private int recomeSize = 0;
    private SwipeRefreshLayout swiperefreshlayout;

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && LoginStatusQuery()) {
                    if (recyclerView.canScrollVertically(-1) && (Fields.VPT == 0 || System.currentTimeMillis() - Fields.VPT >= 2000)) {
                        Fields.VPT = System.currentTimeMillis();
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int totalItemCount = layoutManager.getItemCount();
                        hp.page = totalItemCount - 1;
                        hp.getPeopleNearby(false);
                    }
                }
                if (Nblist.size() > 0) {
                    if (!recyclerView.canScrollVertically(-1) && show_hide_img.getVisibility() == View.VISIBLE)
                        showAndHiddenAnimation(show_hide_img, null, AnimationUtil.AnimationState.STATE_HIDDEN, 500);
                    else if (recyclerView.canScrollVertically(-1) && show_hide_img.getVisibility() != View.VISIBLE)
                        showAndHiddenAnimation(show_hide_img, null, AnimationUtil.AnimationState.STATE_SHOW, 500);
                }

            }
        });

    }

/*    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }*/

    @Override
    protected void init() {


        homeFragment = this;
        back = this;
        Nblist = new ArrayList<>();

        show_hide_img = f(R.id.show_hide_img);
        recyclerView = f(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        swiperefreshlayout = f(R.id.swiperefreshlayout);
        RefreshInit();
        foot_layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment_foot_include, null);
        head_layout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_head_include, null);
        head_layout.setLayoutParams((ViewGroup.LayoutParams) getLayout(0, Fields.MATCH, Fields.WRAP));

        banner_img = (ImageView) head_layout.findViewById(R.id.banner_img);
        page_layout = (FrameLayout) head_layout.findViewById(R.id.page_layout);
        flash_title_layout = (LinearLayout) head_layout.findViewById(R.id.flash_title_layout);
        dynamic_scroll = (HorizontalScrollView) head_layout.findViewById(R.id.dynamic_scroll);
        banner_layout = (FrameLayout) head_layout.findViewById(R.id.banner_layout);


        foot_layout.setVisibility(View.GONE);

        recom_nick_name = (TextView) foot_layout.findViewById(R.id.recom_nick_name);
        foot_tv = (LinearLayout) foot_layout.findViewById(R.id.foot_bottom_layout);
        recom_layout = (LinearLayout) foot_layout.findViewById(R.id.recom_layout);
        foot_recom_layout = (LinearLayout) foot_layout.findViewById(R.id.Foot_recom_layout);

        banner_layout.setLayoutParams((ViewGroup.LayoutParams) getLayout(0, Fields.MATCH, DeviceUtils.getWindowWidth(getActivity()) * 2 / 5));

        mViewPager = (WrapContentHeightViewPager) head_layout.findViewById(R.id.vp_main);
        mLinearLayoutDot = (LinearLayout) head_layout.findViewById(R.id.ll_main_dot);

        topic_circle_layout = (LinearLayout) head_layout.findViewById(R.id.topic_circle_layout);

        flash_more_tv = (TextView) head_layout.findViewById(R.id.flash_more_tv);
        nearby_wb_layout = (LinearLayout) head_layout.findViewById(R.id.nearby_wb_layout);

        polistener = this;

        dynamic_layout = (LinearLayout) head_layout.findViewById(R.id.dynamic_layout);
        query_pass_layout = (LinearLayout) head_layout.findViewById(R.id.query_pass_layout);
        wifi_layout = (LinearLayout) head_layout.findViewById(R.id.wifi_layout);
        player_community_layout = (LinearLayout) head_layout.findViewById(R.id.player_community_layout);

        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) getLayout(1, Fields.WRAP, Fields.WRAP);
        lp1.gravity = Gravity.CENTER | Gravity.LEFT;
        mViewPager.setLayoutParams(lp1);
        mViewPager.setClipChildren(false);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒


        mViewPager.setPageMargin(0);
        hp = new HomePresenter(this, mViewPager, getActivity().getLayoutInflater(), getActivity().getApplicationContext(), mLinearLayoutDot);
        mViewPager.setOffscreenPageLimit(2);

        adapter = new HomeStatusAdapter(getActivity(), Nblist, this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        foot_layout.setLayoutParams((ViewGroup.LayoutParams) getLayout(0, Fields.MATCH, 0));
        mHeaderAndFooterWrapper.addHeaderView(head_layout);
        mHeaderAndFooterWrapper.addFootView(foot_layout);
        recyclerView.setAdapter(mHeaderAndFooterWrapper);

    }

    private void RefreshInit() {
        //设置监听
        swiperefreshlayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swiperefreshlayout.setProgressViewEndTarget(true, 200);
        //改变加载显示的颜色
        swiperefreshlayout.setColorSchemeColors(getRouColors(R.color.dominant_ton), getRouColors(R.color.dominant_ton));
    }


    public static synchronized HomeFragment getHomeInstance() {
        return homeFragment;
    }

    @Override
    protected void initData() {
        hp.Presenter();
    }

    /**
     * 动态添加闪聊图组;
     *
     * @param ab
     */
    private void FLASHCHATData(List<ActiveBean> ab) {
        if (dynamic_layout.getChildCount() > 0)
            dynamic_layout.removeAllViews();

        for (final ActiveBean atv : ab) {
            LinearLayout headLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_flash_layout_include, null);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayout(0, DeviceUtils.getWindowWidth(getActivity()) * 1 / 3, Fields.MATCH);

            lp.rightMargin = DeviceUtils.dip2px(getActivity(), 10);
            ImageView im = (ImageView) headLayout.findViewById(R.id.head_img);
            TextView income = (TextView) headLayout.findViewById(R.id.come_in_tv);
            TextView name_tv = (TextView) headLayout.findViewById(R.id.name_tv);
            name_tv.setText(atv.getName());

            income.setVisibility(View.VISIBLE);

            headLayout.setLayoutParams(lp);

            Glide.with(MyApplication.getContext()).load(atv.getFaceUrl() == null || atv.getFaceUrl().isEmpty() ? R.mipmap.test_icon : atv.getFaceUrl()).apply(getGlideOptions()).into(im);

            income.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginStatusQuery()) {
                        groupId = atv.getGroupId();
                        GroupManagerPresenter.applyJoinGroup(groupId, "", back);
                    } else {
                        ToastUtil.toast2_bottom(getActivity(), "请先登录！");
                        goLoginOperPage(getActivity());
                    }
                }
            });
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginStatusQuery()) {
                        groupId = atv.getGroupId();
                        GroupManagerPresenter.applyJoinGroup(groupId, "", back);
                    } else {
                        ToastUtil.toast2_bottom(getActivity(), "请先登录！");
                        goLoginOperPage(getActivity());
                    }
                }
            });
            dynamic_layout.addView(headLayout);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            hp.start();
            polistener = this;
            Log.i(TAG, "onHiddenChanged onResume");
        } else {
            polistener = null;
            hp.stop();
            Log.i(TAG, "onHiddenChanged onPauser");
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
        if (!LoginStatusQuery()) {
            swiperefreshlayout.setEnabled(false);
//            onForbidSlide(LoginStatusQuery());
        } else {
            hp.RefreshLikeThme(true);
//            onForbidSlide(LoginStatusQuery());
        }

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
        if (LoginStatusQuery() || v.getId() == R.id.show_hide_img) {
            switch (v.getId()) {
                case R.id.banner_img:
                    if (pb != null)
                        goOutSidePage(getActivity(), pb.getHttpUrl());
                    break;
                case R.id.dynamic_layout:
                    goFlashChatPage(getActivity());
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
                    //必须先停止recyclerView的滑动(CoordinatorLayout 时才需要执行此操作),不然跳到顶部后无法跳回来；
                    forceStopRecyclerViewScroll();
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                    mLayoutManager.setStackFromEnd(true);
                    showAndHiddenAnimation(show_hide_img, null, AnimationUtil.AnimationState.STATE_HIDDEN, 500);

                    break;
                case R.id.topic_circle_layout:
                    goTopicCirclePage(getActivity());
                    break;
                case R.id.flash_more_tv:
                    goFlashChatPage(getActivity());
                    break;
            }
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }
    }

    //强制停止RecyclerView滑动方法
    public void forceStopRecyclerViewScroll() {
        recyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
    }

    /**
     * Banner递增回调;
     */
    @Override
    public void onIncrease() {
        hp.current();
    }

    /**
     * 失败方法回调;
     */
    @Override
    public void onFailed(String msg) {
        if (!msg.isEmpty())
            ToastUtil.toast2_bottom(getActivity(), msg);

        swiperefreshlayout.setRefreshing(false);
    }

    /**
     * 单图方法回调;
     */
    @Override
    public void onOneImg(PageTopBean ptb) {
        banner_img.setVisibility(View.VISIBLE);
        Glide.with(MyApplication.getContext()).load(ptb.getImgUrl()).apply(getGlideOptions()).into(banner_img);
        this.pb = ptb;
    }

    /**
     * 登出方法回调;
     */
    @Override
    public void onLogout() {
        LogOut(getActivity(), true);
        Nblist.clear();
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        ToastUtil.toast2_bottom(getActivity(), "已退出登录");
        hp.IsLogin();
    }

    /**
     * 顶部Banner跳转外链回调;
     */
    @Override
    public void onOutSideGo(String url) {
        goOutSidePage(getActivity(), url);
    }

    /**
     * 顶部Banner隐藏方法回调;
     */
    @Override
    public void BannerHide() {
        banner_layout.setVisibility(View.INVISIBLE);
    }

    /**
     * 顶部Banner显示方法回调;
     */
    @Override
    public void BannerShow() {
        hp.getBanner();
    }

    /**
     * 屏蔽滑动方法回调;
     *
     * @param b
     */
    @Override
    public void onForbidSlide(final boolean b) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !b;
            }
        });
    }

    /**
     * 在意的人数据回调;
     */
    @Override
    public void RefreshRecyclerView(List<TopicTypeBean> nblist) {
        Nblist.clear();
        for (TopicTypeBean ttb : nblist) {
            Nblist.add(ttb);
        }

        Log.i(TAG, "Nblist size =====" + Nblist.size());
        mHeaderAndFooterWrapper.notifyDataSetChanged();

        foot_layout.setVisibility(View.GONE);
        recom_layout.setVisibility(View.GONE);
        foot_tv.setVisibility(View.GONE);

        swiperefreshlayout.setRefreshing(false);
    }

    /**
     * 版本更新下载回调;
     */
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

    /**
     * 底部判断回调;
     */
    @Override
    public void onBottom() {
        foot_layout.setVisibility(View.VISIBLE);
        foot_tv.setVisibility(View.VISIBLE);

        if (SPreferences.getMyNickName() != null)
            recom_nick_name.setText("Hi，" + SPreferences.getMyNickName());

        if (foot_layout.getLayoutParams().height != Fields.WRAP)
            foot_layout.getLayoutParams().height = Fields.WRAP;

        swiperefreshlayout.setRefreshing(false);

        Log.i(TAG, "recomeSize ==" + recomeSize);
        if (recomeSize > 0)
            recom_layout.setVisibility(View.VISIBLE);
        else
            recom_layout.setVisibility(View.GONE);
    }

    /**
     * 谷歌刷新;
     */
    @Override
    public void onRefresh() {
        hp.page = 0;
        if (LoginStatusQuery())
            hp.getPeopleNearby(true);
    }

    /**
     * 刷新页面回调;
     */
    public void onRefreshs() {
        hp.IsLogin();
    }

    /**
     * 隐藏闪聊
     */
    @Override
    public void onHideFlashChatInfo() {
        flash_title_layout.setVisibility(View.GONE);
        dynamic_scroll.setVisibility(View.GONE);
    }

    /**
     * 闪聊数据回调;
     *
     * @param ab data
     */
    @Override
    public void onFlashChatInfo(List<ActiveBean> ab) {
        flash_title_layout.setVisibility(View.VISIBLE);
        dynamic_scroll.setVisibility(View.VISIBLE);
        FLASHCHATData(ab);
    }

    @Override
    public void onReComInfo(List<AddressBookBean> add) {
        ReComData(add);
    }

    /**
     * 推荐好友;
     *
     * @param add
     */
    private void ReComData(List<AddressBookBean> add) {
        if (add != null && add.size() > 0) {
            recomeSize = add.size();

            if (foot_recom_layout.getChildCount() > 0) {
                Log.i(TAG, "foot_recom_layout 中有数据");
                foot_recom_layout.removeAllViews();
            }
            for (final AddressBookBean abb : add) {
                LinearLayout headLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_flash_layout_include, null);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayout(0, DeviceUtils.getWindowWidth(getActivity()) * 1 / 3, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.rightMargin = DeviceUtils.dip2px(getActivity(), 10);
                ImageView im = (ImageView) headLayout.findViewById(R.id.head_img);
                ImageView addicon = (ImageView) headLayout.findViewById(R.id.add_img);

                LinearLayout layout = (LinearLayout) headLayout.findViewById(R.id.layout);
                layout.setBackgroundResource(R.drawable.blue_corners3);

                TextView name_tv = (TextView) headLayout.findViewById(R.id.name_tv);
                name_tv.setTextColor(getRouColors(R.color.white));
                name_tv.setText(abb.getName());

                addicon.setVisibility(View.VISIBLE);
                if (LoginStatusQuery()) {
                    headLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goHomePage(getActivity(), abb.getId(), false);
                        }
                    });
                } else {
                    ToastUtil.toast2_bottom(getActivity(), "请先登录！");
                    goLoginOperPage(getActivity());
                }
                headLayout.setLayoutParams(lp);
                Glide.with(MyApplication.getContext()).load(abb.getHeadImgUrl() == null || abb.getHeadImgUrl().isEmpty() ? R.mipmap.test_icon : abb.getHeadImgUrl()).apply(getGlideOptions()).into(im);

                foot_recom_layout.addView(headLayout);
            }
        }
    }

    /**
     * 跳转照片查看器方法回调;
     *
     * @param pos  第几页;
     * @param imgs 照片list;
     */
    @Override
    public void RcOnItemClick(int pos, List<String> imgs) {
        if (LoginStatusQuery()) {
            goPhotoViewerPage(getActivity(), imgs, pos, 1);
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }


    }

    /**
     * 点赞方法法回调;
     */
    @Override
    public void LikeClick(String id) {
        if (LoginStatusQuery()) {
            Show(getActivity(), "", true, null);
            hp.LikeChatTheme(id);
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }
    }

    /**
     * 跳转个人主页方法回调;
     *
     * @param userId 用户id（ 传自己id或者空为自己个人详情页面 ）
     */
    @Override
    public void GoPageHome(String userId) {

        if (LoginStatusQuery()) {
            goHomePage(getActivity(), userId, false);
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }

    }

    /**
     * 跳转话题圈评论详情页方法回调;
     *
     * @param id      话题圈id
     * @param userId  评论用户id（用户滚动评论响应item，传空则不滚动）
     * @param name    评论用户名称（用户滚动评论响应item，传空则不滚动）
     * @param content 用户评论内容（用户滚动评论响应item，传空则不滚动）
     */
    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {

        if (LoginStatusQuery()) {
            goTopicCommentDetailsPage(getActivity(), id, userId, name, content);
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }
    }

    /**
     * 跳转视频播放界面方法回调;
     *
     * @param url        视频路径
     * @param videoCover 视频封面
     */
    @Override
    public void GoVideoPage(String url, String videoCover) {

        if (LoginStatusQuery()) {
            goVideoPlayerPage(getActivity(), url, videoCover);
        } else {
            ToastUtil.toast2_bottom(getActivity(), "请先登录！");
            goLoginOperPage(getActivity());
        }

    }

    /**
     * IM申请加入群方法回调;
     *
     * @param i
     * @param s
     */
    @Override
    public void onError(int i, String s) {
        if (i == 10013) {
            //已经是群成员
            Log.i(TAG, getString(R.string.group_member_already));
            if (!groupId.isEmpty())
                ChatActivity.navToChat(getActivity(), groupId, TIMConversationType.Group);
        } else
            ToastUtil.toast2_bottom(getActivity(), s);
    }

    /**
     * IM申请加入群方法回调;
     */
    @Override
    public void onSuccess() {
        Log.i(TAG, getString(R.string.apply_for_success));
        if (!groupId.isEmpty())
            ChatActivity.navToChat(getActivity(), groupId, TIMConversationType.Group);
    }

}
