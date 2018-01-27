package laoyou.com.laoyou.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.IntentUtils.goMainPage;

/**
 * Created by lian on 2018/1/23.
 */
public class WelcomeActivity extends InitActivity {

    private static final String TAG = "WelcomeActivity";

    private ViewPager viewPager;
    private List<View> imgs;

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
            ImageView im = (ImageView) layout.findViewById(R.id.wel_img);
            Button bt = (Button) layout.findViewById(R.id.go_news_bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goMainPage(WelcomeActivity.this);
                    finish();
                }
            });

            Glide.with(this).load(ins).into(im);
            imgs.add(layout);
        }
        PageAdapter adapter = new PageAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2)
                    showAndHiddenAnimation(imgs.get(position).findViewById(R.id.go_news_bt), null, AnimationUtil.AnimationState.STATE_SHOW, 500);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class PageAdapter extends PagerAdapter {

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
            container.addView(view);
            return imgs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgs.get(position));
        }
    }

}
