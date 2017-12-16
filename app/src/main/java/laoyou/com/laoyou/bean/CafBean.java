package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/15.
 */
public class CafBean {


    /**
     * address : 桃源路
     * avgEvaluate : 3.0
     * createTime : 2017-11-18 13:26:26
     * hourlyPrice : 1.5
     * id : 481ebc68566c4828a61b0a0a5d0bd9f7
     * info : asa
     * internetBarImgs : [{"id":"635108db42ed4cb49f31738e8ebd5378","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15109863583296ef59d.gif","internetBarId":"481ebc68566c4828a61b0a0a5d0bd9f7"},{"id":"508f1ef03b2142169af2ee436ab233a9","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151098635878709ee65.gif","internetBarId":"481ebc68566c4828a61b0a0a5d0bd9f7"},{"id":"441224d0fdee4ddcb599897862724297","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1510986358894afd197.gif","internetBarId":"481ebc68566c4828a61b0a0a5d0bd9f7"}]
     * latitude : 22.800149
     * logoUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1510982785912c8b160.gif
     * longitude : 108.344469
     * name : test
     * phone : 5553335
     * status : 1
     */

    private String address;
    private double avgEvaluate;
    private String createTime;
    private double hourlyPrice;
    private String id;
    private String info;
    private double latitude;
    private String logoUrl;
    private double longitude;
    private String name;
    private String phone;
    private String backgroundUrl;

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    private int status;


    private List<InternetBarImgsBean> internetBarImgs;

    public String getConfigureCPU() {
        return configureCPU;
    }

    public void setConfigureCPU(String configureCPU) {
        this.configureCPU = configureCPU;
    }

    public String getConfigureGraphicsCard() {
        return configureGraphicsCard;
    }

    public void setConfigureGraphicsCard(String configureGraphicsCard) {
        this.configureGraphicsCard = configureGraphicsCard;
    }

    public String getConfigureKeyboard() {
        return configureKeyboard;
    }

    public void setConfigureKeyboard(String configureKeyboard) {
        this.configureKeyboard = configureKeyboard;
    }

    public String getConfigureMouse() {
        return configureMouse;
    }

    public void setConfigureMouse(String configureMouse) {
        this.configureMouse = configureMouse;
    }

    public String getConfigureScreen() {
        return configureScreen;
    }

    public void setConfigureScreen(String configureScreen) {
        this.configureScreen = configureScreen;
    }

    private String configureCPU;
    private String configureGraphicsCard;
    private String configureKeyboard;
    private String configureMouse;
    private String configureScreen;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAvgEvaluate() {
        return avgEvaluate;
    }

    public void setAvgEvaluate(double avgEvaluate) {
        this.avgEvaluate = avgEvaluate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InternetBarImgsBean> getInternetBarImgs() {
        return internetBarImgs;
    }

    public void setInternetBarImgs(List<InternetBarImgsBean> internetBarImgs) {
        this.internetBarImgs = internetBarImgs;
    }

    public static class InternetBarImgsBean {
        /**
         * id : 635108db42ed4cb49f31738e8ebd5378
         * imgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15109863583296ef59d.gif
         * internetBarId : 481ebc68566c4828a61b0a0a5d0bd9f7
         */

        private String id;
        private String imgUrl;
        private String internetBarId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getInternetBarId() {
            return internetBarId;
        }

        public void setInternetBarId(String internetBarId) {
            this.internetBarId = internetBarId;
        }
    }
}
