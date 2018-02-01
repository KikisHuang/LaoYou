package laoyou.com.laoyou.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import laoyou.com.laoyou.activity.LoginOperationActivity;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.tencent.model.UserInfo;
import laoyou.com.laoyou.tencent.presentation.event.MessageEvent;
import laoyou.com.laoyou.tencent.service.TlsBusiness;


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
        return MyApplication.getContext().getResources().getString(id);
    }

    /**
     * 获取Layout通用方法;
     */
    public static XmlResourceParser getRouLayout(int id) {
        return MyApplication.getContext().getResources().getLayout(id);
    }

    /**
     * 获取Colors通用方法;
     */
    public static int getRouColors(int id) {

        return MyApplication.getContext().getResources().getColor(id);
    }

    /**
     * 获取Drawable通用方法;
     */
    public static Drawable getRouDrawable(int id) {

        return MyApplication.getContext().getResources().getDrawable(id);
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
     * Agency FB Bold 字体
     */
    private static Typeface typeface;

    public static Typeface getTypeface(Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Agency_FB_Bold.ttf");
        }
        return typeface;
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
     * 字母判断;
     */
    public static boolean isChinesePunctuation(String str) {
        if (str.matches("^[A-Za-z]+$"))
            return true;
        else
            return false;
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

   /* *//**
     * 进入设置系统应用权限界面;
     * 方法1;
     *
     * @param context
     *//*
    public static void requestPermission(Context context) {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }*/

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
    public static String saveImage(Bitmap bmp, String fileName) {

        File appDir = new File(Environment.getExternalStorageDirectory(), "Hoop_Photo");

        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
        fileName += ".jpg";
        File file = new File(appDir, fileName);

        if (file.exists()) {
            Log.i(TAG, "图片已存在本地无需保存");
            return appDir + "/" + fileName;
        }
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


    //判断网圈照片存储文件夹中的图片是否存在;
    public static boolean fileIsExists(String strFile) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Hoop_Photo");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        try {
            strFile = strFile.substring(strFile.lastIndexOf("/") + 1, strFile.lastIndexOf("."));
            strFile += ".jpg";
            File f = new File(appDir, strFile);
            if (!f.exists())
                return false;

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 保存文件到指定路径,并刷新系统相册;
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hoop";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    /**
     * 根据资源的名字获取其ID值
     */
    public static int getIdByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class r = null;
        int id = 0;

        try {
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }
            if (desireClass != null) {
                id = desireClass.getField(name).getInt(desireClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * @param phoneNumber 手机号
     * @return 有效则返回true, 无效则返回false
     * @function 判断手机号是否有效
     */
    public static boolean validPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 11 && phoneNumber.matches("[0-9]{1,}");
    }

    private static final double EARTH_RADIUS = 6378137.0;

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 过千简略显示方法;
     *
     * @param num
     * @return
     */
    public static String getDistanceKM(double num) {
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat df1 = new DecimalFormat("######0");

        String a = "";
        if (num > 1000) {
            double c = num / 1000;
            a = String.valueOf(df.format(c)) + "km";
        } else
            a = String.valueOf(df1.format(num));
        return a;
    }

    /**
     * 判断是否为汉字;
     *
     * @param str
     * @return
     */
    public static boolean ChineseJudge(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * 获取GetString 通用方法;
     *
     * @param res
     * @return
     */
    public static String gets(int res) {
        return MyApplication.getContext().getString(res);
    }

    /**
     * 登出通用方法;
     *
     * @param context
     * @param relogin
     * @return
     */
    public static void LogOut(Context context, boolean relogin) {
        SPreferences.saveUserToken("");
        SPreferences.saveIdentifier("");
        SPreferences.saveUserSig("");
        SPreferences.saveUserId("");

        TlsBusiness.logout(UserInfo.getInstance().getId());
        UserInfo.getInstance().setId(null);
        MessageEvent.getInstance().clear();
        FriendshipInfo.getInstance().clear();
        GroupInfo.getInstance().clear();
        if (relogin) {
            Intent intent = new Intent(context, LoginOperationActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 根据id判断是否用户自己;
     *
     * @param id
     * @return
     */
    public static boolean IsMe(String id) {
        return id.equals(SPreferences.getUserId()) ? true : false;
    }

    /**
     * 字段非空判断;
     *
     * @param str
     * @return
     */
    public static boolean IsNull(String str) {
        return str == null || str.isEmpty() ? true : false;
    }
    //view 转bitmap

    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }

    //方法二
    public static String replaceBlank(String str) {
        Pattern p = Pattern.compile("//s*|/t|/r|/n");
        Matcher m = p.matcher(str);
        String after = m.replaceAll("");

        return after;
    }

    /**
     * 非空判断;
     *
     * @param ar
     * @return
     */
    public static boolean ArrayIsNull(JSONArray ar) {
        return ar.length() > 0 ? false : true;
    }

    public static String StringIsNull(String str) {
        return str != null && !str.isEmpty() && !str.equals("null") ? str : "";
    }

    public static List<Map<String, Object>> GetAllContact() throws Throwable {
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = MyApplication.getContext().getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //构造联系人信息
            sb.append("contactId=").append(contactId).append(",Name=").append(name);
            map.put("name", name);
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));//联系人ID


            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                sb.append(",Phone=").append(phoneNumber);
                map.put("mobile", phoneNumber);
            }
            phones.close();


           /* //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            while (emails.moveToNext()) {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                sb.append(",Email=").append(emailAddress);
                Log.e("emailAddress", emailAddress);
                map.put("email", emailAddress);


            }
            emails.close();*/

           /* //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
            Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                    null, null);
            while (address.moveToNext()) {
                String workAddress = address.getString(address.getColumnIndex(
                        ContactsContract.CommonDataKinds.StructuredPostal.DATA));


                //添加Email的信息
                sb.append(",address").append(workAddress);
                map.put("address", workAddress);
            }
            address.close();*/

            /*//查询==公司名字==类型的数据操作.Organization.COMPANY  ContactsContract.Data.CONTENT_URI
            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
            String[] orgWhereParams = new String[]{id,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
            Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                    null, orgWhere, orgWhereParams, null);
            if (orgCur.moveToFirst()) {
                //组织名 (公司名字)
                String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                //职位
                String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                sb.append(",company").append(company);
                sb.append(",title").append(title);
                map.put("company", company);
                map.put("title", title);
            }
            orgCur.close();*/
            list.add(map);
//            Log.i("=========orgName=====", sb.toString());//查看所有的数据
            Log.e("=========map=====", map.toString());//有很多数据的时候，只会添加一条  例如邮箱，
        }

        Log.i("=========list=====", list.toString());//
        cursor.close();
        return list;
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) MyApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * ListView滑动到顶部、底部精准判断;
     * int top = 0 ,bottom = 1;
     */
    public static int IsListViewTopOfBottom(int firstVisibleItem, int visibleItemCount, int totalItemCount, ListView mListView) {

        if (firstVisibleItem == 0) {
            View firstVisibleItemView = mListView.getChildAt(0);
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
//                Log.d("ListView", "##### 滚动到顶部 #####");
                return Fields.IsTop;
            }
        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            View lastVisibleItemView = mListView.getChildAt(mListView.getChildCount() - 1);
            if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListView.getHeight()) {
                Log.d("ListView", "##### 滚动到底部 ######");
                return Fields.IsBottom;
            }
        }
        return 99;
    }


    /**
     * @param f 0、LinearLayout  1、FrameLayout  2、RelativeLayout
     * @param w
     * @param h
     * @return
     */

    public static Object getLayout(int f, int w, int h) {

        switch (f) {
            case 0:
                return new LinearLayout.LayoutParams(w, h);
            case 1:
                return new FrameLayout.LayoutParams(w, h);
            case 2:
                return new RelativeLayout.LayoutParams(w, h);
        }
        return null;
    }
}