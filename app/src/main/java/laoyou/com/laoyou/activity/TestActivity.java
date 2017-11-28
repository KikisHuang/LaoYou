package laoyou.com.laoyou.activity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.InfoAdapter;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.view.HorizontalListView;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    private HorizontalListView listview;
    private InfoAdapter adapter;
    private List<AddressBean> ab;

    @Override
    protected void click() {

    }


    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        listview = f(R.id.listview);
        ab = new ArrayList<>();
        AddressBean a = new AddressBean();
        a.setIdentifier("123");
        a.setFaceUrl("http://www.18183.com/uploads/allimg/140616/61-140616111040.jpg");
        ab.add(a);
        adapter = new InfoAdapter(this, ab);
        listview.setAdapter(adapter);
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
