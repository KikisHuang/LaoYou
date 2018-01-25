package laoyou.com.laoyou.tencent.service;

import android.content.Context;
import android.widget.Toast;

import tencent.tls.platform.TLSErrInfo;

/**
 * Created by dgy on 15/7/10.
 * 包含一些辅助方法
 */
public class Util {

    /**
     * @function 将国家码和手机号拼接成86-15112345678的形式
     * @param countryCode 国家码
     * @param phoneNumber 手机号
     * @return 返回拼接后的字符串
     * */
    public static String getWellFormatMobile(String countryCode, String phoneNumber) {
        return countryCode + "-" + phoneNumber;
    }

    /**
     * @function 判断手机号是否有效
     * @param phoneNumber 手机号
     * @return 有效则返回true, 无效则返回false
     * */
    public static boolean validPhoneNumber(String countryCode, String phoneNumber) {
        if (countryCode.equals("86"))
            return phoneNumber.length() == 11 && phoneNumber.matches("[0-9]{1,}");
        else
            return phoneNumber.matches("[0-9]{1,}");
    }

    /**
     * @function: 对屏幕中间显示一个Toast，其内容为msg
     * */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @function: 显示使用TLSSDK过程中发生的错误信息
     * */
    public static void notOK(Context context, TLSErrInfo errInfo) {
        showToast(context, String.format("%s: %s",
                errInfo.ErrCode == TLSErrInfo.TIMEOUT ?
                        "网络超时" : "错误", errInfo.Msg));
    }

}
