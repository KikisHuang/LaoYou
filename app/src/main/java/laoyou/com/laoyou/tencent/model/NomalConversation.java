package laoyou.com.laoyou.tencent.model;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.TemporaryBean;
import laoyou.com.laoyou.tencent.ui.ChatActivity;
import laoyou.com.laoyou.utils.Fields;

/**
 * 好友或群聊的会话
 */
public class NomalConversation extends Conversation {


    private static final String TAG = "NomalConversation";
    private TIMConversation conversation;

    //最后一条消息
    private Message lastMessage;

    public NomalConversation(TIMConversation conversation) {
        this.conversation = conversation;
        type = conversation.getType();
        identify = conversation.getPeer();
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }


    @Override
    public Object getAvatar() {
        switch (type) {
            case C2C:
                FriendProfile profile = FriendshipInfo.getInstance().getProfile(identify);
                return faceurl = profile == null ? "" : profile.getAvatarUrl();
            case Group:
                if (faceurl != null && !faceurl.isEmpty())
                    return faceurl;
                else
                    return R.drawable.head_group;
        }
        return 0;
    }

    /**
     * 跳转到聊天界面或会话详情
     *
     * @param context 跳转上下文
     */
    @Override
    public void navToDetail(Context context) {
        ChatActivity.navToChat(context, identify, type);
    }

    /**
     * 获取最后一条消息摘要
     */
    @Override
    public String getLastMessageSummary() {
        if (lastMessage != null && lastMessage.getMessage().getElement(0).getType() == TIMElemType.GroupTips) {
            final TIMGroupTipsElem e = (TIMGroupTipsElem) lastMessage.getMessage().getElement(0);

            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Map.Entry<String, TIMGroupMemberInfo>> iterator = e.getChangedGroupMemberInfo().entrySet().iterator();
            boolean flag = false;
            switch (e.getTipsType()) {
                case Kick:
                    for (TemporaryBean tb : Fields.temporary) {
                        if (tb.getId().equals(e.getUserList().get(0)))
                            flag = true;
                    }
                    if (!flag)
                        getUserProFile(e.getUserList().get(0));
                    break;
                case Join:
                    while (iterator.hasNext()) {
                        Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                        stringBuilder.append(getNames(item.getValue()));
                        stringBuilder.append(" ");
                    }
                    for (TemporaryBean tb : Fields.temporary) {
                        if (tb.getId().equals(String.valueOf(stringBuilder)))
                            flag = true;
                    }
                    if (!flag)
                        getUserProFile(String.valueOf(stringBuilder));

                    break;
                case ModifyMemberInfo:
                    while (iterator.hasNext()) {
                        Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                        stringBuilder.append(getNames(item.getValue()));
                        stringBuilder.append(" ");
                    }
                    for (TemporaryBean tb : Fields.temporary) {
                        if (tb.getId().equals(String.valueOf(stringBuilder)))
                            flag = true;
                    }
                    if (!flag)
                        getUserProFile(String.valueOf(stringBuilder));
                    break;
                case Quit:
                    for (TemporaryBean tb : Fields.temporary) {
                        if (tb.getId().equals(e.getOpUser()))
                            flag = true;
                    }
                    if (!flag)
                        getUserProFile(e.getOpUser());
                    break;
            }
        }
        if (conversation.hasDraft()) {
            TextMessage textMessage = new TextMessage(conversation.getDraft());
            if (lastMessage == null || lastMessage.getMessage().timestamp() < conversation.getDraft().getTimestamp()) {
                return MyApplication.getContext().getString(R.string.conversation_draft) + textMessage.getSummary();
            } else {
                return lastMessage.getSummary();
            }
        } else {
            if (lastMessage == null) return "";
            return lastMessage.getSummary();
        }
    }

    /**
     * 获取名称
     */
    @Override
    public String getName() {
        if (type == TIMConversationType.Group) {
            name = GroupInfo.getInstance().getGroupName(identify);
            if (name.equals("")) name = identify;
        } else {
            FriendProfile profile = FriendshipInfo.getInstance().getProfile(identify);
            name = profile == null ? identify : profile.getName();
        }
        return name;
    }

    private String getNames(TIMGroupMemberInfo info) {
        if (info.getNameCard().equals("")) {
            return info.getUser();
        }
        return info.getNameCard();
    }

    /**
     * 获取未读消息数量
     */
    @Override
    public long getUnreadNum() {
        if (conversation == null) return 0;
        return conversation.getUnreadMessageNum();
    }

    /**
     * 将所有消息标记为已读
     */
    @Override
    public void readAllMessage() {
        if (conversation != null) {
            conversation.setReadMessage();
        }
    }

    /**
     * 获取最后一条消息的时间
     */
    @Override
    public long getLastMessageTime() {
        if (conversation.hasDraft()) {
            if (lastMessage == null || lastMessage.getMessage().timestamp() < conversation.getDraft().getTimestamp()) {
                return conversation.getDraft().getTimestamp();
            } else {
                return lastMessage.getMessage().timestamp();
            }
        }
        if (lastMessage == null) return 0;
        return lastMessage.getMessage().timestamp();
    }

    /**
     * 获取会话类型
     */
    public TIMConversationType getType() {
        return conversation.getType();
    }

    /**
     * 拉取任何人的资料
     *
     * @param strings
     */
    public void getUserProFile(final String strings) {
        List<String> list = new ArrayList<>();
        list.add(strings);
        //获取用户资料
        TIMFriendshipManager.getInstance().getFriendsProfile(list, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "getUsersProfile failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {

                TemporaryBean tem = new TemporaryBean();
                tem.setId(strings);
                tem.setName(result.get(0).getNickName());
                Fields.temporary.add(tem);

            }
        });
    }

}
