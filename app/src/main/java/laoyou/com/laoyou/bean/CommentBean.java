package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/26.
 */
public class CommentBean {
    /**
     * id : m-13e25a3e3d9e48ea9eb18f94085592c2
     * userId : ud7163f19736d4bee8f907cfa9d2333a6
     * userName : 高仿陈晓燕1
     * userImg : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15149577824502a9d65.jpg
     * messageContent : 请讲普通话
     * hasImg : null
     * reUserId : null
     * reUser : null
     * reMessage : null
     * createTime : 2018-01-02 23:31:52
     * chatImgs : null
     * chatVideo : null
     * chatThemeImgs : ["http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514963572741e48a0c.jpg"]
     * chatThemeId : t-11f801a3d9984ad3a7fc2100be245f53
     */

    private String id;
    private String userId;
    private String userName;
    private String userImg;
    private String messageContent;
    private String hasImg;
    private String reUserId;
    private String reUser;
    private String reMessage;
    private String createTime;
    private String chatImgs;
    private String chatVideo;
    private String chatThemeId;
    private List<String> chatThemeImgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getHasImg() {
        return hasImg;
    }

    public void setHasImg(String hasImg) {
        this.hasImg = hasImg;
    }

    public String getReUserId() {
        return reUserId;
    }

    public void setReUserId(String reUserId) {
        this.reUserId = reUserId;
    }

    public String getReUser() {
        return reUser;
    }

    public void setReUser(String reUser) {
        this.reUser = reUser;
    }

    public String getReMessage() {
        return reMessage;
    }

    public void setReMessage(String reMessage) {
        this.reMessage = reMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getChatImgs() {
        return chatImgs;
    }

    public void setChatImgs(String chatImgs) {
        this.chatImgs = chatImgs;
    }

    public String getChatVideo() {
        return chatVideo;
    }

    public void setChatVideo(String chatVideo) {
        this.chatVideo = chatVideo;
    }

    public String getChatThemeId() {
        return chatThemeId;
    }

    public void setChatThemeId(String chatThemeId) {
        this.chatThemeId = chatThemeId;
    }

    public List<String> getChatThemeImgs() {
        return chatThemeImgs;
    }

    public void setChatThemeImgs(List<String> chatThemeImgs) {
        this.chatThemeImgs = chatThemeImgs;
    }

}
