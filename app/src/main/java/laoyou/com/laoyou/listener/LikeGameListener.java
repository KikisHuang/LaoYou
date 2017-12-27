package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.GameBean;

/**
 * Created by lian on 2017/12/15.
 */
public interface LikeGameListener {
    void onFailedsMSg(String msg);

    void onGamesInfo(List<GameBean> games);

    void onSucceed(String msg);
}
