package laoyou.com.laoyou.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by lian on 2017/10/31.
 */
public class CheckThread extends Thread {
    private static final String TAG = "CheckThread";
    private static CheckThread thread;
    private int time;
    private Handler handler;

    public CheckThread(Handler handler) {
        thread = this;
        this.handler = handler;
        time = 3;
    }

    @Override
    public void run() {
        while (Fields.CHECKFLAG) {
            try {
                Log.i(TAG, "Check Thread run time ==" + time);
                sleep(1000);
                Message message = new Message();
                message.what = 11;

                Bundle bundle = new Bundle();
                bundle.putInt("time", time);
                message.setData(bundle);

                handler.sendMessage(message);

                if (time <= 0)
                    time = 3;
                else
                    time--;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized CheckThread ThreadInstance() {
        return thread;
    }

    public void ClearThread() {
        thread = null;
    }
}
