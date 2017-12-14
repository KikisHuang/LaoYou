package laoyou.com.laoyou.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.moveListener;
import laoyou.com.laoyou.view.StarBar;

import static laoyou.com.laoyou.utils.SynUtils.convertViewToBitmap;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;


/**
 * Created by lian on 2017/9/16.
 */
public class InternetCafLocationActivity extends InitActivity implements AMap.OnMarkerClickListener, LocationSource, AMapLocationListener, moveListener, AMap.OnMapClickListener, TextWatcher, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "LocationActivity";

    private MapView mapView;
    private AMap aMap;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private double latitude;
    private double longitude;

    private String adcode = "";
    private String city = "";
    private String address = "";
    private StarBar starBar;
    private TextView cat_name_tv;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.location_layout);
        setImgTitles(this);
        ViewInit();
        mapView.onCreate(savedInstanceState);
        Mapinit();
    }

    private void ViewInit() {
        cat_name_tv = f(R.id.cat_name_tv);
        //获取地图控件引用
        mapView = f(R.id.map_view);
        starBar = f(R.id.starBar);
    }

    /**
     * 初始化AMap对象
     */
    protected void Mapinit() {
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            MarkerTest();


            aMap.setOnMapClickListener(this);
        }
        starBar.setIntegerMark(false);
        starBar.ClickOpen(false);
        starBar.setStarMark(3.5f);

    }

    @Override
    protected void initData() {

    }

    private void MarkerTest() {

        double[] latLnglongitude = {108.345491, 108.362829, 108.379652, 108.39493, 108.370211, 108.36652, 108.393127};
        double[] latLnglatitude = {22.835689, 22.82493, 22.829677, 22.834028, 22.836717, 22.817098, 22.822162};

        for (int i = 0; i < latLnglatitude.length; i++) {

            LatLng lt = new LatLng(latLnglatitude[i], latLnglongitude[i]);
            MarkerOptions otMarkerOptions = new MarkerOptions();
//            otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_mark));
            otMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(getMyBitmap("网咖地址Marker " + i))));

            otMarkerOptions.title(i + "");

            otMarkerOptions.position(lt);
            Marker marker = aMap.addMarker(otMarkerOptions);
            marker.setObject(latLnglongitude);
//          localMarker.showInfoWindow();
//          aMap.moveCamera(CameraUpdateFactory.changeLatLng(lt));
        }
    }

    protected View getMyBitmap(String pm_val) {
        View view = getLayoutInflater().inflate(R.layout.map_marker_item, null);
        TextView tv_val = (TextView) view.findViewById(R.id.marker_tv_val);
        tv_val.setText(pm_val);
        return view;
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
     /*   myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_mark));*/// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.WHITE);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        myLocationStyle.interval(2000);
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        getMap().setLatLonQuanVisible(false);
        aMap.getUiSettings().setCompassEnabled(false);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        aMap.setOnMarkerClickListener(this);

      aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

        if (mlocationClient == null) {

            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();

        }
    }

    private void getAddressA1ndAdCode() {

        LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        // 设置同步逆地理编码请求
//        geocoderSearch.getFromLocationAsyn(query);

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    /*开启定位时*/
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Log.e(TAG, amapLocation.getLatitude() + "");
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                city = amapLocation.getCity();
//                city = amapLocation.getCity();
                address = amapLocation.getAddress();
                mlocationClient.stopLocation();
                adcode = amapLocation.getAdCode();
//                select = 1;
//                listView.removeFooterView(listNot);
//                doSearchQuery();
                Log.i(TAG, "定位回调....");

            } else {
                /*没开启定位时*/
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见下方错误码表。

                Log.i("erro info：", amapLocation.getErrorCode() + "---" + amapLocation.getErrorInfo());
            }

        }
    }

    //地图点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图后清理图层插上图标，在将其移动到中心位置
//        aMap.clear();
//        latitude = latLng.latitude;
//        longitude = latLng.longitude;
//        MarkerOptions otMarkerOptions = new MarkerOptions();
//        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_mark));
//        otMarkerOptions.position(latLng);
//        aMap.addMarker(otMarkerOptions);
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    @Override
    public void onMove(double lati, double longi, String title) {
     /*   //点击地图后清理图层插上图标，在将其移动到中心位置
        LatLng lt = new LatLng(lati, longi);
        aMap.clear();
        latitude = lati;
        longitude = longi;
        MarkerOptions otMarkerOptions = new MarkerOptions();

        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_mark));
        otMarkerOptions.position(lt);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 20));*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
      /*      case R.id.submit_info:
                getAddressA1ndAdCode();
                break;*/
        }
    }

    /**
     * 搜索回调（废弃）
     * @param result
     * @param rCode
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {

           /* address = result.getRegeocodeAddress().getFormatAddress();
            adcode = result.getRegeocodeAddress().getAdCode();
            Log.i(TAG, "address ====" + address + "----adcode ====" + adcode + "latitude ===" + latitude + "longitude" + longitude);
            String provinceId  = adcode.substring(0,2)+"0000";
            String cityId  = adcode.substring(0,4)+"00";

            Intent intent = new Intent();
            intent.putExtra("Map_adcode", adcode);
            intent.putExtra("Map_provinceId", provinceId);
            intent.putExtra("Map_cityId", cityId);
            intent.putExtra("Map_address", address);
            intent.putExtra("Map_latitude", String.valueOf(latitude));
            intent.putExtra("Map_longitude", String.valueOf(longitude));
            setResult(1008, intent);
            clear();
            finish();*/
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        double[] b = (double[]) marker.getObject();

        Log.i(TAG, "onMarker");
        return false;
    }
}
