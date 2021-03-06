package laoyou.com.laoyou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.LoginOperationListener;
import laoyou.com.laoyou.presenter.LoginOperationPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.activity.MainActivity.MainInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.fragment.MyFragment.SettingInstance;
import static laoyou.com.laoyou.utils.IntentUtils.goRegisterPage;
import static laoyou.com.laoyou.utils.IntentUtils.goSendPhoneCodePage;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/10/25.
 */
public class LoginOperationActivity extends InitActivity implements View.OnClickListener, LoginOperationListener {

    private static final String TAG = "LoginOperationActivity";

    private LoginOperationPresenter lp;
    private ImageView wechat_img;
    private LinearLayout login_layout;
    private TextView register_layout;
    private TextView forget_pass;
    //  private UMShareAPI mShareAPI;
    private RippleView login_bt;
    private EditText pass_ed, phone_ed;


    @Override
    protected void click() {
        wechat_img.setOnClickListener(this);
        forget_pass.setOnClickListener(this);
        register_layout.setOnClickListener(this);
        login_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.login_operation_layout);
        wechat_img = f(R.id.wechat_img);
        phone_ed = f(R.id.phone_ed);
        pass_ed = f(R.id.pass_ed);
        login_layout = f(R.id.login_layout);
        register_layout = f(R.id.register_layout);
        login_bt = f(R.id.login_bt);
        forget_pass = f(R.id.forget_pass);
//        mShareAPI = UMShareAPI.get(this);
        lp = new LoginOperationPresenter(this);
        SmspermissionCheck();
    }

    /**
     * 检查短信发送权限;
     */
    private void SmspermissionCheck() {
        if (ContextCompat.checkSelfPermission(LoginOperationActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginOperationActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }


    @Override
    protected void initData() {
        lp.Presenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_img:
//               Show(LoginOperationActivity.this, "登录中", true, null);
//               lp.getWeChatInfo(LoginOperationActivity.this, mShareAPI);
                break;
            case R.id.login_layout:
//                goLoginPage(this);
                break;
            case R.id.register_layout:
                goRegisterPage(this);
                break;
            case R.id.forget_pass:
                goSendPhoneCodePage(this);
                break;
            case R.id.login_bt:
                Show(LoginOperationActivity.this, "登录中", true, null);
                lp.Login(phone_ed.getText().toString(), pass_ed.getText().toString());
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
                getHomeInstance().onRefreshs();
            finish();
        }
        if (resultCode == Fields.ACRESULET4 && requestCode == Fields.ACRESULET4)
            ToastUtil.toast2_bottom(LoginOperationActivity.this, gets(R.string.change_pass_succe));

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
        ToastUtil.toast2_bottom(this, response);
        Cancle();
    }

    @Override
    public void onSucceed() {
        Cancle();
        ToastUtil.toast2_bottom(this, gets(R.string.loginok));
        if (MainInstance() != null)
            MainInstance().IMInit();
        if (SettingInstance() != null)
            SettingInstance().mp.getUseDetails();
        if (getHomeInstance() != null)
            getHomeInstance().onRefreshs();
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
        lp.clear();
        lp = null;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写操作 如send（）； send函数中New SendMsg （号码，内容）；
                    Log.i(TAG,"已获得短信接收权限");
                } else
                    Log.i(TAG,"没有短信接收权限");

                break;

            default:
        }
    }

}
