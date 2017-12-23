package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/12/23.
 */
public class ChatMessages {


    /**
     * id : m-92a2e93ff268419ba52c657da2b61b2f
     * mcUserByUserId : {"id":"ud7163f19736d4bee8f907cfa9d2333a6","name":"高端大气","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624590aa83ff.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624934b5bfda.jpg","realName":"黄子奇","idcard":"450103199105252511","account":"13878141913","phone":"13878141913","createTime":"2017-11-17 16:16:31","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"1982-01-01","latitude":22.831334,"longitude":108.369069,"cloudTencentAccount":"d8622f0b4a03435eb189d7bdba15f8","autograph":"我是fipri_rnxiaoyan画画","hometown":"山西省 - 太原市","height":"186~190cm","loveStatus":"单身","browseNumber":null,"address":"辽宁省-沈阳市"}
     * mcChatMessage : {"id":"m-9e025c2d97b345fcb9a63bb7d029d869","mcUserByUserId":{"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":37.785834,"longitude":-122.406417,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"},"mcChatMessage":null,"createTime":"2017-12-21 00:15:53","messageTypeFlag":0,"messageContent":"古古怪怪","hasImg":null,"modifyTime":"2017-12-21 00:15:53"}
     * createTime : 2017-12-22 18:24:14
     * messageTypeFlag : 100
     * messageContent : 回复评论
     * hasImg : null
     * modifyTime : 2017-12-22 18:24:14
     */

    private String id;
    private McUserByUserIdBean mcUserByUserId;
    private McChatMessage mcChatMessage;
    private String createTime;
    private int messageTypeFlag;
    private String messageContent;
    private String hasImg;
    private String modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public McUserByUserIdBean getMcUserByUserId() {
        return mcUserByUserId;
    }

    public void setMcUserByUserId(McUserByUserIdBean mcUserByUserId) {
        this.mcUserByUserId = mcUserByUserId;
    }

    public McChatMessage getMcChatMessage() {
        return mcChatMessage;
    }

    public void setMcChatMessage(McChatMessage mcChatMessage) {
        this.mcChatMessage = mcChatMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMessageTypeFlag() {
        return messageTypeFlag;
    }

    public void setMessageTypeFlag(int messageTypeFlag) {
        this.messageTypeFlag = messageTypeFlag;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
