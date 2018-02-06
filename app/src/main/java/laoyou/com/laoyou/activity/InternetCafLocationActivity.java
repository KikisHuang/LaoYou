package laoyou.com.laoyou.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.MarkerBean;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.listener.InternetCafLocationListener;
import laoyou.com.laoyou.overlay.DrivingRouteOverlay;
import laoyou.com.laoyou.overlay.RideRouteOverlay;
import laoyou.com.laoyou.overlay.WalkRouteOverlay;
import laoyou.com.laoyou.presenter.InternetCafLocationPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.StarBar;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goInternetCafPage;
import static laoyou.com.laoyou.utils.SynUtils.convertViewToBitmap;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;


/**
 * Created by lian on 2017/9/16.
 */
public class InternetCafLocationActivity extends InitActivity implements AMap.OnMarkerClickListener, LocationSource, InternetCafLocationListener, AMapLocationListener, AMap.OnMapClickListener, TextWatcher, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener, RouteSearch.OnRouteSearchListener {
    private static final String TAG = "LocationActivity";

    private MapView mapView;
    private AMap aMap;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    /**
     * 自己定位的经纬度;
     */
    private double Mylatitude;
    private double Mylongitude;
    /**
     * 网吧定位的经纬度(默认推荐第一的网吧);
     */
    private double Caflatitude;
    private double Caflongitude;

    private StarBar starBar;
    private TextView caf_name_tv, grade_tv, address_tv;
    private boolean isFirst = true;
    private MyLocationStyle myLocationStyle;
    private LatLng mLocalLatlng;
    private ImageView path_planning_img, caf_logo_img;
    private RouteSearch routeSearch;

    private InternetCafLocationPresenter ip;
    private List<MarkerBean> list;
    private LinearLayout caf_layout;
    private String id;
    private boolean IsList;
    private CardView bottom_banner_card;
    private ImageView return_icon;

