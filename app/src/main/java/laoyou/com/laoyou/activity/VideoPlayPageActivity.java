package laoyou.com.laoyou.activity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.jiaozi.JZMediaIjkplayer;
import laoyou.com.laoyou.utils.jiaozi.MyJZVideoPlayerStandard;

/**
 * Created by lian on 2018/1/2.
 */
public class VideoPlayPageActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "VideoPlayPageActivity";
    private MyJZVideoPlayerStandard myJZVideoPlayerStandard;
    private String videoUrl = "";
    private String coverPath = "";
    private FrameLayout video_layout;

    @Override
    protected void click() {
        video_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.video_play_layout);
        videoUrl = getIntent().getStringExtra("Video_url");
        coverPath = getIntent().getStringExtra("Video_coverPath");
        myJZVideoPlayerStandard = f(R.id.videoplayer);
        video_layout = f(R.id.video_layout);
        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getWindowHeight(this) * 1 / 3);
        fp.gravity = Gravity.CENTER;
        myJZVideoPlayerStandard.setLayoutParams(fp);
        JZVideoPlayer.setMediaInterface(new JZMediaIjkplayer());
        VideoInit();

    }

    private void VideoInit() {

        myJZVideoPlayerStandard.setUp(videoUrl
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Glide.with(this)
                .load(coverPath)
                .into(myJZVideoPlayerStandard.thumbImageView);
        myJZVideoPlayerStandard.startVideo();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myJZVideoPlayerStandard.onStateAutoComplete();
        JZVideoPlayer.releaseAllVideos();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_layout:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
