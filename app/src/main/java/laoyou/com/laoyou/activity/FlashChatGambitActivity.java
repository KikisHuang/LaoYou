package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.FlashChatGambitAdapter;
import laoyou.com.laoyou.bean.FlashTypeIconBean;
import laoyou.com.laoyou.listener.FlashChatGambitListener;
import laoyou.com.laoyou.presenter.FlashChatGambitPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.IntentUtils.goFlashChatMemberPage;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatGambitActivity extends InitActivity implements FlashChatGambitListener {
    private static final String TAG = "FlashChatGambitActivity";
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private FlashChatGambitPresenter fp;
    private FlashChatGambitAdapter adapter;
    private List<FlashTypeIconBean> list;
    private String GroupName;
    private String GroupInfo;
    private RelativeLayout titles_layout;

    @Override
    protected void click() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.flash_chat_gambit_layout);
        ActivityCollector.addActivity(this, getClass());
        recyclerView = f(R.id.recyclerView);
        setTitlesAndBack(this, "", "");
        titles_layout = f(R.id.titles_layout);
        titles_layout.setBackgroundResource(R.color.background_color);
        list = new ArrayList<>();
        //使用不规则的网格布局
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);//3列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new FlashChatGambitAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
        fp = new FlashChatGambitPresenter(this);
        GroupName = getIntent().getStringExtra("basics_name");
        GroupInfo = getIntent().getStringExtra("basics_info");
    }

    @Override
    protected void initData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if (list.size() > 0)
            goFlashChatMemberPage(this, GroupName, GroupInfo, list.get(position).getUrl());
    }

    @Override
    public void onIconInfo(List<FlashTypeIconBean> ar) {
        list.clear();
        for (FlashTypeIconBean ftib : ar) {
            list.add(ftib);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Fields.ACRESULET3) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
