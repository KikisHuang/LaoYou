package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/20.
 */
public class TopicTypeBean {

    public TopicTypeBean() {
    }

    public TopicTypeBean(String chatTypeId, String imgs, String chatTypeName, String videos, int likeCount, String userName, String userId, int replyCount, String headImgUrl, String createTime, String reChatMessages, int likeFlag, String id, String messageContent) {
        this.chatTypeId = chatTypeId;
        this.imgs = imgs;
        this.chatTypeName = chatTypeName;
        this.videos = videos;
        this.likeCount = likeCount;
        this.userName = userName;
        this.userId = userId;
        this.replyCount = replyCount;
        this.headImgUrl = headImgUrl;
        this.createTime = createTime;
        this.reChatMessages = reChatMessages;
        this.likeFlag = likeFlag == 1 ? true : false;
        this.id = id;
        this.messageContent = messageContent;
    }

    /**
     * chatTypeId : test0
     * imgs : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514359266842c85571.png,http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514359266842c85571.png
     * chatTypeName : 公告模块
     * videos : null
     * likeCount : 0
     * userName : 默默
     * userId : ua664fd002910441aa098bcaf295622f8
     * replyCount : 0
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1514278194056f17fa7.jpg
     * createTime : 2017-12-27 15:21:07
     * reChatMessages : null
     * likeFlag : false
     * id : t-9dfc9c5667db448d8c28aa6c86316353
     * messageContent : 都是对的订单
     */

    private String chatTypeId;
    private String imgs;
    private String chatTypeName;
    private String videos;
    private int likeCount;
    private String userName;
    private String userId;
    private int replyCount;
    private String headImgUrl;
    private String createTime;
    private String reChatMessages;
    private boolean likeFlag;
    private String id;
    private String messageContent;

    @Override
    public String toString() {
        return "TopicTypeBean{" +
                "chatTypeId='" + chatTypeId + '\'' +
                ", imgs='" + imgs + '\'' +
                ", chatTypeName='" + chatTypeName + '\'' +
                ", videos='" + videos + '\'' +
                ", likeCount=" + likeCount +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", replyCount=" + replyCount +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", reChatMessages='" + reChatMessages + '\'' +
                ", likeFlag=" + likeFlag +
                ", id='" + id + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }

    private List<String> photos;
    private List<List<String>> comments;
    private String videoFile;

    public String getVideoCover() {
        return videoFile;
    }

    public void setVideoCover(String videoFile) {
        this.videoFile = videoFile;
    }

    public List<List<String>> getComments() {
        return comments;
    }

    public void setComments(List<List<String>> comments) {
        this.comments = comments;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getChatTypeId() {
        return chatTypeId;
    }

    public void setChatTypeId(String chatTypeId) {
        this.chatTypeId = chatTypeId;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getChatTypeName() {
        return chatTypeName;
    }

    public void setChatTypeName(String chatTypeName) {
        this.chatTypeName = chatTypeName;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReChatMessages() {
        return reChatMessages;
    }

    public void setReChatMessages(String reChatMessages) {
        this.reChatMessages = reChatMessages;
    }

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }




/*
    *//**
     * ChatMessages : [{"id":"m-69af3a0787074ef5a3f732463922b361","messageContent":"8325effa72","userId":"ua664fd002910441aa098bcaf295622f8","userImg":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","userName":"默默"},{"id":"m-6e92baa1ba904e28bd7e8a88d8a05cfe","messageContent":"hhhhh","userId":"ua664fd002910441aa098bcaf295622f8","userImg":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","userName":"默默"}]
     * chatTypeId : test0
     * chatTypeName : 公告模块
     * createTime : 2017-12-09 16:43:54
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
     * id : t-2f307520aed24181a537796da5e6dca9
     * likeCount : 0
     * replyCount : 0
     * userId : ua664fd002910441aa098bcaf295622f8
     * userName : 默默
     *//*

    private String chatTypeId;
    private String chatTypeName;
    private String createTime;
    private String headImgUrl;
    private String id;
    private int likeCount;
    private int replyCount;
    private String userId;
    private String imgUrl;
    private String messageContent;
    private boolean likeFlag;

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String userName;
    private List<ChatMessagesBean> chatMessages;

    public String getChatTypeId() {
        return chatTypeId;
    }

    public void setChatTypeId(String chatTypeId) {
        this.chatTypeId = chatTypeId;
    }

    public String getChatTypeName() {
        return chatTypeName;
    }

    public void setChatTypeName(String chatTypeName) {
        this.chatTypeName = chatTypeName;
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

    public List<ChatMessagesBean> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessagesBean> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public static class ChatMessagesBean {
        *//**
     * id : m-69af3a0787074ef5a3f732463922b361
     * messageContent : 8325effa72
     * userId : ua664fd002910441aa098bcaf295622f8
     * userImg : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
     * userName : 默默
     *//*

        private String id;
        private String messageContent;
        private String userId;
        private String userImg;
        private String userName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }*/
}
