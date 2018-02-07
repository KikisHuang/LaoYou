package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

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

import static laoyou.com.laoyou.utils.IntentUtils.goGameInfoDetailsPage;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class GameInformationFragment extends BaseFragment implements SpringListener, AdapterView.OnItemClickListener, GameInformationListener {

    private static final String TAG = getTAG(GameInformationFragment.class);

    private SpringView springView;
    private int tag;
    private ListView listView;
    private GameInformationAdapter adapter;

    private GameInformationPresenter gp;
    private String id;
    private List<GameInfoBean> list;

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
        listView.setOnItemClickListener(this);
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

    }

    @Override
    protected void initData() {
        gp.page = 0;
        gp.getGameInfo(id, true);
    }

    @Override
    public void IsonRefresh(int init) {
        gp.page = init;
        gp.getGameInfo(id, true);
    }

    @Override
    public void IsonLoadmore(int move) {
        gp.page += move;
        gp.getGameInfo(id, false);
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

    @Override
    public void onGameInfor(List<GameInfoBean> li) {
        list = li;
        if (adapter == null) {
            adapter = new GameInformationAdapter(getActivity(), li);
            listView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goGameInfoDetailsPage(getActivity(), list.get(position).getId(),list.get(position).getLogo());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(view);
    }

    /**
     * 清除fragment中的view，防止内存溢出;
     *
     * @param view
     */
    private void unbindDrawables(View view) {
        Log.i(TAG, "unbindDrawables");
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
