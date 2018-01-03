package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
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
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomePageAdapter;
import laoyou.com.laoyou.bean.AttentionGameBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.presenter.HomePagePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.tencent.ui.AddFriendActivity;
import laoyou.com.laoyou.tencent.ui.ProfileActivity;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RoundAngleImageView;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goLikeGamePage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyPhotoPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOthersDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.IsMe;
import static laoyou.com.laoyou.utils.SynUtils.IsNull;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.handleTitleBarColorEvaluate;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/7.
 */
public class HomePageActivity extends InitActivity implements HomePageListener, RecyclerViewOnItemClickListener, AbsListView.OnScrollListener, View.OnClickListener {
    private ListView listView;
    private LinearLayout head_layout, foot_layout;
    private HomePageAdapter adapter;
    private List<TopicTypeBean> list;
    private ImageView background_img, back_img;
    private CircleImageView head_img;
    private RelativeLayout title_layout;
    private LinearLayout game_list_layout, photo_layout;
    private TextView nickname_tv, signature_tv, page_view_tv, cardiac_value_tv, detailsOfcompile_tv, address_tv, authentication_tv;
    private FrameLayout add_layout, chat_layout, heart_layout;
    private HomePagePresenter hp;
    private int imageHeight;
    private int height;
    private String id;
    private boolean isMe;
    private LinearLayout like_game_layout;
    private TextView foot_tv;
    private boolean IsRefresh;
    private String identify = "";
    private String HeadImgUrl = "";

    @Override
    protected void click() {
        listView.setOnScrollListener(this);
        detailsOfcompile_tv.setOnClickListener(this);
        game_list_layout.setOnClickListener(this);
        like_game_layout.setOnClickListener(this);
        heart_layout.setOnClickListener(this);
        chat_layout.setOnClickListener(this);
        add_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.home_page_layout);
        setImgTitles(this);
        id = getIntent().getStringExtra("Page_Home_id");
        isMe = IsMe(id);
        hp = new HomePagePresenter(this);
        if (isMe)
            hp.getMyDetails();
        else
            hp.getOthersDetails(id, false);

        listView = f(R.id.listView);
        title_layout = f(R.id.title_layout);
        add_layout = f(R.id.add_layout);
        chat_layout = f(R.id.chat_layout);
        heart_layout = f(R.id.heart_layout);
        back_img = f(R.id.back_img);

