package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.FindIncidentAdapter;
import laoyou.com.laoyou.adapter.NearbyAdapter;
import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.listener.FindSonListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.FindSonPresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class FindSonFragment extends BaseFragment implements SpringListener, FindSonListener {
    private static final String TAG = getTAG(FindSonFragment.class);
    private SpringView springView;
    private int tag;
    private ListView listView;
    private FindIncidentAdapter hadapter;
    private NearbyAdapter nearbyAdapter;

    private List<NearbyBean> list;
    private RecyclerView recyclerView;
    private FindSonPresenter fp;

    public static FindSonFragment setTag(int tag) {
        FindSonFragment f = new FindSonFragment();
        Bundle args = new Bundle();
        args.putString("Tag", String.valueOf(tag));
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = Integer.parseInt(getArguments() != null ? getArguments().getString("Tag") : "0");
    }

    @Override
    protected void click() {

    }

    private void getData() {
    }

    @Override
    protected int initContentView() {
        return R.layout.find_son_fragment;
    }

    protected void init() {
        listView = f(R.id.listView);
        recyclerView = f(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fp = new FindSonPresenter(this);

        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);

        list = new ArrayList<>();
        switch (tag) {
            case 0:
                list.add(null);
                list.add(null);
                list.add(null);

                hadapter = new FindIncidentAdapter(getActivity(), list);
                listView.setAdapter(hadapter);
                recyclerView.setVisibility(View.GONE);
                fp.getNewIncident();
                break;
            case 1:
                listView.setVisibility(View.GONE);
                fp.getNearbyData(true);
                break;
        }

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void IsonRefresh(int init) {
        switch (tag) {
            case 0:
                break;
            case 1:
                fp.getNearbyData(true);
                break;
        }
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (tag) {
            case 0:
                break;
            case 1:
                fp.getNearbyData(false);
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
    public void RefreshRecyclerView(List<NearbyBean> nblist) {

        list = nblist;
        if (nearbyAdapter == null) {
            nearbyAdapter = new NearbyAdapter(getActivity(), list);
            recyclerView.setAdapter(nearbyAdapter);
        } else
            nearbyAdapter.notifyDataSetChanged();

    }
}
