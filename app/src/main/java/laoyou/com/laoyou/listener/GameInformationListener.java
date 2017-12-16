package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.GameInfoBean;
import laoyou.com.laoyou.bean.GameTypeBean;

/**
 * Created by lian on 2017/12/12.
 */
public interface GameInformationListener {
    void onSucceed();
    void onFailedMsg(String msg);
    void onGameTypeInforMation(List<GameTypeBean> list);

    void onGameInfor(List<GameInfoBean> li);
}
