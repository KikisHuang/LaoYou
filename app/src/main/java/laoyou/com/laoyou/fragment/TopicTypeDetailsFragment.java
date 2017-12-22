package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import com.liaoinstan.springview.widget.SpringView;

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
import laoyou.com.laoyou.view.FullyLinearLayoutManager;

import static laoyou.com.laoyou.activity.TopicTypeDetailsActivity.getTopicTypeInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
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
    private FullyLinearLayoutManager mLayoutManager;
    private String id;
    private boolean Refresh;

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstVisiblePosition == 0)
                        getTopicTypeInstance().getAppBar().setExpanded(true, true);

                    if (isSlideToBottom(recyclerView) || (firstVisiblePosition == 0 && Refresh))
                        springView.setEnable(true);
                    else
                        springView.setEnable(false);

                }

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
//        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new FullyLinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);
        tp = new TopicTypeDetailsPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getTopicTypeInstance() != null)
            tp.setAppBarLayoutStateChangeListener(getTopicTypeInstance().getAppBar());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getTopicTypeInstance() != null)
            tp.removeAppBarLayoutStateChangeListener(getTopicTypeInstance().getAppBar());
    }

    @Override
    protected void initData() {
        switch (tag) {
            case 0:
                tp.getHottestAndNewestData(id, true, 1, 0);
                break;
            case 1:
                tp.getHottestAndNewestData(id, true, 0, 0);
                break;
        }
    }

    @Override
    public void IsonRefresh(int init) {

        switch (tag) {
            case 0:
                tp.getHottestAndNewestData(id, true, 1, init);
                break;
            case 1:
                tp.getHottestAndNewestData(id, true, 0, init);
                break;
        }
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (tag) {
            case 0:
                tp.getHottestAndNewestData(id, false, 1, move);
                break;
            case 1:
                tp.getHottestAndNewestData(id, false, 0, move);
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
        if (adapter == null) {
            adapter = new TopicTypeDetailsAdapter(getActivity(), s, this);
            recyclerView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onStateChange(AppBarLayout appBarLayout, AppBarStateChangeListener.State state, int i) {

        if (i >= 0)
            Refresh = true;
        else
            Refresh = false;


        /*
        switch (state) {
            //展开状态;
            case EXPANDED:
                break;
            //折叠状态;
            case COLLAPSED:
                break;
            //中间状态;
            default:
                break;
        }*/
    }

    /**
     * 是否还可以上拉
     *
     * @return true可以，false不可以
     */
    public boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return recyclerView.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(recyclerView, 1);
        }
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
    public void RcOnItemClick(int pos) {

    }

    @Override
    public void LikeClick(String id) {
        Show(getActivity(), "", true, null);
        tp.LikeChatTheme(id);
    }

    @Override
    public void GoPageHome(String userId) {
        goHomePage(getActivity(), userId);
    }

    @Override
    public void GoCommentPage(String id, String userId, String name,String content) {
        goTopicCommentDetailsPage(getActivity(), id, userId, name,content);
    }
}
