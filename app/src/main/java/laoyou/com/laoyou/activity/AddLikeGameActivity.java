package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.AddLikeGameAdapter;
import laoyou.com.laoyou.bean.GameBean;
import laoyou.com.laoyou.listener.AddLikeGameListener;
import laoyou.com.laoyou.presenter.AddLikeGamePresenter;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.RippleView;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/12/9.
 */
public class AddLikeGameActivity extends InitActivity implements AddLikeGameListener, View.OnClickListener {

    private static final String TAG = "AddLikeGameActivity";
    private ListView listView;
    private AddLikeGamePresenter ap;
    private AddLikeGameAdapter adapter;
    private List<GameBean> list;
    private int tag;
    private RippleView commit_bt;

    @Override
    protected void click() {
        commit_bt.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tag == 1) {
                    Intent inten = new Intent();
                    inten.putExtra("like_game_bean", list.get(position));
                    setResult(RESULT_OK, inten);
                    finish();
                } else {
                    onScreenGame(list.get(position).getId());
                }

            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.add_like_game_layout);
        listView = f(R.id.listView);
        commit_bt = f(R.id.commit_bt);
        ap = new AddLikeGamePresenter(this);
        list = new ArrayList<>();
        tag = Integer.parseInt(getIntent().getStringExtra("add_like_game_tag"));
        if (tag == 0)
            commit_bt.setVisibility(View.VISIBLE);
        GameBean gb = new GameBean();
        gb.setName("test");
        gb.setHeadUrl("123");
        gb.setId("test1");
        gb.setSlector(false);
        list.add(gb);
        GameBean gb1 = new GameBean();
        gb1.setName("test");
        gb1.setHeadUrl("123");
        gb1.setId("test2");
        gb1.setSlector(false);
        list.add(gb1);

        GameBean gb2 = new GameBean();
        gb2.setName("test");
        gb2.setHeadUrl("123");
        gb2.setId("test3");
        gb2.setSlector(false);
        list.add(gb2);

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
        ToastUtil.toast2_bottom(this,msg);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_bt:
                if(list.size()>0){
                    Show(AddLikeGameActivity.this, "提交中", true, null);
                    ap.ChangeLikeGameList(list);
                }
                break;
        }
    }
}
