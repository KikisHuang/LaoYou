package laoyou.com.laoyou.view;


import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import laoyou.com.laoyou.listener.OutSideListener;

/**
 * Created by lian on 2017/11/2.
 */
public class OutSideWebChromeClient extends WebChromeClient {
    private OutSideListener listener;

    public OutSideWebChromeClient(OutSideListener listener) {
        this.listener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100)
            listener.onCancleProgress();
        else
            listener.onShowProgress(newProgress);

    }
}
