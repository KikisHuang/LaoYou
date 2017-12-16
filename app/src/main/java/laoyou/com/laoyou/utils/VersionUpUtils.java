package laoyou.com.laoyou.utils;

import laoyou.com.laoyou.listener.VersionListener;
import laoyou.com.laoyou.save.SPreferences;

/**
 * Created by lian on 2017/12/15.
 */
public class VersionUpUtils {
    /**
     * 检测版本;
     *
     * @param news
     * @param listener
     */
    public static void VersionCheck(int news, VersionListener listener) {
        int old = SynUtils.getVersionCode(SPreferences.context);
        if (news == old)
            listener.onVersionUp();
        else
            listener.onVersionMatching();
    }
}
