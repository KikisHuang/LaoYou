package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/11/17.
 */
public class NearbyBean {

    /**
     * distance : 25714843
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151002385043237d79d.jpg
     * id : u00e523da34e948a4b1a47854c6dccc60
     * name : 萌萌哒
     */

    private double distance;
    private String headImgUrl;
    private String id;
    private String name;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
