package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/12/18.
 */
public class TopicBean {

    /**
     * createTime : 2017-12-02 16:35:16
     * id : test0
     * name : 公告模块
     * num : 0
     * uid : -1
     */

    private String createTime;
    private String id;
    private String name;
    private int num;
    private int uid;
    private String imgUrl;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getChatThemeCount() {
        return chatThemeCount;
    }

    public void setChatThemeCount(String chatThemeCount) {
        this.chatThemeCount = chatThemeCount;
    }

    private String followCount;
    private String chatThemeCount;



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
