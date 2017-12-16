package laoyou.com.laoyou.utils;

/**
 * Created by lian on 2017/12/16.
 */
public class ClickUtils {
    private static long lastClickTime;
    private static long ClickTime = 1000; //500毫秒内按钮无效，这样可以控制快速点击，自己调整频率

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < ClickTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
