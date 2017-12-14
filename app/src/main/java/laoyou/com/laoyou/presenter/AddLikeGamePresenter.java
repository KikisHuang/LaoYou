package laoyou.com.laoyou.presenter;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.AddLikeGameListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/9.
 */
public class AddLikeGamePresenter implements HttpResultListener {
    private static final String TAG = "AddLikeGamePresenter";
    private AddLikeGameListener listener;

    public AddLikeGamePresenter(AddLikeGameListener listener) {
        this.listener = listener;
        getGameListData();
    }

    private void getGameListData() {

    }

    @Override
    public void onSucceed(String response, int tag) {

    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    public void ChangeLikeGameList(List<GameBean> list) {
        List<String> data = new ArrayList<>();
        for (GameBean gb : list) {
            if (gb.isSlector())
                data.add(gb.getId());
        }
        if (data.size() <= 0)
            listener.onFailedMsg(gets(R.string.add_game_is_null));
        else
            Commit(data);
    }

    private void Commit(List<String> data) {

    }
}
