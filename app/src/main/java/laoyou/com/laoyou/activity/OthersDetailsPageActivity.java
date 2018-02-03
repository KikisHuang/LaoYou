package laoyou.com.laoyou.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.OthersListener;
import laoyou.com.laoyou.presenter.OthersPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.ObservableScrollView;

import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goLikeGamePage;
import static laoyou.com.laoyou.utils.SynUtils.IsNull;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/26.
 */
public class OthersDetailsPageActivity extends InitActivity implements OthersListener, View.OnClickListener, ObservableScrollView.ScrollViewListener {
    private static final String TAG = "OthersDetailsPageActivity";
    private LinearLayout private_layout;
    private ImageView background_img, back_img;
    private CircleImageView head_img;
    private TextView sex_tv, region_tv, attestation_state_tv, like_game_tv;
    private EditText nickname_ed, signature_ed;
    private ObservableScrollView scrollView;
    private String id;
    private OthersPresenter op;
    private String TencentId;
    private RelativeLayout title_layout;
    private int imageHeight;

    @Override
    protected void click() {
        like_game_tv.setOnClickListener(this);
        scrollView.setScrollViewListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.other_details_page_layout);
        ActivityCollector.addActivity(this, getClass());
        setImgTitles(this);
        private_layout = f(R.id.private_layout);
        background_img = f(R.id.background_img);
        head_img = f(R.id.head_img);
        scrollView = f(R.id.scrollView);
        back_img = f(R.id.back_img);
        title_layout = f(R.id.title_layout);

        sex_tv = f(R.id.sex_tv);
        region_tv = f(R.id.region_tv);
        attestation_state_tv = f(R.id.attestation_state_tv);
        nickname_ed = f(R.id.nickname_ed);
        like_game_tv = f(R.id.like_game_tv);
        signature_ed = f(R.id.signature_ed);
        id = getIntent().getStringExtra("Others_id");
        op = new OthersPresenter(this);
        imageHeight = DeviceUtils.dip2px(this, 360);
    }

    @Override
    protected void initData() {
        op.getOthersDataList(id, false);

    }

    @Override
    public void onFailedMsg(String msg) {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onShowUserInfo(UserInfoBean ub) {
        TencentId = ub.getCloudTencentAccount();
        op.FindsWhether(TencentId);

        Glide.with(MyApplication.getContext()).load(ub.getHeadImgUrl()).apply(getGlideOptions()).into(head_img);
        nickname_ed.setText(ub.getName());
        sex_tv.setText(ub.getSex() == 1 ? gets(R.string.man) : gets(R.string.woman));

        Glide.with(MyApplication.getContext()).load(IsNull(ub.getBackgroundUrl()) ? Fields.Catalina : ub.getBackgroundUrl()).apply(getGlideOptions()).into(background_img);
        signature_ed.setText(IsNull(ub.getAutograph()) ? gets(R.string.default_signature) : ub.getAutograph());
        region_tv.setText(IsNull(ub.getAddress()) ? "" : ub.getAddress());
        if (!IsNull(ub.getIdcard())) {
            attestation_state_tv.setTextColor(getRouColors(R.color.text_blue));
            attestation_state_tv.setText(gets(R.string.certification));

        } else
            attestation_state_tv.setText(gets(R.string.un_certification));
    }


    @Override
    public void onIsFinds(boolean isfind) {
        private_layout.setVisibility(isfind ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_game_tv:
                goLikeGamePage(this, id);
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (y <= 0) {
            title_layout.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
            back_img.setImageResource(R.mipmap.return_icon_white);
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            title_layout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            back_img.setImageResource(R.mipmap.return_icon);
        } else
            title_layout.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
