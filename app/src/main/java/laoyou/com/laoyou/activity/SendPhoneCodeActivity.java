package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.SendCodeListener;
import laoyou.com.laoyou.presenter.SendCodePresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goForGetPassPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/27.
 */
public class SendPhoneCodeActivity extends InitActivity implements View.OnClickListener, SendCodeListener {

    private static final String TAG = "SendPhoneCodeActivity";
    private ImageView commit_img;
    private EditText phone_ed;
    private SendCodePresenter sp;

    @Override
    protected void click() {
        commit_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.send_phone_layout);
        setTitles(SendPhoneCodeActivity.this);
        commit_img = f(R.id.commit_img);
        phone_ed = f(R.id.phone_ed);
        sp = new SendCodePresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_img:
                Show(SendPhoneCodeActivity.this, "", true, null);
                sp.SendCode(phone_ed.getText().toString());
//                goForGetPassPage(this, "13878141913");
                break;
        }
    }

    @Override
    public void onError() {

        ToastUtil.toast2_bottom(this, gets(R.string.networkerror));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET2) {
            setResult(Fields.ACRESULET4);
            finish();
        }
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(this, "已发送验证码..");
        goForGetPassPage(this, phone_ed.getText().toString());
    }

    @Override
    public void onFailed(String msg) {

        ToastUtil.toast2_bottom(this, msg);
    }
}
