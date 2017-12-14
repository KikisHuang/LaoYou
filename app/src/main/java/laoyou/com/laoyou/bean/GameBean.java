package laoyou.com.laoyou.bean;

import java.io.Serializable;

/**
 * Created by lian on 2017/12/9.
 */
public class GameBean implements Serializable {
    private String id;
    private String name;
    private String headUrl;
    private boolean slector;

    public boolean isSlector() {
        return slector;
    }

    public void setSlector(boolean slector) {
        this.slector = slector;
    }

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
