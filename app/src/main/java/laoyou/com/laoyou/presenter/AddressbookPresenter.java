package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.listener.AddressBookListener;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.utils.FriendUtils;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/11/23.
 */
public class AddressbookPresenter {
    private static final String TAG = "AddressbookPresenter";
    private AddressBookListener listener;

    public AddressbookPresenter(AddressBookListener listener) {
        this.listener = listener;
    }

    public void getFriendList() {

        //获取好友列表
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "getFriendList failed: " + code + " desc");
                listener.onErrorMsg(desc);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                listener.ongetFriendData(FriendUtils.getAddressBean(result));
            }
        });
    }

    public void AddOfCancle(boolean add, String identifier, String faceUrl, String name) {
        if (!add) {
            listener.onCancle(faceUrl, identifier,name);
        } else
            listener.onAdd(faceUrl, identifier,name);
    }

    public void CreateGroup(List<AddressBean> list) {
        if (list.size() > 0) {
            String groupName = "";
            List<String> info = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                info.add(list.get(i).getIdentifier());

                if (i <= 2)
                    groupName += list.get(i).getNickName().isEmpty() ? list.get(i).getIdentifier() : list.get(i).getNickName();
                if (i + 1 != list.size())
                    groupName += "，";
            }
            int remainder = 30;
            String name = "";
            if (groupName.length() > 30)
               name =  groupName.substring(0, groupName.length() - remainder);

              GroupManagerPresenter.createGroup(groupName,
                    GroupInfo.privateGroup,
                    info,
                    new TIMValueCallBack<String>() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "Create Group Failed Error == " + s);
                            if (i == 80001)
                                listener.onErrorMsg(gets(R.string.create_group_fail_because_wording));
                            else
                                listener.onErrorMsg(gets(R.string.create_group_fail));
                        }

                        @Override
                        public void onSuccess(String s) {
                            GroupSendMsg(s);
                        }
                    }
            );
        } else if (listener != null)
            listener.onErrorMsg(gets(R.string.select_people_null));

    }

    private void GroupSendMsg(String id) {
        //获取群聊会话
        String groupId = id;

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                groupId);
        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(gets(R.string.group_create_msg));

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d(TAG, "Msg - addElement failed");
            return;
        }
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                listener.onCreateGroupSuccess(gets(R.string.create_group_succeed));
            }
        });
    }
}
