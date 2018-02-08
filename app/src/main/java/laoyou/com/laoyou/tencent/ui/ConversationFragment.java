package laoyou.com.laoyou.tencent.ui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupPendencyItem;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.MainActivity;
import laoyou.com.laoyou.activity.MyCreateGroupActivity;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.adapters.ConversationAdapter;
import laoyou.com.laoyou.tencent.model.Conversation;
import laoyou.com.laoyou.tencent.model.CustomMessage;
import laoyou.com.laoyou.tencent.model.FriendshipConversation;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.tencent.model.GroupManageConversation;
import laoyou.com.laoyou.tencent.model.MessageFactory;
import laoyou.com.laoyou.tencent.model.NomalConversation;
import laoyou.com.laoyou.tencent.presentation.presenter.ConversationPresenter;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.ConversationView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.FriendInfoView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.FriendshipMessageView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupInfoView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupManageMessageView;
import laoyou.com.laoyou.tencent.utils.PushUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goAddressBookPage;

/**
 * 会话列表界面
 */
public class ConversationFragment extends Fragment implements ConversationView, FriendshipMessageView, GroupManageMessageView, View.OnClickListener, FriendInfoView, GroupInfoView {

    private final String TAG = "ConversationFragment";

    private View view;
    private LinkedList<Conversation> conversationList = new LinkedList<>();
    private ConversationAdapter adapter;
    private ListView listView;
    public ConversationPresenter presenter;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    private FriendshipManagerPresenter infoPresenter;
    private GroupManagerPresenter groupManagerPresenter;
    private List<String> groupList;
    private FriendshipConversation friendshipConversation;
    private GroupManageConversation groupManageConversation;
    private CircleImageView add_find_img, address_img;
    private Dialog inviteDialog;
    private TextView addFriend, managerGroup, addGroup;
    private FriendshipMessageView friendship;
    private FriendInfoView FriendInfo;
    private static ConversationFragment fragment;

    public ConversationFragment() {
        // Required empty public constructor
    }

    public static ConversationFragment ConversationInstance() {
        if (fragment == null)
            fragment = new ConversationFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_conversation, container, false);
            listView = (ListView) view.findViewById(R.id.list);
            add_find_img = (CircleImageView) view.findViewById(R.id.add_find_img);
            address_img = (CircleImageView) view.findViewById(R.id.address_img);

