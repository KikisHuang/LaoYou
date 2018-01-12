package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.LikeGameAdapter;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.LikeGameListener;
import laoyou.com.laoyou.listener.LikeListener;
import laoyou.com.laoyou.presenter.LikeGamePresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goAddLikeGamePage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/9.
 */
public class LikeGameActivity extends InitActivity implements LikeListener, View.OnClickListener, LikeGameListener, AbsListView.OnScrollListener {

    private static final String TAG = "LikeGameActivity";
    private ListView listView;
    private LikeGameAdapter adapter;
    private List<GameBean> list;
    private ImageView add_game_img;
    private LikeGamePresenter lp;
    private String id = "";
    private boolean isRefresh = true;
    private LinearLayout foot_layout;

    @Override
    protected void click() {
        add_game_img.setOnClickListener(this);
        listView.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.like_game_layout);
        setTitlesAndBack(this, gets(R.string.goback), "");
        listView = f(R.id.listView);
        foot_layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.foot_include, null);
        add_game_img = f(R.id.add_game_img);
        id = getIntent().getStringExtra("Like_games_id");
        lp = new LikeGamePresenter(this, id);
        list = new ArrayList<>();
        adapter = new LikeGameAdapter(this, list, this);
        foot_layout.setVisibility(View.GONE);
        listView.addFooterView(foot_layout);
        listView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        if (!id.isEmpty())
            add_game_img.setVisibility(View.GONE);

    }

    @Override
    public void onSucceed(String msg) {
//        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onFailedMsg(String msg) {


    }

    @Override
    public void onDeleteGame(int pos, String id) {
        if (this.id.isEmpty()) {
            lp.page = 0;
            isRefresh = true;
            Show(LikeGameActivity.this, "提交中", true, null);
            lp.FollowGameOfCancle(id);
//          list.remove(pos);
//          adapter.notifyDataSetChanged();
        } else
            ToastUtil.toast2_bottom(this, "不能删除他人数据..");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_game_img:
                goAddLikeGamePage(this, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Fields.ACRESULET5:
                    Bundle bundle = data.getExtras();
//                  list.add((GameBean) bundle.getSerializable("like_game_bean"));
                    lp.page = 0;
                    isRefresh = true;
                    String requestId = ((GameBean) bundle.getSerializable("like_game_bean")).getId();

                    lp.FollowGameOfCancle(requestId);

                    break;
            }
        }
    }

    @Override
    public void onFailedsMSg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onGamesInfo(List<GameBean> games) {
        if (isRefresh)
            list.clear();

        if (!isRefresh && games.size() <= 0) {
            foot_layout.setVisibility(View.VISIBLE);
            if (list.size() > 3)
                foot_layout.findViewById(R.id.foot_tv).setVisibility(View.VISIBLE);
            else
                foot_layout.findViewById(R.id.foot_tv).setVisibility(View.GONE);
        }


        for (GameBean gb : games) {
            list.add(gb);
        }
        adapter.notifyDataSetChanged();
        if (list.size() == 0)
            foot_layout.findViewById(R.id.foot_tv).setVisibility(View.GONE);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
       /* if (firstVisibleItem == 0) {
        }*/
        if (visibleItemCount + firstVisibleItem == totalItemCount) {
            if (foot_layout.getVisibility() == View.GONE) {
                isRefresh = false;
                lp.page += 20;
                lp.getMyLikeGameDataList(id);
            }
        }
    }
}
