package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.RegisterListener;
import laoyou.com.laoyou.presenter.RegisterPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goOverInfoPage;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/26.
 */
public class RegisterActivity extends InitActivity implements View.OnClickListener, RegisterListener {

    private static final String TAG = "RegisterActivity";

    private EditText code_ed, pass_ed, phone_ed;
    private RippleView code_bt;
    private ImageView commit_img;
    private RegisterPresenter rp;
    private boolean codeOk = false;

    @Override
    protected void click() {
        code_bt.setOnClickListener(this);
        commit_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.register_layout);
        setTitles(this);
        rp = new RegisterPresenter(this);
        commit_img = f(R.id.commit_img);
        code_bt = f(R.id.code_bt);
        code_ed = f(R.id.code_ed);
        pass_ed = f(R.id.pass_ed);
        phone_ed = f(R.id.phone_ed);

    }

    @Override
    protected void initData() {
        rp.Presenter();
        rp.EdittextInit(pass_ed);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.code_bt:
                Show(RegisterActivity.this, "发送验证码中", true, null);
                rp.CheckAccount(phone_ed.getText().toString());

                break;
            case R.id.commit_img:
                Show(RegisterActivity.this, "", true, null);
                rp.CommitData(phone_ed.getText().toString(), pass_ed.getText().toString(), code_ed.getText().toString(), codeOk);
                break;
        }
    }


    @Override
    public void onCountDown(int page) {
        code_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_corner2));
        code_bt.setText("重发(" + page + "s" + ")");
        code_bt.setTextColor(getRouColors(R.color.white));

    }

    @Override
    public void onOver() {
        code_bt.setEnabled(true);
        code_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_bt_normal));
        code_bt.setText("获取验证码");
        code_bt.setTextColor(getRouColors(R.color.white));
    }

    @Override
    public void onForbid() {

        codeOk = true;
        code_bt.setEnabled(false);
    }

    @Override
    public void onPhoneLengthError() {
        Cancle();
        ToastUtil.toast2_bottom(RegisterActivity.this, "请输入正确的手机号!");
    }

    @Override
    public void onRegisterFailedMsg(String msg) {
        Cancle();
        ToastUtil.toast2_bottom(RegisterActivity.this, msg);
    }

    @Override
    public void OverInfo() {
        Cancle();
        goOverInfoPage(RegisterActivity.this, phone_ed.getText().toString(), pass_ed.getText().toString(), code_ed.getText().toString(), 1);
    }

    @Override
    public void onFailed(String msg) {
        Cancle();
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void SendCode() {
        rp.CodeCountDown(phone_ed.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rp.HandlerClear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET1) {
            setResult(Fields.ACRESULET2);
            finish();
        }
        if (resultCode == Fields.ACRESULET3)
            finish();

    }
}
