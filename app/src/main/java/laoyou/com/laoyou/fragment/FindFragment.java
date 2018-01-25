package laoyou.com.laoyou.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TabPagerAdapter;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.CustomViewPager;

import static laoyou.com.laoyou.fragment.FindSonFragment.getInstances;
import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.IntentUtils.goReleaseTopicPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/9.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "FindFragment";
    private CustomViewPager viewPager;
    private TabLayout mTab;
    private List<String> list;
    private TabPagerAdapter adapter;
    private TextView idscreen_tv;
    private ImageView issue_img;

    @Override
    protected int initContentView() {
        return R.layout.find_fragment;
    }

    @Override
    protected void click() {
        idscreen_tv.setOnClickListener(this);
        issue_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        viewPager = f(R.id.viewPager);
        mTab = f(R.id.tab_layout);
        idscreen_tv = f(R.id.idscreen_tv);
        issue_img = f(R.id.issue_img);
        list = new ArrayList<>();
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        list.add(gets(R.string.new_incident));
        list.add(gets(R.string.people_nearby));

        mTab.addTab(mTab.newTab().setText(list.get(0)));
        mTab.addTab(mTab.newTab().setText(list.get(1)));
        viewPager.setOffscreenPageLimit(2);
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
                if (position == 0) {
                    showAndHiddenAnimation(idscreen_tv, null, AnimationUtil.AnimationState.STATE_HIDDEN, 500);
                    showAndHiddenAnimation(issue_img, null, AnimationUtil.AnimationState.STATE_SHOW, 500);
                } else {
                    showAndHiddenAnimation(idscreen_tv, null, AnimationUtil.AnimationState.STATE_SHOW, 500);
                    showAndHiddenAnimation(issue_img, null, AnimationUtil.AnimationState.STATE_HIDDEN, 500);
                }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idscreen_tv:
                final List<FindSonFragment> f = getInstances();
                if (f != null && f.size() == 2) {
                    new ActionSheetDialog(getActivity()).builder().addSheetItem(gets(R.string.man), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            f.get(1).ScreenSexInfo(1);
                        }
                    }).addSheetItem(gets(R.string.woman), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            f.get(1).ScreenSexInfo(0);
                        }
                    }).addSheetItem(gets(R.string.owner), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            f.get(1).ScreenSexInfo(99);
                        }
                    }).show();
                }
                break;
            case R.id.issue_img:
                goReleaseTopicPage(getActivity(), null, null, null);
                break;
        }
    }
}
