package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/26.
 */
public interface RegisterListener {
    //开启读秒;
    void onCountDown(int page);

    //读秒完成回调;
    void onOver();

    //下一步开关回调;
    void onForbid();

    //手机长度错误回调;
    void onPhoneLengthError();

    //失败消息Toast回调;
    void onRegisterFailedMsg(String msg);

    void OverInfo();

    //失败回调;
    void onFailed(String msg);

    //发送验证码;
    void SendCode();
}
