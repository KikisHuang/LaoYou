package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/12/23.
 */
public  class McChatTypeBean {
    /**
     * id : test0
     * createTime : 2017-12-02 16:35:16
     * name : 公告模块
     * followCount : null
     * chatThemeCount : null
     * imgUrl : null
     */

    private String id;
    private String createTime;
    private String name;
    private Object followCount;
    private Object chatThemeCount;
    private Object imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Object followCount) {
        this.followCount = followCount;
    }

    public Object getChatThemeCount() {
        return chatThemeCount;
    }

    public void setChatThemeCount(Object chatThemeCount) {
        this.chatThemeCount = chatThemeCount;
    }

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }
}
