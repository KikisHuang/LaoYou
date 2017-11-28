package laoyou.com.laoyou.utils;

import android.app.Activity;
import android.os.Handler;

import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.SpringListener;

import static laoyou.com.laoyou.utils.SynUtils.getRouColors;


/**
 * Created by lian on 2017/5/26.
 * 刷新控件初始化;
 */
public class SpringUtils {

    public static void SpringViewInit(final SpringView springView, final Activity ac, final SpringListener springListener) {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new MeituanHeader(ac, Fields.pullAnimSrcs, Fields.refreshAnimSrcs));
        springView.setFooter(new MeituanFooter(ac, Fields.loadingAnimSrcs));
        springView.setBackgroundColor(getRouColors(R.color.white));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonRefresh(0);
                    }
                }, 500);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonLoadmore(10);
                    }
                }, 500);
            }
        });
    }
}
