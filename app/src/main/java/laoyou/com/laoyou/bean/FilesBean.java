package laoyou.com.laoyou.bean;

import java.io.File;
import java.util.Map;

/**
 * Created by lian on 2017/10/25.
 */
public class FilesBean {
    private String key ;
    private Map<String,File> maps;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, File> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, File> maps) {
        this.maps = maps;
    }
}
