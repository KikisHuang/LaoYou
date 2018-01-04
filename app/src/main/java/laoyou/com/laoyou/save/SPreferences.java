package laoyou.com.laoyou.save;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by on 2017/5/13
 * Sp存储类;
 */
public class SPreferences {

    public static Context context;
    /**
     * 用户登录token;
     */
    private static final String KEY_USER_TOKEN = "Laoyou_token";
    /**
     * 纬度;
     */
    private static final String LATITUD = "Laoyou_Latitud";
    /**
     * 经度;
     */
    private static final String LONGITUDE = "Laoyou_Longitude";
    /**
     * 地址信息;
     */
    private static final String ADDRESS = "Laoyou_Address";
    /**
     * 用户UUID(启动次数统计);
     */
    private static final String KEY_UUID = "User_UUID";
    /**
     * 腾讯IM账号;
     */
    private static final String IM_IDENTIFIER = "Im_Identifier";
    /**
     * 腾讯IMUserSig;
     */
    private static final String IM_USERSIG = "Im_UserSig";
    /**
     * 保存用户头像;
     */
    private static final String USER_HEAD_IMG = "User_Head_Img";
    /**
     * 保存用户名称;
     */
    private static final String USER_NAME = "User_Name";
    /**
     * 保存用户ID;
     */
    private static final String USER_ID = "User_Id";
    /**
     * 保存用户昵称;
     */
    private static final String USER_NICKNAME = "User_NickName";
    /**
     * 临时存储聊天对象头像(仅用于单聊);
     */
    private static final String TEMPORARY_USER_HEAD = "Temporary_User_Head";

    /**
     * /**
     * 保存登录Token;
     */
    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    /**
     * 获取登录Token;
     */
    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    /**
     * 保存用户ID;
     */
    public static void saveUserId(String token) {
        saveString(USER_ID, token);
    }

    /**
     * 获取用户ID;
     */
    public static String getUserId() {
        return getString(USER_ID);
    }
    /**
     * 保存用户昵称;
     */
    public static void saveMyNickName(String token) {
        saveString(USER_NICKNAME, token);
    }

    /**
     * 获取用户昵称;
     */
    public static String getMyNickName() {
        return getString(USER_NICKNAME);
    }

    /**
     * 保存用户头像;
     */
    public static void saveTemporaryImg(String str) {
        saveString(TEMPORARY_USER_HEAD, str);
    }

    /**
     * 获取用户头像;
     */
    public static String getTemporaryImg() {
        return getString(TEMPORARY_USER_HEAD);
    }

    /**
     * 保存腾讯Im账号;
     */
    public static void saveIdentifier(String str) {
        saveString(IM_IDENTIFIER, str);
    }

    /**
     * 获取Im账号;
     */
    public static String getIdentifier() {
        return getString(IM_IDENTIFIER);
    }

    /**
     * 保存腾讯ImUserSig;
     */
    public static void saveUserSig(String str) {
        saveString(IM_USERSIG, str);
    }

    /**
     * 获取ImUserSIg;
     */
    public static String getUserSig() {
        return getString(IM_USERSIG);
    }


    /**
     * 保存用户头像;
     */
    public static void saveUserHeadImg(String str) {
        saveString(USER_HEAD_IMG, str);
    }

    /**
     * 获取用户头像;
     */
    public static String getUserHeadImg() {
        return getString(USER_HEAD_IMG);
    }

    /**
     * 保存用户名称;
     */
    public static void saveUserName(String str) {
        saveString(USER_NAME, str);
    }

    /**
     * 获取用户名称;
     */
    public static String getUserName() {
        return getString(USER_NAME);
    }

    /**
     * 保存地址信息
     */
    public static void saveAddress(String latitud) {
        saveString(ADDRESS, latitud);
    }

    /**
     * 获取地址信息;
     */
    public static String getAddress() {
        return getString(ADDRESS);
    }

    /**
     * 保存纬度
     */
    public static void saveLatitud(String latitud) {
        saveString(LATITUD, latitud);
    }

    /**
     * 获取纬度;
     */
    public static String getLatitud() {
        return getString(LATITUD);
    }

    /**
     * 保存经度
     */
    public static void saveLongitude(String longitude) {
        saveString(LONGITUDE, longitude);
    }

    /**
     * 获取经度;
     */
    public static String getLongitude() {
        return getString(LONGITUDE);
    }

    /**
     * 读取String方法;
     *
     * @param key
     * @return String
     */
    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * 读取boolean方法;
     *
     * @param key
     * @return boolean
     */
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }

    /**
     * 获取UUID;
     */
    public static String getUserUUID() {
        return getString(KEY_UUID);
    }

    /**
     * 保存UUID;
     */
    public static void saveUserUUID(String uuid) {
        saveString(KEY_UUID, uuid);
    }

    /**
     * 保存boolean方法;
     *
     * @param key
     * @param value
     */
    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存String方法;
     *
     * @param key
     * @param value
     */
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 通用SharedPreferences方法;
     */
    static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("WlGx", Context.MODE_PRIVATE);
    }

    /**
     * 获取全局Context方法;
     *
     * @param context
     */
    public static void setContext(Context context) {
        SPreferences.context = context.getApplicationContext();
    }


}
