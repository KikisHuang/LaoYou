package laoyou.com.laoyou.utils;

import android.os.Handler;
import android.support.v4.view.ViewPager;

import java.util.TimerTask;

import laoyou.com.laoyou.fragment.HomeFragment;

/**
 * Created by lian on 2017/5/4.
 * 广告Banner切换通用方法类;
 */
public class PageTopTask extends TimerTask {
    private static final String TAG = "PageTopTask";
    private Handler handler;
    private ViewPager mViewPager;
    /**
     * 广告页面标识符;
     * flag
     * 0: PageFragment
     * 1: StoreFragment2
     * 2: VRFragment
     * 3: BuygoodsActivity
     */
    private int flag;

    public PageTopTask(Handler handler, ViewPager mViewPager, int flag) {
        this.handler = handler;
        this.mViewPager = mViewPager;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (mViewPager != null) {
//            Log.i(TAG, "PageTopTask run ...  flag ==" + flag);
            if (mViewPager.isShown() && System.currentTimeMillis() - Fields.TouchTime > 3000) {
                if (flag == 0)
                    if (HomeFragment.polistener != null)
                        HomeFragment.polistener.onIncrease();
                try {
                    if (handler != null)
                        handler.sendEmptyMessage(1);
                } catch (Exception e) {

                }
            }
        } else
            cancel();
    }

    /**
     * 关闭方法（防止内存泄露）;
     *
     * @param task
     */
    public void Close(PageTopTask task) {
        if (handler != null) {
            handler.removeCallbacks(task);
            handler = null;
        }
    }
}
