package laoyou.com.laoyou.presenter;

import org.json.JSONException;

import laoyou.com.laoyou.listener.AddTopicTypeListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/12/28.
 */
public class AddTopicTypePresenter implements HttpResultListener {

    private static final String TAG = getTAG(AddTopicTypePresenter.class);
    private AddTopicTypeListener listener;

    public AddTopicTypePresenter(AddTopicTypeListener listener) {
        this.listener = listener;
        getTopicTypeDataList();
    }

    private void getTopicTypeDataList() {

    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

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
}
