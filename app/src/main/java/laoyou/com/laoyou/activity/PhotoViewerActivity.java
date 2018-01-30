package laoyou.com.laoyou.activity;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.PhotoPagerAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.BigPhotoViewPager;

import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.saveImageToGallery;

/**
 * Created by lian on 2017/12/8.
 */
public class PhotoViewerActivity extends InitActivity {

    private BigPhotoViewPager viewPager;
    private List<String> list;
    private TextView page_tv;
    private int pos;
    //功能标识符 0 ：全开,1：只开启保存, 2：只开启删除, 3：全部关闭;
    private int Function;
    private static PhotoViewerActivity instance;

    @Override
    protected void click() {

    }

    public static PhotoViewerActivity getPhotoInstance() {
        return instance;
    }

    /**
     * 保存图片方法;
     *
     * @param url
     */
    public void SavePhoto(String url) {
        Glide.with(MyApplication.getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, Transition<? super Bitmap> transition) {
                if (saveImageToGallery(getApplicationContext(), resource))
                    ToastUtil.toast2_bottom(PhotoViewerActivity.this, gets(R.string.save_succ));
                else
                    ToastUtil.toast2_bottom(PhotoViewerActivity.this, gets(R.string.save_fail));
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    protected void init() {
        setContentView(R.layout.photo_viewer_layout);
        instance = this;
        viewPager = f(R.id.viewpager);
        page_tv = f(R.id.page_tv);
        list = new ArrayList<>();
        list = getIntent().getStringArrayListExtra("Photo_list");
        pos = Integer.parseInt(getIntent().getStringExtra("Photo_pos"));
        Function = Integer.parseInt(getIntent().getStringExtra("Photo_Function"));
        if (list.size() == 1)
            page_tv.setVisibility(View.INVISIBLE);
        else
            page_tv.setText(pos + 1 + "/" + list.size());


    }

    /**
     * ViewPager初始化设置;
     */
    private void setPage() {
        viewPager.setAdapter(new PhotoPagerAdapter(getSupportFragmentManager(), list, Function));
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
        viewPager.setOffscreenPageLimit(1);
    }

    @Override
    protected void initData() {
        setPage();
    }
}
