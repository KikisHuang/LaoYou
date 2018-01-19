package laoyou.com.laoyou.activity;

import android.view.View;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
