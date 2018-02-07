package laoyou.com.laoyou.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.HomePageAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.AttentionGameBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.presenter.HomePagePresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.ui.ChatActivity;
import laoyou.com.laoyou.tencent.view.NotifyDialog;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.ParallaxScollListView;
import laoyou.com.laoyou.view.RoundAngleImageView;

import static laoyou.com.laoyou.activity.AddressbookActivity.AddressBookInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goAddFriendPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLikeGamePage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyPhotoPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOthersDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.IsListViewTopOfBottom;
import static laoyou.com.laoyou.utils.SynUtils.IsMe;
import static laoyou.com.laoyou.utils.SynUtils.IsNull;
import static laoyou.com.laoyou.utils.SynUtils.getLayout;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.handleTitleBarColorEvaluate;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/7.
 */
public class HomePageActivity extends InitActivity implements HomePageListener, RecyclerViewOnItemClickListener, AbsListView.OnScrollListener, View.OnClickListener {
    private static final String TAG = "HomePageActivity";
    private ParallaxScollListView listView;
    private LinearLayout head_layout, foot_layout;
    private HomePageAdapter adapter;
    private List<TopicTypeBean> list;
    private ImageView background_img, back_img, more_img;
    private CircleImageView head_img;
    private RelativeLayout title_layout;
    private LinearLayout game_list_layout, photo_layout;
    private TextView nickname_tv, signature_tv, page_view_tv, cardiac_value_tv, detailsOfcompile_tv, address_tv, authentication_tv;
    private FrameLayout add_layout, chat_layout, heart_layout;
    private HomePagePresenter hp;
    private int imageHeight;
    private int height;
    private String id = "";
    private boolean isMe;
    private LinearLayout like_game_layout;
    private TextView foot_tv;
    private boolean IsRefresh;
    private String identify = "";
    private String HeadImgUrl = "";
    private LinearLayout bottom_menu_layout;
    //腾讯云id判断标识符;
    private boolean isTencent;
    private TextView status_tv, photo_tv;
    private FrameLayout top_background_head;
    private HomePageListener listener;


    @Override
    protected void click() {
        listView.setOnScrollListener(this);
        detailsOfcompile_tv.setOnClickListener(this);
        game_list_layout.setOnClickListener(this);
        like_game_layout.setOnClickListener(this);
        heart_layout.setOnClickListener(this);
        chat_layout.setOnClickListener(this);
        add_layout.setOnClickListener(this);
        more_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.home_page_layout);
        listener = this;
        setImgTitles(this);
        ActivityCollector.addActivity(this, getClass());
   /*     id = getIntent().getStringExtra("Page_Home_id");
        isTencent = Boolean.parseBoolean(getIntent().getStringExtra("Page_Home_Tencent_Flag"));
        Log.i(TAG, " isTencent ===" + isTencent);
        isMe = id.isEmpty() ? true : IsMe(id);*/
        hp = new HomePagePresenter(listener);

        bottom_menu_layout = f(R.id.bottom_menu_layout);

        listView = f(R.id.listView);
        title_layout = f(R.id.title_layout);
        add_layout = f(R.id.add_layout);
        chat_layout = f(R.id.chat_layout);
        heart_layout = f(R.id.heart_layout);
        back_img = f(R.id.back_img);
        more_img = f(R.id.more_img);

