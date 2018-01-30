package laoyou.com.laoyou.thread;

import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.listener.OverInfoListener;

/**
 * Created by lian on 2017/11/30.
 */
public class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
    private OverInfoListener listener;

    public getImageCacheAsyncTask(OverInfoListener listener) {
        this.listener = listener;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl = params[0];
        try {
            return Glide.with(MyApplication.getContext())
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        listener.onFile(result);
    }
}
