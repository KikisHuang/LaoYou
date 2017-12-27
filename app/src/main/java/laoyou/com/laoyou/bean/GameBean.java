package laoyou.com.laoyou.bean;

import java.io.Serializable;

/**
 * Created by lian on 2017/12/9.
 */
public class GameBean implements Serializable {
    /**
     * id : 0
     * uid : 2
     * name : DOTA2
     * info : dota
     * imgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15141812684636d40d5.gif
     * createTime : 2017-12-20 16:29:02
     * followCount : null
     */

    private String id;
    private int uid;
    private String name;
    private String info;
    private String imgUrl;
    private String createTime;
    private String followCount;
    private boolean Slector;

    public boolean isSlector() {
        return Slector;
    }

    public void setSlector(boolean slector) {
        Slector = slector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }
}
