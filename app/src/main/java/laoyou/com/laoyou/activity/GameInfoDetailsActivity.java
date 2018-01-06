package laoyou.com.laoyou.activity;

import android.content.Context;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.qcloud.sdk.Interface;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.KeyboardChangeListener;
import laoyou.com.laoyou.listener.OutSideListener;
import laoyou.com.laoyou.presenter.OutSidePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.showSoftInputFromWindow;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2018/1/5.
 */
public class GameInfoDetailsActivity extends InitActivity implements OutSideListener, UMShareListener, KeyboardChangeListener.KeyBoardListener, View.OnClickListener {

    private static final String TAG = "GameInfoDetailsActivity";

    private String url = "";
    private WebView webView;
    private OutSidePresenter op;
    private ProgressBar pg;
    private FrameLayout add_like_layout, comment_layout, share_layout;
    private ImageView photo_img;
    private EditText comment_ed;
    private TextView send_comment_tv;
    private LinearLayout send_comment_layout, bottom_menu_layout;
    private String id = "";
    private ImageView like_icon;


    @Override
    protected void click() {
        new KeyboardChangeListener(this).setKeyBoardListener(this);
        send_comment_tv.setOnClickListener(this);
        comment_layout.setOnClickListener(this);
        add_like_layout.setOnClickListener(this);
        share_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.game_info_details_layout);
        setTitles(this);
        webView = f(R.id.webView);
        pg = f(R.id.progressBar);
        add_like_layout = f(R.id.add_like_layout);
        comment_layout = f(R.id.comment_layout);
        share_layout = f(R.id.share_layout);
        photo_img = f(R.id.photo_img);
        comment_ed = f(R.id.comment_ed);
        like_icon = f(R.id.like_icon);

        send_comment_layout = f(R.id.send_comment_layout);
        bottom_menu_layout = f(R.id.bottom_menu_layout);
        send_comment_tv = f(R.id.send_comment_tv);

        photo_img.setVisibility(View.INVISIBLE);

        op = new OutSidePresenter(this);
        id = getIntent().getStringExtra("game_info_id");
        url = Interface.NEWSDETAILS + "id=" + id + "&key=" + SPreferences.getUserToken();
        op.CheckLike(id);
    }

    @Override
    protected void initData() {
        try {
//            Show(this, "加载中", true, null);
            op.Presetner(webView, url);
            synCookies(this, url);
        } catch (Exception e) {
            Log.e(TAG, "异常信息 =====" + e);
            ToastUtil.toast2_bottom(GameInfoDetailsActivity.this, "非常抱歉,该页面发生了异常!");
            finish();
        }
    }

    /**
     * 同步cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "Cookie Test...");//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

    /*  @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
          Cancle();
          if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
              webView.goBack();
              return true;
          } else
              finish();
          return super.onKeyDown(keyCode, event);
      }
  */
    @Override
    public void onCancleProgress() {
        pg.setVisibility(View.INVISIBLE);
        pg.setProgress(0);
    }

    @Override
    public void onShowProgress(int pro) {
        pg.setVisibility(View.VISIBLE);
        pg.setProgress(pro);
    }

    @Override
    public void onCommentSucced() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:loadComments()");

            }
        });
        ToastUtil.toast2_bottom(this, gets(R.string.send_success));
    }

    @Override
    public void onFailedMsg(String gets) {
        ToastUtil.toast2_bottom(this, gets);
    }

    @Override
    public void onLikeStatus(boolean b) {
        Glide.with(this).load(b ? R.mipmap.like_heart : R.mipmap.heart_black_icon).into(like_icon);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send_comment_tv:
                Show(GameInfoDetailsActivity.this, "提交中", true, null);
                op.SendComment(comment_ed.getText().toString(), id);
                break;
            case R.id.comment_layout:
                if (send_comment_layout.getVisibility() == View.GONE) {
                    comment_ed.setText("");
                    send_comment_layout.setVisibility(View.VISIBLE);
                    bottom_menu_layout.setVisibility(View.GONE);
                    showSoftInputFromWindow(comment_ed);
                }
                break;
            case R.id.add_like_layout:
                Show(GameInfoDetailsActivity.this, "", true, null);
                op.LikeNews(id);
                break;
            case R.id.share_layout:

                UMWeb web = new UMWeb(Interface.NEWSDETAILS + "id=" + id);
                web.setTitle(gets(R.string.game_information));//标题
                web.setThumb(new UMImage(this, R.mipmap.logo_icon));  //缩略图
                web.setDescription("");//描述

                new ShareAction(this)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
//                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                        .withMedia(web)
                        .setCallback(this)
                        .open();

                break;
        }
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        Log.i(TAG, "isShow ===" + isShow);
        if (!isShow) {
            bottom_menu_layout.setVisibility(View.VISIBLE);
            send_comment_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.toast2_bottom(this, gets(R.string.share_succ));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.toast2_bottom(this, gets(R.string.share_fail));
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.i(TAG, "取消分享");
    }
}
