package laoyou.com.laoyou.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.CustomViewPager;

import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.IntentUtils.goMainPage;

/**
 * Created by lian on 2018/1/23.
 */
public class WelcomeActivity extends InitActivity {

    private static final String TAG = "WelcomeActivity";

    private CustomViewPager viewPager;
    private List<View> imgs;
    private PageAdapter adapter;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.welcome_layout);
        imgs = new ArrayList<>();
        viewPager = f(R.id.viewPager);
        SPreferences.saveFirstStart(false);
    }

    @Override
    protected void initData() {

        for (int ins : Fields.welImgs) {
            FrameLayout layout = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.welcome_page_include, null);
            imgs.add(layout);
        }
        adapter = new PageAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2 && imgs.get(position).findViewById(R.id.go_news_bt).getVisibility() == View.GONE)
                    showAndHiddenAnimation(imgs.get(position).findViewById(R.id.go_news_bt), null, AnimationUtil.AnimationState.STATE_SHOW, 500);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = imgs.get(position);

            ImageView im = (ImageView) view.findViewById(R.id.wel_img);
            Button bt = (Button) view.findViewById(R.id.go_news_bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goMainPage(WelcomeActivity.this);
                    finish();
                }
            });
//            Glide.with(MyApplication.getContext()).load(Fields.welImgs[position]).into(im);
            im.setImageResource(Fields.welImgs[position]);

            container.addView(view);
            return imgs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgs.get(position));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgs = null;
        viewPager = null;
        adapter = null;
        MyApplication.getRefWatcher().watch(this);
    }
}
