package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicCircletempAdapter;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.MyNoticeListener;
import laoyou.com.laoyou.presenter.MyNoticePresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goTopicTypeDetailsPage;

/**
 * Created by lian on 2017/12/23.
 */
public class MyNoticeActivity extends InitActivity implements MyNoticeListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MyNoticeActivity";
    private ListView listView;
    private MyNoticePresenter mp;
    private List<TopicBean> list;
    private TopicCircletempAdapter adapter;


    @Override
    protected void click() {
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.my_notice_layout);
        ActivityCollector.addActivity(this, getClass());
        listView = f(R.id.listView);
        list = new ArrayList<>();
        mp = new MyNoticePresenter(this);
        adapter = new TopicCircletempAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onFailedsMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onTopicTypeList(List<TopicBean> ar) {
        for (TopicBean tb : ar) {
            list.add(tb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goTopicTypeDetailsPage(MyNoticeActivity.this, list.get(position).getId(), list.get(position).getName(), list.get(position).getFollowCount(), list.get(position).getChatThemeCount(), list.get(position).getImgUrl());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
