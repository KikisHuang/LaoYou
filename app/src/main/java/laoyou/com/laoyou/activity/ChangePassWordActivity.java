package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.ChangePassListener;
import laoyou.com.laoyou.presenter.ChangePassPresenter;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/11/1.
 */
public class ChangePassWordActivity extends InitActivity implements View.OnClickListener, ChangePassListener {
    private static final String TAG = "ChangePassWordActivity";
    private EditText old_pass_ed, new_pass_ed;
    private ChangePassPresenter cp;
    private ImageView commit_img;

    @Override
    protected void click() {
        commit_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.change_pass_word_layout);
        setTitles(this);
        new_pass_ed = f(R.id.new_pass_ed);
        old_pass_ed = f(R.id.old_pass_ed);
        commit_img = f(R.id.commit_img);
        cp = new ChangePassPresenter(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_img:
                Show(ChangePassWordActivity.this, "提交中", true, null);
                cp.ChangePassWord(old_pass_ed.getText().toString(), new_pass_ed.getText().toString());
                break;
        }
    }

    @Override
    public void VerifyFailed(String msg) {
        ToastUtil.toast2_bottom(ChangePassWordActivity.this, msg);
    }

    @Override
    public void onChangeSucceed() {
        ToastUtil.toast2_bottom(ChangePassWordActivity.this, "修改密码成功，请重新登录！");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onErrorMsg(String msg) {
        ToastUtil.toast2_bottom(ChangePassWordActivity.this, msg);
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(ChangePassWordActivity.this, msg);
    }
}