            friendship = this;
            FriendInfo = this;
            fragment = this;
            adapter = new ConversationAdapter(getActivity(), R.layout.item_conversation, conversationList);
            listView.setAdapter(adapter);
            friendshipManagerPresenter = new FriendshipManagerPresenter(friendship);
            infoPresenter = new FriendshipManagerPresenter(FriendInfo);
            groupManagerPresenter = new GroupManagerPresenter((GroupManageMessageView) this);
            presenter = new ConversationPresenter(this, this);
            presenter.getConversation();
            registerForContextMenu(listView);
            click();
        }
        adapter.notifyDataSetChanged();
        return view;

    }

    private void click() {

        add_find_img.setOnClickListener(this);
        address_img.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (conversationList.get(position).getType() == TIMConversationType.C2C) {
                    if (conversationList.get(position).getAvatar() == null || conversationList.get(position).getAvatar() instanceof Integer)
                        SPreferences.saveTemporaryImg("");
                    else
                        SPreferences.saveTemporaryImg(String.valueOf(conversationList.get(position).getAvatar()));
                }
                conversationList.get(position).navToDetail(getActivity());
                if (conversationList.get(position) instanceof GroupManageConversation) {
//                    groupManagerPresenter.getGroupManageLastMessage();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        PushUtil.getInstance().reset();
    }


    /**
     * 初始化界面或刷新界面
     *
     * @param conversationList
     */
    @Override
    public void initView(List<TIMConversation> conversationList) {

        infoPresenter.getMyProfile();
        this.conversationList.clear();
        groupList = new ArrayList<>();
        for (TIMConversation item : conversationList) {
            switch (item.getType()) {
                case C2C:
                case Group:
                    this.conversationList.add(new NomalConversation(item));
                    groupList.add(item.getPeer());
                    break;
            }
        }
        friendshipManagerPresenter.getFriendshipLastMessage();
//        groupManagerPresenter.getGroupManageLastMessage();

    }


    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    @Override
    public void updateMessage(TIMMessage message) {


        if (message == null) {
            adapter.notifyDataSetChanged();
            return;
        }

        if (message.getConversation().getType() == TIMConversationType.System) {
//            groupManagerPresenter.getGroupManageLastMessage();
            return;
        }
        if (MessageFactory.getMessage(message) instanceof CustomMessage) return;
        NomalConversation conversation = new NomalConversation(message.getConversation());
        Iterator<Conversation> iterator = conversationList.iterator();
        while (iterator.hasNext()) {
            Conversation c = iterator.next();
            if (conversation.equals(c)) {
                conversation = (NomalConversation) c;
                iterator.remove();
                break;
            }
        }

        conversation.setLastMessage(MessageFactory.getMessage(message));

        conversationList.add(conversation);

        Collections.sort(conversationList);

        refresh();
        List<String> groupId = new ArrayList<>();
        for (Conversation con : conversationList) {
            if (con.getType() == TIMConversationType.Group)
                groupId.add(con.getIdentify());
        }
        if (groupId.size() > 0)
            presenter.getGroupDetails(groupId);

    }

    /**
     * 更新好友关系链消息
     */
    @Override
    public void updateFriendshipMessage() {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }

    /**
     * 删除会话
     *
     * @param identify
     */
    @Override
    public void removeConversation(String identify) {
        Iterator<Conversation> iterator = conversationList.iterator();
        while (iterator.hasNext()) {
            Conversation conversation = iterator.next();
            if (conversation.getIdentify() != null && conversation.getIdentify().equals(identify)) {
                iterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 更新群信息
     *
     * @param info
     */
    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {
        for (Conversation conversation : conversationList) {
            if (conversation.getIdentify() != null && conversation.getIdentify().equals(info.getGroupInfo().getGroupId())) {
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        for (int i = 0; i < conversationList.size(); i++) {
            Conversation conve = conversationList.get(i);
            if (conve.getType() != null && conve.getType() == TIMConversationType.C2C && !FriendshipInfo.getInstance().isFriend(conve.getIdentify()))
                conversationList.remove(i);
        }
        Collections.sort(conversationList);
        if (conversationList != null) {
            for (int i = 0; i < conversationList.size(); i++) {
                if (conversationList.get(i).getIdentify() != null && conversationList.get(i).getIdentify().equals("96ecd7900a6341f6aecde21cd5efb7"))
                    Collections.swap(conversationList, i, 0);
            }
        }

        adapter.notifyDataSetChanged();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setMsgUnread(getTotalUnreadNum() == 0);


    }


    /**
     * 获取好友关系链管理系统最后一条消息的回调
     *
     * @param message     最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {
        if (friendshipConversation == null) {
            friendshipConversation = new FriendshipConversation(message);
            conversationList.add(friendshipConversation);
        } else {
            friendshipConversation.setLastMessage(message);
        }
        friendshipConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    /**
     * 获取好友关系链管理最后一条系统消息的回调
     *
     * @param message 消息列表
     */
    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }

    @Override
    public void onSystemInfo(List<TIMUserProfile> result) {
    }

    /**
     * 获取群管理最后一条系统消息的回调
     *
     * @param message     最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetGroupManageLastMessage(TIMGroupPendencyItem message, long unreadCount) {
        if (groupManageConversation == null) {
            //群组系统消息;
            groupManageConversation = new GroupManageConversation(message);
            conversationList.add(groupManageConversation);
        } else {
            groupManageConversation.setLastMessage(message);
        }
        groupManageConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    /**
     * 获取群管理系统消息的回调
     *
     * @param message 分页的消息列表
     */
    @Override
    public void onGetGroupManageMessage(List<TIMGroupPendencyItem> message) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Conversation conversation = conversationList.get(info.position);
        if (conversation instanceof NomalConversation) {
            menu.add(0, 1, Menu.NONE, getString(R.string.conversation_del));
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NomalConversation conversation = (NomalConversation) conversationList.get(info.position);
        switch (item.getItemId()) {
            case 1:
                if (conversation != null) {
                    if (presenter.delConversation(conversation.getType(), conversation.getIdentify())) {
                        conversationList.remove(conversation);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 自定删除方法;
     *
     * @param identify
     */
    public void MyDelMethod(String identify) {
        if (conversationList != null && conversationList.size() > 0) {
            for (Conversation cst : conversationList) {
                if (identify.equals(cst.getIdentify())) {
                    NomalConversation conversation = (NomalConversation) cst;
                    if (conversation != null) {
                        if (presenter.delConversation(conversation.getType(), conversation.getIdentify())) {
                            conversationList.remove(conversation);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                }
            }
        }
    }

    private long getTotalUnreadNum() {
        long num = 0;
        for (Conversation conversation : conversationList) {
            num += conversation.getUnreadNum();
        }
        return num;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_img:
                goAddressBookPage(getActivity());
                break;
            case R.id.add_find_img:
                showMoveDialog();
                break;
        }
    }


    private void showMoveDialog() {
        inviteDialog = new Dialog(getActivity(), R.style.dialog);
        inviteDialog.setContentView(R.layout.contact_more);
        addFriend = (TextView) inviteDialog.findViewById(R.id.add_friend);
        managerGroup = (TextView) inviteDialog.findViewById(R.id.manager_group);
        addGroup = (TextView) inviteDialog.findViewById(R.id.add_group);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        managerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageFriendGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchGroupActivity.class);
//                getActivity().startActivity(intent);
                Intent intent = new Intent(getActivity(), MyCreateGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        Window window = inviteDialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        inviteDialog.show();
    }

    @Override
    public void showUserInfo(List<TIMUserProfile> users) {

        if (users.get(0).getFaceUrl() == null || users.get(0).getFaceUrl().equals(""))
            SPreferences.saveUserHeadImg("");
        else {
            SPreferences.saveUserHeadImg(users.get(0).getFaceUrl());
            SPreferences.saveUserName(users.get(0).getNickName());
        }

    }

    /**
     * 获得群头像;
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        if (groupInfos.size() > 0) {
            for (int i = 0; i < conversationList.size(); i++) {
                for (TIMGroupDetailInfo info : groupInfos) {
                    if (conversationList.get(i).getType() == TIMConversationType.Group && info.getGroupId().equals(conversationList.get(i).getIdentify())) {
                        conversationList.get(i).setFaceurl(info.getFaceUrl());
                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
