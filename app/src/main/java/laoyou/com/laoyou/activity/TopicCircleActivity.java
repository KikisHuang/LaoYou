package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicCircleAdapter;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.listener.TopicCircleListener;
import laoyou.com.laoyou.presenter.TopicCirclePresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goTopicTypeDetailsPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/18.
 */
public class TopicCircleActivity extends InitActivity implements TopicCircleListener, SpringListener, AdapterView.OnItemClickListener {

    private static final String TAG = "TopicCircleActivity";
    private ListView listView;
    private TopicCirclePresenter tp;
    private FrameLayout head;
    private TopicCircleAdapter adapter;
    private List<TopicBean> list;
    private SpringView springView;

    @Override
    protected void click() {
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.topic_circle_layout);
        listView = f(R.id.listView);
        springView = f(R.id.springView);
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
        list = s;
        if (adapter == null) {
            adapter = new TopicCircleAdapter(this, s);
            listView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onHeadViewShwoOfHide(boolean b) {
        tp.getTopicDataList(true);
        if (b)
            if (listView.getHeaderViewsCount() == 0)
                listView.addHeaderView(head);

    }

    @Override
    public void IsonRefresh(int init) {
        list.clear();
//        tp.getMyFollowChatType();
        tp.page = init;
        tp.getTopicDataList(true);
    }

    @Override
    public void IsonLoadmore(int move) {
//        tp.getMyFollowChatType();
        tp.page += move;
        tp.getTopicDataList(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //判断是否有头部;
        if (listView.getHeaderViewsCount() == 0)
            goTopicTypeDetailsPage(TopicCircleActivity.this, list.get(position).getId(), list.get(position).getName(), list.get(position).getFollowCount(), list.get(position).getChatThemeCount(), list.get(position).getImgUrl());
        else if (position == 0) {

        } else
            goTopicTypeDetailsPage(TopicCircleActivity.this, list.get(position - 1).getId(), list.get(position - 1).getName(), list.get(position - 1).getFollowCount(), list.get(position - 1).getChatThemeCount(), list.get(position - 1).getImgUrl());

    }
}
