package laoyou.com.laoyou.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.save.SPreferences;

import static laoyou.com.laoyou.utils.IntentUtils.goRegisterPage;


/**
 * Created by lian on 2017/6/7.
 * 杂项方法工具类;
 */
public class SynUtils {
    private static final String TAG = getTAG(SynUtils.class);
    public static Timer timer;
    private static String[] Sex = {"女", "男"};
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * String中取出int类型;
     *
     * @param str
     * @return
     */
    public static String StrGetInt(String str) {
        str = str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }

    /**
     * 软键盘弹出方法(针对某些界面软键盘不自动弹出);
     * 如果软键盘是隐藏状态就显示,反之则隐藏;
     *
     * @param editText
     * @param context
     * @return
     */
    public static void ShowofHideSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    /**
     * 获取String通用方法;
     */
    public static String getRouString(int id) {
        return SPreferences.context.getResources().getString(id);
    }

    /**
     * 获取Layout通用方法;
     */
    public static XmlResourceParser getRouLayout(int id) {
        return SPreferences.context.getResources().getLayout(id);
    }

    /**
     * 获取Colors通用方法;
     */
    public static int getRouColors(int id) {

        return SPreferences.context.getResources().getColor(id);
    }

    /**
     * 获取Drawable通用方法;
     */
    public static Drawable getRouDrawable(int id) {

        return SPreferences.context.getResources().getDrawable(id);
    }

    /**
     * 登录判断通用方法;
     */
    public static boolean LoginStatusQuery() {
        if (SPreferences.getUserToken() == null || SPreferences.getUserToken().equals("")) {
            return false;
        } else {
//            Log.i(TAG, "SPreferences.getUserToken() =====" + SPreferences.getUserToken());
            return true;
        }
    }


    public static Object getSex(Object object) {
        if (object instanceof Integer) {
            if (Sex.length > 0)
                return Sex[(int) object];
            else
                Sex = new String[]{"女", "男"};
            return Sex[(int) object];
        }
        if (object instanceof String) {
            if (Sex.length > 0) {
                for (int i = 0; i < Sex.length; i++) {
                    if (object.equals(Sex[i]))
                        return i;
                }
            } else {
                Sex = new String[]{"女", "男"};
                for (int i = 0; i < Sex.length; i++) {
                    if (object.equals(Sex[i]))
                        return i;
                }
            }
        }
        return null;
    }


    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                Log.i(TAG, "qq pn = " + pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                Log.i(TAG, "wechat pn = " + pn);
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static synchronized int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);//获取包对象信息
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static synchronized String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Wifi环境判断
     *
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

//    /**
//     * 判断路径后缀;
//     */
//    public static boolean getSuffix(String name) {
//        String a = name.substring(name.lastIndexOf(".") + 1);
//        Log.i(TAG, "gif =======" + a);
//        if (a.equals("gif"))
//            return false;
//        else
//            return true;
//    }

    /**
     * 网络连接判断
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取包名.类名 通用方法;
     *
     * @param Class 类
     * @return
     */
    public static String getTAG(Class Class) {
        return Class.getName();
    }

    /**
     * 过千简略显示方法;
     *
     * @param num
     * @return
     */
    public static String KswitchWay(double num) {
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat df1 = new DecimalFormat("######0");

        String a = "";
        if (num > 1000) {
            double c = num / 1000;
            a = String.valueOf(df.format(c)) + "k";
        } else
            a = String.valueOf(df1.format(num));

        return a;
    }

    /**
     * 过万简略显示方法;
     *
     * @param num
     * @return
     */
    public static String WswitchWay(double num) {
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat df1 = new DecimalFormat("######0");

        String a = "";
        if (num > 10000) {
            double c = num / 10000;
            a = String.valueOf(df.format(c)) + "万";
        } else
            a = String.valueOf(df1.format(num));

        return a;
    }

    /**
     * 过千解析方法;
     *
     * @param str
     * @return
     */
    public static String ParseK(String str, boolean add) {
        if (str.length() > 0) {
            if (add) {
                if (str.indexOf("k") != -1)
                    return KswitchWay(Double.valueOf(Double.valueOf(str.replace("k", "")) * 1000 + 1));
                else
                    return String.valueOf(Integer.valueOf(str.replace("k", "")) + 1);
            } else {
                if (str.indexOf("k") != -1)
                    return KswitchWay(Double.valueOf(Double.valueOf(str.replace("k", "")) * 1000 - 1));
                else
                    return String.valueOf(Integer.valueOf(str.replace("k", "")) - 1);
            }
        }
        return "";
    }

    /**
     * int判断通用方法;
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取通知栏权限是否开启
     */

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 进入设置系统应用权限界面;
     * 方法1;
     *
     * @param context
     */
    public static void requestPermission(Context context) {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 进入设置系统应用权限界面;
     * 方法2;
     *
     * @param context
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }


    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     */
    public static int getAppSatus(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

    /**
     * 判断intent是否存在;
     *
     * @param context
     * @param action
     * @return
     */
    public static boolean isIntentExisting(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {

            return true;
        }
        return false;
    }

    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
            Log.i(TAG, "删除成功");
        } else {
            Log.i(TAG, "文件不存在");
        }
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public static void Finish(Activity activity) {
    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            activity.finishAfterTransition();
        else*/
        activity.finish();
    }

    public static void ChangeTextViewColors(String content, String color, int start, int end, TextView tv) {
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    /**
     * 随机抓取视频帧数图片;
     *
     * @param filePath
     * @return
     */
    public static Bitmap getVideoThumbnail(String filePath, int timeMs, int len) {
        Bitmap bitmap = null;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(filePath);
            for (long i = timeMs; i < len; i += 1000) {
                bitmap = retriever.getFrameAtTime(i * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (bitmap != null) {
                    retriever.release();
                    break;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return bitmap;
    }

    /**
     * 获取视频毫秒;
     *
     * @param mUri
     * @return
     */
    public static int getRingDuring(String mUri) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        int time = 0;
        try {
            mediaPlayer.setDataSource(mUri);

            mediaPlayer.prepare();
            time = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, " time ====" + time);
        return time;
    }

    /**
     * 删除文件夹所有文件;
     *
     * @param pPath
     * @return
     */
    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        Log.i(TAG, "删除文件路径 ===" + pPath);
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }


    /**
     * 将bitmap保存到本地;
     *
     * @param bmp
     * @return File
     */
    public static String saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            return null;
        }

        return appDir + "/" + fileName;
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
     * 广告开启停止通用方法;
     *
     * @param handler
     * @param mViewPager
     */
    private static PageTopTask task;

    //  设置自动播放;

    public static void startPlay(Handler handler, ViewPager mViewPager, int flag) {

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(task = new PageTopTask(handler, mViewPager, flag), 3000, 3000);
    }

    public static void stopPlay() {
        if (timer != null && task != null) {
            timer.cancel();
            task.Close(task);
            task = null;
            timer = null;
        }
    }

    /**
     * 隐藏手机号码中间部分;
     *
     * @param pNumber
     * @return
     */
    public static String getHidePhone(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 开发中;
     */
    public static void Indevelopment(Context context) {
        ToastUtil.toast2_bottom(context, "功能开发中,尽情期待...");
    }

    /**
     * 获取本地SHA1 方法;
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}