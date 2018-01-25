package laoyou.com.laoyou.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TabPagerAdapter;
import laoyou.com.laoyou.bean.ActiveUserBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.AppBarStateChangeListener;
import laoyou.com.laoyou.listener.TopicTypeDetailsListener;
import laoyou.com.laoyou.presenter.TopicTypeDetailsPresenter;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goParticipationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goReleaseTopicPage;
import static laoyou.com.laoyou.utils.SynUtils.KswitchWay;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/19.
 */
public class TopicTypeDetailsActivity extends InitActivity implements TopicTypeDetailsListener, View.OnClickListener {

    private static final String TAG = "TopicTypeDetailsActivity";
    private String id = "";
    private TopicTypeDetailsPresenter tp;
    private TabLayout mTab;
    private List<String> tablist;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;
    private LinearLayout active_user_layout, titles_layout;
    private static AppBarLayout appbar_layout;
    private Toolbar toolbar;
    private TextView attention_tv;
    private String name;
    private int followCount;
    private int chatThemeCount;
    private TextView title_tv, status_tv;
    private ImageView background_img, issue_img;
    private String imgurl;
    private static TopicTypeDetailsActivity activity;
    private boolean NoBackGroup = false;
    private ImageView back_img, attention_img;

    public static TopicTypeDetailsActivity getTopicTypeInstance() {
        return activity;
    }


    public AppBarLayout getAppBar() {
        return appbar_layout;
    }

    @Override
    protected void click() {
        attention_tv.setOnClickListener(this);
        issue_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.topic_type_details_layout);
        id = getIntent().getStringExtra("Topic_Type_id");
        activity = this;
        name = getIntent().getStringExtra("Topic_Type_name");
        followCount = Integer.parseInt(getIntent().getStringExtra("Topic_Type_follow"));
        chatThemeCount = Integer.parseInt(getIntent().getStringExtra("Topic_Type_chat"));
        imgurl = getIntent().getStringExtra("Topic_Type_imgurl");

        tp = new TopicTypeDetailsPresenter(this);
        mTab = f(R.id.tab_layout);
        title_tv = f(R.id.title_tv);
        back_img = f(R.id.back_img);
        issue_img = f(R.id.issue_img);

        status_tv = f(R.id.status_tv);
        background_img = f(R.id.background_img);
        toolbar = f(R.id.toolbar);
        titles_layout = f(R.id.titles_layout);
        attention_tv = f(R.id.attention_tv);
        attention_img = f(R.id.attention_img);
        appbar_layout = f(R.id.appbar_layout);
        active_user_layout = f(R.id.active_user_layout);
        tablist = new ArrayList<>();
        tablist.add(gets(R.string.news));
        tablist.add(gets(R.string.hosts));

//      mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = f(R.id.viewPager);
        setImgTitles(this);

        setPager();
    }

    @Override
    protected void initData() {
        tp.getFollowChatType(id);
        tp.getActiveUserData(id);
        if (!imgurl.isEmpty()) {
            Glide.with(this).load(imgurl).apply(getGlideOptions()).into(background_img);
            NoBackGroup = false;
        } else {
            background_img.setVisibility(View.GONE);
            NoBackGroup = true;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = DeviceUtils.dip2px(this, 40);
            title_tv.setLayoutParams(lp);
            back_img.setImageResource(R.mipmap.return_icon);
            titles_layout.setBackgroundResource(R.color.black);
        }


        mTab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTab, 10, 10);
            }
        });
        status_tv.setText(KswitchWay(followCount) + gets(R.string.participant) + " | " + KswitchWay(chatThemeCount) + gets(R.string.status_num));
        title_tv.setText(name);

    }

    @Override
    public void onFailedsMsg(String msg) {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onShowDetailsInfo(List<TopicTypeBean> list) {

    }

    @Override
    public void onStateChange(AppBarLayout appBarLayout, AppBarStateChangeListener.State state, int i) {

        titles_layout.setBackgroundColor(changeAlpha(getResources().getColor(R.color.white), Math.abs(i * 1.0f) / appBarLayout.getTotalScrollRange()));

        switch (state) {
            //展开状态;
            case EXPANDED:
                if (!NoBackGroup)
                    back_img.setImageResource(R.mipmap.return_icon_white);
                break;
            //折叠状态;
            case COLLAPSED:
                back_img.setImageResource(R.mipmap.return_icon);
                break;
            //中间状态;
            default:
                back_img.setImageResource(R.mipmap.return_icon);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void IsFollowChat(boolean b) {
        attention_tv.setText(b ? gets(R.string.followed) : gets(R.string.unfollowed));
        Glide.with(this).load(b ? R.mipmap.yellow_star : R.mipmap.black_star).into(attention_img);
    }

    @Override
    public void GetActiveUserData(final List<ActiveUserBean> aub) {

        active_user_layout.setVisibility(aub != null && aub.size() > 0 ? View.VISIBLE : View.GONE);

        int w = DeviceUtils.getWindowWidth(this) * 1 / 11;
        for (int i = 0; i < aub.size(); i++) {
            if (i > 7)
                break;
            if (i == 7) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w / 2, w / 2);
                lp.leftMargin = DeviceUtils.dip2px(this, 2);
                ImageView im = new ImageView(this);
                im.setLayoutParams(lp);
                Glide.with(this).load(R.mipmap.gray_right).into(im);
                active_user_layout.addView(im);
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goParticipationPage(TopicTypeDetailsActivity.this, 1, id);
                    }
                });
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
                lp.rightMargin = DeviceUtils.dip2px(this, 8);
                lp.leftMargin = DeviceUtils.dip2px(this, 2);

                CircleImageView im = new CircleImageView(this);
                im.setLayoutParams(lp);
                Glide.with(this).load(aub.get(i).getHeadImgUrl()).apply(getGlideOptions()).into(im);
                active_user_layout.addView(im);
                final int finalI = i;
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goHomePage(TopicTypeDetailsActivity.this, aub.get(finalI).getUserId(), false);
                    }
                });

            }
        }
    }


    private void setPager() {
        adapter = new TabPagerAdapter(getSupportFragmentManager(), tablist, Fields.ISTOPIC, id);
        viewPager.setAdapter(adapter);
        mTab.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tp.setAppBarLayoutStateChangeListener(appbar_layout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tp.removeAppBarLayoutStateChangeListener(appbar_layout);
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attention_tv:
                Show(TopicTypeDetailsActivity.this, "", true, null);
                tp.AttentionTopic(id);
                break;
            case R.id.issue_img:
                if (!id.isEmpty() && !name.isEmpty()) {
                    List<String> str = new ArrayList<>();
                    str.add(id);
                    str.add(name);
                    goReleaseTopicPage(this, null, null, str);
                }

                break;
        }
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;

            child.setLayoutParams(params);
            child.invalidate();
        }

    }

}
