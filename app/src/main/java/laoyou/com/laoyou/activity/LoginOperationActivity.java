package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umeng.socialize.UMShareAPI;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.presenter.LoginOperationPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.activity.MainActivity.MainInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.fragment.MyFragment.SettingInstance;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginPage;
import static laoyou.com.laoyou.utils.IntentUtils.goRegisterPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;

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

    private UMShareAPI mShareAPI;


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

        mShareAPI = UMShareAPI.get(this);

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
                Show(LoginOperationActivity.this, "登录中", true, null);
                lp.getWeChatInfo(LoginOperationActivity.this, mShareAPI);
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
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (resultCode == Fields.ACRESULET2) {
            if (MainInstance() != null)
                MainInstance().IMInit();
            if (getHomeInstance() != null)
                getHomeInstance().onRefresh();
            finish();
        }
    }

    @Override
    public void onWechatLoginSucceed(String accessToken, String openid, String url) {
        lp.WeChatLogin(accessToken, openid, url);
    }

    @Override
    public void onWechatLoginFailed() {
        ToastUtil.toast2_bottom(LoginOperationActivity.this, gets(R.string.wechatloginerror));
        Cancle();
    }

    @Override
    public void onFailed(String response) {
        Cancle();
    }

    @Override
    public void onSucceed() {
        Cancle();
        ToastUtil.toast2_bottom(this, gets(R.string.loginok));
        if (MainInstance() != null)
            MainInstance().IMInit();
        if(SettingInstance()!=null)
            SettingInstance().mp.getUseDetails();
        if (getHomeInstance() != null)
            getHomeInstance().onRefresh();
        finish();
    }

    @Override
    public void onImFailed(String msg) {
        ToastUtil.toast2_bottom(LoginOperationActivity.this, msg);
        SPreferences.saveUserToken("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }
}
