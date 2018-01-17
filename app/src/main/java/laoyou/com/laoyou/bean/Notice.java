package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2018/1/16.
 */
public class Notice {
    // 通知开关状态,0 false，1 true；

    public Notice() {
    }

    public Notice(String id, int notice, int c2c, int group) {
        this.notice = notice;
        this.c2c = c2c;
        this.chatgroups = group;
        this.id = id;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getC2c() {
        return c2c;
    }

    public void setC2c(int c2c) {
        this.c2c = c2c;
    }

    public int getGroup() {
        return chatgroups;
    }

    public void setGroup(int group) {
        this.chatgroups = group;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "notice=" + notice +
                ", c2c=" + c2c +
                ", chatgroups=" + chatgroups +
                '}';
    }

    private int notice;
    private int c2c;
    private int chatgroups;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
