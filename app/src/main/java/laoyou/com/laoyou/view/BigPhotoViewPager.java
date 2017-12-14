package laoyou.com.laoyou.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by Bodyplus on 2016/4/18.
 * 解决  photoview 与viewpager 组合时 图片缩放的错误 ；异常：.IllegalArgumentException: pointerIndex out of range
 */
public class BigPhotoViewPager extends ViewPager {
    private static final String TAG = getTAG(BigPhotoViewPager.class);

    public BigPhotoViewPager(Context context) {
        super(context);
    }

    public BigPhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

}