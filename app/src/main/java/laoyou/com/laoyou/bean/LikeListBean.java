package laoyou.com.laoyou.bean;

import java.io.Serializable;

/**
 * Created by lian on 2017/12/23.
 */
public class LikeListBean implements Serializable {

    /**
     * name : 默默
     * headImgUrl : http://service-platform-internet-bar.oss-cn-hangzhou.aliyuncs.com/1513582522786fa1c70.jpg
     * id : ua664fd002910441aa098bcaf295622f8
     */

    private String name;
    private String headImgUrl;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
