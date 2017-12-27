package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.GameBean;

/**
 * Created by lian on 2017/12/9.
 */
public interface AddLikeGameListener {
    void onSucceed();

    void onFailedMsg(String msg);

    void onScreenGame(String id);

    void onGamesInfo(List<GameBean> games);

    void onAddLikeGames();
}
