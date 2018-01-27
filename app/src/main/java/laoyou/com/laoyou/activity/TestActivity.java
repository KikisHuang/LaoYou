package laoyou.com.laoyou.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.DeviceUtils;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private ImageView img;
    private Toolbar toolbar;
    @Override
    protected void click() {
        img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        img = f(R.id.img);
        toolbar = f(R.id.toolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img:
                CoordinatorLayout.LayoutParams cp =  new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(this,50));
                toolbar.setLayoutParams(cp);

                break;
        }
    }

}
