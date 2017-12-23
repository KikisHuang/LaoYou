package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/22.
 */
public class TopicCommentBean {


    private String id;
    private McUser mcUser;
    private String userId;
    private String typeId;
    private McChatTypeBean mcChatType;
    private String createTime;
    private int commentsCount;
    private String lastTime;
    private String title;
    private Object mainImg;
    private String messageContent;
    private int likeCount;
    private int replyCount;
    private Object type;
    private List<ChatMessages> chatMessages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public McUser getMcUser() {
        return mcUser;
    }

    public void setMcUser(McUser mcUser) {
        this.mcUser = mcUser;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
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

    public Object getMainImg() {
        return mainImg;
    }

    public void setMainImg(Object mainImg) {
        this.mainImg = mainImg;
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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public List<ChatMessages> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessages> chatMessages) {
        this.chatMessages = chatMessages;
    }

}
