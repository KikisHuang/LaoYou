package laoyou.com.laoyou.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.GridAdapter;
import laoyou.com.laoyou.adapter.TopicCommentAdapter;
import laoyou.com.laoyou.bean.ChatMessages;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.bean.TopicCommentBean;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.listener.KeyboardChangeListener;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.presenter.TopicCommentPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.HandleDataListView;
import laoyou.com.laoyou.view.MinheightGridView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.AnimationUtil.TitleZoomAnima;
import static laoyou.com.laoyou.utils.DateUtils.getMyDate;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goParticipationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.fileIsExists;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.saveImage;
import static laoyou.com.laoyou.utils.VideoUtils.createVideoThumbnail;

/**
 * Created by lian on 2017/12/20.
 */
public class TopicCommentDetailsActivity extends InitActivity implements View.OnClickListener, AbsListView.OnScrollListener, TopicCommentListener, Animation.AnimationListener, KeyboardChangeListener.KeyBoardListener, AdapterView.OnItemClickListener, HandleDataListView.DataChangedListener {

    private static final String TAG = "TopicCommentDetailsActivity";
    private String id;
    private String userId;
    private String name;
    private HandleDataListView listView;
    private LinearLayout head_layout;

    private TextView user_name, issue_tv, type_name, time_tv, content_tv, like_num_tv;
    private LinearLayout content_img_layout;
    private MinheightGridView gridView;
    private GridAdapter gadapter;
    private LinearLayout like_layout, comment_layout, send_comment_layout, menu_layout;
    private TopicCommentPresenter tp;
    private ImageView like_icon;
    private EditText comment_ed;
    private TextView send_comment_tv, comment_num_tv;
    private CircleImageView user_head_img;
    private TopicCommentAdapter adapter;
    private List<ChatMessages> list;
    private boolean isRefresh = true;
    private String content;
    private int pos = -1;
    private List<LikeListBean> likelist;
    private boolean loadmore = true;
    private CircleImageView photo_img;

    private List<LocalMedia> selectList = new ArrayList<>();
    private boolean IsPhoto;
    private String coverPath = "";
    private ImageView video_cover_img;
    private FrameLayout video_layout;

    @Override
    protected void click() {
        like_layout.setOnClickListener(this);
        comment_layout.setOnClickListener(this);
        send_comment_tv.setOnClickListener(this);
        new KeyboardChangeListener(this).setKeyBoardListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        listView.setDataChangedListener(this);
        gridView.setOnItemClickListener(this);
        photo_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.topic_details_comment_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        listView = f(R.id.listView);
        like_icon = f(R.id.like_icon);
        comment_ed = f(R.id.comment_ed);
        send_comment_tv = f(R.id.send_comment_tv);
        menu_layout = f(R.id.menu_layout);
        like_layout = f(R.id.like_layout);
        send_comment_layout = f(R.id.send_comment_layout);
        comment_layout = f(R.id.comment_layout);

        photo_img = f(R.id.photo_img);


        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.topic_comment_head, null);
        tp = new TopicCommentPresenter(this);

        video_cover_img = (ImageView) head_layout.findViewById(R.id.video_cover_img);
        video_layout = (FrameLayout) head_layout.findViewById(R.id.video_layout);

        user_name = (TextView) head_layout.findViewById(R.id.user_name);
        issue_tv = (TextView) head_layout.findViewById(R.id.issue_tv);
        type_name = (TextView) head_layout.findViewById(R.id.type_name);
        comment_num_tv = (TextView) head_layout.findViewById(R.id.comment_num_tv);
        time_tv = (TextView) head_layout.findViewById(R.id.time_tv);
        content_tv = (TextView) head_layout.findViewById(R.id.content_tv);
        like_num_tv = (TextView) head_layout.findViewById(R.id.like_num_tv);
        gridView = (MinheightGridView) head_layout.findViewById(R.id.gridView);

        content_img_layout = (LinearLayout) head_layout.findViewById(R.id.content_img_layout);


        user_head_img = (CircleImageView) head_layout.findViewById(R.id.user_head_img);

        list = new ArrayList<>();
        likelist = new ArrayList<>();
        listView.addHeaderView(head_layout);

        adapter = new TopicCommentAdapter(this, list, this);
        listView.setAdapter(adapter);

        gridView.setAdapter(gadapter = new GridAdapter(this, likelist));

        id = getIntent().getStringExtra("Page_CommentDetails_id");
        userId = getIntent().getStringExtra("Page_CommentDetails_userId");
        name = getIntent().getStringExtra("Page_CommentDetails_name");
        content = getIntent().getStringExtra("Page_CommentDetails_content");

