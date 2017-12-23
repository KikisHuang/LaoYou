package laoyou.com.laoyou.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.UserListAdapter;
import laoyou.com.laoyou.bean.LikeListBean;
import laoyou.com.laoyou.listener.ParticipationListener;
import laoyou.com.laoyou.presenter.ParticipationPresenter;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;

/**
 * Created by lian on 2017/12/21.
 */
public class ParticipationActivity extends InitActivity implements ParticipationListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private static final String TAG = "ParticipationActivity";
    private ListView listView;
    //1、参与人列表 2、点赞列表;
    private int Tag;
    private ParticipationPresenter pip;
    private String id;
    private List<LikeListBean> likelist;
    private UserListAdapter adapter;
    private TextView no_more_tv;
    private LinearLayout foot_layout;

    @Override
    protected void click() {
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.participation_layout);
        listView = f(R.id.listView);

        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.no_more_include, null);
        no_more_tv = (TextView) foot_layout.findViewById(R.id.no_more_tv);

        pip = new ParticipationPresenter(this);
        likelist = new ArrayList<>();
        Tag = Integer.parseInt(getIntent().getStringExtra("List_Tag"));
        id = getIntent().getStringExtra("List_id");
        adapter = new UserListAdapter(this, likelist, Tag);
        listView.addFooterView(foot_layout);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        switch (Tag) {
            case 1:
                pip.getActiveUserData(id);
                break;
            case 2:
                pip.getLikeList(id);
                break;
        }

    }

    @Override
    public void onFailedsMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void LikeListData(List<LikeListBean> li) {
        for (LikeListBean like : li) {
            likelist.add(like);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void NoData() {
        no_more_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (likelist.size() > 0) {
            goHomePage(this, likelist.get(position).getId());
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 当不滚动时
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1 && no_more_tv.getVisibility() == View.GONE) {
                pip.page += 20;
                switch (Tag) {
                    case 1:
                        pip.getActiveUserData(id);
                        break;
                    case 2:
                        pip.getLikeList(id);
                        break;
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
