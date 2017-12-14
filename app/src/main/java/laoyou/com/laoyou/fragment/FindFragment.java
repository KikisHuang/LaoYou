package laoyou.com.laoyou.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TabPagerAdapter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.CustomViewPager;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/9.
 */
public class FindFragment extends BaseFragment {

    private static final String TAG = "FindFragment";
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> list;
    private TabPagerAdapter adapter;

    @Override
    protected int initContentView() {
        return R.layout.find_fragment;
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        viewPager = f(R.id.viewPager);
        mTab = f(R.id.tab_layout);
        list = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        list.add(gets(R.string.new_incident));
        list.add(gets(R.string.people_nearby));

        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));

        setPager();
    }


    private void setPager() {

        adapter = new TabPagerAdapter(getChildFragmentManager(), list, Fields.ISFIND);
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
    protected void initData() {

    }
}
