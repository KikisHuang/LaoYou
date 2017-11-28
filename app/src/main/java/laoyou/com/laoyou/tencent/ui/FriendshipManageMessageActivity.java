package laoyou.com.laoyou.tencent.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMFutureFriendType;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.tencent.adapters.FriendManageMessageAdapter;
import laoyou.com.laoyou.tencent.model.FriendFuture;

public class FriendshipManageMessageActivity extends Activity implements FriendshipMessageView {


    private final String TAG = FriendshipManageMessageActivity.class.getSimpleName();
    private static FriendshipManagerPresenter presenter;
    private ListView listView;
    private List<FriendFuture> list = new ArrayList<>();
    private FriendManageMessageAdapter adapter;
    private final int FRIENDSHIP_REQ = 100;
    private int index;
    private LinearLayout close_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship_manage_message);
        listView = (ListView) findViewById(R.id.list);
        close_layout = (LinearLayout) findViewById(R.id.close_layout);
        adapter = new FriendManageMessageAdapter(this, R.layout.item_two_line, list);
        listView.setAdapter(adapter);
        close_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (list.get(position).getType() == TIMFutureFriendType.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE) {
                    index = position;
                    Intent intent = new Intent(FriendshipManageMessageActivity.this, FriendshipHandleActivity.class);
                    intent.putExtra("id", list.get(position).getIdentify());
                    intent.putExtra("word", list.get(position).getMessage());
                    intent.putExtra("face_url", list.get(position).getFaceUrl());
                    startActivityForResult(intent, FRIENDSHIP_REQ);
                }

            }
        });
        presenter = new FriendshipManagerPresenter(this);
        presenter.getFriendshipMessage();
    }

    public static FriendshipManagerPresenter getFriendPresenter() {
        return presenter;
    }

    /**
     * 获取好友关系链管理最后一条系统消息的回调
     *
     * @param message     最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {

    }

    /**
     * 获取好友关系链管理最后一条系统消息的回调
     *
     * @param message 消息列表
     */
    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        list.clear();
        if (message != null && message.size() != 0) {
            for (TIMFriendFutureItem item : message) {
                list.add(new FriendFuture(item));
            }
            presenter.readFriendshipMessage(message.get(0).getAddTime());
        }
        List<String> id = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            id.add(list.get(i).getIdentify());
            Log.i(TAG, "FaceUrl Size ===" + list.get(i).getFaceUrl());
        }

        presenter.getUserProFile(id);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSystemInfo(List<TIMUserProfile> result) {

        if (list.size() == result.size()) {
            for (int i = 0; i < list.size(); i++) {
                for (TIMUserProfile tpf : result) {
                    if (list.get(i).getIdentify().equals(tpf.getIdentifier())) {
                        list.get(i).setFaceUrl(tpf.getFaceUrl());
                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FRIENDSHIP_REQ) {
            if (resultCode == RESULT_OK) {
                if (index >= 0 && index < list.size()) {
                    boolean isAccept = data.getBooleanExtra("operate", true);
                    if (isAccept) {
                        list.get(index).setType(TIMFutureFriendType.TIM_FUTURE_FRIEND_DECIDE_TYPE);
                    } else {
                        list.remove(index);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }
}
