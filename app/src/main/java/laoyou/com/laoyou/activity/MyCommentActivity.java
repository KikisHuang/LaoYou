package laoyou.com.laoyou.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TabPagerAdapter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.view.CustomViewPager;

import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/8.
 */
public class MyCommentActivity extends InitActivity {
    private static final String TAG = "MyCommentActivity";
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> list;
    private TabPagerAdapter page_adapter;
    private int flag;
    private TextView title_name_tv;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.comment_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitlesAndBack(this, gets(R.string.goback), "");
        flag = Integer.parseInt(getIntent().getStringExtra("Comment_of_HeartValue"));

        mTab = f(R.id.tab_layout);
        title_name_tv = f(R.id.title_name_tv);
        list = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = f(R.id.viewPager);
        switch (flag) {
            case 0:
                list.add(gets(R.string.receive_comment));
                list.add(gets(R.string.send_comment));
                title_name_tv.setText(gets(R.string.comment));
                break;
            case 1:
                list.add(gets(R.string.receive_heart_value));
                list.add(gets(R.string.send_heart_value));
                title_name_tv.setText(gets(R.string.heart_beat));

                break;
        }

        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));
        setPager();
    }

    private void setPager() {

        page_adapter = new TabPagerAdapter(getSupportFragmentManager(), list, flag);

        viewPager.setAdapter(page_adapter);
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
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
