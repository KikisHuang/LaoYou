package laoyou.com.laoyou.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;

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
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.presenter.TopicCommentPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.HandleDataListView;
import laoyou.com.laoyou.view.MinheightGridView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.AnimationUtil.TitleZoomAnima;
import static laoyou.com.laoyou.utils.DateUtils.getMyDate;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goParticipationPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/20.
 */
public class TopicCommentDetailsActivity extends InitActivity implements View.OnClickListener, AbsListView.OnScrollListener, TopicCommentListener, Animation.AnimationListener, KeyboardChangeListener.KeyBoardListener, SpringListener, AdapterView.OnItemClickListener, HandleDataListView.DataChangedListener {

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
    private SpringView springView;
    private boolean isRefresh = true;
    private String content;
    private int pos = -1;
    private List<LikeListBean> likelist;


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
    }

    @Override
    protected void init() {
        setContentView(R.layout.topic_details_comment_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        listView = f(R.id.listView);
        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, this, this);
        springView.setEnable(false);
        like_icon = f(R.id.like_icon);
        comment_ed = f(R.id.comment_ed);
        send_comment_tv = f(R.id.send_comment_tv);
        menu_layout = f(R.id.menu_layout);
        like_layout = f(R.id.like_layout);
        send_comment_layout = f(R.id.send_comment_layout);
        comment_layout = f(R.id.comment_layout);

        head_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.topic_comment_head, null);
        tp = new TopicCommentPresenter(this);
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


    }

    @Override
    protected void initData() {
        tp.getTopicDetails(id);
        tp.getLikeStatus(id);
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
                Show(TopicCommentDetailsActivity.this, "", true, null);
                tp.SendComment(id, userId, comment_ed.getText().toString(), null);
                break;

        }
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
        like_layout.setEnabled(true);
        Cancle();
    }

    @Override
    public void onSucceed() {
        isRefresh = true;
        tp.getTopicDetails(id);
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
    public void onThemeDetails(TopicCommentBean tcb) {

        Glide.with(this).load(tcb.getMcUser().getHeadImgUrl()).into(user_head_img);
        user_name.setText(tcb.getMcUser().getName());
        type_name.setText(tcb.getMcChatType().getName());

        time_tv.setText(getMyDate(tcb.getCreateTime()));
        content_tv.setText(tcb.getMessageContent() == null || tcb.getMessageContent().isEmpty() ? "" : tcb.getMessageContent());
        content_tv.setVisibility(tcb.getMessageContent() == null || tcb.getMessageContent().isEmpty() ? View.GONE : View.VISIBLE);

        like_num_tv.setText(String.valueOf(tcb.getLikeCount()) + "赞");
        comment_num_tv.setText(tcb.getCommentsCount() + "条评论");

        if (isRefresh)
            list.clear();
        for (int i = 0; i < tcb.getChatMessages().size(); i++) {
            if (!userId.isEmpty() && !name.isEmpty())
                if (tcb.getChatMessages().get(i).getMessageContent().equals(content) && userId.equals(tcb.getChatMessages().get(i).getId()))
                    pos = i;

            list.add(tcb.getChatMessages().get(i));
        }
        if (adapter != null) {
            if (!userId.isEmpty() && !name.isEmpty() && pos >= 0) {
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setSelection(pos + 1);
                showSoftInputFromWindow();
            } else
                adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void GoHomePage(String id) {
        goHomePage(this, id);
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
        like_num_tv.setText(num + "赞");
    }

    @Override
    public void onDeleteSucceed() {
        this.onSucceed();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private void showSoftInputFromWindow() {
        send_comment_layout.setVisibility(View.VISIBLE);
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

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (!isShow) {
            comment_ed.setText("");
            menu_layout.setVisibility(View.VISIBLE);
            send_comment_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void IsonRefresh(int init) {
        isRefresh = true;
    }

    @Override
    public void IsonLoadmore(int move) {
        isRefresh = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long ids) {

        if (parent instanceof ListView) {
            if (list.size() > 0) {
                Log.i(TAG, " position ===" + position);
                if (position != 0) {
                    if (list.get(position - 1).getMcUserByUserId().getCloudTencentAccount().equals(SPreferences.getIdentifier())) {

                        new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.delete), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Show(TopicCommentDetailsActivity.this, "请稍后..", true, null);
                                tp.DeleteMyComment(list.get(position - 1).getId());
                            }
                        }).show();

                    } else {
                        userId = list.get(position - 1).getId();
                        name = list.get(position - 1).getMcUserByUserId().getName();
                        showSoftInputFromWindow();
                    }
                }
            }
        }

        if (parent instanceof GridView) {
            if (likelist.size() > 0) {
                if (position < 7)
                    goHomePage(this, likelist.get(position).getId());
                else
                    goParticipationPage(this, 2, id);
            }

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        springView.setEnable(false);
//        if (firstVisibleItem == 0)
//            springView.setEnable(false);
//        else if ((firstVisibleItem + visibleItemCount) == totalItemCount)
//            springView.setEnable(true);

    }

    @Override
    public void onDataChangedSuccess() {

    }
}
