package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/9/16.
 */
public class LocationAddressBean {
    private double longitude;//经度
    private double latitude;//纬度
    private String title;//信息标题
    private String text;//信息内容

    public LocationAddressBean(double lon, double lat, String title, String text){
        this.longitude = lon;
        this.latitude = lat;
        this.title = title;
        this.text = text;
    }
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public String getTitle() {
        return title;
    }
    public String getText(){
        return text;
    }
}
