package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.FlashAdapter;
import laoyou.com.laoyou.bean.GroupBean;
import laoyou.com.laoyou.listener.FlashChatListener;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.FlashChatPresenter;
import laoyou.com.laoyou.tencent.ui.ChatActivity;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.SpringUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goCreateFlashChatPage;
import static laoyou.com.laoyou.utils.IntentUtils.goNearbyFlashChatPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatActivity extends InitActivity implements FlashChatListener, SpringListener, View.OnClickListener, TIMCallBack {
    private static final String TAG = "FlashChatActivity";
    private ListView listView;
    private LinearLayout head_view;
    private TextView flash_num_tv;
    private ImageView create_flash_group_img;
    private FlashChatPresenter fp;
    private FlashAdapter adapter;
    private List<GroupBean.GroupInfo> list;
    private SpringView springview;
    private LinearLayout nearby_layout;
    private String identify = "";


    @Override
    protected void click() {
        nearby_layout.setOnClickListener(this);
        create_flash_group_img.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                identify = list.get(position - 1).getGroupId();
                GroupManagerPresenter.applyJoinGroup(identify, "", FlashChatActivity.this);

            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.flash_chat_layout);
        setTitlesAndBack(this, gets(R.string.discover), "");
        list = new ArrayList<>();
        listView = f(R.id.listView);
        springview = f(R.id.springview);
        SpringUtils.SpringViewInit(springview, this, this);
        head_view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.flash_head_layout, null);
        flash_num_tv = (TextView) head_view.findViewById(R.id.flash_num_tv);
        nearby_layout = (LinearLayout) head_view.findViewById(R.id.nearby_layout);
        create_flash_group_img = (ImageView) head_view.findViewById(R.id.create_flash_group_img);

        //设置HeaderView不可点击
        listView.addHeaderView(head_view, null, false);

//        listView.addHeaderView(head_view);
        adapter = new FlashAdapter(this, list);
        listView.setAdapter(adapter);
        fp = new FlashChatPresenter(this);
    }


    @Override
    protected void initData() {

    }

    @Override
    public void IsonRefresh(int init) {
        fp.getData(true);
    }

    @Override
    public void IsonLoadmore(int move) {
        fp.getData(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_flash_group_img:
                goCreateFlashChatPage(this);
                break;
            case R.id.nearby_layout:
                goNearbyFlashChatPage(this);
                break;
        }
    }

    @Override
    public void onSucceed(List<GroupBean> result) {
        list.clear();
        List<GroupBean.GroupInfo> info = result.get(0).getGroupInfo();
        for (GroupBean.GroupInfo gi : info) {
            if (gi.getGroupId().indexOf(Fields.OFFICIAL) != -1 && gi.getGroupId().indexOf(Fields.TGS) == -1)
                list.add(gi);
        }
//        int NearbyNum = result.get(0).getGroupInfo().size() - list.size();
//        flash_num_tv.setText("附近有" + NearbyNum + "个闪聊");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Fields.ACRESULET3) {
            if (!identify.isEmpty())
                ChatActivity.navToChat(FlashChatActivity.this, identify, TIMConversationType.Group);
        }
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
                ChatActivity.navToChat(FlashChatActivity.this, identify, TIMConversationType.Group);
        } else
            ToastUtil.toast2_bottom(this, s);
    }

    @Override
    public void onSuccess() {
        Log.i(TAG, getString(R.string.apply_for_success));
        if (!identify.isEmpty())
            ChatActivity.navToChat(FlashChatActivity.this, identify, TIMConversationType.Group);
    }
}
