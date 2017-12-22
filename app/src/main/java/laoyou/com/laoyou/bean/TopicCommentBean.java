package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/22.
 */
public class TopicCommentBean {

    /**
     * chatMessages : [{"id":"m-dec10ee4e94c4e718a7b066aed166b71","mcUserByUserId":{"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":0,"longitude":0,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"},"mcChatMessage":null,"createTime":"2017-12-02 16:40:58","messageTypeFlag":0,"messageContent":"aaaaaa","mcChatMessages":[],"hasImg":null,"modifyTime":"2017-12-02 16:40:58"}]
     * id : t-eb34feacff9f431f882739b5d689a72e
     * mcUser : {"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":0,"longitude":0,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"}
     * userId : ua664fd002910441aa098bcaf295622f8
     * typeId : test0
     * mcChatType : {"id":"test0","createTime":"2017-12-02 16:35:16","name":"公告模块","followCount":null,"chatThemeCount":null,"imgUrl":null}
     * createTime : 2017-12-01 16:40:58
     * commentsCount : 0
     * lastTime : 2017-12-01 16:40:58
     * title : null
     * mainImg : null
     * messageContent : null
     * likeCount : null
     * replyCount : null
     * type : null
     */

    private String id;
    private McUserBean mcUser;
    private String userId;
    private String typeId;
    private McChatTypeBean mcChatType;
    private String createTime;
    private int commentsCount;
    private String lastTime;
    private String title;
    private String mainImg;
    private String messageContent;
    private int likeCount;
    private int replyCount;
    private int type;
    private List<ChatMessagesBean> chatMessages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public McUserBean getMcUser() {
        return mcUser;
    }

