package laoyou.com.laoyou.presenter;


import com.tencent.smtt.sdk.CacheManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.io.File;

import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.OutSideListener;
import laoyou.com.laoyou.view.OutSideWebChromeClient;
import laoyou.com.laoyou.view.OutSideWebViewClient;
import okhttp3.Request;


/**
 * Created by lian on 2017/11/1.
 */
public class OutSidePresenter implements HttpResultListener {
    private OutSideListener listener;

    public OutSidePresenter(OutSideListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSucceed(String response, int tag) {

    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public void onFailed(String response, int code, int tag) {

    }

    public void Presetner( WebView webView, String url) {
//        if(Build.VERSION.SDK_INT<18)
            webView.loadUrl(url);
        /*else
        webView.evaluateJavascript(url, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {

            }
        });*/

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new OutSideWebViewClient());
        webView.setWebChromeClient(new OutSideWebChromeClient(listener));
        webinit(webView);

    }

    private void webinit(WebView webView) {
//        webView.setInitialScale(50);//这里一定要设置，数值可以根据各人的需求而定，我这里设置的是50%的缩放

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
            webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
//            webView.pauseTimers();
            webView.removeAllViews();
            webView.destroy();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }
}