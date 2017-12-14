package laoyou.com.laoyou.activity;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.PhotoPagerAdapter;
import laoyou.com.laoyou.view.BigPhotoViewPager;

/**
 * Created by lian on 2017/12/8.
 */
public class PhotoViewerActivity extends InitActivity {

    private BigPhotoViewPager viewPager;
    private List<String> list;
    private TextView page_tv;

    private int pos;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.photo_viewer_layout);
        viewPager = f(R.id.viewpager);
        page_tv = f(R.id.page_tv);
        list = new ArrayList<>();
        list = getIntent().getStringArrayListExtra("Photo_list");
        pos = Integer.parseInt(getIntent().getStringExtra("Photo_pos"));
        page_tv.setText(pos + 1 + "/" + list.size());
    }

    /**
     * ViewPager初始化设置;
     */
    private void setPage() {
        viewPager.setAdapter(new PhotoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page_tv.setText(position + 1 + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(pos);
    }

    @Override
    protected void initData() {
        setPage();
    }
}
