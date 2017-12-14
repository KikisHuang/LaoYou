package laoyou.com.laoyou.presenter;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.listener.FindSonListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getJsonAr;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/11.
 */
public class FindSonPresenter implements HttpResultListener {

    private FindSonListener listener;
    private boolean RefreshFlag;
    public int page = 0;
    private List<NearbyBean> Nblist;

    public FindSonPresenter(FindSonListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    JSONArray ar = getJsonAr(response);
                    if (RefreshFlag)
                        Nblist = new ArrayList<>();

                    if (ar.length() > 0) {
                        for (int i = 0; i < ar.length(); i++) {
                            NearbyBean pb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), NearbyBean.class);
                            Nblist.add(pb);
                        }

                        listener.RefreshRecyclerView(Nblist);
                    }else if(RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nodata));
                        else if(!RefreshFlag)
                        listener.onFailedMsg(gets(R.string.nomore));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    /**
     * 附近的人数据获取;
     */
    public void getNearbyData(boolean flag) {
        /**
         * 如果没有经纬度，不能获取附近的人信息;
         */
        if (SPreferences.getLatitud() != null && Double.parseDouble(SPreferences.getLatitud()) > 0) {
            RefreshFlag = flag;
            Map<String, String> map = getParamsMap();
            map.put("page", String.valueOf(page));
            map.put("pageSize", String.valueOf(page + Fields.SIZE));
            map.put("latitude", String.valueOf(SPreferences.getLatitud()));
            map.put("longitude", String.valueOf(SPreferences.getLongitude()));

            httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETNEARBYUSER);
        }
    }

    /**
     * 新事件数据获取;
     */
    public void getNewIncident() {

    }
}
