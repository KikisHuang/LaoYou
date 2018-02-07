package laoyou.com.laoyou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by lian on 2018/2/7.
 */
public class NoScrollWebView extends WebView {

    public NoScrollWebView(Context context) {
        super(context);
    }

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

}
