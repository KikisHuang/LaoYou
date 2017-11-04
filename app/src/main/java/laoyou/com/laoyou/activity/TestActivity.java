package laoyou.com.laoyou.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity {
    private ImageView img;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        img = f(R.id.img);
        Glide.with(this).load("http://fns-photo-public.oss-cn-hangzhou.aliyuncs.com/15088166925080d80b7.jpg").into(img);
    }

    @Override
    protected void initData() {

    }
}
