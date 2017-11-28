package laoyou.com.laoyou.activity;

import android.view.KeyEvent;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.ToastUtil;


/**
 * Created by lian on 2017/9/11.
 */
public class BaseActivity extends InitActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.loading_layout);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 判断退出
            if (timeDValue == 0) {
                ToastUtil.toast2_bottom(this, getResources().getString(R.string.appfinish), 1000);
                timeDValue = System.currentTimeMillis();
                return true;
            } else {
                timeDValue = System.currentTimeMillis() - timeDValue;
                if (timeDValue >= 1500) { // 大于1.5秒不处理。
                    timeDValue = 0;
                    return true;
                } else {// 退出应用
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
