package laoyou.com.laoyou.save;

import android.content.Context;
import android.content.SharedPreferences;

import laoyou.com.laoyou.application.MyApplication;


/**
 * Created by on 2017/5/13
 * Sp存储类;
 */
public class SPreferences {

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
     * 跳转标识符;
     */
    private static final String SKIP_FLAG = "Skip_Flag";
    /**
     * 第一次登录喜欢的游戏添加跳转;
     */
    private static final String LIKE_GAMES_STATUS = "Like_Games_Status";
    /**
     * 第一次启动标识符;
     */
    private static final String FIRST_START = "First_Start";
    /**
     * 系统客服腾讯云Id;
     */
    private static final String SYSTEM_SERVICE_ID = "Service_Id";


    /**
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
     * 保存第一次启动状态
     */
    public static void saveFirstStart(boolean flag) {
        saveBoolean(FIRST_START, flag);
    }

    /**
     * 获取第一次启动状态;
     */
    public static boolean getFirstStart() {
        return getBoolean(FIRST_START, true);
    }

    /**
     * 保存喜欢的游戏状态;
     */
    public static void saveLikeGamesStatus(boolean flag) {
        saveBoolean(LIKE_GAMES_STATUS, flag);
    }

    /**
     * 获取喜欢的游戏状态;
     */
    public static boolean getLikeGamesStatus() {
        return getBoolean(LIKE_GAMES_STATUS, false);
    }

    /**
     * 保存MainActivity 页面切换标识符;
     */
    public static void saveSkipFlag(int token) {
        saveInteger(SKIP_FLAG, token);
    }

    /**
     * 获取MainActivity 页面切换标识符;
     */
    public static int getSkipFlag() {
        return getInteger(SKIP_FLAG);
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
     * 读取int方法;
     *
     * @param key
     * @return boolean
     */
    private static int getInteger(String key) {
        return getSharedPreferences().getInt(key, 1);
    }

    /**
     * 读取boolean方法;
     *
     * @param key
     * @return boolean
     */
    private static boolean getBoolean(String key, boolean b) {
        return getSharedPreferences().getBoolean(key, b);
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
     * 保存int方法;
     *
     * @param key
     * @param value
     */
    private static void saveInteger(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 通用SharedPreferences方法;
     */
    static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences("Hoop", Context.MODE_PRIVATE);
    }


}
