package laoyou.com.laoyou.utils;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.save.SPreferences;

import static laoyou.com.laoyou.utils.SynUtils.fileIsExists;
import static laoyou.com.laoyou.utils.SynUtils.saveImage;
import static laoyou.com.laoyou.utils.VideoUtils.createVideoThumbnail;


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
     * @param ob  JSONObject
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

    public static Map<String, String> getParamsMap() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    public static Map<String, File> getFileMap() {
        Map<String, File> map = new HashMap<>();
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

    /**
     * 动态接口通用解析;
     *
     * @param ar
     * @param nblist
     * @return
     * @throws JSONException
     */
    public static List<TopicTypeBean> StatusPaser(JSONArray ar, List<TopicTypeBean> nblist) throws JSONException {

        if (ar.length() > 0) {
            for (int i = 0; i < ar.length(); i++) {
                TopicTypeBean ttb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), TopicTypeBean.class);

                if (ttb.getReChatMessages() != null) {
                    JSONArray tta = new JSONArray(ttb.getReChatMessages().replace(" ", ""));

                    Gson gson = new Gson();
                    String[][] ss = gson.fromJson(String.valueOf(tta), new TypeToken<String[][]>() {
                    }.getType());
                    List<List<String>> outlist = new ArrayList<>();
                    for (String[] strings : ss) {
                        List<String> inlist = null;
                        for (String string : strings) {
                            if (inlist == null)
                                inlist = new ArrayList<>();
                            inlist.add(string);
                        }
                        outlist.add(inlist);
                    }
                    ttb.setComments(outlist);
                }
                if (ttb.getVideos() != null) {
                    if (fileIsExists(ttb.getVideos()))
                        ttb.setVideoCover(saveImage(null, ttb.getVideos()));
                    else {
                        Bitmap bitmap = createVideoThumbnail(ttb.getVideos(), DeviceUtils.getWindowWidth(SPreferences.context), (int) (DeviceUtils.getWindowWidth(SPreferences.context) * 0.8 / 1));
                        ttb.setVideoCover(saveImage(bitmap, ttb.getVideos()));
                    }
                }

                if (ttb.getImgs() != null) {
                    String b[] = ttb.getImgs().split("[,]");
                    if (b != null && b.length > 0) {
                        List<String> list = new ArrayList<>();
                        for (String str : b) {
                            list.add(str);
                        }
                        ttb.setPhotos(list);
                    }
                }
                nblist.add(ttb);
            }

        }
        return nblist;
    }

    /**
     * 动态本地数据库通用解析;
     *
     * @param nblist
     * @return
     * @throws JSONException
     */
    public static List<TopicTypeBean> StatusPaser(List<TopicTypeBean> nblist) throws JSONException {

        if (nblist.size() > 0) {
            for (int i = 0; i < nblist.size(); i++) {
                TopicTypeBean ttb = nblist.get(i);

                if (ttb.getReChatMessages() != null) {
                    JSONArray tta = new JSONArray(ttb.getReChatMessages().replace(" ", ""));

                    Gson gson = new Gson();
                    String[][] ss = gson.fromJson(String.valueOf(tta), new TypeToken<String[][]>() {
                    }.getType());
                    List<List<String>> outlist = new ArrayList<>();
                    for (String[] strings : ss) {
                        List<String> inlist = null;
                        for (String string : strings) {
                            if (inlist == null)
                                inlist = new ArrayList<>();
                            inlist.add(string);
                        }
                        outlist.add(inlist);
                    }
                    ttb.setComments(outlist);
                }
                if (ttb.getVideos() != null) {
                    if (fileIsExists(ttb.getVideos()))
                        ttb.setVideoCover(saveImage(null, ttb.getVideos()));
                    else {
                        Bitmap bitmap = createVideoThumbnail(ttb.getVideos(), DeviceUtils.getWindowWidth(SPreferences.context), (int) (DeviceUtils.getWindowWidth(SPreferences.context) * 0.8 / 1));
                        ttb.setVideoCover(saveImage(bitmap, ttb.getVideos()));
                    }
                }

                if (ttb.getImgs() != null) {
                    String b[] = ttb.getImgs().split("[,]");
                    if (b != null && b.length > 0) {
                        List<String> list = new ArrayList<>();
                        for (String str : b) {
                            list.add(str);
                        }
                        ttb.setPhotos(list);
                    }
                }
            }

        }
        return nblist;
    }
}
