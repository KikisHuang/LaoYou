package laoyou.com.laoyou.activity;

import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomePageAdapter;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.presenter.HomePagePresenter;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.handleTitleBarColorEvaluate;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/7.
 */
public class HomePageActivity extends InitActivity implements HomePageListener, AbsListView.OnScrollListener {
    private ListView listView;
    private LinearLayout head_layout, foot_layout;
    private HomePageAdapter adapter;
    private List<String> list;
    private ImageView background_img, back_img;
    private CircleImageView head_img;
    private RelativeLayout title_layout;
    private LinearLayout game_list_layout, photo_layout;
    private TextView nickname_tv, signature_tv, page_view_tv, cardiac_value_tv, detailsOfcompile_tv, address_tv, authentication_tv;
    private FrameLayout add_layout, chat_layout, heart_layout;
    private HomePagePresenter hp;
    private int imageHeight;
    private int height;

    @Override
    protected void click() {
        listView.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.home_page_layout);
        setImgTitles(this);
        listView = f(R.id.listView);
        title_layout = f(R.id.title_layout);
        add_layout = f(R.id.add_layout);
        chat_layout = f(R.id.chat_layout);
        heart_layout = f(R.id.heart_layout);
        back_img = f(R.id.back_img);

        hp = new HomePagePresenter(this);
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.home_page_head_layout, null);
        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.foot_include, null);

        background_img = (ImageView) head_layout.findViewById(R.id.background_img);
        head_img = (CircleImageView) head_layout.findViewById(R.id.head_img);
        nickname_tv = (TextView) head_layout.findViewById(R.id.nickname_tv);
        signature_tv = (TextView) head_layout.findViewById(R.id.signature_tv);
        cardiac_value_tv = (TextView) head_layout.findViewById(R.id.cardiac_value_tv);
        detailsOfcompile_tv = (TextView) head_layout.findViewById(R.id.detailsOfcompile_tv);
        address_tv = (TextView) head_layout.findViewById(R.id.address_tv);
        photo_layout = (LinearLayout) head_layout.findViewById(R.id.photo_layout);
        game_list_layout = (LinearLayout) head_layout.findViewById(R.id.game_list_layout);

        adapter = new HomePageAdapter(this, list);
        listView.addHeaderView(head_layout);
        listView.addFooterView(foot_layout);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        Glide.with(this).load(Fields.Catalina).centerCrop().into(background_img);
        addPhotoList();
    }

    private void addPhotoList() {
//        photo_layout
        for (int i = 0; i < 4; i++) {
            int w = DeviceUtils.getWindowWidth(this) * 1 / 4;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            lp.rightMargin = DeviceUtils.dip2px(this, 3);
            ImageView im = new ImageView(this);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            im.setLayoutParams(lp);
            Glide.with(this).load(Fields.Catalina).bitmapTransform(new RoundedCornersTransformation(this, 10, 0, RoundedCornersTransformation.CornerType.ALL)).into(im);
            photo_layout.addView(im);
        }
    }

    @Override
    public void onFailedMSg(String msg) {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onShowUserInfo(UserInfoBean ub) {
        Glide.with(HomePageActivity.this).load(ub.getHeadImgUrl()).into(head_img);
        nickname_tv.setText(ub.getName());
    }

    @Override
    public void onCertificaTion(int state) {
        //	status  审核状态 0未审核，1已通过，-1已拒绝，2密码错误要求复检,3重新上传
        if (state == 1 || state == 2)
            authentication_tv.setText(gets(R.string.certification));
        else if (state == 0 || state == 3)
            authentication_tv.setText(gets(R.string.in_certification));
        else if (state == -1)
            authentication_tv.setText(gets(R.string.refuse_certification));
        else
            authentication_tv.setText(gets(R.string.un_certification));

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int location[] = new int[2];

        head_layout.getLocationInWindow(location);
        height = location[1];

        imageHeight = head_layout.getHeight() - DeviceUtils.dip2px(this, 50);
        handleTitleBarColorEvaluate(height,imageHeight,title_layout,back_img);
    }

}
