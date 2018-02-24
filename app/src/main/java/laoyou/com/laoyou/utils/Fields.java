package laoyou.com.laoyou.utils;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.TemporaryBean;

/**
 * Created by lian on 2017/10/25.
 */
public class Fields {

    public static List<TemporaryBean> temporary = new ArrayList<>();

    public static String KEY = "key";
    public static long VPT = 0;

    public static final boolean DEBUG = true;
    public static final int MAXWIDTH = 540;
    public static final int MAXHEIGHT = 432;

    public static final String SYSTEM_SERVICE_ID = "f8603819a675402bb8cf4f2829ac08";

    public static final String WECHATAPPID = "wx16258856fb255f4b";
    public static final String WECHATAPPSECRET = "b92df5eccddc26af53b6e02cc692087b";
    public static final String PACKAGE_NAME = "laoyou.com.laoyou";

    public static final String QQAPPID = "101455644";
    public static final String QQAPPSECRET = "b94e759ecd0abfb670bf3a0dcae07922";

    public static final String VIDEOTYPE = "video/mp4";
    public static final String IMAGETYPE = "image/jpeg";

    public static final int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

    public static final int C2CCHAT = 2821;

    public static final int REQUEST1 = 348851;
    public static final int REQUEST2 = 348852;
    public static final int REQUEST3 = 348853;
    public static final int REQUEST4 = 348854;
    public static final int REQUEST5 = 348855;
    public static final int REQUEST6 = 348856;


    public static final int ACRESULET1 = 3213;
    public static final int ACRESULET2 = 3121;
    public static final int ACRESULET3 = 3211;
    public static final int ACRESULET4 = 3214;
    public static final int ACRESULET5 = 3215;

    public static final int IsBottom = (1 << 0);
    public static final int IsTop = (1 << 1);


    public static final String OFFICIAL = "official-";
    public static final String TGS = "@TGS#";

    public static final String br = "<br />";

    public static int[] welImgs = new int[]{R.drawable.wel_one, R.drawable.wel_two, R.drawable.wel_three};


    /**
     * 递增长度
     */
    public static final int SIZE = 10;

    /**
     * 全局广告触摸时间变量;
     */
    public static long TouchTime = 0;
    public static final int ISFIND = 32;
    public static final int ISGAME = 33;
    public static final int ISTOPIC = 34;
    public static boolean CHECKFLAG = true;
    public static String[] ADMINISTRATIVEAREA = {"香港", "澳门", "台湾"};

    public static String[] HEIGHT = {"150cm以下", "151~155cm", "156~160cm", "161~165cm", "166~170cm", "171~175cm", "176~180cm", "181~185cm", "186~190cm", "191cm及以上"};

    public static String getAdministrative(int i) {
        return ADMINISTRATIVEAREA[i];
    }

    private static final int[] DefaultBackGround = {R.drawable.random_backgroup1, R.drawable.random_backgroup2, R.drawable.random_backgroup3};

    public static int RandomBackGGround() {
        Random ra = new Random();
        return DefaultBackGround[ra.nextInt(DefaultBackGround.length)];
    }

    /**
     * Glide transformations 方法;
     *
     * 圆角;
     * .bitmapTransform(new RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000);
     * <p/>
     * 指定尺寸;
     * .override(w, h);
     * <p/>
     * 淡入加载效果;
     * .crossFade();
     * <p/>
     * 自定义动画;
     * .animate();
     * <p/>
     * 圆形;
     * .bitmapTransform(new CropCircleTransformation(context)).crossFade(200);
     * <p/>
     * 毛玻璃;
     * .bitmapTransform(new BlurTransformation(context, 25)).crossFade(1000);
     * <p/>
     * 显示模糊预加载;
     * .thumbnail(0.1f);
     * <p/>
     * 失败图片
     * .error(R.drawable.failed);
     * <p/>
     * 占位符 也就是加载中的图片，可放个gif
     * .placeholder(R.drawable.loading)
     */
}
