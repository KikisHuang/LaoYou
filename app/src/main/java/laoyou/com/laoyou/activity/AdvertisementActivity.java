package laoyou.com.laoyou.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Timer;
import java.util.TimerTask;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.AdvertisementListener;
import laoyou.com.laoyou.presenter.AdvertisementPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;

import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.SynUtils.StringIsNull;


/**
 * Created by lian on 2018/1/23.
 */
public class AdvertisementActivity extends Activity implements View.OnClickListener, AdvertisementListener {
    private TextView skip_tv;
    private ImageView welcome_img;
    private Handler handler;
    private int time = 5;
    private Timer tm;
    private AdvertisementPresenter attp;
    private String advUrl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_layout);
        ActivityCollector.addActivity(this, getClass());
        skip_tv = (TextView) findViewById(R.id.skip_tv);
        skip_tv.setOnClickListener(this);
        welcome_img = (ImageView) findViewById(R.id.welcome_img);
        Hand();
        attp = new AdvertisementPresenter(this);
    }

    private void setAdvClickListener() {
        welcome_img.setOnClickListener(this);
    }

    private void Hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 99:
                        startTimer();
                        skip_tv.setText("跳过 " + time + "s");
                        skip_tv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        skip_tv.setText("跳过 " + time + "s");
                        goMain("");
                        break;
                    default:
                        skip_tv.setText("跳过 " + time + "s");
                        break;
                }
            }
        };
    }

    private void startTimer() {
        tm = new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                if (time == 0) {

                } else {
                    time--;
                    handler.sendEmptyMessage(time);
                }
            }
        }, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

        if (handler != null) {
            welcome_img.setImageBitmap(null);
            handler.removeCallbacksAndMessages(null);
            handler = null;
            if (tm != null) {
                tm.cancel();
                tm = null;
            }
        }
    }

    private void goMain(String advUrl) {
        if (!advUrl.isEmpty())
            goOutSidePage(this, advUrl);

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.skip_tv:
                goMain("");
                welcome_img.setEnabled(false);
                break;
            case R.id.welcome_img:
                if(!advUrl.isEmpty()){
                    goMain(advUrl);
                    welcome_img.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onSucceed(String welcomeImgUrl, String welcomeHTTPUrl) {
        advUrl = welcomeHTTPUrl;
        if (!StringIsNull(welcomeImgUrl).isEmpty()) {
            if (!StringIsNull(advUrl).isEmpty())
                setAdvClickListener();
            Glide.with(getApplicationContext()).load(welcomeImgUrl).into(welcome_img);
            Glide.with(getApplicationContext()).asBitmap().load(welcomeImgUrl).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, Transition<? super Bitmap> transition) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = 99;
                            handler.sendMessage(message);
                        }
                    });
                }
            });

        } else
            goMain("");
    }

    @Override
    public void onFailedMsg(String msg) {
        goMain("");
    }
}
