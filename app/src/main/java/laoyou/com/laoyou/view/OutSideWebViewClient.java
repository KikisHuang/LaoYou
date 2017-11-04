package laoyou.com.laoyou.view;

import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by lian on 2017/7/11.
 */
public class OutSideWebViewClient extends WebViewClient {
    private static final String TAG = "OutSideWebViewClient";

    public OutSideWebViewClient() {
    }


    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.toString());
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.e(TAG, "Cookies = " + CookieStr);
//        Cancle();
        super.onPageFinished(view, url);
    }

}
