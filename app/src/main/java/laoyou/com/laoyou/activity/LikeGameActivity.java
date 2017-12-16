package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import static laoyou.com.laoyou.utils.IntentUtils.goAddLikeGamePage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/9.
 */
public class LikeGameActivity extends InitActivity implements LikeListener, View.OnClickListener, LikeGameListener {

    private static final String TAG = "LikeGameActivity";
    private ListView listView;
    private LikeGameAdapter adapter;
    private List<GameBean> list;
    private ImageView add_game_img;
    private LikeGamePresenter lp;


    @Override
    protected void click() {
        add_game_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.like_game_layout);
        setTitlesAndBack(this, gets(R.string.goback), "");
        listView = f(R.id.listView);
        add_game_img = f(R.id.add_game_img);
        lp = new LikeGamePresenter(this);
        list = new ArrayList<>();
        adapter = new LikeGameAdapter(this, list, this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFailedMsg(String msg) {


    }

    @Override
    public void onDeleteGame(int pos) {
        list.remove(pos);
        adapter.notifyDataSetChanged();
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
                case Fields.ACRESULET1:
                    Bundle bundle = data.getExtras();
                    list.add((GameBean) bundle.getSerializable("like_game_bean"));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onFailedsMSg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }
}
