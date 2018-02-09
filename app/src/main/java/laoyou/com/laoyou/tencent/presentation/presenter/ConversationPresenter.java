package laoyou.com.laoyou.tencent.presentation.presenter;

import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import laoyou.com.laoyou.tencent.presentation.event.FriendshipEvent;
import laoyou.com.laoyou.tencent.presentation.event.GroupEvent;
import laoyou.com.laoyou.tencent.presentation.event.MessageEvent;
import laoyou.com.laoyou.tencent.presentation.event.RefreshEvent;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.ConversationView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupInfoView;

/**
 * 会话界面逻辑
 */
public class ConversationPresenter implements Observer, TIMValueCallBack<List<TIMGroupDetailInfo>> {

    private static final String TAG = "ConversationPresenter";
    private ConversationView view;
    private GroupInfoView listener;

    public ConversationPresenter(ConversationView view, GroupInfoView listener) {
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        //注册刷新监听
        RefreshEvent.getInstance().addObserver(this);
        //注册好友关系链监听
        FriendshipEvent.getInstance().addObserver(this);
        //注册群关系监听
        GroupEvent.getInstance().addObserver(this);
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent) {
            TIMMessage msg = (TIMMessage) data;
            view.updateMessage(msg);
        } else if (observable instanceof FriendshipEvent) {
            FriendshipEvent.NotifyCmd cmd = (FriendshipEvent.NotifyCmd) data;
            switch (cmd.type) {
                case ADD_REQ:
                case READ_MSG:
                case ADD:
                    view.updateFriendshipMessage();
                    break;
            }
        } else if (observable instanceof GroupEvent) {
            GroupEvent.NotifyCmd cmd = (GroupEvent.NotifyCmd) data;
            switch (cmd.type) {
                case UPDATE:
                case ADD:
                    view.updateGroupInfo((TIMGroupCacheInfo) cmd.data);
                    break;
                case DEL:
                    view.removeConversation((String) cmd.data);
                    break;

            }
        } else if (observable instanceof RefreshEvent) {
            view.refresh();
        }
    }


    public void getConversation() {
        Log.i(TAG, "初始化消息界面");
        List<TIMConversation> list = TIMManager.getInstance().getConversionList();
        List<TIMConversation> result = new ArrayList<>();
        for (TIMConversation conversation : list) {
            if (conversation.getType() == TIMConversationType.System) continue;
            result.add(conversation);
            conversation.getMessage(1, null, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    if (timMessages.size() > 0) {
                        view.updateMessage(timMessages.get(0));
                    }

                }
            });

        }
        view.initView(result);
    }

    /**
     * 删除会话
     *
     * @param type 会话类型
     * @param id   会话对象id
     */
    public boolean delConversation(TIMConversationType type, String id) {
        return TIMManager.getInstance().deleteConversationAndLocalMsgs(type, id);
    }


    public void getGroupDetails(List<String> id) {
        TIMGroupManager.getInstance().getGroupDetailInfo(id, this);
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
        listener.showGroupInfo(timGroupDetailInfos);
    }
}
