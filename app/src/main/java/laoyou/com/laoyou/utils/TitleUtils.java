package laoyou.com.laoyou.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.save.SPreferences;

import static laoyou.com.laoyou.utils.IntentUtils.goRegisterPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/12.
 */
public class TitleUtils {

    // 处理标题栏颜色渐变
    public static void handleTitleBarColorEvaluate(int height, int imageHeight, View title_layout, ImageView back_img, ImageView more_img) {
        //比例
        float fraction;
        if (height > 0) {

            fraction = 1f - height * 1f / 60;
            if (fraction < 0f) {
                fraction = 0f;
            }
//            title_layout.setAlpha(fraction);
            return;
        }

        //高度值是负数，但是负号仅仅是表示方向，取绝对值
        float space = Math.abs(height) * 1f;
        // 标题栏的高度
        fraction = space / imageHeight;
        if (fraction < 0f)
            fraction = 0f;
        if (fraction > 1f)
            fraction = 1f;
        title_layout.setAlpha(1f);

        if (fraction >= 1f) {
            title_layout.setBackgroundColor(Color.parseColor(gets(R.color.whiteff)));
        } else {
            //根据比例，生成一个按比例的颜色值
            title_layout.setBackgroundColor(getNewColorByStartEndColor(SPreferences.context, fraction, R.color.transparent, R.color.white));
        }

        if (fraction >= 0.8f) {
            back_img.setImageResource(R.mipmap.return_icon);
            if (more_img != null)
                more_img.setImageResource(R.mipmap.more_blue);
        } else {
            back_img.setImageResource(R.mipmap.return_icon_white);
            if (more_img != null)
                more_img.setImageResource(R.mipmap.more_white);
        }
    }


    // 成新的颜色值
    public static int getNewColorByStartEndColor(Context context, float fraction, int startValue, int endValue) {
        return evaluate(fraction, context.getResources().getColor(startValue), context.getResources().getColor(endValue));
    }

    /**
     * 成新的颜色值
     *
     * @param fraction   颜色取值的级别 (0.0f ~ 1.0f)
     * @param startValue 开始显示的颜色
     * @param endValue   结束显示的颜色
     * @return 返回生成新的颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 子页面标题设置;
     *
     * @param ac
     */
    public static void setTitles(final Activity ac) {

        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
        ac.findViewById(R.id.register_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterPage(ac);
                ac.finish();
            }
        });
    }

    /**
     * 子页面标题设置;
     *
     * @param ac
     */
    public static void setImgTitles(final Activity ac) {

        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
    }

    /**
     * 子页面标题设置2;
     *
     * @param ac
     */
    public static void setTitlesAndBack(final Activity ac, String back, String titles) {

        ac.findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
        TextView btv = (TextView) ac.findViewById(R.id.back_tv);
        btv.setText(back);
        btv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });
        TextView title = (TextView) ac.findViewById(R.id.register_tv);
        title.setText(titles);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterPage(ac);
                ac.finish();
            }
        });
    }

}
