package laoyou.com.laoyou.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.UUID;

import laoyou.com.laoyou.application.MyApplication;

import static laoyou.com.laoyou.save.SPreferences.getUserUUID;
import static laoyou.com.laoyou.save.SPreferences.saveUserUUID;

/**
 * Created by Forrest on 16/5/4.
 */
public class DeviceUtils {
    public static final int DEVICE_SCALE = 640;
    private static final String TAG = "DeviceUtils";
    public static int param = 0;

    /**
     * 获取屏幕宽和高
     *
     * @param context
     * @return
     */
    public static int[] getScreenHW(Context context) {
        int[] hw = new int[3];
        try {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            hw[0] = dm.widthPixels;//屏幕宽带(像素)
            hw[1] = dm.heightPixels;//屏幕高度(像素)
            hw[2] = dm.densityDpi;//屏幕密度(120/160/240)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hw;
    }

    /**
     * 手机屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getWindowWidth(Context ctx) {
        Display display = ((WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
     * version >= 4.2.2
     */
    public static int getRatio(Context context, boolean wid) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        if (wid)
            return width;
        else
            return height;
    }

    /**
     * 全局Banner高度比例;
     *
     * @return
     */
    public static int BannerHeight(Context context) {

        return DeviceUtils.getWindowWidth(context) * 4 / 10;
    }

    /**
     * 手机屏幕高度
     *
     * @param ctx
     * @return
     */
    public static int getWindowHeight(Context ctx) {

        Display display = ((WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 单次测量view控件宽高方法;
     *
     * @param flag 0高,1宽;
     * @param view 需要测量的view控件;
     * @return
     */
    public static int getViewHeightOfWidth(final int flag, final View view) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        param = 0;
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (flag == 0)
                    param = view.getHeight();
                else
                    param = view.getWidth();
            }
        });
        return param;
    }


    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取IMEI
     *
     * @return
     */
    public static synchronized String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.getContext().getSystemService(MyApplication.getContext().TELEPHONY_SERVICE);
        String ID = TelephonyMgr.getDeviceId();
        if (ID == null || ID.isEmpty())
            ID = getUUID();
        return ID;
    }
    /**
     * 得到全局唯一UUID
     */
    public static synchronized String getUUID() {
        String uuid = getUserUUID();
        if (uuid == null || uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
            saveUserUUID(uuid);
        } else
            return uuid;
        return uuid;
    }
}
