package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/12/13.
 */
public class MarkerBean {

    /**
     * address : 桃源路
     * avgEvaluate : 3.0
     * distance : 4481
     * hourlyPrice : 1.5
     * id : 481ebc68566c4828a61b0a0a5d0bd9f7
     * info : asa
     * latitude : 22.800149
     * logoUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1510982785912c8b160.gif
     * longitude : 108.344469
     * name : test
     * phone : 5553335
     */

    private String address;
    private double level;
    private int distance;
    private double hourlyPrice;
    private String id;
    private String info;
    private double latitude;
    private String logoUrl;
    private double longitude;
    private String name;
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAvgEvaluate() {
        return level;
    }

    public void setAvgEvaluate(double avgEvaluate) {
        this.level = avgEvaluate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
