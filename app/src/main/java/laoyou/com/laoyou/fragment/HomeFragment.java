package laoyou.com.laoyou.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomeListener;
import laoyou.com.laoyou.listener.PositionAddListener;
import laoyou.com.laoyou.presenter.HomePresenter;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.utils.ViewPagerScroller;
import laoyou.com.laoyou.view.SlidePopupWindow;

import static laoyou.com.laoyou.utils.DeviceUtils.getWindowWidth;
import static laoyou.com.laoyou.utils.IntentUtils.goCertificationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;


/**
 * Created by lian on 2017/4/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListener, PositionAddListener {
    private static final String TAG = "HomeFragment";

    private TextView query_of_login_tv, pass_tv, check_pass_tv, loading_tv, refresh_tv;
    private LinearLayout refresh_layout;
    private ImageView refresh_img;
    private ViewPager mViewPager;
    //flag 0登录,1查询;
    private int flag = 0;
    private HomePresenter hp;
    private LinearLayout mLinearLayoutDot;
    public static PositionAddListener polistener;
    private ImageView banner_img, camera_img;
    private FrameLayout page_layout;
    private static HomeFragment homeFragment;
    private String name = "";
    private String HeadImg = "";
    private TextView refuse_tv, again_query_tv, regain_tv;
    private EditText refuse_ed;
    private int Status = 100;
    private PageTopBean pb = null;

    @Override
    protected int initContentView() {
        return R.layout.home_fragment;
    }

    @Override
    protected void click() {
        query_of_login_tv.setOnClickListener(this);
        camera_img.setOnClickListener(this);
        again_query_tv.setOnClickListener(this);
        regain_tv.setOnClickListener(this);
        banner_img.setOnClickListener(this);
    }


    @Override
    protected void init() {
        homeFragment = this;
        query_of_login_tv = f(R.id.query_of_login_tv);
        pass_tv = f(R.id.pass_tv);
        check_pass_tv = f(R.id.check_pass_tv);
        loading_tv = f(R.id.loading_tv);
        refresh_layout = f(R.id.refresh_layout);
        refresh_img = f(R.id.refresh_img);
        refresh_tv = f(R.id.refresh_tv);
        banner_img = f(R.id.banner_img);
        camera_img = f(R.id.camera_img);
        refuse_tv = f(R.id.refuse_tv);
        again_query_tv = f(R.id.again_query_tv);
        page_layout = f(R.id.page_layout);
        refuse_ed = f(R.id.refuse_ed);
        regain_tv = f(R.id.regain_tv);

        mViewPager = f(R.id.vp_main);
        mLinearLayoutDot = f(R.id.ll_main_dot);

        polistener = this;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getWindowWidth(getActivity()) / 2.4));

        page_layout.setLayoutParams(lp);
        banner_img.setLayoutParams(lp);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒

        hp = new HomePresenter(this, mViewPager, getActivity().getLayoutInflater(), getActivity().getApplicationContext(), mLinearLayoutDot);
        HideOver();
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

    private void HideOver() {
        check_pass_tv.setVisibility(View.GONE);
        pass_tv.setVisibility(View.GONE);
        query_of_login_tv.setVisibility(View.GONE);
        loading_tv.setVisibility(View.GONE);
        refresh_layout.setVisibility(View.GONE);
        again_query_tv.setVisibility(View.GONE);
        refuse_tv.setVisibility(View.GONE);
        refuse_ed.setVisibility(View.GONE);
        regain_tv.setVisibility(View.GONE);
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
        switch (v.getId()) {
            case R.id.query_of_login_tv:
                if (flag == 0)
                    if (!LoginStatusQuery())
                        goLoginOperPage(getActivity());

                if (flag == 1)
                    hp.getUseDetails();
                break;
            case R.id.camera_img:
                if (LoginStatusQuery()) {
                    SlidePopupWindow spw = new SlidePopupWindow(getActivity(), HeadImg, name, Status);
                    spw.ScreenPopupWindow();
                }
                break;
            case R.id.again_query_tv:
                goCertificationPage(getActivity());
                break;
            case R.id.regain_tv:
                hp.RegainPassWord();
                break;
            case R.id.banner_img:
                if (pb != null)
                    goOutSidePage(getActivity(), pb.getHttpUrl());
                break;
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
    public void onDetails(UserInfoBean ub) {
        Glide.with(getActivity()).load(ub.getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(getActivity())).into(camera_img);
        HeadImg = ub.getHeadImgUrl();
        name = ub.getName();
    }

    @Override
    public void onCertificaTion() {
        query_of_login_tv.setVisibility(View.VISIBLE);
        ToastUtil.toast2_bottom(getActivity(), "必须实名认证后才可查询密码");
//        if (getCertifiInstance() == null)
        goCertificationPage(getActivity());
    }

    @Override
    public void IsLogin(boolean b) {
        HideOver();
        query_of_login_tv.setVisibility(View.VISIBLE);
        if (b) {
            flag = 1;
            query_of_login_tv.setText("查询");
            camera_img.setVisibility(View.VISIBLE);
        } else {
            flag = 0;
            query_of_login_tv.setText("登录");
            camera_img.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void unCheck() {
        HideOver();
        loading_tv.setVisibility(View.VISIBLE);
        refresh_layout.setVisibility(View.VISIBLE);

        hp.startAnima(refresh_img);
    }

    @Override
    public void CheckOK(String passwd) {
        HideOver();
        hp.checkPassLength(passwd.length(), passwd);

        check_pass_tv.setVisibility(View.VISIBLE);
        pass_tv.setVisibility(View.VISIBLE);
        regain_tv.setVisibility(View.VISIBLE);
        hp.stopAnima(refresh_img);
    }

    @Override
    public void Refuse(String msg) {
        HideOver();
        refuse_ed.setText(msg);
        refuse_ed.setVisibility(View.VISIBLE);
        refuse_tv.setVisibility(View.VISIBLE);
        again_query_tv.setVisibility(View.VISIBLE);
        hp.stopAnima(refresh_img);
    }

    @Override
    public void onTime(int time) {
        refresh_tv.setText(time + "秒自动刷新");
        if (time == 0)
            hp.CheckID();
    }

    @Override
    public void onCheckStatus(int status) {
        Status = status;
    }

    @Override
    public void onLogout() {
        ToastUtil.toast2_bottom(getActivity(), "已退出登录");
        hp.Presenter();
        hp.stopAnima(refresh_img);
    }

    @Override
    public void onOutSideGo(String url) {
        goOutSidePage(getActivity(), url);
    }

    @Override
    public void onNormalPassSize(String passwd) {
        pass_tv.setTextSize(55);
        pass_tv.setText(passwd);
    }

    @Override
    public void onMinPassSize(String passwd) {
        pass_tv.setTextSize(25);
        pass_tv.setText(passwd);
    }

    public void onRefresh() {
        hp.getUseDetails();
        hp.IsLogin();
    }
}
