package laoyou.com.laoyou.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import laoyou.com.laoyou.save.SPreferences;


/**
 * Created by lian on 2017/6/15.
 */
public class JsonUtils {
    /**
     * 获取Json数组通用方法;
     *
     * @param str
     * @return
     * @throws JSONException
     */
    public static JSONArray getJsonAr(String str) throws JSONException {
        JSONArray ar = new JSONObject(str).optJSONArray("data");
        return ar;
    }

    /**
     * 获取JsonObject通用方法;
     *
     * @param str
     * @return
     * @throws JSONException
     */
    public static JSONObject getJsonOb(String str) throws JSONException {
        JSONObject ob = new JSONObject(str).optJSONObject("data");
        return ob;
    }

    /**
     * 获取String通用方法;
     *
     * @param str
     * @return
     * @throws JSONException
     */
    public static String getJsonSring(String str) throws JSONException {
        String s = new JSONObject(str).optString("data");
        return s;
    }

    /**
     * 获取Int通用方法;
     *
     * @param str
     * @return
     * @throws JSONException
     */
    public static int getJsonInt(String str) throws JSONException {
        int s = new JSONObject(str).optInt("data");
        return s;
    }

    /**
     * Json空判断通用方法;
     *
     * @param ob JSONObject
     * @param key 值
     */
    public static String NullDispose(JSONObject ob, String key) {
        if (ob.isNull(key))
            return "";
        else
            return ob.optString(key);
    }

    /**
     * 获取用户key通用方法;
     *
     * @return
     */
    public static Map<String, String> getKeyMap() {
        Map<String, String> map = new HashMap<>();
        map.put(Fields.KEY, SPreferences.getUserToken());
        return map;
    }
    /**
     * 获取Code通用方法;
     *
     * @return
     */
    public static int getCode(String str) throws JSONException {
        int code = new JSONObject(str).optInt("code");
        return code;
    }
}
