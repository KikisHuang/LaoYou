package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/10/30.
 */
public class UserInfoBean {

    /**
     * account : 13878141913
     * createTime : 2017-10-30 10:31:37
     * headImgUrl : http://service-platform-internet-bar.oss-cn-shenzhen.aliyuncs.com/1509332123982dc05e5.jpg
     * id : u173180038fa6447da5e654fe3545dbff
     * name : kikid
     * phone : 13878141913
     * status : true
     * uid : 6
     */

    private String account;
    private String createTime;
    private String headImgUrl;
    private String id;
    private String name;
    private String phone;
    private boolean status;
    private int uid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