        Log.i(TAG, "userId ===" + userId);
    }

    @Override
    protected void initData() {
        tp.getTopicDetails(id);
        tp.getLikeStatus(id);
        tp.getComment(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_layout:

                Show(TopicCommentDetailsActivity.this, "", true, null);
                like_layout.setEnabled(false);
                ScaleAnimation anima = TitleZoomAnima();
                anima.setAnimationListener(this);
                like_layout.startAnimation(anima);
                tp.LikeTopic(id);

                break;

            case R.id.comment_layout:
                if (send_comment_layout.getVisibility() == View.GONE) {
                    name = "";
                    userId = "";
                    showSoftInputFromWindow();
                }
                break;
            case R.id.send_comment_tv:
                String content = comment_ed.getText().toString();
                Show(TopicCommentDetailsActivity.this, "", true, null);
                if (selectList.size() > 0)
                    tp.SendComment(id, userId, content, new File(selectList.get(0).getCompressPath()));
                else
                    tp.SendComment(id, userId, content, null);
                break;
            case R.id.photo_img:
                IsPhoto = true;
                SelectPhoto();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
//                    Glide.with(this).load(selectList.get(0).getCompressPath()).into(comment_img);
                    tp.ShowPhotoPopup(selectList.get(0).getCompressPath(), comment_layout);
                    break;
            }
        }

    }

    private void SelectPhoto() {
        menu_layout.setVisibility(View.GONE);
        send_comment_layout.setVisibility(View.VISIBLE);
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)// 样式
                .imageSpanCount(3) //每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(false)// 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/Hoop_Photo")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
        like_layout.setEnabled(true);
        Cancle();
    }

    @Override
    public void onSucceed() {
        tp.ClosePopupWindow();
        selectList.clear();
        IsPhoto = false;
        menu_layout.setVisibility(View.VISIBLE);
        send_comment_layout.setVisibility(View.GONE);

        isRefresh = true;
        tp.page = 0;
//      tp.getTopicDetails(id);
        tp.getComment(id);
        Cancle();
    }

    @Override
    public void onLikeStatus(boolean b) {
        tp.getLikeNum(id);
        tp.GetLikeUserByPage(id);
        like_layout.setEnabled(true);
        Cancle();
        Glide.with(this).load(b ? R.mipmap.like_heart : R.mipmap.unlike_heart).into(like_icon);
    }

    /**
     * messageTypeFlag
     * 类型标记 0普通回帖 100帖子回复 200是主题1楼
     *
     * @param tcb
     */
    @Override
    public void onThemeDetails(final TopicCommentBean tcb) {


        Glide.with(this).load(tcb.getMcUser().getHeadImgUrl()).apply(getGlideOptions()).into(user_head_img);
        user_name.setText(tcb.getMcUser().getName());
        type_name.setText(tcb.getMcChatType().getName());
        time_tv.setText(getMyDate(tcb.getCreateTime()));

        content_tv.setText(tcb.getMessageContent() == null || tcb.getMessageContent().isEmpty() ? "" : tcb.getMessageContent());
        content_tv.setVisibility(tcb.getMessageContent() == null || tcb.getMessageContent().isEmpty() ? View.GONE : View.VISIBLE);

        if (tcb.getLikeCount() == 0)
            like_num_tv.setVisibility(View.GONE);
        else {
            like_num_tv.setVisibility(View.VISIBLE);
            like_num_tv.setText(String.valueOf(tcb.getLikeCount()) + "赞");
        }

        comment_num_tv.setVisibility(tcb.getCommentsCount() == null ? View.GONE : View.VISIBLE);
        comment_num_tv.setText(tcb.getCommentsCount() == null ? "0" + "条评论" : tcb.getCommentsCount() + "条评论");
        /**
         * 图片类型;
         */
        if (tcb.getType() == 1) {
            if (tcb.getPhotos() != null) {
                for (int i = 0; i < tcb.getPhotos().size(); i++) {

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.topMargin = DeviceUtils.dip2px(this, 2);
                    ImageView im = new ImageView(this);
                    im.setScaleType(ImageView.ScaleType.FIT_XY);
                    im.setLayoutParams(lp);
                    Glide.with(this).load(tcb.getPhotos().get(i)).apply(getGlideOptions()).into(im);
                    final int finalI = i;
                    im.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goPhotoViewerPage(TopicCommentDetailsActivity.this, tcb.getPhotos(), finalI, 1);
                        }
                    });
                    content_img_layout.addView(im);
                }
            }
        }
        /**
         * 视频类型;
         */
        if (tcb.getType() == 2) {
            if (tcb.getVideos() != null) {
                if (fileIsExists(tcb.getVideos()))
                    coverPath = saveImage(null, tcb.getVideos());
                else {
                    Bitmap bitmap = createVideoThumbnail(tcb.getVideos(), DeviceUtils.getWindowWidth(SPreferences.context), (int) (DeviceUtils.getWindowWidth(SPreferences.context) * 0.8 / 1));
                    coverPath = saveImage(bitmap, tcb.getVideos());
                }
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.topMargin = DeviceUtils.dip2px(this, 2);
                video_cover_img.setLayoutParams(lp);
                Glide.with(this).load(coverPath).apply(getGlideOptions()).into(video_cover_img);
                video_layout.setVisibility(View.VISIBLE);

                video_cover_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goVideoPlayerPage(TopicCommentDetailsActivity.this, tcb.getVideos(), coverPath);
                    }
                });
            }
        }

    }

    @Override
    public void GoHomePage(String id) {
        goHomePage(this, id,false);
    }

    @Override
    public void LikeListData(List<LikeListBean> li) {
        likelist.clear();
        for (LikeListBean like : li) {
            likelist.add(like);
        }
        gadapter.notifyDataSetChanged();
    }

    @Override
    public void onLikeNum(String num) {
        if (Integer.valueOf(num) > 0)
            like_num_tv.setVisibility(View.VISIBLE);
        else
            like_num_tv.setVisibility(View.GONE);

        like_num_tv.setText(num + "赞");
    }

    @Override
    public void onDeleteSucceed() {
        this.onSucceed();
    }

    @Override
    public void onCommentInfo(List<ChatMessages> cm) {
        if (isRefresh)
            list.clear();

        for (int i = 0; i < cm.size(); i++) {
            list.add(cm.get(i));
        }

        if (adapter != null) {
            if (!userId.isEmpty() && !name.isEmpty()) {
                for (int i = 0; i < cm.size(); i++) {
                    ChatMessages ctm = cm.get(i);
                    if (userId.equals(ctm.getUserId()) && name.equals(ctm.getUserName()) && content.equals(ctm.getMessageContent())){
                        pos = i;
                        break;
                    }

                }
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setSelection(pos + 1);
                showSoftInputFromWindow();
            } else
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNoMore(String msg) {
//        ToastUtil.toast2_bottom(this, msg);
        loadmore = false;
    }

    @Override
    public void onPhotoCancle() {
        selectList.clear();
        IsPhoto = false;
        menu_layout.setVisibility(View.VISIBLE);
        send_comment_layout.setVisibility(View.GONE);
    }

    @Override
    public void onPhotoImgClick() {
        //预览
        if (selectList.size() > 0)
            PictureSelector.create(this).externalPicturePreview(0, selectList);
    }

    @Override
    public void GoPhotoPage(String url) {
        List<String> list = new ArrayList<>();
        list.add(url);
        goPhotoViewerPage(this, list, 0, 1);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private void showSoftInputFromWindow() {
        send_comment_layout.setVisibility(View.VISIBLE);
        comment_ed.setText("");
        menu_layout.setVisibility(View.GONE);

        if (!name.isEmpty() && !userId.isEmpty())
            comment_ed.setHint(gets(R.string.reply) + " " + name + "：");
        else
            comment_ed.setHint(gets(R.string.click_add_comment));

        comment_ed.setFocusable(true);
        comment_ed.setFocusableInTouchMode(true);
        comment_ed.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

//        Glide.with(this).load(IsLike ? R.mipmap.unlike_heart : R.mipmap.like_heart).into(like_icon);
//        IsLike = !IsLike;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    /**
     * 软键盘收缩回调方法;
     *
     * @param isShow         true is show else hidden
     * @param keyboardHeight keyboard height
     */
    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (!isShow && !IsPhoto) {
            menu_layout.setVisibility(View.VISIBLE);
            send_comment_layout.setVisibility(View.GONE);
        } else if (isShow && IsPhoto) {
            tp.MovePopupWindow(comment_layout);
        } else if (!isShow && IsPhoto)
            tp.MovePopupWindow(comment_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tp.ClosePopupWindow();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long ids) {

        if (parent instanceof ListView) {
            if (list.size() > 0) {
                Log.i(TAG, " position ===" + position);
                if (position != 0) {
                    if (list.get(position - 1).getUserId().equals(SPreferences.getUserId())) {

                        new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.delete), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Show(TopicCommentDetailsActivity.this, "请稍后..", true, null);
                                tp.DeleteMyComment(list.get(position - 1).getId());
                            }
                        }).show();

                    } else {
                        userId = list.get(position - 1).getId();
                        name = list.get(position - 1).getUserName();
                        showSoftInputFromWindow();
                    }
                }
            }
        }

        if (parent instanceof GridView) {
            if (likelist.size() > 0) {
                if (position < 7)
                    goHomePage(this, likelist.get(position).getId(),false);
                else
                    goParticipationPage(this, 2, id);
            }

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && loadmore) {
            isRefresh = false;
            tp.page += 10;
            tp.getComment(id);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onDataChangedSuccess() {

    }
}
