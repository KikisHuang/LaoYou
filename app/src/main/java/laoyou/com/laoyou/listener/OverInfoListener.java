package laoyou.com.laoyou.listener;

import java.io.File;

/**
 * Created by lian on 2017/10/28.
 */
public interface OverInfoListener {
    /**
     * 网络成功回调
     *
     * @param flag
     */
    void onSucced(int flag);

    /**
     * 网络失败回调
     *
     * @param msg 失败信息;
     */
    void onFailed(String msg);

    /**
     * Edittext清空回调
     */
    void onClear();

    /**
     * Edittext提交监听;
     */
    void onCommit();

    /**
     * 网络异常回调
     *
     * @param msg 失败信息;
     */
    void onErrorMsg(String msg);

    /**
     * 设置头像、名称回调;
     *
     * @param imgPath 头像;
     * @param name    名称;
     */
    void setHeadImgAndName(String imgPath, String name);

    /**
     * im 登录成功回调;
     */
    void onImSucceed();

    /**
     * im 登录异常或失败回调;
     *
     * @param gets
     */
    void onImFailed(String gets);

    void onFile(File f);

    void onCompressSucceed(File f);
}
