package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.PhotoGridAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.EdittextListener;
import laoyou.com.laoyou.listener.ReleaseTopicListener;
import laoyou.com.laoyou.presenter.ReleaseTopicPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.ContainsEmojiEditText;
import laoyou.com.laoyou.view.MinheightGridView;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goAddTopicTypePage;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/27.
 */
public class ReleaseTopicActivity extends InitActivity implements View.OnClickListener, ReleaseTopicListener, AdapterView.OnItemClickListener, EdittextListener {

    private static final String TAG = "ReleaseTopicActivity";
    private TextView cancel_tv, issue_tv, topic_type_tv;
    private ContainsEmojiEditText topic_content_ed;
    private MinheightGridView photo_gridView;
    private PhotoGridAdapter adapter;
    private LinearLayout photo_layout, topic_type_layout, video_layout;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> videoselectList = new ArrayList<>();
    private ImageView video_img, remove_img;
    private FrameLayout video_cover_layout;
    private int contentNum = 0;
    private ReleaseTopicPresenter rp;
    private String topicType = "";
    private TextView topic_name, user_name;
    private LinearLayout issue_layout;
    private ImageView user_icon;


    @Override
    protected void click() {
        photo_layout.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        video_layout.setOnClickListener(this);
        photo_gridView.setOnItemClickListener(this);
        video_img.setOnClickListener(this);
        remove_img.setOnClickListener(this);
        video_cover_layout.setOnClickListener(this);
        issue_tv.setOnClickListener(this);
        topic_content_ed.setContentListener(this);
        topic_type_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.release_topic_layout);
        ActivityCollector.addActivity(this, getClass());
        cancel_tv = f(R.id.cancel_tv);
        issue_tv = f(R.id.issue_tv);
        topic_type_tv = f(R.id.topic_type_tv);
        topic_name = f(R.id.topic_name);
        issue_layout = f(R.id.issue_layout);
        video_cover_layout = f(R.id.video_cover_layout);
        topic_content_ed = f(R.id.topic_content_ed);
        user_icon = f(R.id.user_icon);
        user_name = f(R.id.user_name);

        topic_content_ed.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        topic_content_ed.setGravity(Gravity.TOP);
        //改变默认的单行模式
        topic_content_ed.setSingleLine(false);
        //水平滚动设置为False
        topic_content_ed.setHorizontallyScrolling(false);

        photo_gridView = f(R.id.photo_gridView);

        topic_type_layout = f(R.id.topic_type_layout);
        photo_layout = f(R.id.photo_layout);
        video_img = f(R.id.video_img);
        remove_img = f(R.id.remove_img);
        video_layout = f(R.id.video_layout);
        rp = new ReleaseTopicPresenter(this);

        if (getIntent().getParcelableArrayListExtra("Release_photo") != null) {
            selectList = getIntent().getParcelableArrayListExtra("Release_photo");
            issue_tv.setTextColor(getRouColors(R.color.blue_text));
            video_layout.setVisibility(View.GONE);
            photo_gridView.setVisibility(View.VISIBLE);
            selectList.add(null);
        }
        if (getIntent().getParcelableArrayListExtra("Release_video") != null) {
            videoselectList = getIntent().getParcelableArrayListExtra("Release_video");

            if (videoselectList != null) {
                issue_tv.setTextColor(getRouColors(R.color.blue_text));
                photo_layout.setVisibility(View.GONE);
                video_cover_layout.setVisibility(View.VISIBLE);
                Glide.with(MyApplication.getContext()).load(videoselectList.get(0).getPath()).apply(getGlideOptions()).into(video_img);
            }
        }
        if (getIntent().getStringArrayListExtra("Release_type") != null) {
            issue_layout.setVisibility(View.VISIBLE);
            topicType = getIntent().getStringArrayListExtra("Release_type").get(0);
            topic_name.setText(getIntent().getStringArrayListExtra("Release_type").get(1));
        }

