package laoyou.com.laoyou.thread;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.ThumbnailListener;

import static laoyou.com.laoyou.utils.JsonUtils.StatusPaser;

/**
 * Created by lian on 2018/1/20.
 */
public class CustomAsyncTask extends AsyncTask<Object, Void, List<TopicTypeBean>> {
    private ThumbnailListener listener;
    public CustomAsyncTask(ThumbnailListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<TopicTypeBean> doInBackground(Object... params) {
        try {
            if (isCancelled()) {
                return null;
            }
            return isCancelled() ? null : StatusPaser((JSONArray) params[0], (List<TopicTypeBean>) params[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<TopicTypeBean> list) {
        if (!isCancelled() && list != null)
            listener.onThumbnailResult(list);
    }
}
