package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/12/23.
 */
public class McChatMessage {

    /**
     * id : m-92a2e93ff268419ba52c657da2b61b2f
     * mcUserByUserId : {"id":"ud7163f19736d4bee8f907cfa9d2333a6","name":"高端大气","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624590aa83ff.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624934b5bfda.jpg","realName":"黄子奇","idcard":"450103199105252511","account":"13878141913","phone":"13878141913","createTime":"2017-11-17 16:16:31","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"1982-01-01","latitude":22.831043,"longitude":108.369624,"cloudTencentAccount":"d8622f0b4a03435eb189d7bdba15f8","autograph":"我是fipri_rnxiaoyan画画","hometown":"山西省 - 太原市","height":"186~190cm","loveStatus":"单身","browseNumber":null,"address":"辽宁省-沈阳市"}
     * mcChatMessage : {"id":"m-9e025c2d97b345fcb9a63bb7d029d869","mcUserByUserId":{"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":37.785834,"longitude":-122.406417,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"},"mcChatMessage":null,"createTime":"2017-12-21 00:15:53","messageTypeFlag":0,"messageContent":"古古怪怪","hasImg":null,"modifyTime":"2017-12-21 00:15:53"}
     * createTime : 2017-12-22 18:24:14
     * messageTypeFlag : 100
     * messageContent : 回复评论
     * hasImg : null
     * modifyTime : 2017-12-22 18:24:14
     */

    private String id;
    private McUserByUserIdBean mcUserByUserId;
    private McChatMessageBean mcChatMessage;
    private String createTime;
    private int messageTypeFlag;
    private String messageContent;
    private Object hasImg;
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

    public McChatMessageBean getMcChatMessage() {
        return mcChatMessage;
    }

    public void setMcChatMessage(McChatMessageBean mcChatMessage) {
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

    public static class McUserByUserIdBean {
        /**
         * id : ud7163f19736d4bee8f907cfa9d2333a6
         * name : 高端大气
         * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624590aa83ff.jpg
         * backgroundUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513578624934b5bfda.jpg
         * realName : 黄子奇
         * idcard : 450103199105252511
         * account : 13878141913
         * phone : 13878141913
         * createTime : 2017-11-17 16:16:31
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
         * birthday : 1982-01-01
         * latitude : 22.831043
         * longitude : 108.369624
         * cloudTencentAccount : d8622f0b4a03435eb189d7bdba15f8
         * autograph : 我是fipri_rnxiaoyan画画
         * hometown : 山西省 - 太原市
         * height : 186~190cm
         * loveStatus : 单身
         * browseNumber : null
         * address : 辽宁省-沈阳市
         */

        private String id;
        private String name;
        private String headImgUrl;
        private String backgroundUrl;
        private String realName;
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
        private double latitude;
        private double longitude;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
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

    public static class McChatMessageBean {
        /**
         * id : m-9e025c2d97b345fcb9a63bb7d029d869
         * mcUserByUserId : {"id":"ua664fd002910441aa098bcaf295622f8","name":"默默","headImgUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg","backgroundUrl":"http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582523104122992.jpg","realName":null,"idcard":"452731199101136011","account":"18269286442","phone":"18269286442","createTime":"2017-11-23 12:56:02","loginTime":null,"wx":null,"wxNumber":null,"wxOpenId":null,"status":true,"myInvitationCode":null,"useInvitationCode":null,"invitationUserId":null,"channelCode":null,"channelId":null,"userId":null,"sex":0,"birthday":"2008-10-10","latitude":37.785834,"longitude":-122.406417,"cloudTencentAccount":"814e8953f2644b2baa9d5ed1eddcce","autograph":"jhfjfhjh","hometown":"关西","height":"158-168cm","loveStatus":"保密","browseNumber":null,"address":"广西南宁"}
         * mcChatMessage : null
         * createTime : 2017-12-21 00:15:53
         * messageTypeFlag : 0
         * messageContent : 古古怪怪
         * hasImg : null
         * modifyTime : 2017-12-21 00:15:53
         */

        private String id;
        private McUserByUserIdBeanX mcUserByUserId;
        private Object mcChatMessage;
        private String createTime;
        private int messageTypeFlag;
        private String messageContent;
        private Object hasImg;
        private String modifyTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public McUserByUserIdBeanX getMcUserByUserId() {
            return mcUserByUserId;
        }

        public void setMcUserByUserId(McUserByUserIdBeanX mcUserByUserId) {
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

        public static class McUserByUserIdBeanX {
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
             * latitude : 37.785834
             * longitude : -122.406417
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
            private double latitude;
            private double longitude;
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

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
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
