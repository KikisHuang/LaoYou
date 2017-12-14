package laoyou.com.laoyou.bean;

import java.util.List;

/**
 * Created by lian on 2017/12/5.
 */
public class GroupBean {
    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public long getNext() {
        return next;
    }

    public void setNext(long next) {
        this.next = next;
    }

    public List<GroupBean.GroupInfo> getGroupInfo() {
        return GroupInfo;
    }

    public void setGroupInfo(List<GroupBean.GroupInfo> groupInfo) {
        GroupInfo = groupInfo;
    }

    private String ActionStatus;
    private String ErrorCode;
    private long next;
    private List<GroupInfo> GroupInfo;

    /**
     * Copyright 2017 bejson.com
     */

    /**
     * Auto-generated: 2017-12-05 13:37:13
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class GroupInfo {

        private String ApplyJoinOption;
        private int CreateTime;
        private int ErrorCode;
        private String FaceUrl;
        private String GroupId;
        private String Introduction;
        private int LastInfoTime;
        private int LastMsgTime;
        private int MaxMemberNum;
        private List<MemberList> MemberList;
        private int MemberNum;
        private String Name;
        private int NextMsgSeq;
        private String Notification;
        private String Owner_Account;
        private String Type;
        public void setApplyJoinOption(String ApplyJoinOption) {
            this.ApplyJoinOption = ApplyJoinOption;
        }
        public String getApplyJoinOption() {
            return ApplyJoinOption;
        }

        public void setCreateTime(int CreateTime) {
            this.CreateTime = CreateTime;
        }
        public int getCreateTime() {
            return CreateTime;
        }

        public void setErrorCode(int ErrorCode) {
            this.ErrorCode = ErrorCode;
        }
        public int getErrorCode() {
            return ErrorCode;
        }

        public void setFaceUrl(String FaceUrl) {
            this.FaceUrl = FaceUrl;
        }
        public String getFaceUrl() {
            return FaceUrl;
        }

        public void setGroupId(String GroupId) {
            this.GroupId = GroupId;
        }
        public String getGroupId() {
            return GroupId;
        }

        public void setIntroduction(String Introduction) {
            this.Introduction = Introduction;
        }
        public String getIntroduction() {
            return Introduction;
        }

        public void setLastInfoTime(int LastInfoTime) {
            this.LastInfoTime = LastInfoTime;
        }
        public int getLastInfoTime() {
            return LastInfoTime;
        }

        public void setLastMsgTime(int LastMsgTime) {
            this.LastMsgTime = LastMsgTime;
        }
        public int getLastMsgTime() {
            return LastMsgTime;
        }

        public void setMaxMemberNum(int MaxMemberNum) {
            this.MaxMemberNum = MaxMemberNum;
        }
        public int getMaxMemberNum() {
            return MaxMemberNum;
        }

        public void setMemberList(List<MemberList> MemberList) {
            this.MemberList = MemberList;
        }
        public List<MemberList> getMemberList() {
            return MemberList;
        }

        public void setMemberNum(int MemberNum) {
            this.MemberNum = MemberNum;
        }
        public int getMemberNum() {
            return MemberNum;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
        public String getName() {
            return Name;
        }

        public void setNextMsgSeq(int NextMsgSeq) {
            this.NextMsgSeq = NextMsgSeq;
        }
        public int getNextMsgSeq() {
            return NextMsgSeq;
        }

        public void setNotification(String Notification) {
            this.Notification = Notification;
        }
        public String getNotification() {
            return Notification;
        }

        public void setOwner_Account(String Owner_Account) {
            this.Owner_Account = Owner_Account;
        }
        public String getOwner_Account() {
            return Owner_Account;
        }

        public void setType(String Type) {
            this.Type = Type;
        }
        public String getType() {
            return Type;
        }
        /**
         * Auto-generated: 2017-12-05 13:37:13
         *
         * @author bejson.com (i@bejson.com)
         * @website http://www.bejson.com/java2pojo/
         */
        public class MemberList {

            private int JoinTime;
            private int LastSendMsgTime;
            private String Member_Account;
            private String MsgFlag;
            private int MsgSeq;
            private String Role;
            public void setJoinTime(int JoinTime) {
                this.JoinTime = JoinTime;
            }
            public int getJoinTime() {
                return JoinTime;
            }

            public void setLastSendMsgTime(int LastSendMsgTime) {
                this.LastSendMsgTime = LastSendMsgTime;
            }
            public int getLastSendMsgTime() {
                return LastSendMsgTime;
            }

            public void setMember_Account(String Member_Account) {
                this.Member_Account = Member_Account;
            }
            public String getMember_Account() {
                return Member_Account;
            }

            public void setMsgFlag(String MsgFlag) {
                this.MsgFlag = MsgFlag;
            }
            public String getMsgFlag() {
                return MsgFlag;
            }

            public void setMsgSeq(int MsgSeq) {
                this.MsgSeq = MsgSeq;
            }
            public int getMsgSeq() {
                return MsgSeq;
            }

            public void setRole(String Role) {
                this.Role = Role;
            }
            public String getRole() {
                return Role;
            }

        }
    }

}