        list = new ArrayList<>();

        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.home_page_head_layout, null);
        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.foot_include, null);

        background_img = (ImageView) head_layout.findViewById(R.id.background_img);
        head_img = (CircleImageView) head_layout.findViewById(R.id.head_img);
        nickname_tv = (TextView) head_layout.findViewById(R.id.nickname_tv);
        signature_tv = (TextView) head_layout.findViewById(R.id.signature_tv);
        page_view_tv = (TextView) head_layout.findViewById(R.id.page_view_tv);
        cardiac_value_tv = (TextView) head_layout.findViewById(R.id.cardiac_value_tv);
        authentication_tv = (TextView) head_layout.findViewById(R.id.authentication_tv);
        detailsOfcompile_tv = (TextView) head_layout.findViewById(R.id.detailsOfcompile_tv);
        like_game_layout = (LinearLayout) head_layout.findViewById(R.id.like_game_layout);

        detailsOfcompile_tv.setText(isMe ? gets(R.string.compile_info) : gets(R.string.view_details));

        address_tv = (TextView) head_layout.findViewById(R.id.address_tv);
        photo_layout = (LinearLayout) head_layout.findViewById(R.id.photo_layout);
        game_list_layout = (LinearLayout) head_layout.findViewById(R.id.game_list_layout);

        foot_tv = (TextView) foot_layout.findViewById(R.id.foot_tv);
        foot_tv.setVisibility(View.GONE);

        adapter = new HomePageAdapter(this, list, this);
        listView.addHeaderView(head_layout);
        listView.addFooterView(foot_layout);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMe)
            hp.getMyDetails();
        else
            hp.getOthersDetails(id, false);
    }

    @Override
    protected void initData() {

    }

    /**
     * 添加照片墙;
     *
     * @param ll
     */
    private void addPhotoList(List<PhotoBean> ll) {
        final List<String> photo = new ArrayList<>();
        for (PhotoBean pb : ll) {
            photo.add(pb.getUrl());
        }
        photo_layout.removeAllViews();
        for (int i = 0; i < ll.size(); i++) {
            int w = DeviceUtils.getWindowWidth(this) * 1 / 4;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            lp.rightMargin = DeviceUtils.dip2px(this, 3);
            RoundAngleImageView im = new RoundAngleImageView(this);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            im.setLayoutParams(lp);
            Glide.with(this).load(ll.get(i).getUrl()).into(im);
            photo_layout.addView(im);

            final int finalI = i;
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goMyPhotoPage(HomePageActivity.this, isMe, id);
//                    goPhotoViewerPage(HomePageActivity.this, photo, finalI, 1);
                }
            });
        }
    }

    @Override
    public void onFailedMSg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onShowUserInfo(UserInfoBean ub) {
        identify = ub.getCloudTencentAccount();
        HeadImgUrl = ub.getHeadImgUrl();
        Glide.with(HomePageActivity.this).load(ub.getHeadImgUrl()).into(head_img);
        nickname_tv.setText(ub.getName());
        Glide.with(this).load(IsNull(ub.getBackgroundUrl()) ? Fields.Catalina : ub.getBackgroundUrl()).into(background_img);
        signature_tv.setText(IsNull(ub.getAutograph()) ? gets(R.string.default_signature) : ub.getAutograph());
        page_view_tv.setText(IsNull(ub.getBrowseNumber()) ? String.valueOf(0) : ub.getBrowseNumber());
        address_tv.setText(IsNull(ub.getAddress()) ? "" : ub.getAutograph());
        authentication_tv.setText(IsNull(ub.getIdcard()) ? gets(R.string.un_certification) : gets(R.string.certification));

    }

    /**
     * 废弃
     *
     * @param state
     */
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
    public void onMyHeartValue(String num) {
        cardiac_value_tv.setText(IsNull(num) ? String.valueOf(0) : num);
    }

    /**
     * 关注的游戏;
     *
     * @param ll
     */
    @Override
    public void onAttentGames(List<AttentionGameBean> ll) {
        game_list_layout.removeAllViews();
        for (AttentionGameBean ab : ll) {
            int w = DeviceUtils.getWindowWidth(this) * 1 / 10;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            CircleImageView civ = new CircleImageView(this);
            civ.setLayoutParams(lp);
            lp.rightMargin = DeviceUtils.dip2px(this, 2);
            Glide.with(this).load(ab.getImgUrl()).into(civ);
            civ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isMe)
                        goLikeGamePage(HomePageActivity.this, "");
                    else
                        goLikeGamePage(HomePageActivity.this, id);
                }
            });
            game_list_layout.addView(civ);
        }
    }

    @Override
    public void onPhotoList(List<PhotoBean> photos) {
        addPhotoList(photos);
    }

    @Override
    public void onBottom() {
        if (list.size() > 0)
            foot_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStatusInfo(List<TopicTypeBean> nblist) {
        if (IsRefresh)
            list.clear();
        for (TopicTypeBean ttb : nblist) {
            list.add(ttb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        IsRefresh = true;
        if (isMe)
            hp.getPersonaldynamic(null, true);
        else
            hp.getPersonaldynamic(id, true);
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
        handleTitleBarColorEvaluate(height, imageHeight, title_layout, back_img);

        if (visibleItemCount + firstVisibleItem == totalItemCount) {
            if (foot_tv.getVisibility() == View.GONE) {
                IsRefresh = false;
                if (isMe)
                    hp.getPersonaldynamic(null, false);
                else
                    hp.getPersonaldynamic(id, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailsOfcompile_tv:
                if (isMe)
                    goMyHomePage(this);
                else
                    goOthersDetailsPage(this, id);
                break;
            case R.id.like_game_layout:
                if (isMe)
                    goLikeGamePage(this, "");
                else
                    goLikeGamePage(this, id);
                break;
            case R.id.heart_layout:
                if (isMe)
                    hp.getMyHeart();
                else
                    hp.GiveHeart(id);
                break;
            case R.id.add_layout:
                if (!isMe && !identify.isEmpty()) {
                    if (FriendshipInfo.getInstance().isFriend(identify)) {
                        ProfileActivity.navToProfile(this, identify);
                    } else {
                        Intent person = new Intent(this, AddFriendActivity.class);
                        person.putExtra("id", identify);
                        person.putExtra("name", nickname_tv.getText().toString());
                        person.putExtra("head_img", HeadImgUrl);
                        startActivity(person);
                    }
                }

                break;
            case R.id.chat_layout:
                if (!isMe && !identify.isEmpty() && FriendshipInfo.getInstance().isFriend(identify)) {
                    SPreferences.saveTemporaryImg(String.valueOf(HeadImgUrl));
                }
                break;
        }
    }

    @Override
    public void RcOnItemClick(int pos, List<String> imgs) {
        goPhotoViewerPage(this, imgs, pos, 1);
    }

    @Override
    public void LikeClick(String id) {
        Show(this, "", true, null);
        hp.LikeChatTheme(id);
    }

    @Override
    public void GoPageHome(String userId) {

    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {
        goTopicCommentDetailsPage(this, id, userId, name, content);
    }

    @Override
    public void GoVideoPage(String url, String videoCover) {
        goVideoPlayerPage(this, url, videoCover);
    }
}
