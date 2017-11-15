package laoyou.com.laoyou.utils;

/**
 * Created by lian on 2017/10/25.
 */
public class Fields {

    public static String KEY = "key";

    public static final String WECHATAPPID = "wx16258856fb255f4b";
    public static final String WECHATAPPSECRET = "b92df5eccddc26af53b6e02cc692087b";


    public static final int REQUEST1 = 348851;
    public static final int REQUEST2 = 348852;
    public static final int REQUEST3 = 348853;
    public static final int REQUEST4 = 348854;
    public static final int REQUEST5 = 348855;


    public static final int ACRESULET1 = 3213;
    public static final int ACRESULET2 = 3121;
    public static final int ACRESULET3 = 3211;


    public static final String PASSNULLMSG = "密码不能为空！！！";
    public static final String PHONENULLMSG = "手机号码不能为空！！！";
    public static final String HEADNULLMSG = "头像不能为空！！！";
    public static final String NICKNAMENULLMSG = "昵称不能为空！！！";
    public static final String NAMENULLMSG = "姓名不能为空！！！";
    public static final String IDNULLMSG = "身份证号不能为空！！！";
    public static final String FRONTIDNULLMSG = "身份证正面照不能为空！！！";
    public static final String TAILIDNULLMSG = "身份证反面照不能为空！！！";
    public static final String HANDIDNULLMSG = "手持证件照不能为空！！！";
    public static final String PHONEUNCORRECTMSG = "请输入正确的手机号！！！";
    public static final String CODENULLMSG = "验证码不能为空！！！";
    public static final String CHANGEPASSOK = "修改密码成功！！！";
    public static final String LOGINOK = "登录成功！！！";
    public static final String NETWORKERROR = "网络不顺畅...";
    public static final String DONTGETCODE = "请先获取验证码！";
    public static final String REGISTERSUCCEED = "注册成功！！";
    public static final String IDUPLOADSUCCEED = "提交审核成功，通过后即显示密码。";
    public static final String OLDPASSNULLMSG = "旧密码不能为空！！！";
    public static final String NEWPASSNULLMSG = "新密码不能为空！！！";
    public static final String WECHATLOGINERROR = "微信登录授权失败";


    /**
     * 全局广告触摸时间变量;
     */
    public static long TouchTime = 0;
    public static boolean CHECKFLAG = true;
    public static double Latitud = 0;
    public static double Longitude = 0;
    public static String address = "";


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
