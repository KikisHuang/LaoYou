package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.AddLikeGameListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/9.
 */
public class AddLikeGamePresenter implements HttpResultListener {
    private static final String TAG = "AddLikeGamePresenter";
    private AddLikeGameListener listener;
    public int page = 0;

    public AddLikeGamePresenter(AddLikeGameListener listener) {
        this.listener = listener;
    }

    public void getGameListData() {

        Map<String, String> map = getKeyMap();
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + 10));

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETGAMESBYPAGE);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                List<GameBean> games = GsonUtil.jsonToList(getJsonSring(response), GameBean.class);
                listener.onGamesInfo(games);
                break;
            case Fields.REQUEST2:
                int f = Integer.parseInt(getJsonSring(response));
                if (f == 0)
                    listener.onAddLikeGames();
                else
                    listener.onFailedMsg(gets(R.string.already_game));
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "Parse Error ===" + e);
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
        Cancle();
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

    public void CheckGames(GameBean gameBean) {

        Map<String, String> map = getKeyMap();
        map.put("gameId", gameBean.getId());

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.CHECKFOLLOWGAME);
    }
}
