package laoyou.com.laoyou.bean;

import android.view.View;

/**
 * Created by lian on 2017/6/15.
 */
public class PageTopBannerBean {

    private String httpUrl;
    private String id;
    private String imgUrl;
    private String remarks;
    private int type;
    private int uid;
    private int clickCount;

    private String info;
    private String title;
    private String value;
    private int showPosition;

    @Override
    public String toString() {
        return "PageTopBannerBean{" +
                "httpUrl='" + httpUrl + '\'' +
                ", id='" + id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", remarks='" + remarks + '\'' +
                ", type=" + type +
                ", uid=" + uid +
                ", clickCount=" + clickCount +
                ", info='" + info + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", showPosition=" + showPosition +
                ", view=" + view +
                '}';
    }

    public int getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(int showPosition) {
        this.showPosition = showPosition;
    }

    private View view;

    public PageTopBannerBean() {

    }

    public PageTopBannerBean(String id,String httpUrl, String imgUrl, String remarks, int type, int uid, int clickCount, String info, String title, String value, int showPosition) {
        this.httpUrl = httpUrl;
        this.id = id;
        this.imgUrl = imgUrl;
        this.remarks = remarks;
        this.type = type;
        this.uid = uid;
        this.clickCount = clickCount;
        this.info = info;
        this.title = title;
        this.value = value;
        this.showPosition = showPosition;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
