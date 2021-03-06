package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.listener.CertificationListener;
import laoyou.com.laoyou.presenter.CertificationPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.DeviceUtils.getIMEI;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IPUtils.isWifi;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhotoTag;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitles;

/**
 * Created by lian on 2017/10/30.
 */
public class CertificationActivity extends InitActivity implements View.OnClickListener, CertificationListener {

    private static final String TAG = "CertificationActivity";
    private RippleView commit_bt;
    private EditText name_ed, id_ed;

    private FrameLayout front_id_layout, Tail_id_layout, hand_id_layout;

    private ImageView front_img, tail_img, hand_img, example_front_img, example_tail_img, example_hand_img;
    private CertificationPresenter cp;

    private File FrontFile = null;
    private File TailFile = null;
    private File HandFile = null;
    private String IP = "";

    @Override
    protected void click() {
        front_id_layout.setOnClickListener(this);
        Tail_id_layout.setOnClickListener(this);
        hand_id_layout.setOnClickListener(this);
        commit_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.certification_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitles(CertificationActivity.this);
        cp = new CertificationPresenter(this);
        front_id_layout = f(R.id.front_id_layout);
        Tail_id_layout = f(R.id.Tail_id_layout);
        hand_id_layout = f(R.id.hand_id_layout);

        example_front_img = f(R.id.example_front_img);
        example_tail_img = f(R.id.example_tail_img);
        example_hand_img = f(R.id.example_hand_img);

        front_img = f(R.id.front_img);
        tail_img = f(R.id.tail_img);
        hand_img = f(R.id.hand_img);

        name_ed = f(R.id.name_ed);
        id_ed = f(R.id.id_ed);

        commit_bt = f(R.id.commit_bt);
        if (isWifi(CertificationActivity.this))
            cp.getAddressIP();
    }

    @Override
    protected void initData() {
        ImgInit();
    }


    private void ImgInit() {
        int w = DeviceUtils.getWindowWidth(this) / 2;

        FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) getLayout(1,ViewGroup.LayoutParams.MATCH_PARENT, (int) (w / 1.6));
        front_img.setLayoutParams(fl);
        tail_img.setLayoutParams(fl);
        hand_img.setLayoutParams(fl);
        example_front_img.setLayoutParams(fl);
        example_tail_img.setLayoutParams(fl);
        example_hand_img.setLayoutParams(fl);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_bt:
                Show(CertificationActivity.this, "提交中", true, null);
                cp.CommitCheck(name_ed.getText().toString(), id_ed.getText().toString(), FrontFile, TailFile, HandFile, IP, getIMEI());

                break;
            case R.id.front_id_layout:
                getMULTIPLEPhotoTag(this, Fields.ACRESULET1);
                break;
            case R.id.Tail_id_layout:
                getMULTIPLEPhotoTag(this, Fields.ACRESULET2);
                break;
            case R.id.hand_id_layout:
                getMULTIPLEPhotoTag(this, Fields.ACRESULET3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            switch (requestCode) {
                case Fields.ACRESULET1:
                    // 图片选择结果回调
                    FrontFile = new File(list.get(0).getCompressPath() != null || !list.get(0).getCompressPath().isEmpty() ? list.get(0).getCompressPath() : list.get(0).getPath());
                    Glide.with(MyApplication.getContext()).load(FrontFile).apply(getGlideOptions()).into(front_img);
                    break;
                case Fields.ACRESULET2:
                    TailFile = new File(list.get(0).getCompressPath() != null || !list.get(0).getCompressPath().isEmpty() ? list.get(0).getCompressPath() : list.get(0).getPath());
                    Glide.with(MyApplication.getContext()).load(TailFile).apply(getGlideOptions()).into(tail_img);
                    break;
                case Fields.ACRESULET3:
                    HandFile = new File(list.get(0).getCompressPath() != null || !list.get(0).getCompressPath().isEmpty() ? list.get(0).getCompressPath() : list.get(0).getPath());
                    Glide.with(MyApplication.getContext()).load(HandFile).apply(getGlideOptions()).into(hand_img);
                    break;
            }
        }
    }
/*
    */

    /**
     * 压缩
     *//*
    private void Compress(List<String> list, final int result) {
        Luban.with(this)
                .load(list)                                   // 传人要压缩的图片列表
                .ignoreBy(300)                               // 忽略不压缩图片的大小
//                .setTargetDir(FileManager.getSaveFilePath() + "gxLuban")// 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Log.i(TAG, "onSuccess" + file.getAbsolutePath());
                        switch (result) {
                            case Fields.ACRESULET1:
                                FrontFile = file;
                                Glide.with(MyApplication.getContext()).load(file).apply(getGlideOptions()).into(front_img);
                                break;
                            case Fields.ACRESULET2:
                                TailFile = file;
                                Glide.with(MyApplication.getContext()).load(file).apply(getGlideOptions()).into(tail_img);
                                break;
                            case Fields.ACRESULET3:
                                HandFile = file;
                                Glide.with(MyApplication.getContext()).load(file).apply(getGlideOptions()).into(hand_img);
                                break;
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.toast2_bottom(CertificationActivity.this, "照片获取异常！！！");
                    }
                }).launch();    //启动压缩
    }*/
    @Override
    public void onCheckFailed(String ms) {
        ToastUtil.toast2_bottom(CertificationActivity.this, ms);
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(CertificationActivity.this, gets(R.string.iduploadsucceed));
        setResult(Fields.ACRESULET3);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(CertificationActivity.this, msg);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.toast2_bottom(CertificationActivity.this, msg);
    }

    @Override
    public void setIP(String ip) {
        IP = ip;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
//        MyApplication.getRefWatcher().watch(this);
    }
}
