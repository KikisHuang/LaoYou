package laoyou.com.laoyou.listener;

import okhttp3.Request;

/**
 * Created by lian on 2017/10/25.
 */
public interface HttpResultListener {
    /**
     * 成功回调;
     *
     * @param response
     */
    void onSucceed(String response, int tag);

    /**
     * 网络异常回调;
     *
     * @param request
     * @param e
     */
    void onError(Request request, Exception e);

    /**
     * 解析异常回调;
     *
     * @param e
     */
    void onParseError(Exception e);

    /**
     * code返回失败回调;
     *
     * @param response
     * @param code
     */
    void onFailed(String response, int code, int tag);


}