    @Override
    protected void click() {
        path_planning_img.setOnClickListener(this);
        caf_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.location_layout);
        return_icon = f(R.id.back_img);
        return_icon.setImageResource(R.mipmap.return_icon);
        ActivityCollector.addActivity(this, getClass());
        ViewInit();
        ListOfGps();
        setImgTitles(this);
        mapView.onCreate(savedInstanceState);
        Mapinit();
    }

    private void ListOfGps() {
        IsList = Double.parseDouble(getIntent().getStringExtra("caf_latitude")) == 0 && Double.parseDouble(getIntent().getStringExtra("caf_longitude")) == 0 ? true : false;
        if (!IsList) {
            Caflatitude = Double.parseDouble(getIntent().getStringExtra("caf_latitude"));
            Caflongitude = Double.parseDouble(getIntent().getStringExtra("caf_longitude"));
            bottom_banner_card.setVisibility(View.GONE);
        }
    }

    private void ViewInit() {
        ip = new InternetCafLocationPresenter(this);
        caf_name_tv = f(R.id.caf_name_tv);
        //获取地图控件引用
        mapView = f(R.id.map_view);
        bottom_banner_card = f(R.id.bottom_banner_card);
        caf_layout = f(R.id.caf_layout);
        caf_logo_img = f(R.id.caf_logo_img);
        starBar = f(R.id.starBar);
        path_planning_img = f(R.id.path_planning_img);
        grade_tv = f(R.id.grade_tv);
        address_tv = f(R.id.address_tv);
        list = new ArrayList<>();
        starBar.setIntegerMark(false);
        starBar.ClickOpen(false);

        starBar.setStarMark(0f);
        //路线规划初始化;
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
    }

    /**
     * 初始化AMap对象
     */
    protected void Mapinit() {
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (aMap == null)
            aMap = mapView.getMap();

        setUpMap();
        aMap.setOnMapClickListener(this);

    }

    @Override
    protected void initData() {

    }

    private void AddCatMarker() {
        if (list.size() > 0) {
            setBottomBanner(list.get(0));
            for (MarkerBean mb : list) {

                LatLng lt = new LatLng(mb.getLatitude(), mb.getLongitude());
                MarkerOptions otMarkerOptions = new MarkerOptions();
                //otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_mark));
                otMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(getInfoWindow(mb.getName()))));

                otMarkerOptions.position(lt);
                Marker marker = aMap.addMarker(otMarkerOptions);

                marker.setObject(mb);
                //otMarkerOptions.title(i + "");
                //localMarker.showInfoWindow();
                //aMap.moveCamera(CameraUpdateFactory.changeLatLng(lt));
            }
        }
    }

    /**
     * infowindow布局;
     *
     * @param pm_val
     * @return
     */
    protected View getInfoWindow(String pm_val) {
        View view = getLayoutInflater().inflate(R.layout.map_marker_item, null);
        TextView tv_val = (TextView) view.findViewById(R.id.marker_tv_val);
        tv_val.setText(pm_val);
        return view;
    }

    /**
     * 初始化定位;
     */
    private void setUpMap() {
        //自定义系统定位小蓝点
        myLocationStyle = new MyLocationStyle();
     /*   myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_mark));*/// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.WHITE);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        myLocationStyle.interval(5000);
        // 定位、且将视角移动到地图中心点,定位点依照设备方向旋转,并且会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        getMap().setLatLonQuanVisible(false);
        aMap.getUiSettings().setCompassEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMarkerClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
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
            mLocationOption.setNeedAddress(true);
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

        LatLonPoint latLonPoint = new LatLonPoint(Mylatitude, Mylongitude);
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
                Mylatitude = amapLocation.getLatitude();
                Mylongitude = amapLocation.getLongitude();

                if (isFirst) {
                    Log.i(TAG, "第一次 定位回调 ");
                    mLocalLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocalLatlng, 13));
                    if (IsList)
                        ip.getInternetCafData(amapLocation.getLatitude(), amapLocation.getLongitude());
                    else {
                        WalkQuery();
                    }
                    isFirst = false;
                } else
                    Log.i(TAG, "定位回调  Mylatitude == " + Mylatitude + " Mylongitude ==" + Mylongitude);

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
   /*     aMap.clear();
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_mark));
        otMarkerOptions.position(latLng);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));*/

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.path_planning_img:
                if (routeSearch != null)
                    PathPlanning();
                else {
                    routeSearch = new RouteSearch(this);
                    routeSearch.setRouteSearchListener(this);
                    PathPlanning();
                }
                break;
            case R.id.caf_layout:
                if (list.size() > 0) {
                    goInternetCafPage(this, id);
                }
                break;
        }
    }

    /**
     * 路线规划
     */
    private void PathPlanning() {
        new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.walk), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {

                WalkQuery();

            }
        }).addSheetItem(gets(R.string.drive), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                DriveQuery();
            }
        }).addSheetItem(gets(R.string.ride), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                RideQuery();
            }
        }).show();

    }

    /**
     * 步行;
     */
    private void WalkQuery() {
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(Mylatitude, Mylongitude), new LatLonPoint(Caflatitude, Caflongitude));
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
        routeSearch.calculateWalkRouteAsyn(query);
    }

    /**
     * 骑行;
     */
    private void RideQuery() {
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(Mylatitude, Mylongitude), new LatLonPoint(Caflatitude, Caflongitude));
        RouteSearch.RideRouteQuery query = new RouteSearch.RideRouteQuery(fromAndTo);
        routeSearch.calculateRideRouteAsyn(query);
    }

    /**
     * 驾车;
     */
    private void DriveQuery() {
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(Mylatitude, Mylongitude), new LatLonPoint(Caflatitude, Caflongitude));
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
    }

    /**
     * 搜索回调（废弃）
     *
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
        MarkerBean mb = (MarkerBean) marker.getObject();
        setBottomBanner(mb);
        return false;
    }

    /**
     * 设置底部Banner数据;
     *
     * @param mb
     */
    private void setBottomBanner(MarkerBean mb) {
        id = mb.getId();
        caf_name_tv.setText(mb.getName());
        Caflatitude = mb.getLatitude();
        Caflongitude = mb.getLongitude();
        starBar.setStarMark((float) mb.getAvgEvaluate());
        grade_tv.setText(mb.getAvgEvaluate() + "分");
        address_tv.setText(gets(R.string.address) + "：" + mb.getAddress());

        Glide.with(MyApplication.getContext()).load(mb.getLogoUrl()).apply(getGlideOptions()).into(caf_logo_img);
    }

    /**
     * 公车路线回调方法;
     *
     * @param busRouteResult
     * @param code           状态码;
     */
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int code) {


    }

    /**
     * 驾车路线回调方法;
     *
     * @param driveRouteResult
     * @param code             状态码;
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int code) {

        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                //清除路线再次添加网吧marker;
                ClearAgainAddMarker();
                //在地图上绘制路径：
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(InternetCafLocationActivity.this, aMap, driveRouteResult.getPaths().get(0), driveRouteResult.getStartPos(), driveRouteResult.getTargetPos(), null);

                drivingRouteOverlay.removeFromMap();//去掉Overlay上所有的Marker。
                drivingRouteOverlay.setNodeIconVisibility(false);//隐藏转弯的节点
                drivingRouteOverlay.addToMap();//将Overlay添加到地图上显示
                drivingRouteOverlay.zoomToSpan();//调整地图能看到起点和终点
            } else
                ToastUtil.toast2_bottom(this, gets(R.string.not_route_paths));
        } else
            ToastUtil.toast2_bottom(this, gets(R.string.walkroute_error) + code);

    }

    private void ClearAgainAddMarker() {
        aMap.clear();
        AddCatMarker();
    }

    /**
     * 步行路线回调方法;
     *
     * @param walkRouteResult
     * @param code            状态码;
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int code) {
//        aMap.clear();// 清理地图上的所有覆盖物
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (walkRouteResult != null && walkRouteResult.getPaths() != null) {
                ClearAgainAddMarker();
                //在地图上绘制路径：
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(InternetCafLocationActivity.this, aMap, walkRouteResult.getPaths().get(0), walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());

                walkRouteOverlay.removeFromMap();//去掉Overlay上所有的Marker。
                walkRouteOverlay.addToMap();//将Overlay添加到地图上显示
                walkRouteOverlay.zoomToSpan();//调整地图能看到起点和终点
            } else
                ToastUtil.toast2_bottom(this, gets(R.string.not_route_paths));
        } else
            ToastUtil.toast2_bottom(this, gets(R.string.walkroute_error) + code);

    }

    /**
     * 骑行路线回调方法;
     *
     * @param rideRouteResult
     * @param code            状态码;
     */
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int code) {
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (rideRouteResult != null && rideRouteResult.getPaths() != null) {
                ClearAgainAddMarker();
                //在地图上绘制路径：
                RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(InternetCafLocationActivity.this, aMap, rideRouteResult.getPaths().get(0), rideRouteResult.getStartPos(), rideRouteResult.getTargetPos());

                rideRouteOverlay.removeFromMap();//去掉Overlay上所有的Marker。
                rideRouteOverlay.addToMap();//将Overlay添加到地图上显示
                rideRouteOverlay.zoomToSpan();//调整地图能看到起点和终点
            } else
                ToastUtil.toast2_bottom(this, gets(R.string.not_route_paths));
        } else
            ToastUtil.toast2_bottom(this, gets(R.string.walkroute_error) + code);

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFailesMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onInternetCafDataList(List<MarkerBean> list) {
        bottom_banner_card.setVisibility(View.VISIBLE);
        this.list = list;
        AddCatMarker();
    }
}
