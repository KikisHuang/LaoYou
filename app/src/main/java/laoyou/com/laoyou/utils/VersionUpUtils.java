package laoyou.com.laoyou.utils;

import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.listener.VersionListener;

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
        int old = SynUtils.getVersionCode(MyApplication.getContext());
        if (news > old)
            listener.onVersionUp();
        else
            listener.onVersionMatching();
    }
}
