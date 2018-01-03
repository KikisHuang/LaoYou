package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.listener.OverInfoListener;
import laoyou.com.laoyou.presenter.OverInfoPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.thread.getImageCacheAsyncTask;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/28.
 */
public class OverInfoActivity extends InitActivity implements View.OnClickListener, OverInfoListener {

    private static final String TAG = "OverInfoActivity";
    private CircleImageView head_img;
    private EditText nickname_ed, sex_ed;
    private RippleView commit_bt;
    private File headFile = null;
    private OverInfoPresenter op;
    private boolean clickFlag = false;

    private String phone = "";
    private String pass = "";
    private String code = "";
    private int sex;

    @Override
    protected void click() {
        commit_bt.setOnClickListener(this);
        head_img.setOnClickListener(this);
        sex_ed.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.over_info_layout);
        setTitles(this);
        op = new OverInfoPresenter(this);
        commit_bt = f(R.id.commit_bt);
        nickname_ed = f(R.id.nickname_ed);
        head_img = f(R.id.head_img);
        sex_ed = f(R.id.sex_ed);

        phone = getIntent().getStringExtra("register_phone_of_headimg");
        pass = getIntent().getStringExtra("register_pass_of_name");
        code = getIntent().getStringExtra("register_code");
        sex = Integer.parseInt(getIntent().getStringExtra("register_sex"));
        op.Changejudge(phone, pass, code);

        if (sex == 1)
            sex_ed.setText(gets(R.string.man));
        else
            sex_ed.setText(gets(R.string.woman));
    }

    @Override
    protected void initData() {
        op.setListener(nickname_ed);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:

                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    ArrayList<String> p = new ArrayList<>();
                    for (LocalMedia lma : list) {
                        p.add(lma.getCompressPath() != null || !lma.getCompressPath().isEmpty() ? lma.getCompressPath() : lma.getPath());
                    }
//                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    op.CompressFile(this, p, 300);
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img:
                op.ChangeHeadImg(this, 1);
                break;
            case R.id.commit_bt:
                if (clickFlag) {
                    Show(OverInfoActivity.this, "请稍候", true, null);
                    op.CheckInfo(headFile, nickname_ed.getText().toString(), phone, pass, code, sex);
                }
                break;

            case R.id.sex_ed:
                new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.man), ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        sex_ed.setText(gets(R.string.man));
                        sex = 1;
                    }
                }).addSheetItem(gets(R.string.woman), ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        sex_ed.setText(gets(R.string.woman));
                        sex = 0;
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onSucced(int flag) {
        Cancle();
        if (flag == 1)
            op.getImIdentifier();

        if (flag == 2) {
            ToastUtil.toast2_bottom(OverInfoActivity.this, gets(R.string.changeinfook));
            setResult(Fields.ACRESULET1);
            finish();
        }

    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(OverInfoActivity.this, msg);
        Cancle();
    }

    @Override
    public void onClear() {
        clickFlag = false;
        commit_bt.setBackgroundResource(R.drawable.gray_bt_normal);
    }

    @Override
    public void onCommit() {
        clickFlag = true;
        commit_bt.setBackgroundResource(R.drawable.green_bt_normal2);
    }

    @Override
    public void onErrorMsg(String msg) {
        ToastUtil.toast2_bottom(OverInfoActivity.this, msg);
        Cancle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }

    @Override
    public void setHeadImgAndName(String imgPath, String name) {
        if (!imgPath.isEmpty() && !name.isEmpty()) {
            Glide.with(OverInfoActivity.this).load(imgPath).into(head_img);
            new getImageCacheAsyncTask(getApplicationContext(), this).execute(imgPath);

            nickname_ed.setText(name);
            nickname_ed.setSelection(name.length());
            onCommit();
        }
    }

    @Override
    public void onImSucceed() {
        ToastUtil.toast2_bottom(OverInfoActivity.this, gets(R.string.registersucceed));
        setResult(Fields.ACRESULET1);
        finish();
    }

    @Override
    public void onImFailed(String msg) {
        ToastUtil.toast2_bottom(OverInfoActivity.this, msg);
        SPreferences.saveUserToken("");
        setResult(Fields.ACRESULET3);
        finish();
    }

    @Override
    public void onFile(File f) {
        headFile = f;
    }

    @Override
    public void onCompressSucceed(File f) {
        headFile = f;
        Glide.with(OverInfoActivity.this).load(f).into(head_img);
    }
}
