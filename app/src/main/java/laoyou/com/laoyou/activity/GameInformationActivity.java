package laoyou.com.laoyou.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TabPagerAdapter;
import laoyou.com.laoyou.bean.GameInfoBean;
import laoyou.com.laoyou.bean.GameTypeBean;
import laoyou.com.laoyou.listener.GameInformationListener;
import laoyou.com.laoyou.presenter.GameInformationPresenter;
import laoyou.com.laoyou.view.CustomViewPager;

import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/12.
 */
public class GameInformationActivity extends InitActivity implements GameInformationListener {

    private static final String TAG = "GameInformationActivity";

    private CustomViewPager viewPager;
    private TabLayout mTab;
    //游戏分类名称集合;
    private List<GameTypeBean> tablist;
    private GameInformationPresenter gp;
    private TabPagerAdapter adapter;


    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.game_information_layout);
        setTitlesAndBack(this, "", "");
        TextView title = (TextView) findViewById(R.id.title_tv);
        title.setText(gets(R.string.game_information));

        viewPager = f(R.id.viewPager);
        mTab = f(R.id.tab_layout);

        tablist = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        gp = new GameInformationPresenter(this);
        viewPager.setOffscreenPageLimit(1);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFailedMsg(String msg) {

    }

    @Override
    public void onGameTypeInforMation(List<GameTypeBean> list) {
        tablist = list;
        for (GameTypeBean gb : tablist) {
            mTab.addTab(mTab.newTab().setText(gb.getName()));
        }
        setPager();
    }

    @Override
    public void onGameInfor(List<GameInfoBean> li) {

    }


    private void setPager() {

        adapter = new TabPagerAdapter(getSupportFragmentManager(), tablist);
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
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        viewPager = null;
        mTab = null;
    }
}