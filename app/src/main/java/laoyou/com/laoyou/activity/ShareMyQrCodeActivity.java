package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.Log;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.ShareMyQrCodeListener;
import laoyou.com.laoyou.presenter.ShareMyQrCodePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2018/2/9.
 */
public class ShareMyQrCodeActivity extends InitActivity implements View.OnClickListener, UMShareListener, ShareMyQrCodeListener {

    private static final java.lang.String TAG = "ShareMyQrCodeActivity";
    private ImageView close_img;
    private ImageView user_head_img;
    private ImageView qr_code_img;
    private String head_icon;
    private TextView name_tv;
    private LinearLayout qq_share_layout;
    private LinearLayout wx_share_layout;
    private LinearLayout circle_share_layout;
    private ShareMyQrCodePresenter sp;
    private String shareurl = "";

    @Override
    protected void click() {
        close_img.setOnClickListener(this);
        qq_share_layout.setOnClickListener(this);
        wx_share_layout.setOnClickListener(this);
        circle_share_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.share_my_qrcode_layout);
        close_img = f(R.id.close_img);
        user_head_img = f(R.id.user_head_img);
        qr_code_img = f(R.id.qr_code_img);
        name_tv = f(R.id.name_tv);
        qq_share_layout = f(R.id.qq_share_layout);
        wx_share_layout = f(R.id.wx_share_layout);
        circle_share_layout = f(R.id.circle_share_layout);
        sp = new ShareMyQrCodePresenter(this);

    }

    @Override
    protected void initData() {
        head_icon = getIntent().getStringExtra("my_head_id");
        Glide.with(this).load(head_icon).into(user_head_img);
        name_tv.setText(SPreferences.getMyNickName() != null && !SPreferences.getMyNickName().isEmpty() ? SPreferences.getMyNickName() : "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                finish();
                break;
            case R.id.qq_share_layout:
                UmShare(0);
                break;
            case R.id.wx_share_layout:
                UmShare(1);
                break;
            case R.id.circle_share_layout:
                UmShare(2);
                break;
        }
    }

    @Override
    public void ShowMyQrCode(String codeurl) {
        Glide.with(this).load(codeurl).into(qr_code_img);
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onShareQrCodeUrl(String str) {
        shareurl = str;
    }

    public void UmShare(int type) {
        if (!shareurl.isEmpty()) {


            UMWeb web = new UMWeb(shareurl);
            web.setTitle(gets(R.string.business_card));//标题
            web.setThumb(new UMImage(this, R.mipmap.logo_icon));  //缩略图
            web.setDescription(SPreferences.getMyNickName() != null && !SPreferences.getMyNickName().isEmpty() ? SPreferences.getMyNickName() + " " + gets(R.string.business_card) : gets(R.string.business_card));//描述
            if (type == 0) {
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(web)
                        .setCallback(this)//回调监听器
                        .share();
            } else if (type == 1) {
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(web)
                        .setCallback(this)//回调监听器
                        .share();
            } else {
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(web)
                        .setCallback(this)//回调监听器
                        .share();
            }
        } else
            ToastUtil.toast2_bottom(this, "没有获取到分享链接");
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.toast2_bottom(this, gets(R.string.share_succ));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.toast2_bottom(this, gets(R.string.share_fail));
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.i(TAG, "取消分享");
    }
}
