package laoyou.com.laoyou.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

import laoyou.com.laoyou.adapter.IcSearchAdapter;
import laoyou.com.laoyou.bean.LocationAddressBean;
import laoyou.com.laoyou.listener.moveListener;

/**
 * Created by lian on 2017/9/16.
 */
public class InputTask implements PoiSearch.OnPoiSearchListener {
    private static final String TAG = "InputTask";
    private static InputTask mInstance;
    private IcSearchAdapter mAdapter;
    private PoiSearch mSearch;
    private Context mContext;
    private moveListener move;

    private InputTask(Context context, IcSearchAdapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;
    }

    /**
     * 获取实例
     *
     * @param context 上下文
     * @param adapter 数据适配器
     * @return
     */
    public static InputTask getInstance(Context context, IcSearchAdapter adapter) {
        if (mInstance == null) {
            synchronized (InputTask.class) {
                if (mInstance == null) {
                    mInstance = new InputTask(context, adapter);
                }
            }
        }
        return mInstance;
    }
    public static void clear(){
        mInstance = null;
    }
    /**
     * 设置数据适配器
     *
     * @param adapter
     */
    public void setAdapter(IcSearchAdapter adapter) {
        this.mAdapter = adapter;
    }

    /**
     * POI搜索
     *
     * @param key  关键字
     * @param city 城市
     */
    public void onSearch(String key, String city) {

        //POI搜索条件
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        mSearch = new PoiSearch(mContext, query);
        //设置异步监听
        mSearch.setOnPoiSearchListener(this);
        //查询POI异步接口
        mSearch.searchPOIAsyn();
    }

    /**
     * 异步搜索回调
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        Log.i(TAG, "RCode ====" + rCode);
        if (rCode == 1000 && poiResult != null) {

            ArrayList<LocationAddressBean> data = new ArrayList<>();
            ArrayList<PoiItem> items = poiResult.getPois();
            if (items == null) {
                return;
            }
            for (PoiItem item : items) {
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                data.add(new LocationAddressBean(lon, lat, title, text));
            }
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}