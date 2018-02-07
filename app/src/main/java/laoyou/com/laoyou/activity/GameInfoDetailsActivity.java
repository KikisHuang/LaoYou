package laoyou.com.laoyou.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.GamesCommentAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.bean.GamesAdvBean;
import laoyou.com.laoyou.listener.KeyboardChangeListener;
import laoyou.com.laoyou.listener.OutSideListener;
import laoyou.com.laoyou.presenter.OutSidePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.Fields.RandomBackGGround;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;
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
    //private ProgressBar pg;
    private FrameLayout add_like_layout, comment_layout, share_layout;
    private ImageView photo_img;
    private EditText comment_ed;
    private TextView send_comment_tv;
    private LinearLayout send_comment_layout, bottom_menu_layout, wx_layout, wxcircle_layout;
    private String id = "";
    private ImageView like_icon;
    private LinearLayout head_layout;
    private ImageView head_img, adv_img;
    private float webheight = 0;
    private ListView listView;
    private GamesCommentAdapter adapter;
    private List<CafCommentBean> list;
    private List<GamesAdvBean> advlist;
    private String advUrl = "";
    private String CoverUrl = "";

    @Override
    protected void click() {
        new KeyboardChangeListener(this).setKeyBoardListener(this);
        send_comment_tv.setOnClickListener(this);
        comment_layout.setOnClickListener(this);
        add_like_layout.setOnClickListener(this);
        share_layout.setOnClickListener(this);
        adv_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.game_info_details_layout);
        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.game_info_details_head, null);
        ActivityCollector.addActivity(this, getClass());
        setTitles(this);
        webView = (WebView) head_layout.findViewById(R.id.webView);
        webView.setFocusable(false);
        head_img = (ImageView) head_layout.findViewById(R.id.head_img);
        adv_img = (ImageView) head_layout.findViewById(R.id.adv_img);
        wx_layout = (LinearLayout) head_layout.findViewById(R.id.wx_layout);
        wxcircle_layout = (LinearLayout) head_layout.findViewById(R.id.wxcircle_layout);
        head_img.setLayoutParams((ViewGroup.LayoutParams) getLayout(0, Fields.MATCH, DeviceUtils.getWindowWidth(this) * 4 / 5));

        adv_img.setLayoutParams((ViewGroup.LayoutParams) getLayout(1, Fields.MATCH, DeviceUtils.getWindowWidth(this) * 1 / 2));

        listView = f(R.id.listView);
        list = new ArrayList<>();
        advlist = new ArrayList<>();

//      pg = f(R.id.progressBar);
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
        CoverUrl = getIntent().getStringExtra("game_info_cover");
        url = Interface.NEWSDETAILS + "id=" + id + "&key=" + SPreferences.getUserToken();
        op.CheckLike(id);
    }

    @Override
    protected void initData() {

        Glide.with(MyApplication.getContext()).load(CoverUrl.isEmpty() ? RandomBackGGround() : CoverUrl).into(head_img);

        adapter = new GamesCommentAdapter(this, list);
        listView.setAdapter(adapter);

        try {
//          Show(this, "加载中", true, null);
            op.Presetner(webView, url);
            op.getAdvData();
            op.getCommentData(id);
//          synCookies(this, url);
        } catch (Exception e) {
            Log.e(TAG, "异常信息 =====" + e);
            ToastUtil.toast2_bottom(GameInfoDetailsActivity.this, "非常抱歉,该页面发生了异常!");
            finish();
        }
        listView.addHeaderView(head_layout);
    }

/*    */

    /**
     * 同步cookie
     *//*
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "Cookie Test...");//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }*/
    @Override
    public void onCancleProgress() {
//        pg.setVisibility(View.INVISIBLE);
//        pg.setProgress(0);
    }

    @Override
    public void onShowProgress(int pro) {
//        pg.setVisibility(View.VISIBLE);
//        pg.setProgress(pro);
    }

    @Override
    public void onCommentSucced() {
     /*   webView.reload();
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:loadComments()");
            }
        });*/

        op.getCommentData(id);
        ToastUtil.toast2_bottom(this, gets(R.string.send_success));
    }

    @Override
    public void onFailedMsg(String gets) {
        ToastUtil.toast2_bottom(this, gets);
    }

    @Override
    public void onLikeStatus(boolean b) {
        Glide.with(MyApplication.getContext()).load(b ? R.mipmap.on_like_icon : R.mipmap.heart_black_icon).into(like_icon);
    }

    @Override
    public void onShowAdv(GamesAdvBean adv) {
        if (!adv.getImgUrl().isEmpty()) {
            Glide.with(MyApplication.getContext()).load(adv.getImgUrl()).into(adv_img);
            adv_img.setVisibility(View.VISIBLE);
            adv_img.setOnClickListener(this);
            advUrl = adv.getHttpUrl();
        } else {
            advUrl = "";
            adv_img.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCommentData(List<CafCommentBean> data) {

        list.clear();

        for (CafCommentBean cfb : data) {
            list.add(cfb);
        }
        adapter.notifyDataSetChanged();
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
                JsShare();
                break;

            case R.id.adv_img:
                goOutSidePage(this, url);
                break;
            case R.id.wx_layout:

                break;
            case R.id.wxcircle_layout:

                break;

        }
    }

    public void JsShare() {

        UMWeb web = new UMWeb(Interface.NEWSDETAILS + "id=" + id);
        web.setTitle(gets(R.string.game_information));//标题
        web.setThumb(new UMImage(this, R.mipmap.logo_icon));  //缩略图
        web.setDescription(gets(R.string.game_information));//描述

        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//              .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .withMedia(web)
                .setCallback(this)
                .open();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        op.destroyWebView(webView);
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
    }
}
