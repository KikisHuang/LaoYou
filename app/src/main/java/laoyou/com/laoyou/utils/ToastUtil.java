package laoyou.com.laoyou.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import laoyou.com.laoyou.bean.ServerCode;
import laoyou.com.laoyou.save.SPreferences;

import static laoyou.com.laoyou.bean.ServerCode.getCodeStatusMsg;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ToastUtil {

    /**
     * 中间提示
     *
     * @param context
     * @param str
     */
    public static void toast1_center(Context context, String str) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }
        Toast ts = Toast.makeText(context, str, 3000);
        ts.setGravity(Gravity.CENTER, 0, 0);
//		LinearLayout ll = new LinearLayout(context);
//		ll.setGravity(Gravity.CENTER);
//		TextView te = new TextView(context);
////		te.setBackgroundResource(drawable.topbackground);
//		te.setTextColor(Color.BLACK);
//		te.setPadding(10, 10, 10, 10);
//		te.setBackgroundColor(backColor);
//		LinearLayout.LayoutParams llip = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 80);
//		te.setText(str);
//		te.setGravity(Gravity.CENTER);
//		ll.addView(te,llip);
//		ts.setView(ll);
        ts.show();
    }

    /**
     * 底部提示
     *
     * @param context
     * @param str
     */
    public static void toast2_bottom(Context context, String str) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }
        if (str.isEmpty()) {
            str = "错误";
        }
        Toast ts = Toast.makeText(context, str, 3000);
        ts.show();
    }

    public static void toast2_bottom(Context context, String str, int time) {
        if (context == null) {
            System.out.println("context isnull, toast =" + str);
            return;
        }
        Toast ts = Toast.makeText(context, str, time);
        ts.show();
    }

    /**
     * 服务端返回异常信息
     * 如果本地存储异常code没有则toast服务端返回的msg;
     *
     * @param
     */
    public static String ErrorMsg(String src, int code) throws JSONException {
        //登录过期;
        if (code == ServerCode.code[6]) {
            SPreferences.saveUserToken("");
            return getCodeStatusMsg(code);
        } else
            return new JSONObject(src).optString("erroMsg");
    }
}
