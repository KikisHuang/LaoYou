package laoyou.com.laoyou.activity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.OutSideListener;
import laoyou.com.laoyou.presenter.OutSidePresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;


/**
 * Created by lian on 2017/7/8.
 */
public class OutSideActivity extends InitActivity implements OutSideListener {
    private static final String TAG = "OutSideActivity";
    private String url = "";
    private WebView webView;
    private OutSidePresenter op;
    private ProgressBar pg;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.outside_activity_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitles(this);
        webView = f(R.id.webView);
        pg = f(R.id.progressBar);
        op = new OutSidePresenter(this);
        url = getIntent().getStringExtra("outside_url");
        Log.i(TAG, "OutSide Url ===" + url);
//        url = "https://www.baidu.com";
    }

    @Override
    protected void initData() {
        try {
//            Show(this, "加载中", true, null);
            op.Presetner(webView, url);
//            synCookies(this, url);
        } catch (Exception e) {
            ToastUtil.toast2_bottom(OutSideActivity.this, "非常抱歉,该页面发生了异常!");
            Cancle();
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
        ActivityCollector.removeActivity(this);
        op.destroyWebView(webView);
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Cancle();
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else
            finish();
        return super.onKeyDown(keyCode, event);
    }

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

    }

    @Override
    public void onFailedMsg(String gets) {

    }

    @Override
    public void onLikeStatus(boolean b) {

    }
}
