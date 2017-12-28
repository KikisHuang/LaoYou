package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/23.
 */
public class ChatMessages {


    /**
     * id : m-f68ffd31ca2245edaef36d4346441f92
     * userId : ua664fd002910441aa098bcaf295622f8
     * userName : 默默
     * userImg : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514278194056f17fa7.jpg
     * messageContent : 00000
     * hasImg : 1
     * reUser : null
     * reMessage : null
     * createTime : 2017-12-27 18:06:38
     * chatImgs : [{"id":76,"url":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514426798150283d3d.png"}]
     */

    private String id;
    private String userId;
    private String userName;
    private String userImg;
    private String messageContent;
    private int hasImg;
    private String reUser;
    private String reMessage;
    private String createTime;
    private List<ChatImgsBean> chatImgs;

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

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public Object getReUser() {
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

    public List<ChatImgsBean> getChatImgs() {
        return chatImgs;
    }

    public void setChatImgs(List<ChatImgsBean> chatImgs) {
        this.chatImgs = chatImgs;
    }

    public static class ChatImgsBean {
        /**
         * id : 76
         * url : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514426798150283d3d.png
         */

        private int id;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
