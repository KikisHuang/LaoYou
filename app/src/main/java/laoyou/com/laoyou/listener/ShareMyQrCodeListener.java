package laoyou.com.laoyou.listener;

/**
 * Created by lian on 2018/2/9.
 */
public interface ShareMyQrCodeListener {

    void ShowMyQrCode(String codeurl);

    void onFailedMsg(String msg);

    void onShareQrCodeUrl(String str);
}
