package laoyou.com.laoyou.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.NearbyAdapter;
import laoyou.com.laoyou.adapter.TopicTypeDetailsAdapter;
import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.listener.FindSonListener;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.FindSonPresenter;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.IntentUtils.goTopicCommentDetailsPage;
import static laoyou.com.laoyou.utils.IntentUtils.goVideoPlayerPage;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class FindSonFragment extends BaseFragment implements SpringListener, FindSonListener, RecyclerViewOnItemClickListener {
    private static final String TAG = getTAG(FindSonFragment.class);
    private SpringView springView;
    //0新奇事,1附近;
    private int tag;
    private TopicTypeDetailsAdapter hadapter;
    private NearbyAdapter nearbyAdapter;

    private List<NearbyBean> list;
    private List<TopicTypeBean> topics;
    private RecyclerView recyclerView;
    private FindSonPresenter fp;
    private boolean isRefresh;
    private static List<FindSonFragment> fragments = new ArrayList<>();
    private int sex = 99;

    public static FindSonFragment setTag(int tag) {
        FindSonFragment f = new FindSonFragment();
        fragments.add(f);
        Bundle args = new Bundle();
        args.putString("Tag", String.valueOf(tag));
        f.setArguments(args);
        return f;
    }

    public static List<FindSonFragment> getInstances() {
        if (fragments != null && fragments.size() > 0)
            return fragments;
        else
            return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = Integer.parseInt(getArguments() != null ? getArguments().getString("Tag") : "0");
    }

    @Override
    protected void click() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (tag == 0) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            fp.page = totalItemCount;
            isRefresh = true;
            fp.getHottestAndNewestRefresh();
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.find_son_fragment;
    }

    protected void init() {
        recyclerView = f(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fp = new FindSonPresenter(this);

        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);

        list = new ArrayList<>();
        topics = new ArrayList<>();
        switch (tag) {
            case 0:
                hadapter = new TopicTypeDetailsAdapter(getActivity(), topics, this);
                recyclerView.setAdapter(hadapter);
//              fp.getNewIncident(true);
                break;
            case 1:
                nearbyAdapter = new NearbyAdapter(getActivity(), list, this);
                recyclerView.setAdapter(nearbyAdapter);
                fp.getNearbyData(true, sex);
                break;
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void IsonRefresh(int init) {
        switch (tag) {
            case 0:
                fp.page = init;
                isRefresh = true;
                fp.getNewIncident(true);
                break;
            case 1:
                fp.page = init;
                isRefresh = true;
                fp.getNearbyData(true, sex);
                break;
        }
    }

    public void ScreenSexInfo(int s) {
        sex = s;
        isRefresh = true;
        fp.getNearbyData(true, sex);
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (tag) {
            case 0:
                fp.page = topics.size();
                isRefresh = false;
                fp.getNewIncident(false);
                break;
            case 1:
                fp.page = list.size();
                isRefresh = false;
                fp.getNearbyData(false, sex);
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
    public void RefreshNearby(List<NearbyBean> nblist) {
        if (isRefresh)
            list.clear();

        for (NearbyBean nb : nblist) {
            if (nb.getCloud_tencent_account() != null && !nb.getCloud_tencent_account().isEmpty())
                list.add(nb);
        }
        nearbyAdapter.notifyDataSetChanged();
    }

    @Override
    public void RefreshNewWonders(List<TopicTypeBean> toppic) {
        if (isRefresh)
            topics.clear();

        for (TopicTypeBean nb : toppic) {
            topics.add(nb);
        }
        hadapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        fp.getHottestAndNewestRefresh();
    }


    @Override
    public void onNotLatiLongTude() {

        LocationManager lm = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 没有权限，申请权限。
                Log.i(TAG,"无定位权限");

            } else {
                // 有权限
                Log.i(TAG,"有定位权限");
            }

        } else {
            Toast.makeText(getActivity(), "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }


    @Override
    public void RcOnItemClick(int pos, List<String> list) {
        goPhotoViewerPage(getActivity(), list, pos, 1);
    }

    @Override
    public void LikeClick(String id) {
        Show(getActivity(), "", true, null);
        fp.LikeChatTheme(id);
    }

    @Override
    public void GoPageHome(String userId) {
        goHomePage(getActivity(), userId, false);
    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {
        goTopicCommentDetailsPage(getActivity(), id, userId, name, content);
    }

    @Override
    public void GoVideoPage(String url, String videoCover) {
        goVideoPlayerPage(getActivity(), url, videoCover);
    }
}
