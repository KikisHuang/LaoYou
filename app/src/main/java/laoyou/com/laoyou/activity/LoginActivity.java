package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.listener.LoginListener;
import laoyou.com.laoyou.presenter.LoginPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.IntentUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goSendPhoneCodePage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/26.
 * 登录注册界面;
 */
public class LoginActivity extends InitActivity implements View.OnClickListener, LoginListener {
    private static final String TAG = "LoginActivity";
    private ImageView commit_img;
    private EditText pass_ed, phone_ed;
    private boolean ClickFlag = false;
    private LoginPresenter lp;
    private TextView forget_pass;
    private TextView register_tv;

    @Override
    protected void click() {
        commit_img.setOnClickListener(this);
        forget_pass.setOnClickListener(this);
        register_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.login_layout);
        setTitles(this);
        commit_img = f(R.id.commit_img);
        phone_ed = f(R.id.phone_ed);
        pass_ed = f(R.id.pass_ed);
        forget_pass = f(R.id.forget_pass);
        register_tv = f(R.id.register_tv);
        lp = new LoginPresenter(this, pass_ed);
        findViewById(R.id.register_tv).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        lp.Presenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_img:
                if (ClickFlag) {
                    Show(LoginActivity.this, "登录中", true, null);
                    lp.Login(phone_ed.getText().toString(), pass_ed.getText().toString());
                } else
                    pass_ed.setText("");
                break;
            case R.id.forget_pass:
                goSendPhoneCodePage(this);
                setResult(123456);
                finish();
                break;
            case R.id.register_tv:
                IntentUtils.goRegisterPage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET2) {
            setResult(Fields.ACRESULET2);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }

    @Override
    public void onClear() {
        ClickFlag = false;
        Glide.with(MyApplication.getContext()).load(R.mipmap.close_icon).into(commit_img);
    }

    @Override
    public void onCommit() {
        ClickFlag = true;
        Glide.with(MyApplication.getContext()).load(R.mipmap.right_icon_green).into(commit_img);
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(LoginActivity.this, gets(R.string.loginok));
        setResult(Fields.ACRESULET2);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(LoginActivity.this, msg);
        SPreferences.saveUserToken("");
        Cancle();
    }

    @Override
    public void onError(String msg) {
        ToastUtil.toast2_bottom(LoginActivity.this, msg);
        Cancle();
    }

    @Override
    public void onImFailed(String msg) {
        SPreferences.saveUserToken("");
        Cancle();
    }
}
