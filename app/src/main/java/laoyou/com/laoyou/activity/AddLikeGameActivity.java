package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.AddLikeGameAdapter;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.AddLikeGameListener;
import laoyou.com.laoyou.presenter.AddLikeGamePresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.SynUtils.IsListViewTopOfBottom;

/**
 * Created by lian on 2017/12/9.
 */
public class AddLikeGameActivity extends InitActivity implements AddLikeGameListener, View.OnClickListener, AbsListView.OnScrollListener {

    private static final String TAG = "AddLikeGameActivity";
    private ListView listView;
    private AddLikeGamePresenter ap;
    private AddLikeGameAdapter adapter;
    private List<GameBean> list;
    private int tag;
    private RippleView commit_bt;
    private LinearLayout foot_layout;
    private boolean isRefresh = true;
    private GameBean games;

    @Override
    protected void click() {
        commit_bt.setOnClickListener(this);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tag == 1) {
                    games = list.get(position);
                    Show(AddLikeGameActivity.this, "", true, null);
                    ap.CheckGames(games);
                } else
                    onScreenGame(list.get(position).getId());

            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.add_like_game_layout);
        listView = f(R.id.listView);
        commit_bt = f(R.id.commit_bt);
        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.foot_include, null);

        ap = new AddLikeGamePresenter(this);
        list = new ArrayList<>();
        tag = Integer.parseInt(getIntent().getStringExtra("add_like_game_tag"));
        if (tag == 0)
            commit_bt.setVisibility(View.VISIBLE);

        ap.getGameListData();
        foot_layout.setVisibility(View.GONE);
        listView.addFooterView(foot_layout);

        adapter = new AddLikeGameAdapter(this, list, tag);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSucceed() {
        Cancle();
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
        Cancle();
    }

    @Override
    public void onScreenGame(String id) {

        for (GameBean gb : list) {
            if (gb.getId().equals(id)) {
                gb.setSlector(!gb.isSlector());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGamesInfo(List<GameBean> games) {
        if (isRefresh)
            list.clear();
        if (!isRefresh && games.size() <= 0)
            foot_layout.setVisibility(View.VISIBLE);

        for (GameBean gb : games) {
            list.add(gb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddLikeGames() {
        if (tag == 0) {
            Intent inten = new Intent();
            setResult(RESULT_OK, inten);
            finish();
        }
        if (games != null) {
            Intent inten = new Intent();
            inten.putExtra("like_game_bean", games);
            setResult(RESULT_OK, inten);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_bt:
                if (list.size() > 0) {
                    Show(AddLikeGameActivity.this, "提交中", true, null);
                    ap.ChangeLikeGameList(list);
                }
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (IsListViewTopOfBottom(firstVisibleItem, visibleItemCount, totalItemCount, listView) == Fields.IsBottom) {
                isRefresh = false;
                ap.page += 10;
                ap.getGameListData();
        }
    }
}
