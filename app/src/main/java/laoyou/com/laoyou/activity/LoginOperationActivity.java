package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.presenter.LoginOperationPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginPage;
import static laoyou.com.laoyou.utils.IntentUtils.goRegisterPage;

/**
 * Created by lian on 2017/10/25.
 */
public class LoginOperationActivity extends InitActivity implements View.OnClickListener, LoginOperationListener {

    private static final String TAG = "LoginOperationActivity";

    private LoginOperationPresenter lp;
    private ImageView wechat_img;
    private LinearLayout login_layout;
    private LinearLayout register_layout;
    private static LoginOperationActivity instance;

    @Override
    protected void click() {
        wechat_img.setOnClickListener(this);
        login_layout.setOnClickListener(this);
        register_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.login_operation_layout);
        instance = this;
        wechat_img = f(R.id.wechat_img);
        login_layout = f(R.id.login_layout);
        register_layout = f(R.id.register_layout);
        lp = new LoginOperationPresenter(this);
    }

    @Override
    protected void initData() {
        lp.Presenter();
    }

    /**
     * 获取实力方法;
     *
     * @return
     */
    public static synchronized LoginOperationActivity getInstance() {
        if (instance == null)
            instance = new LoginOperationActivity();

        return instance;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_img:
                ToastUtil.toast2_bottom(this, "功能开发中，敬请期待...");
                break;
            case R.id.login_layout:
                goLoginPage(this);
                break;
            case R.id.register_layout:
                goRegisterPage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET2) {
            if (getHomeInstance() != null)
                getHomeInstance().onRefresh();
            finish();
        }
    }
}
