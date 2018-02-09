package laoyou.com.laoyou.presenter;


import android.util.Log;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.CacheManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONException;

import java.io.File;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.bean.GamesAdvBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.OutSideListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import laoyou.com.laoyou.view.OutSideWebChromeClient;
import laoyou.com.laoyou.view.OutSideWebViewClient;
import okhttp3.Request;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;
import static laoyou.com.laoyou.utils.SynUtils.gets;


/**
 * Created by lian on 2017/11/1.
 */
public class OutSidePresenter implements HttpResultListener {
    private static final String TAG = "OutSidePresenter";
    private OutSideListener listener;
    public int page = 0;

    public OutSidePresenter(OutSideListener listener) {
        this.listener = listener;
    }


    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:
                listener.onCommentSucced();
                break;
            case Fields.REQUEST2:
                //date1表示已点赞，0表示未点赞
                int data = Integer.parseInt(getJsonSring(response));
                if (data == 1)
                    listener.onLikeStatus(true);
                else if (data == 0)
                    listener.onLikeStatus(false);

                break;
            case Fields.REQUEST3:
                //成功code是1,date1表示点赞成功，0表示取消点赞
                int c = Integer.parseInt(getJsonSring(response));
                if (c == 1) {
                    listener.onLikeStatus(true);
                    listener.onFailedMsg(gets(R.string.like_succ));
                } else if (c == 0) {
                    listener.onLikeStatus(false);
                    listener.onFailedMsg(gets(R.string.cancel_succ));
                }
                listener.onWebRefresh();
                break;

            case Fields.REQUEST4:
                GamesAdvBean av = GsonUtil.GsonToBean(getJsonSring(response), GamesAdvBean.class);
                listener.onShowAdv(av);
                break;
            case Fields.REQUEST5:
                List<CafCommentBean> list = GsonUtil.jsonToList(getJsonSring(response), CafCommentBean.class);
                listener.onCommentData(list);
                break;
        }
        Cancle();
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailedMsg(gets(R.string.networkerror));
        Cancle();
    }

    @Override
    public void onParseError(Exception e) {
        Log.e(TAG, "parse Error ==" + e);
        Cancle();
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailedMsg(response);
        Cancle();
    }

    public void Presetner(WebView webView, String url) {

        Log.i(TAG, " url ===" + url);

        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new OutSideWebViewClient());
        webView.setWebChromeClient(new OutSideWebChromeClient(listener));
        webinit(webView);

     /*   int h = (int) (webView.getContentHeight() * webView.getScale() - webView.getHeight());
        com.umeng.socialize.utils.Log.e(TAG, "webheight =====" + h);*/
        webView.setLayoutParams((ViewGroup.LayoutParams) getLayout(0, Fields.MATCH, Fields.WRAP));
    }

    private void webinit(WebView webView) {
//      webView.setInitialScale(50);//这里一定要设置，数值可以根据各人的需求而定，我这里设置的是50%的缩放

        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setDomStorageEnabled(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webView.goBack();

    }

    /**
     * 清除WebView资源，及清除缓存；
     *
     * @param webView
     */
    public void destroyWebView(WebView webView) {
        File file = CacheManager.getCacheFileBaseDir();

        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
            file.delete();

        }
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank");
            webView.freeMemory();
//          webView.pauseTimers();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }

    public void SendComment(String content, String id) {
        Map<String, String> map = getKeyMap();
        map.put("newsId", id);
        map.put("content", content);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GAMEINFOSENDCOMMENT);
    }

    public void CheckLike(String id) {
        Map<String, String> map = getKeyMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.CHECKLIKENEWS);
    }

    public void LikeNews(String id) {
        Map<String, String> map = getKeyMap();
        map.put("id", id);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST3, Interface.URL + Interface.LIKENEWS);
    }

    public void getAdvData() {

        Map<String, String> map = getParamsMap();
        map.put("showPosition", String.valueOf(100));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST4, Interface.URL + Interface.GETRANDOMBANNER);
    }

    public void getCommentData(String id) {
        Map<String, String> map = getParamsMap();
        map.put("id", id);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(page + Fields.SIZE));
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST5, Interface.URL + Interface.GETGAMESCOMMENT);
    }
}