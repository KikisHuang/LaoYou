package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/20.
 */
public class TopicTypeBean {


    /**
     * chatMessages : [{"id":"m-69af3a0787074ef5a3f732463922b361","messageContent":"8325effa72","userId":"ua664fd002910441aa098bcaf295622f8","userImg":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","userName":"默默"},{"id":"m-6e92baa1ba904e28bd7e8a88d8a05cfe","messageContent":"hhhhh","userId":"ua664fd002910441aa098bcaf295622f8","userImg":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","userName":"默默"}]
     * chatTypeId : test0
     * chatTypeName : 公告模块
     * createTime : 2017-12-09 16:43:54
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
     * id : t-2f307520aed24181a537796da5e6dca9
     * likeCount : 0
     * replyCount : 0
     * userId : ua664fd002910441aa098bcaf295622f8
     * userName : 默默
     */

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
        /**
         * id : m-69af3a0787074ef5a3f732463922b361
         * messageContent : 8325effa72
         * userId : ua664fd002910441aa098bcaf295622f8
         * userImg : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
         * userName : 默默
         */

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
    }
}