        list = new ArrayList<>();

        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.home_page_head_layout, null);

        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.foot_include, null);
        HeadViewInit();

        detailsOfcompile_tv.setText(isMe ? gets(R.string.compile_info) : gets(R.string.view_details));

        foot_tv = (TextView) foot_layout.findViewById(R.id.foot_tv);
        foot_tv.setVisibility(View.GONE);

        adapter = new HomePageAdapter(this, list, this);
        listView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
        listView.setParallaxImageView(background_img);

        listView.addHeaderView(head_layout);
        listView.addFooterView(foot_layout);
        listView.setAdapter(adapter);


    }

    private void HeadViewInit() {
        status_tv = (TextView) head_layout.findViewById(R.id.status_tv);
        photo_tv = (TextView) head_layout.findViewById(R.id.photo_tv);

        background_img = (ImageView) head_layout.findViewById(R.id.background_img);
        head_img = (CircleImageView) head_layout.findViewById(R.id.head_img);
        nickname_tv = (TextView) head_layout.findViewById(R.id.nickname_tv);
        signature_tv = (TextView) head_layout.findViewById(R.id.signature_tv);
        page_view_tv = (TextView) head_layout.findViewById(R.id.page_view_tv);
        cardiac_value_tv = (TextView) head_layout.findViewById(R.id.cardiac_value_tv);
        authentication_tv = (TextView) head_layout.findViewById(R.id.authentication_tv);
        detailsOfcompile_tv = (TextView) head_layout.findViewById(R.id.detailsOfcompile_tv);
        like_game_layout = (LinearLayout) head_layout.findViewById(R.id.like_game_layout);

        address_tv = (TextView) head_layout.findViewById(R.id.address_tv);
        photo_layout = (LinearLayout) head_layout.findViewById(R.id.photo_layout);
        game_list_layout = (LinearLayout) head_layout.findViewById(R.id.game_list_layout);
        top_background_head = (FrameLayout) head_layout.findViewById(R.id.top_background_head);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (id != null && !id.isEmpty() && !getIntent().getStringExtra("Page_Home_id").equals(id)) {
            IsRefresh = true;
            hp.page = 0;
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(0);
                    back_img.setImageResource(R.mipmap.return_icon_white);
                    title_layout.setBackgroundColor(getRouColors(R.color.transparent));
                    if (more_img.getVisibility() == View.VISIBLE)
                        more_img.setImageResource(R.mipmap.more_white);

                }
            });
        }

        id = getIntent().getStringExtra("Page_Home_id");

        isTencent = Boolean.parseBoolean(getIntent().getStringExtra("Page_Home_Tencent_Flag"));

        Log.i(TAG, " isTencent ===" + isTencent);
        isMe = id.isEmpty() ? true : IsMe(id);

        if (isMe) {
            hp.getMyDetails();
            IsRefresh = true;
        } else {
            IsRefresh = true;
            hp.getOthersDetails(id, isTencent);
            bottom_menu_layout.setVisibility(View.VISIBLE);
        }

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
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayout(0, w, w);
            lp.rightMargin = DeviceUtils.dip2px(this, 3);
            RoundAngleImageView im = new RoundAngleImageView(this);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            im.setLayoutParams(lp);
            Glide.with(MyApplication.getContext()).load(ll.get(i).getUrl()).apply(getGlideOptions()).into(im);
            photo_layout.addView(im);

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goMyPhotoPage(HomePageActivity.this, isMe, id);
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
        foot_tv.setVisibility(View.GONE);
        id = ub.getId();
        isTencent = false;
        if (!isMe) {
            hp.getMyHeartNum(ub.getId());
            hp.getAttentGames(ub.getId());
            hp.getMyPhotoList(ub.getId());
            hp.getPersonaldynamic(ub.getId(), true);
        }

        identify = ub.getCloudTencentAccount();
        HeadImgUrl = ub.getHeadImgUrl();

        if (IsNull(ub.getHeadImgUrl()))
            Glide.with(MyApplication.getContext()).load(R.mipmap.test_icon).apply(getGlideOptions()).into(head_img);
        else
            Glide.with(MyApplication.getContext()).load(ub.getHeadImgUrl()).apply(getGlideOptions()).into(head_img);

        nickname_tv.setText(ub.getName());

        Glide.with(MyApplication.getContext()).load(IsNull(ub.getBackgroundUrl()) ? Fields.RandomBackGGround() : ub.getBackgroundUrl()).apply(getGlideOptions()).into(background_img);
        signature_tv.setText(IsNull(ub.getAutograph()) ? gets(R.string.default_signature) : ub.getAutograph());
        page_view_tv.setText(IsNull(ub.getBrowseNumber()) ? String.valueOf(0) : ub.getBrowseNumber());
        address_tv.setText(IsNull(ub.getAddress()) ? "" : ub.getAutograph());
        authentication_tv.setText(IsNull(ub.getIdcard()) ? gets(R.string.un_certification) : gets(R.string.certification));

        if (!isMe)
            if (FriendshipInfo.getInstance().isFriend(identify))
                more_img.setVisibility(View.VISIBLE);
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
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayout(0, w, w);
            CircleImageView civ = new CircleImageView(this);
            civ.setLayoutParams(lp);
            lp.rightMargin = DeviceUtils.dip2px(this, 2);
            Glide.with(MyApplication.getContext()).load(ab.getImgUrl()).apply(getGlideOptions()).into(civ);
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
        photo_tv.setVisibility(photos.size() > 0 ? View.VISIBLE : View.GONE);
        addPhotoList(photos);
    }

    @Override
    public void onBottom() {

        if (list.size() > 0)
            foot_tv.setVisibility(View.VISIBLE);
        else
            status_tv.setVisibility(View.GONE);
    }

    @Override
    public void onStatusInfo(List<TopicTypeBean> nblist) {
        status_tv.setVisibility(View.VISIBLE);

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
    public void onAddFriend(TIMFriendStatus status) {

        switch (status) {
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succeed), Toast.LENGTH_SHORT).show();
                break;
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succ), Toast.LENGTH_SHORT).show();
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Toast.makeText(this, getResources().getString(R.string.add_friend_refuse_all), Toast.LENGTH_SHORT).show();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                Toast.makeText(this, getResources().getString(R.string.add_friend_to_blacklist), Toast.LENGTH_SHORT).show();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.add_friend_del_black_list), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FriendshipManagerPresenter.delBlackList(Collections.singletonList(identify), new TIMValueCallBack<List<TIMFriendResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(HomePageActivity.this, getResources().getString(R.string.add_friend_del_black_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                                Toast.makeText(HomePageActivity.this, getResources().getString(R.string.add_friend_del_black_succ), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.add_friend_error), Toast.LENGTH_SHORT).show();
                break;
        }
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

        handleTitleBarColorEvaluate(height, title_layout, back_img, more_img.getVisibility() == View.GONE ? null : more_img);

        if (IsListViewTopOfBottom(firstVisibleItem, visibleItemCount, totalItemCount, listView) == Fields.IsBottom && (Fields.VPT == 0 || System.currentTimeMillis() - Fields.VPT >= 2000)) {
            Log.i(TAG, " IsListViewTopOfBottom");
            IsRefresh = false;
            if (isMe)
                hp.getPersonaldynamic(null, false);
            else
                hp.getPersonaldynamic(id, false);
        } else if (IsListViewTopOfBottom(firstVisibleItem, visibleItemCount, totalItemCount, listView) == Fields.IsTop) {
            title_layout.setBackgroundColor(getRouColors(R.color.transparent));//AGB由相关工具获得，或者美工提供
            back_img.setImageResource(R.mipmap.return_icon_white);
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
                if (!isMe) {
                    Show(HomePageActivity.this, "", true, null);
                    hp.GiveHeart(id);
                }

                break;
            case R.id.add_layout:
                if (!isMe && !identify.isEmpty()) {
                    if (FriendshipInfo.getInstance().isFriend(identify))
                        ToastUtil.toast2_bottom(this, gets(R.string.already_find));
                    else
                        goAddFriendPage(this, identify);
                }

                break;
            case R.id.chat_layout:
                if (!isMe && !identify.isEmpty() && FriendshipInfo.getInstance().isFriend(identify)) {
                    SPreferences.saveTemporaryImg(String.valueOf(HeadImgUrl));
                    ChatActivity.navToChat(this, identify, TIMConversationType.C2C);
                } else
                    ToastUtil.toast2_bottom(this, gets(R.string.is_not_find));

                break;
            case R.id.more_img:
                if (!identify.isEmpty())
                    ShowDialog();
                break;
        }
    }

    /**
     * 更多操作弹窗;
     */
    private void ShowDialog() {
        new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.pull_the_black_friend), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                FriendshipManagerPresenter.addBlackList(Collections.singletonList(identify), new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "add black list error " + s);
                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        if (timFriendResults.get(0).getStatus() == TIMFriendStatus.TIM_FRIEND_STATUS_SUCC) {
                            Toast.makeText(HomePageActivity.this, getString(R.string.profile_black_succ), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addSheetItem(gets(R.string.profile_del), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Show(HomePageActivity.this, "提交中", true, null);
                hp.delFriend(identify);
            }
        }).show();
    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {
        switch (status) {
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.profile_del_succeed), Toast.LENGTH_SHORT).show();
                AddressbookActivity ac = AddressBookInstance();
                if (ac != null)
                    ac.onRefresh();

//                if (ConversationInstance() != null)
//                    ConversationInstance().MyDelMethod(identify);

                break;
            case TIM_FRIEND_STATUS_UNKNOWN:
                Toast.makeText(this, getResources().getString(R.string.profile_del_fail), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void RcOnItemClick(int pos, List<String> imgs) {
        if (pos < imgs.size())
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
