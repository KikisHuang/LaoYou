package laoyou.com.laoyou.bean;

import android.view.View;

/**
 * Created by lian on 2018/1/5.
 */
public class AddressBookBean {

    /**
     * address : 辽宁省-沈阳市
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/15149577824502a9d65.jpg
     * sex : 1
     * cloudTencentAccount : d8622f0b4a03435eb189d7bdba15f8
     * name : 高仿陈晓燕1
     * browseNumber : null
     * autograph : 我是fipri_rnxiaoyan画画
     * id : ud7163f19736d4bee8f907cfa9d2333a6
     */

    private String address;
    private String headImgUrl;
    private int sex;
    private String cloudTencentAccount;
    private String name;
    private Object browseNumber;
    private String autograph;
    private String id;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    private View view;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCloudTencentAccount() {
        return cloudTencentAccount;
    }

    public void setCloudTencentAccount(String cloudTencentAccount) {
        this.cloudTencentAccount = cloudTencentAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(Object browseNumber) {
        this.browseNumber = browseNumber;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
