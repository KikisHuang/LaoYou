package laoyou.com.laoyou.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import laoyou.com.laoyou.activity.AddressbookActivity;
import laoyou.com.laoyou.activity.CertificationActivity;
import laoyou.com.laoyou.activity.ChangePassWordActivity;
import laoyou.com.laoyou.activity.ForgetPasswordActivity;
import laoyou.com.laoyou.activity.LoginActivity;
import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.activity.OutSideActivity;
import laoyou.com.laoyou.activity.OverInfoActivity;
import laoyou.com.laoyou.activity.QueryActivity;
import laoyou.com.laoyou.activity.RegisterActivity;
import laoyou.com.laoyou.activity.SendPhoneCodeActivity;
import laoyou.com.laoyou.activity.WifiActivity;

/**
 * Created by lian on 2017/10/25.
 */
public class IntentUtils {
    private static final String TAG = "IntentUtils";

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginOperPage(Context context) {

        Intent intent = new Intent(context, LoginOperationActivity.class);
        startPage(context, intent);
    }

    /**
     * 登录页面;
     *
     * @param context 上下文;
     */
    public static void goLoginPage(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET2);
    }

    /**
     * 实名认证页面;
     *
     * @param context 上下文;
     */
    public static void goCertificationPage(Context context) {
        Intent intent = new Intent(context, CertificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET3);
    }

    /**
     * 忘记密码页面;
     *
     * @param context 上下文;
     */
    public static void goForGetPassPage(Context context, String phone) {

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra("forget_phone", phone);
        startPage(context, intent);
    }

    /**
     * 发送验证码页面;
     *
     * @param context 上下文;
     */
    public static void goSendPhoneCodePage(Context context) {

        Intent intent = new Intent(context, SendPhoneCodeActivity.class);
        startPage(context, intent);
    }

    /**
     * 注册页面;
     *
     * @param context 上下文;
     */
    public static void goRegisterPage(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET2);
    }

    /**
     * 修改密码页面;
     *
     * @param context 上下文;
     */
    public static void goChangePassPage(Context context) {
        Intent intent = new Intent(context, ChangePassWordActivity.class);
        startPage(context, intent);
    }

    /**
     * 外链页面;
     *
     * @param context 上下文;
     */
    public static void goOutSidePage(Context context, String url) {
        Intent intent = new Intent(context, OutSideActivity.class);
        intent.putExtra("outside_url", url);
        startPage(context, intent);
    }

    /**
     * 获取Wifi页面;
     *
     * @param context 上下文;
     */
    public static void goWifiPage(Context context) {
        Intent intent = new Intent(context, WifiActivity.class);
        startPage(context, intent);
    }

    /**
     * 查询密码页面;
     *
     * @param context 上下文;
     */
    public static void goQueryPassPage(Context context) {
        Intent intent = new Intent(context, QueryActivity.class);
        startPage(context, intent);
    }

    /**
     * 通讯录页面;
     *
     * @param context 上下文;
     */
    public static void goAddressBookPage(Context context) {
        Intent intent = new Intent(context, AddressbookActivity.class);
        startPage(context, intent);
    }

    /**
     * 完善资料页面;
     *
     * @param context 上下文;
     * @param sex
     */
    public static void goOverInfoPage(Context context, String phone, String pass, String code, int sex) {

        Intent intent = new Intent(context, OverInfoActivity.class);
        intent.putExtra("register_phone_of_headimg", phone);
        intent.putExtra("register_pass_of_name", pass);
        intent.putExtra("register_code", code);
        intent.putExtra("register_sex", sex + "");

//        startPage(context, intent);
        ((Activity) context).startActivityForResult(intent, Fields.ACRESULET1);
    }

    /**
     * 跳转通用方法;
     *
     * @param context
     * @param intent
     */
    private static void startPage(Context context, Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
//            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            context.startActivity(intent);
        else
            context.startActivity(intent);
    }

}
