package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2017/10/26.
 */
public interface RegisterListener {
    void onCountDown(int page);
    void onOver();
    void onForbid();
    void onPhoneLengthError();
    void onRegisterFailedMsg(String msg);
    void OverInfo();
    void onFailed(String msg);
}
