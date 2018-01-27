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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.ToastUtil;
import okhttp3.Call;

import static laoyou.com.laoyou.bean.ServerCode.getCodeStatusMsg;
import static laoyou.com.laoyou.utils.IntentUtils.goMainPage;
import static laoyou.com.laoyou.utils.IntentUtils.goWelcomePage;
import static laoyou.com.laoyou.utils.JsonUtils.getCode;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
import static laoyou.com.laoyou.utils.SynUtils.StringIsNull;


/**
 * Created by lian on 2018/1/23.
 */
public class GuidePageActivity extends Activity implements View.OnClickListener {
    private TextView skip_tv;
    private ImageView welcome_img;
    private Handler handler;
    private int time = 5;
    private Timer tm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPreferences.getFirstStart())
            goWel();

        setContentView(R.layout.guide_layout);
        skip_tv = (TextView) findViewById(R.id.skip_tv);
        skip_tv.setOnClickListener(this);
        welcome_img = (ImageView) findViewById(R.id.welcome_img);
        Hand();
        getData();

    }

    private void getData() {
        OkHttpUtils
                .get()
                .url(Interface.URL + Interface.GETSETTING)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GuidePageActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                String imgurl = ob.optString("welcomeImgUrl");

                                if (!StringIsNull(imgurl).isEmpty()) {
                                    Glide.with(getApplicationContext()).load(imgurl).into(welcome_img);
                                    Glide.with(getApplicationContext()).asBitmap().load(imgurl).into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(final Bitmap resource, Transition<? super Bitmap> transition) {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Message message = new Message();
                                                    message.what = 99;
//                                                message.obj = resource;
                                                    handler.sendMessage(message);
                                                }
                                            });
                                        }
                                    });

                                } else
                                    goMain();

                            } else {
                                ToastUtil.toast2_bottom(GuidePageActivity.this, getCodeStatusMsg(code));
                                goMain();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
                        goMain();
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

        OkHttpUtils.getInstance().cancelTag(this);

        if (handler != null) {
            welcome_img.setImageBitmap(null);
            handler.removeCallbacksAndMessages(null);
            handler = null;
            if (tm != null) {
                tm.cancel();
                tm = null;
            }
        }
        System.gc();
    }

    private void goMain() {
        goMainPage(this);
        finish();
    }

    private void goWel() {
        goWelcomePage(this);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.skip_tv:
                goMain();
                break;
        }
    }
}
