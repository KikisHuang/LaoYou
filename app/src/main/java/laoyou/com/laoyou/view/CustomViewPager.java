package laoyou.com.laoyou.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by lian on 2017/5/26.
 * ViewPager切换动画禁止;
 */
public class CustomViewPager extends ViewPager {


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context) {
        super(context);
    }
//
//    //去除页面切换时的滑动翻页效果
//    @Override
//    public void setCurrentItem(int item, boolean smoothScroll) {
//        // TODO Auto-generated method stub
//        super.setCurrentItem(item, smoothScroll);
//    }

    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, false);
    }

}