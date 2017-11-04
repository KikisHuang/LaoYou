package laoyou.com.laoyou.fragment;

import android.view.View;

import laoyou.com.laoyou.R;


/**
 * Created by lian on 2017/4/22.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "MyFragment";

    @Override
    protected int initContentView() {
        return R.layout.my_fragment;
    }

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
    }


    @Override
    protected void initData() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }
}