        adapter = new PhotoGridAdapter(this, selectList, this);
        photo_gridView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getWindowHeight(this) * 1 / 1.8));
        video_img.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_layout:
                if (videoselectList.size() <= 0) {
                    PhotoSelect();
                } else
                    ToastUtil.toast2_bottom(this, "图片和视频不能图示发布哦！");

                break;
            case R.id.video_layout:
                if (selectList.size() <= 0) {
                    VideoSelect();
                } else
                    ToastUtil.toast2_bottom(this, "图片和视频不能图示发布哦！");
                break;

            case R.id.video_img:
                if (videoselectList.size() > 0) {
                    LocalMedia media = videoselectList.get(0);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    if (mediaType == 2)
                        // 预览视频
                        PictureSelector.create(this).externalPictureVideo(media.getPath());
                }

                break;
            case R.id.remove_img:
                if (videoselectList.size() > 0) {
                    if (contentNum <= 0)
                        issue_tv.setTextColor(getRouColors(R.color.content3));
                    videoselectList.remove(0);
                    Glide.with(MyApplication.getContext()).load(R.color.white).load(video_img);
                    video_cover_layout.setVisibility(View.GONE);
                    video_layout.setVisibility(View.VISIBLE);
                    photo_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.issue_tv:
                int content = topic_content_ed.getText().toString().length();
                int photo = selectList != null ? selectList.size() : 0;
                int video = videoselectList != null ? videoselectList.size() : 0;

                if (photo > 0 || video > 0 || content > 0) {
                    List<File> photos = null;
                    if (selectList.size() > 0)
                        photos = new ArrayList<>();
                    for (LocalMedia lm : selectList) {
                        if (lm != null)
                            photos.add(new File(lm.getCompressPath() == null || lm.getCompressPath().isEmpty() ? lm.getPath() : lm.getCompressPath()));
                    }
                    File videos = null;
                    Show(ReleaseTopicActivity.this, "发布中", true, null);
                    if (videoselectList.size() > 0)
                        videos = new File(videoselectList.get(0).getPath());

                    rp.IssueTopic(topicType, topic_content_ed.getText().toString(), photos, videos);

                } else
                    ToastUtil.toast2_bottom(this, gets(R.string.cannot_send_null_content));
                break;

            case R.id.cancel_tv:
                if (contentNum > 0 || selectList.size() > 0 || videoselectList.size() > 0) {
                    new MyAlertDialog(this).builder().setCancelable(true).setTitle("提示").setMsg("确定放弃编辑内容？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
                } else
                    finish();

                break;

            case R.id.topic_type_layout:
                goAddTopicTypePage(this);
                break;
        }
    }

    private void VideoSelect() {
        //R.style.picture_default_style 如果不设置样式就为默认样式;
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)// 样式
                .imageSpanCount(3) //每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)
                .previewVideo(true)// 是否可预览视频 true or false;
                .enablePreviewAudio(false)// 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .selectionMedia(videoselectList)// 是否传入已选图片 List<LocalMedia> list
                .videoMaxSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(5)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(30)//视频秒数录制 默认60s int
                .forResult(PictureConfig.REQUEST_CAMERA);//结果回调onActivityResult code
    }

    /**
     * 0 ofAll
     * 1 ofImage
     * 2 ofVideo
     * 3 ofAudio
     */
    private void PhotoSelect() {
        if (selectList.size() > 0 && selectList.get(selectList.size() - 1) == null)
            selectList.remove(selectList.size() - 1);

        //R.style.picture_default_style 如果不设置样式就为默认样式;
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)// 样式
                .maxSelectNum(9)// 最大选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3) //每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)// 是否可预览图片 true or false
                .enablePreviewAudio(false)// 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/Hoop_Photo")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
//        PictureFileUtils.deleteCacheDirFile(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    issue_tv.setTextColor(getRouColors(R.color.blue_text));
                    video_layout.setVisibility(View.GONE);
                    photo_gridView.setVisibility(View.VISIBLE);
                    // 图片选择结果回调
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    selectList.clear();
                    for (LocalMedia lm : list) {
                        selectList.add(lm);
                    }
                    selectList.add(null);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.size() <= 0) {
                        video_layout.setVisibility(View.VISIBLE);
                        photo_layout.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "onActivityResult selectList size === " + selectList.size());
                    break;

                case PictureConfig.REQUEST_CAMERA:
                    issue_tv.setTextColor(getRouColors(R.color.blue_text));
                    photo_layout.setVisibility(View.GONE);
                    video_cover_layout.setVisibility(View.VISIBLE);
                    // 图片选择结果回调
                    videoselectList = PictureSelector.obtainMultipleResult(data);
                    Glide.with(MyApplication.getContext()).load(videoselectList.get(0).getPath()).apply(getGlideOptions()).into(video_img);
                    Log.i(TAG, "onActivityResult videoselectList size === " + videoselectList.size());
                    break;

                case Fields.ACRESULET5:
                    topicType = data.getExtras().getString("Topic_Type_Id");
                    topic_name.setText(data.getExtras().getString("Topic_Type_Name"));
                    issue_layout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onFaileds(String msg) {
        ToastUtil.toast2_bottom(ReleaseTopicActivity.this, msg);
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(this, gets(R.string.issue_succ));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRemove(int pos) {
        if (selectList.size() > 0) {
            selectList.remove(pos);
            adapter.notifyDataSetChanged();
        }
        if (selectList.size() == 1 && selectList.get(0) == null) {
            if (contentNum <= 0)
                issue_tv.setTextColor(getRouColors(R.color.content3));

            selectList.remove(0);
            adapter.notifyDataSetChanged();
            video_layout.setVisibility(View.VISIBLE);
            photo_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void AddPhoto() {
        PhotoSelect();
    }

    @Override
    public void ShowUserInfo(UserInfoBean ub) {
        Glide.with(MyApplication.getContext()).load(ub.getHeadImgUrl()).into(user_icon);
        user_name.setText(ub.getName());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectList.size() > 0) {
            LocalMedia media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    if (position + 1 != selectList.size()) {
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(this).externalPicturePreview(position, selectList);
                    }
                    break;
         /*       case 2:
                    // 预览视频
                    PictureSelector.create(this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(this).externalPictureAudio(media.getPath());
                    break;*/
            }
        }
    }

    @Override
    public void onQuery(String s) {

    }

    @Override
    public void onClear() {

    }

    @Override
    public void onContentChange(String s) {
        contentNum = s.length();
        if (s.length() > 0)
            issue_tv.setTextColor(getRouColors(R.color.blue_text));
        else if (selectList.size() <= 0 && videoselectList.size() <= 0)
            issue_tv.setTextColor(getRouColors(R.color.content3));

    }
}
