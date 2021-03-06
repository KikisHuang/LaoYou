package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2018/1/5.
 */
public class ActiveBean {


    public ActiveBean() {
    }

    public ActiveBean(String id, String groupId, String type, String name, String introduction, String faceUrl, int lastMsgTime, int memberNum,int maxMemberNum, String applyJoinOption) {
        MaxMemberNum = maxMemberNum;
        GroupId = groupId;
        Type = type;
        Name = name;
        Introduction = introduction;
        FaceUrl = faceUrl;
        LastMsgTime = lastMsgTime;
        MemberNum = memberNum;
        ApplyJoinOption = applyJoinOption;
        this.id = id;
    }

    /**
     * GroupId : official-f81bd9e560544b889e2f49b8828112f4
     * Type : Public
     * Name : asdfasdf
     * Introduction : asdfasdf
     * FaceUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/151265504401257974a.gif
     * LastMsgTime : 0
     * MemberNum : 0
     * MaxMemberNum : 2000
     * ApplyJoinOption : FreeAccess
     */

    private String GroupId;
    private String Type;
    private String Name;
    private String Introduction;
    private String FaceUrl;
    private int LastMsgTime;
    private int MemberNum;
    private int MaxMemberNum;
    private String ApplyJoinOption;

    @Override
    public String toString() {
        return "ActiveBean{" +
                "GroupId='" + GroupId + '\'' +
                ", Type='" + Type + '\'' +
                ", Name='" + Name + '\'' +
                ", Introduction='" + Introduction + '\'' +
                ", FaceUrl='" + FaceUrl + '\'' +
                ", LastMsgTime=" + LastMsgTime +
                ", MemberNum=" + MemberNum +
                ", MaxMemberNum=" + MaxMemberNum +
                ", ApplyJoinOption='" + ApplyJoinOption + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String Introduction) {
        this.Introduction = Introduction;
    }

    public String getFaceUrl() {
        return FaceUrl;
    }

    public void setFaceUrl(String FaceUrl) {
        this.FaceUrl = FaceUrl;
    }

    public int getLastMsgTime() {
        return LastMsgTime;
    }

    public void setLastMsgTime(int LastMsgTime) {
        this.LastMsgTime = LastMsgTime;
    }

    public int getMemberNum() {
        return MemberNum;
    }

    public void setMemberNum(int MemberNum) {
        this.MemberNum = MemberNum;
    }

    public int getMaxMemberNum() {
        return MaxMemberNum;
    }

    public void setMaxMemberNum(int MaxMemberNum) {
        this.MaxMemberNum = MaxMemberNum;
    }

    public String getApplyJoinOption() {
        return ApplyJoinOption;
    }

    public void setApplyJoinOption(String ApplyJoinOption) {
        this.ApplyJoinOption = ApplyJoinOption;
    }
}
