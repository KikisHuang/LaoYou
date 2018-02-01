package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupAddOpt;
import com.tencent.TIMGroupManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.FlashChatMemberListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.model.GroupInfo;

import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatMemberPresenter  {
    private FlashChatMemberListener listener;
    private static final String TAG = "FlashChatMemberPresenter";

    public FlashChatMemberPresenter(FlashChatMemberListener listener) {
        this.listener = listener;
    }

    public void onCreateFlashChatGroup(String name, String info, String Grouphead, int num) {
        TIMGroupManager.CreateGroupParam param = TIMGroupManager.getInstanceById("").new CreateGroupParam();
        param.setGroupType(GroupInfo.publicGroup);
        param.setGroupName(name);
        param.setIntroduction(info);
        param.setMaxMemberNum(num);
        param.setFaceUrl(Grouphead);
        param.setNotification("welcome to hello group");
        param.setAddOption(TIMGroupAddOpt.TIM_GROUP_ADD_ANY);

        //创建群组
        TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
            @Override
            public void onError(int code, String desc) {
                listener.onCreateFailed(code);

                Log.d(TAG, "create group failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(String s) {
                GroupSendMsg(s);
                Log.d(TAG, "create group succ, groupId:" + s);
            }
        });
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
        elem.setText(SPreferences.getUserName() + gets(R.string.theGroup));

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
                listener.onCreateSucceed();
            }
        });
    }

}
