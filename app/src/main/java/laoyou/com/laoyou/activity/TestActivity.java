package laoyou.com.laoyou.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.ArrayList;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.view.group.GroupCircularImageView;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    private GroupCircularImageView circularImageView;
    ArrayList<Bitmap> mBmps3 = new ArrayList<Bitmap>();

    @Override
    protected void click() {

    }


    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        circularImageView = f(R.id.circularImageView);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.test_head_icon);
        mBmps3.add(avatar3);
        mBmps3.add(avatar3);
        mBmps3.add(avatar3);
        mBmps3.add(avatar3);
    }


    @Override
    protected void initData() {
        circularImageView.setImageBitmaps(mBmps3);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
