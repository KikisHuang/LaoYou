package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.MyCommentAdapter;
import laoyou.com.laoyou.adapter.MyHeartValueAdapter;
import laoyou.com.laoyou.bean.CommentBean;
import laoyou.com.laoyou.bean.HeartBean;
import laoyou.com.laoyou.listener.HeartValueAndCommentListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.HeartValueAndCommentPresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class CommentFragment extends BaseFragment implements SpringListener, HeartValueAndCommentListener, AdapterView.OnItemClickListener {
    private static final String TAG = getTAG(CommentFragment.class);
    private SpringView springView;
    private int tag;
    //1心动值，0评论；
    private int flag;
    private ListView listView;
    private MyCommentAdapter cadapter;
    private MyHeartValueAdapter hadapter;
    private HeartValueAndCommentPresenter hcp;
    private List<HeartBean> heartsList;
    private List<CommentBean> commentList;
    private boolean isRefresh;

    public static CommentFragment setTag(int tag, int flag) {
        CommentFragment f = new CommentFragment();
        Bundle args = new Bundle();
        args.putString("Tag", String.valueOf(tag));
        args.putString("Flag", String.valueOf(flag));
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = Integer.parseInt(getArguments() != null ? getArguments().getString("Tag") : "0");
        flag = Integer.parseInt(getArguments() != null ? getArguments().getString("Flag") : "0");
    }

    @Override
    protected void click() {
        listView.setOnItemClickListener(this);
    }

    private void getData() {
        switch (flag) {
            case 0:
                if (tag == 0)
                    hcp.GetMyReceiverdChatMsg(true);
                else
                    hcp.GetMyChatMsg(true);
                break;
            case 1:
                springView.setEnable(false);
                if (tag == 0)
                    hcp.GetMyHeartNumber(false);
                else
                    hcp.GetMyHeartNumber(true);
                break;

        }

    }

    @Override
    protected int initContentView() {
        return R.layout.comment_fragment;
    }

    protected void init() {
        listView = f(R.id.listView);
        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);
        hcp = new HeartValueAndCommentPresenter(this);
        heartsList = new ArrayList<>();
        commentList = new ArrayList<>();
        switch (flag) {
            case 0:
                cadapter = new MyCommentAdapter(getActivity(), commentList, tag, this);
                listView.setAdapter(cadapter);
                break;
            case 1:
                hadapter = new MyHeartValueAdapter(getActivity(), heartsList);
                listView.setAdapter(hadapter);
                break;
        }
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void IsonRefresh(int init) {
        switch (flag) {
            case 0:
                hcp.page = 0;
                if (tag == 0)
                    hcp.GetMyReceiverdChatMsg(true);
                else
                    hcp.GetMyChatMsg(true);
                break;
            case 1:
                break;
        }
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (flag) {
            case 0:
                hcp.page += 20;
                if (tag == 0)
                    hcp.GetMyReceiverdChatMsg(false);
                else
                    hcp.GetMyChatMsg(false);
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onFailedsMsg(String msg) {
        ToastUtil.toast2_bottom(getActivity(), msg);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onHeartData(List<HeartBean> hb) {
        heartsList.clear();
        for (HeartBean h : hb) {
            heartsList.add(h);
        }
        hadapter.notifyDataSetChanged();
    }

    @Override
    public void onCommentData(List<CommentBean> comments) {
        if (isRefresh)
            commentList.clear();

        for (CommentBean h : comments) {
            commentList.add(h);
        }
        cadapter.notifyDataSetChanged();
    }

    @Override
    public void GoHomePage(String userId) {
        goHomePage(getActivity(), userId, false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (flag == 0) {
            goTopicCommentDetailsPage(getActivity(), commentList.get(position).getChatThemeId(), commentList.get(position).getUserId(), "", "");
        } else
            goHomePage(getActivity(), heartsList.get(position).getId(), false);
    }
}
