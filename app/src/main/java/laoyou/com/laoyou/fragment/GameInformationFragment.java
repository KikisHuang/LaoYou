package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.GameInformationAdapter;
import laoyou.com.laoyou.bean.GameInfoBean;
import laoyou.com.laoyou.bean.GameTypeBean;
import laoyou.com.laoyou.listener.GameInformationListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.GameInformationPresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class GameInformationFragment extends BaseFragment implements SpringListener, GameInformationListener {

    private static final String TAG = getTAG(GameInformationFragment.class);

    private SpringView springView;
    private int tag;
    private ListView listView;
    private GameInformationAdapter adapter;

    private List<GameInfoBean> list;
    private GameInformationPresenter gp;
    private String id;

    public static GameInformationFragment setTag(int tag, String id) {
        GameInformationFragment f = new GameInformationFragment();
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

    }


    @Override
    protected int initContentView() {
        return R.layout.game_information_fragment;
    }

    protected void init() {
        listView = f(R.id.listView);
        gp = new GameInformationPresenter(this);

        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);

        list = new ArrayList<>();

        list.add(null);
        list.add(null);
        list.add(null);

        adapter = new GameInformationAdapter(getActivity(), list);
        listView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        gp.getGameInfo(id);
    }

    @Override
    public void IsonRefresh(int init) {
        switch (tag) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (tag) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFailedMsg(String str) {
        ToastUtil.toast2_bottom(getActivity(), str);
    }

    @Override
    public void onGameTypeInforMation(List<GameTypeBean> list) {

    }
}
