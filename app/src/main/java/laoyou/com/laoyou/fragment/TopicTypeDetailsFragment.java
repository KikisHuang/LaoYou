package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicTypeDetailsAdapter;
import laoyou.com.laoyou.bean.ActiveUserBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.AppBarStateChangeListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.listener.TopicTypeDetailsListener;
import laoyou.com.laoyou.presenter.TopicTypeDetailsPresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.activity.TopicTypeDetailsActivity.getTopicTypeInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class TopicTypeDetailsFragment extends BaseFragment implements SpringListener, TopicTypeDetailsListener, RecyclerViewOnItemClickListener {

    private static final String TAG = getTAG(TopicTypeDetailsFragment.class);

    private SpringView springView;
    private int tag;
    private TopicTypeDetailsAdapter adapter;
    private TopicTypeDetailsPresenter tp;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private String id;
    private boolean Refresh;
    private int LSize = 0;
    private LinearLayout foot_layout;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private TextView foot_tv;

    public static TopicTypeDetailsFragment setTag(int tag, String id) {
        TopicTypeDetailsFragment f = new TopicTypeDetailsFragment();
        Bundle args = new Bundle();
        args.putString("Tag", String.valueOf(tag));
        args.putString("Id", String.valueOf(id));
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = Integer.parseInt(getArguments() != null ? getArguments().getString("Tag") : "0");
        id = getArguments() != null ? getArguments().getString("Id") : "";
    }

    @Override
    protected void click() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && LSize >= 10) {
                    int firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstVisiblePosition == 0)
                        getTopicTypeInstance().getAppBar().setExpanded(true, true);

                    if (isSlideToBottom(recyclerView) || (firstVisiblePosition == 0 && Refresh))
                        springView.setEnable(true);
                    else
                        springView.setEnable(false);

                } else
                    springView.setEnable(false);

            }
        });
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    @Override
    protected int initContentView() {
        return R.layout.topic_type_fragment;
    }

    protected void init() {
        recyclerView = f(R.id.recyclerView);
//      recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        springView = f(R.id.springView);
        foot_layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.foot_include, null);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        foot_layout.setLayoutParams(lps);
        foot_tv = (TextView) foot_layout.findViewById(R.id.foot_tv);
        foot_tv.setVisibility(View.INVISIBLE);

        SpringUtils.SpringViewInit(springView, getActivity(), this);
        tp = new TopicTypeDetailsPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if (getTopicTypeInstance() != null)
            tp.setAppBarLayoutStateChangeListener(getTopicTypeInstance().getAppBar());
        this.onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getTopicTypeInstance() != null)
            tp.removeAppBarLayoutStateChangeListener(getTopicTypeInstance().getAppBar());

//        tp.CloseAsynck();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void IsonRefresh(int init) {
        switch (tag) {
            case 0:
                tp.getHottestAndNewestData(id, true, 0, init);
                break;
            case 1:
                tp.getHottestAndNewestData(id, true, 1, init);
                break;
        }

    }

    @Override
    public void IsonLoadmore(int move) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();

        switch (tag) {
            case 0:
                tp.getHottestAndNewestData(id, false, 0, totalItemCount);
                break;
            case 1:
                tp.getHottestAndNewestData(id, false, 1, totalItemCount);
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
    public void onShowDetailsInfo(List<TopicTypeBean> s) {
            LSize = s.size();
            if (s.size() < 10) {
                springView.setEnable(false);
                foot_tv.setVisibility(View.VISIBLE);
            } else {
                springView.setEnable(true);
                foot_tv.setVisibility(View.INVISIBLE);
            }

            if (adapter == null) {
                adapter = new TopicTypeDetailsAdapter(getActivity(), s, this);
                mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

                mHeaderAndFooterWrapper.addFootView(foot_layout);
                recyclerView.setAdapter(mHeaderAndFooterWrapper);
            } else
                mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onStateChange(AppBarLayout appBarLayout, AppBarStateChangeListener.State state, int i) {
        if (i >= 0)
            Refresh = true;
        else
            Refresh = false;
    }


    @Override
    public void onRefresh() {
        switch (tag) {
            case 0:
                tp.getHottestAndNewestRefresh(id, true, 1);
                break;
            case 1:
                tp.getHottestAndNewestRefresh(id, true, 0);
                break;
        }
    }

    @Override
    public void IsFollowChat(boolean b) {

    }

    @Override
    public void GetActiveUserData(List<ActiveUserBean> aub) {

    }


    @Override
    public void RcOnItemClick(int pos, List<String> list) {
        goPhotoViewerPage(getActivity(), list, pos, 1);
    }

    @Override
    public void LikeClick(String id) {
        Show(getActivity(), "", true, null);
        tp.LikeChatTheme(id);
    }

    @Override
    public void GoPageHome(String userId) {
        goHomePage(getActivity(), userId, false);
    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {
        goTopicCommentDetailsPage(getActivity(), id, userId, name, content);
    }

    @Override
    public void GoVideoPage(String url, String videoCover) {
        goVideoPlayerPage(getActivity(), url, videoCover);
    }
}
