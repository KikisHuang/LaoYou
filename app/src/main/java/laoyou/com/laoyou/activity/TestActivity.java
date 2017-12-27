package laoyou.com.laoyou.activity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.CustomImageView;
import laoyou.com.laoyou.view.NineGridlayout;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private NineGridlayout nine_gd_layout;
    private CustomImageView customImageView;

    @Override
    protected void click() {

    }


    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        nine_gd_layout = f(R.id.nine_gd_layout);
        customImageView = f(R.id.customImageView);
    }


    @Override
    protected void initData() {
        List<String> images = new ArrayList<>();
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);

        nine_gd_layout.setImagesData(images);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
