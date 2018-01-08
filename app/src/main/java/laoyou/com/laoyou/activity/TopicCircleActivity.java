package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicCircleAdapter;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.listener.TopicCircleListener;
import laoyou.com.laoyou.presenter.TopicCirclePresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goMyNoticesPage;
import static laoyou.com.laoyou.utils.IntentUtils.goReleaseTopicPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicTypeDetailsPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/18.
 */
public class TopicCircleActivity extends InitActivity implements TopicCircleListener, SpringListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "TopicCircleActivity";
    private ListView listView;
    private TopicCirclePresenter tp;
    private FrameLayout head;
    private TopicCircleAdapter adapter;
    private List<TopicBean> list;
    private SpringView springView;
    private boolean IsRefresh;
    private ImageView issue_img, photo_img, camera_img;

    @Override
    protected void click() {
        listView.setOnItemClickListener(this);
        issue_img.setOnClickListener(this);
        photo_img.setOnClickListener(this);
        camera_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.topic_circle_layout);
        listView = f(R.id.listView);
        springView = f(R.id.springView);
        issue_img = f(R.id.issue_img);
        photo_img = f(R.id.photo_img);
        camera_img = f(R.id.camera_img);
        SpringUtils.SpringViewInit(springView, this, this);
        head = (FrameLayout) getLayoutInflater().inflate(R.layout.topic_circle_head, null);
        setTitlesAndBack(this, gets(R.string.discover), "");
        tp = new TopicCirclePresenter(this);
        list = new ArrayList<>();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onTopicTypeList(List<TopicBean> s) {
        if (IsRefresh)
            list.clear();

        for (TopicBean tb : s) {
            list.add(tb);
        }
        if (adapter == null) {
            adapter = new TopicCircleAdapter(this, list);
            listView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onHeadViewShwoOfHide(boolean b) {
        IsRefresh = true;
        tp.getTopicDataList(true);
        if (b)
            if (listView.getHeaderViewsCount() == 0)
                listView.addHeaderView(head);

    }

    @Override
    public void IsonRefresh(int init) {
        IsRefresh = true;
//        tp.getMyFollowChatType();
        tp.page = init;
        tp.getTopicDataList(true);
    }

    @Override
    public void IsonLoadmore(int move) {
//        tp.getMyFollowChatType();
        IsRefresh = false;
        tp.page += move;
        tp.getTopicDataList(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //判断是否有头部;
        if (listView.getHeaderViewsCount() == 0)
            goTopicTypeDetailsPage(TopicCircleActivity.this, list.get(position).getId(), list.get(position).getName(), list.get(position).getFollowCount(), list.get(position).getChatThemeCount(), list.get(position).getImgUrl());
        else if (position == 0) {
            goMyNoticesPage(this);
        } else
            goTopicTypeDetailsPage(TopicCircleActivity.this, list.get(position - 1).getId(), list.get(position - 1).getName(), list.get(position - 1).getFollowCount(), list.get(position - 1).getChatThemeCount(), list.get(position - 1).getImgUrl());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.issue_img:
                goReleaseTopicPage(this, null, null);
                break;
            case R.id.photo_img:
                PhotoSelect();
                break;

            case R.id.camera_img:
                VideoSelect();
                break;
        }
    }


    private void PhotoSelect() {
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
                .sizeMultiplier(0.8f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/Hoop_Photo")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void VideoSelect() {
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
                .videoMaxSecond(30)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(5)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(30)//视频秒数录制 默认60s int
                .forResult(PictureConfig.REQUEST_CAMERA);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> photo = PictureSelector.obtainMultipleResult(data);
                    goReleaseTopicPage(this, photo, null);
                    break;

                case PictureConfig.REQUEST_CAMERA:
                    List<LocalMedia> video = PictureSelector.obtainMultipleResult(data);
                    goReleaseTopicPage(this, null, video);

                    break;
                case Fields.ACRESULET5:
                    IsRefresh = true;
                    tp.page = 0;
                    tp.getTopicDataList(true);
                    break;
            }
        }
    }
}