    public void setMcUser(McUserBean mcUser) {
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

    public Object getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Object getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Object getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Object getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ChatMessagesBean> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessagesBean> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public static class McUserBean {
        /**
         * id : ua664fd002910441aa098bcaf295622f8
         * name : 默默
         * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
         * backgroundUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg
         * realName : null
         * idcard : 452731199101136011
         * account : 18269286442
         * phone : 18269286442
         * createTime : 2017-11-23 12:56:02
         * loginTime : null
         * wx : null
         * wxNumber : null
         * wxOpenId : null
         * status : true
         * myInvitationCode : null
         * useInvitationCode : null
         * invitationUserId : null
         * channelCode : null
         * channelId : null
         * userId : null
         * sex : 0
         * birthday : 2008-10-10
         * latitude : 0
         * longitude : 0
         * cloudTencentAccount : 814e8953f2644b2baa9d5ed1eddcce
         * autograph : jhfjfhjh
         * hometown : 关西
         * height : 158-168cm
         * loveStatus : 保密
         * browseNumber : null
         * address : 广西南宁
         */

        private String id;
        private String name;
        private String headImgUrl;
        private String backgroundUrl;
        private Object realName;
        private String idcard;
        private String account;
        private String phone;
        private String createTime;
        private Object loginTime;
        private Object wx;
        private Object wxNumber;
        private Object wxOpenId;
        private boolean status;
        private Object myInvitationCode;
        private Object useInvitationCode;
        private Object invitationUserId;
        private Object channelCode;
        private Object channelId;
        private Object userId;
        private int sex;
        private String birthday;
        private int latitude;
        private int longitude;
        private String cloudTencentAccount;
        private String autograph;
        private String hometown;
        private String height;
        private String loveStatus;
        private Object browseNumber;
        private String address;

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

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(Object loginTime) {
            this.loginTime = loginTime;
        }

        public Object getWx() {
            return wx;
        }

        public void setWx(Object wx) {
            this.wx = wx;
        }

        public Object getWxNumber() {
            return wxNumber;
        }

        public void setWxNumber(Object wxNumber) {
            this.wxNumber = wxNumber;
        }

        public Object getWxOpenId() {
            return wxOpenId;
        }

        public void setWxOpenId(Object wxOpenId) {
            this.wxOpenId = wxOpenId;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Object getMyInvitationCode() {
            return myInvitationCode;
        }

        public void setMyInvitationCode(Object myInvitationCode) {
            this.myInvitationCode = myInvitationCode;
        }

        public Object getUseInvitationCode() {
            return useInvitationCode;
        }

        public void setUseInvitationCode(Object useInvitationCode) {
            this.useInvitationCode = useInvitationCode;
        }

        public Object getInvitationUserId() {
            return invitationUserId;
        }

        public void setInvitationUserId(Object invitationUserId) {
            this.invitationUserId = invitationUserId;
        }

        public Object getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(Object channelCode) {
            this.channelCode = channelCode;
        }

        public Object getChannelId() {
            return channelId;
        }

        public void setChannelId(Object channelId) {
            this.channelId = channelId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public String getCloudTencentAccount() {
            return cloudTencentAccount;
        }

        public void setCloudTencentAccount(String cloudTencentAccount) {
            this.cloudTencentAccount = cloudTencentAccount;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLoveStatus() {
            return loveStatus;
        }

        public void setLoveStatus(String loveStatus) {
            this.loveStatus = loveStatus;
        }

        public Object getBrowseNumber() {
            return browseNumber;
        }

        public void setBrowseNumber(Object browseNumber) {
            this.browseNumber = browseNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class McChatTypeBean {
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

    public static class ChatMessagesBean {
        /**
         * id : m-dec10ee4e94c4e718a7b066aed166b71
         * mcUserByUserId : {"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":0,"longitude":0,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"}
         * mcChatMessage : null
         * createTime : 2017-12-02 16:40:58
         * messageTypeFlag : 0
         * messageContent : aaaaaa
         * mcChatMessages : []
         * hasImg : null
         * modifyTime : 2017-12-02 16:40:58
         */

        private String id;
        private McUserByUserIdBean mcUserByUserId;
        private Object mcChatMessage;
        private String createTime;
        private int messageTypeFlag;
        private String messageContent;
        private Object hasImg;
        private String modifyTime;
        private List<?> mcChatMessages;

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

        public Object getMcChatMessage() {
            return mcChatMessage;
        }

        public void setMcChatMessage(Object mcChatMessage) {
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

        public Object getHasImg() {
            return hasImg;
        }

        public void setHasImg(Object hasImg) {
            this.hasImg = hasImg;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public List<?> getMcChatMessages() {
            return mcChatMessages;
        }

        public void setMcChatMessages(List<?> mcChatMessages) {
            this.mcChatMessages = mcChatMessages;
        }

        public static class McUserByUserIdBean {
            /**
             * id : ua664fd002910441aa098bcaf295622f8
             * name : 默默
             * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
             * backgroundUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg
             * realName : null
             * idcard : 452731199101136011
             * account : 18269286442
             * phone : 18269286442
             * createTime : 2017-11-23 12:56:02
             * loginTime : null
             * wx : null
             * wxNumber : null
             * wxOpenId : null
             * status : true
             * myInvitationCode : null
             * useInvitationCode : null
             * invitationUserId : null
             * channelCode : null
             * channelId : null
             * userId : null
             * sex : 0
             * birthday : 2008-10-10
             * latitude : 0
             * longitude : 0
             * cloudTencentAccount : 814e8953f2644b2baa9d5ed1eddcce
             * autograph : jhfjfhjh
             * hometown : 关西
             * height : 158-168cm
             * loveStatus : 保密
             * browseNumber : null
             * address : 广西南宁
             */

            private String id;
            private String name;
            private String headImgUrl;
            private String backgroundUrl;
            private Object realName;
            private String idcard;
            private String account;
            private String phone;
            private String createTime;
            private Object loginTime;
            private Object wx;
            private Object wxNumber;
            private Object wxOpenId;
            private boolean status;
            private Object myInvitationCode;
            private Object useInvitationCode;
            private Object invitationUserId;
            private Object channelCode;
            private Object channelId;
            private Object userId;
            private int sex;
            private String birthday;
            private int latitude;
            private int longitude;
            private String cloudTencentAccount;
            private String autograph;
            private String hometown;
            private String height;
            private String loveStatus;
            private Object browseNumber;
            private String address;

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

            public String getHeadImgUrl() {
                return headImgUrl;
            }

            public void setHeadImgUrl(String headImgUrl) {
                this.headImgUrl = headImgUrl;
            }

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public Object getRealName() {
                return realName;
            }

            public void setRealName(Object realName) {
                this.realName = realName;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getLoginTime() {
                return loginTime;
            }

            public void setLoginTime(Object loginTime) {
                this.loginTime = loginTime;
            }

            public Object getWx() {
                return wx;
            }

            public void setWx(Object wx) {
                this.wx = wx;
            }

            public Object getWxNumber() {
                return wxNumber;
            }

            public void setWxNumber(Object wxNumber) {
                this.wxNumber = wxNumber;
            }

            public Object getWxOpenId() {
                return wxOpenId;
            }

            public void setWxOpenId(Object wxOpenId) {
                this.wxOpenId = wxOpenId;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public Object getMyInvitationCode() {
                return myInvitationCode;
            }

            public void setMyInvitationCode(Object myInvitationCode) {
                this.myInvitationCode = myInvitationCode;
            }

            public Object getUseInvitationCode() {
                return useInvitationCode;
            }

            public void setUseInvitationCode(Object useInvitationCode) {
                this.useInvitationCode = useInvitationCode;
            }

            public Object getInvitationUserId() {
                return invitationUserId;
            }

            public void setInvitationUserId(Object invitationUserId) {
                this.invitationUserId = invitationUserId;
            }

            public Object getChannelCode() {
                return channelCode;
            }

            public void setChannelCode(Object channelCode) {
                this.channelCode = channelCode;
            }

            public Object getChannelId() {
                return channelId;
            }

            public void setChannelId(Object channelId) {
                this.channelId = channelId;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public int getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public int getLongitude() {
                return longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }

            public String getCloudTencentAccount() {
                return cloudTencentAccount;
            }

            public void setCloudTencentAccount(String cloudTencentAccount) {
                this.cloudTencentAccount = cloudTencentAccount;
            }

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getHometown() {
                return hometown;
            }

            public void setHometown(String hometown) {
                this.hometown = hometown;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getLoveStatus() {
                return loveStatus;
            }

            public void setLoveStatus(String loveStatus) {
                this.loveStatus = loveStatus;
            }

            public Object getBrowseNumber() {
                return browseNumber;
            }

            public void setBrowseNumber(Object browseNumber) {
                this.browseNumber = browseNumber;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
