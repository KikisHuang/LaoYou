package laoyou.com.laoyou.bean;

import laoyou.com.laoyou.utils.Cn2Spell;

/**
 * Created by lian on 2017/11/23.
 */
public class AddressBean implements Comparable<AddressBean> {
    private String identifier = "";
    private String nickName = "";
    private String remark = "";
    private String faceUrl = "";
    private String letter = "";
    private boolean AddFlag;

    public boolean isAddFlag() {
        return AddFlag;
    }

    public void setAddFlag(boolean addFlag) {
        AddFlag = addFlag;
    }

    public AddressBean() {
    }

    public AddressBean(String name) {
        this.nickName = name;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        letter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!letter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            letter = "#";
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    private String pinyin; // 姓名对应的拼音

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int compareTo(AddressBean ab) {
        if (letter.equals("#") && !ab.getLetter().equals("#")) {
            return 1;
        } else if (!letter.equals("#") && ab.getLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(ab.getPinyin());
        }
    }
}
