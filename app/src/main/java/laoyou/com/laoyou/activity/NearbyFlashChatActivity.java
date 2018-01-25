package laoyou.com.laoyou.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.FlashAdapter;
import laoyou.com.laoyou.bean.GroupBean;
import laoyou.com.laoyou.listener.NearbyFlashChatListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.NearbyFlashChatPresenter;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.tencent.ui.ChatActivity;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class NearbyFlashChatActivity extends InitActivity implements SpringListener, NearbyFlashChatListener, TIMCallBack {

    private static final String TAG = "NearbyFlashChatActivity";
    private ListView listView;
    private LinearLayout head_view;
    private SpringView springview;
    private NearbyFlashChatPresenter np;
    private FlashAdapter adapter;
    private List<GroupBean.GroupInfo> list;
    private String identify = "";

    @Override
    protected void click() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                identify = list.get(position - 1).getGroupId();
                GroupManagerPresenter.applyJoinGroup(identify, "", NearbyFlashChatActivity.this);
            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.nearby_lash_chat_layout);
        setTitlesAndBack(this, gets(R.string.goback), "");
        listView = f(R.id.listView);
        list = new ArrayList<>();
        springview = f(R.id.springview);
        head_view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nearby_flash_head_layout, null);
        adapter = new FlashAdapter(this, list);
        listView.addHeaderView(head_view);
        listView.setAdapter(adapter);
        SpringUtils.SpringViewInit(springview, this, this);


        np = new NearbyFlashChatPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void IsonRefresh(int init) {
        np.getData(true);
    }

    @Override
    public void IsonLoadmore(int move) {
        np.getData(false);
    }

    @Override
    public void onSucceed(List<GroupBean> result) {
        list.clear();
        List<GroupBean.GroupInfo> info = result.get(0).getGroupInfo();
        for (GroupBean.GroupInfo gi : info) {
            if (gi.getGroupId().indexOf(Fields.TGS) != -1 && gi.getType().equals(GroupInfo.publicGroup))
                list.add(gi);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }


    @Override
    public void onError(int i, String s) {
        if (i == 10013) {
            //已经是群成员
            Log.i(TAG, getString(R.string.group_member_already));
            if (!identify.isEmpty())
                ChatActivity.navToChat(NearbyFlashChatActivity.this, identify, TIMConversationType.Group);
        } else
            ToastUtil.toast2_bottom(this, s);
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, getString(R.string.apply_for_success));
        if (!identify.isEmpty()) {
            Show(NearbyFlashChatActivity.this, "加入中...", true, null);
            listView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Cancle();
                    ChatActivity.navToChat(NearbyFlashChatActivity.this, identify, TIMConversationType.Group);
                }
            }, 1000);
        }


    }
}
