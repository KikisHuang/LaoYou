package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/22.
 */
public class TopicCommentBean {


    private List<String> photos;
    /**
     * chatMessages : null
     * likeUsers : null
     * id : t-fd7a665cb41c41328b98b4d3302b57fd
     * mcUser : null
     * userId : null
     * typeId : null
     * mcChatType : {"id":"0142880b35354ab0b13e9ab1330103e6","uid":7,"createTime":"2017-12-26 00:38:54","name":"Dddddsf","num":2,"followCount":null,"chatThemeCount":null,"imgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514277736051d452cc.gif"}
     * uid : null
     * createTime : 2017-12-27 22:11:37
     * commentsCount : null
     * num : null
     * lastTime : null
     * title : null
     * messageContent : 哈哈
     * likeCount : 0
     * replyCount : 0
     * type : 1
     * imgs : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514441496772c83ac4.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514441497384e74bdb.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514441497595eb2c75.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514441497891eea441.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15144414981626fabca.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514441498449ec5242.jpg,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151444149870352c3db.jpg
     * videos : null
     * reChatMessages : null
     */

    private Object chatMessages;
    private String likeUsers;
    private String id;
    private String userId;
    private String typeId;
    private McChatTypeBean mcChatType;
    private String uid;
    private String createTime;
    private String commentsCount;
    private int num;
    private String lastTime;
    private String title;
    private String messageContent;
    private int likeCount;
    private int replyCount;
    private int type;
    private String imgs;
    private String videos;
    private Object reChatMessages;
    private McUser mcUser;

    public McUser getMcUser() {
        return mcUser;
    }

    public void setMcUser(McUser mcUser) {
        this.mcUser = mcUser;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Object getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Object chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Object getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(String likeUsers) {
        this.likeUsers = likeUsers;
    }

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public McChatTypeBean getMcChatType() {
        return mcChatType;
    }

    public void setMcChatType(McChatTypeBean mcChatType) {
        this.mcChatType = mcChatType;
    }

    public Object getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Object getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public Object getReChatMessages() {
        return reChatMessages;
    }

    public void setReChatMessages(Object reChatMessages) {
        this.reChatMessages = reChatMessages;
    }

    public static class McChatTypeBean {
        /**
         * id : 0142880b35354ab0b13e9ab1330103e6
         * uid : 7
         * createTime : 2017-12-26 00:38:54
         * name : Dddddsf
         * num : 2
         * followCount : null
         * chatThemeCount : null
         * imgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514277736051d452cc.gif
         */

        private String id;
        private int uid;
        private String createTime;
        private String name;
        private int num;
        private int followCount;
        private int chatThemeCount;
        private String imgUrl;

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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public int getChatThemeCount() {
            return chatThemeCount;
        }

        public void setChatThemeCount(int chatThemeCount) {
            this.chatThemeCount = chatThemeCount;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
