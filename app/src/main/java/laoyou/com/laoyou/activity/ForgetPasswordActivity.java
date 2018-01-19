package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.ForgetPassListener;
import laoyou.com.laoyou.presenter.ForgetPassPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.getHidePhone;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/27.
 */
public class ForgetPasswordActivity extends InitActivity implements View.OnClickListener, ForgetPassListener {

    private static final String TAG = "ForgetPasswordActivity";
    private String phone;
    private TextView phone_tv;

    private EditText pass_ed, codes_ed;
    private ImageView commit_img;
    private ForgetPassPresenter fp;

    @Override
    protected void click() {
        commit_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.forget_passwrod_layout);
        setTitles(ForgetPasswordActivity.this);
        phone = getIntent().getStringExtra("forget_phone");
        phone_tv = f(R.id.phone_tv);
        codes_ed = f(R.id.codes_ed);
        pass_ed = f(R.id.pass_ed);
        commit_img = f(R.id.commit_img);
        fp = new ForgetPassPresenter(this);

        phone_tv.setText("验证码已发送至" + getHidePhone(phone) + "请填写验证码输入新\n的密码代替原来的密码");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_img:
                fp.ForgetPassSend(pass_ed.getText().toString(), codes_ed.getText().toString(), phone);
                break;
        }
    }

    @Override
    public void onErrorMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(this, gets(R.string.changepassok));
        setResult(Fields.ACRESULET2);
        /*if (getHomeInstance() != null)
            getHomeInstance().onRefresh();*/
        finish();
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }
}
