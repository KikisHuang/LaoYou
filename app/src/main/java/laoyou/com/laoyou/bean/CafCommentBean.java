package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/15.
 */
public class CafCommentBean {

    /**
     * createTime : 2017-11-18 14:35:23
     * id : dcb8ccadd7ab491b89af29ec9141dbe6
     * info : asdfasdf
     * internetBarId : 481ebc68566c4828a61b0a0a5d0bd9f7
     * level : 3
     * mcCommentImgs : [{"id":"669ef1bfd1c040258d7de16b940ba3c0","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15109869233162aac63.gif"},{"id":"c60712301fbe4743a2aa59cfb9d47c46","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1510986923421a2e3f3.gif"},{"id":"0c66b1dc5324444f8287269cba6177be","imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15109869231921550cd.gif"}]
     * mcUserByUserId : {"account":"13878141913","birthday":"1991-5-25","cloudTencentAccount":"d8622f0b4a03435eb189d7bdba15f8","createTime":"2017-11-17 16:16:31","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151262634489216923c.jpeg","id":"ud7163f19736d4bee8f907cfa9d2333a6","idcard":"450103199105252511","latitude":22.831043,"longitude":108.369605,"name":"手动","phone":"13878141913","sex":0,"status":true}
     * type : 0
     */

    private String createTime;
    private String id;
    private String info;
    private String internetBarId;
    private int level;
    private McUserByUserIdBean mcUserByUserId;
    private int type;
    private List<McCommentImgsBean> mcCommentImgs;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getInternetBarId() {
        return internetBarId;
    }

    public void setInternetBarId(String internetBarId) {
        this.internetBarId = internetBarId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public McUserByUserIdBean getMcUserByUserId() {
        return mcUserByUserId;
    }

    public void setMcUserByUserId(McUserByUserIdBean mcUserByUserId) {
        this.mcUserByUserId = mcUserByUserId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<McCommentImgsBean> getMcCommentImgs() {
        return mcCommentImgs;
    }

    public void setMcCommentImgs(List<McCommentImgsBean> mcCommentImgs) {
        this.mcCommentImgs = mcCommentImgs;
    }

    public static class McUserByUserIdBean {
        /**
         * account : 13878141913
         * birthday : 1991-5-25
         * cloudTencentAccount : d8622f0b4a03435eb189d7bdba15f8
         * createTime : 2017-11-17 16:16:31
         * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151262634489216923c.jpeg
         * id : ud7163f19736d4bee8f907cfa9d2333a6
         * idcard : 450103199105252511
         * latitude : 22.831043
         * longitude : 108.369605
         * name : 手动
         * phone : 13878141913
         * sex : 0
         * status : true
         */

        private String account;
        private String birthday;
        private String cloudTencentAccount;
        private String createTime;
        private String headImgUrl;
        private String id;
        private String idcard;
        private double latitude;
        private double longitude;
        private String name;
        private String phone;
        private int sex;
        private boolean status;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCloudTencentAccount() {
            return cloudTencentAccount;
        }

        public void setCloudTencentAccount(String cloudTencentAccount) {
            this.cloudTencentAccount = cloudTencentAccount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    public static class McCommentImgsBean {
        /**
         * id : 669ef1bfd1c040258d7de16b940ba3c0
         * imgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15109869233162aac63.gif
         */

        private String id;
        private String imgUrl;

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
    }
}
