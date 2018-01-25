package laoyou.com.laoyou.tencent.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupAddOpt;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMUserProfile;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.tencent.model.UserInfo;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupInfoPresenter;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.FriendInfoView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupInfoView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupUpLoadListener;
import laoyou.com.laoyou.tencent.view.LineControllerView;
import laoyou.com.laoyou.tencent.view.ListPickerDialog;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhotoTag;

public class GroupProfileActivity extends FragmentActivity implements GroupInfoView, View.OnClickListener, FriendInfoView, GroupUpLoadListener {

    private final String TAG = "GroupProfileActivity";

    private String identify, type;
    private GroupInfoPresenter groupInfoPresenter;
    private TIMGroupDetailInfo info;
    private boolean isInGroup;
    private boolean isGroupOwner;
    private final int REQ_CHANGE_NAME = 100, REQ_CHANGE_INTRO = 200;
    private TIMGroupMemberRoleType roleType = TIMGroupMemberRoleType.NotMember;
    private Map<String, TIMGroupAddOpt> allowTypeContent;
    private Map<String, TIMGroupReceiveMessageOpt> messageOptContent;
    private LineControllerView name, intro;
    private String headImg;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    private CircleImageView group_head;
    private LinearLayout group_head_layout;
    private File GroupFile;
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_setting);
        identify = getIntent().getStringExtra("identify");
        isInGroup = GroupInfo.getInstance().isInGroup(identify);

        groupInfoPresenter = new GroupInfoPresenter(this, Collections.singletonList(identify), isInGroup, this);
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        groupInfoPresenter.getGroupDetailInfo();
        name = (LineControllerView) findViewById(R.id.nameText);
        intro = (LineControllerView) findViewById(R.id.groupIntro);
        group_head = (CircleImageView) findViewById(R.id.group_head);

        LinearLayout controlInGroup = (LinearLayout) findViewById(R.id.controlInGroup);
        group_head_layout = (LinearLayout) findViewById(R.id.group_head_layout);
        controlInGroup.setVisibility(isInGroup ? View.VISIBLE : View.GONE);
        TextView controlOutGroup = (TextView) findViewById(R.id.controlOutGroup);
        controlOutGroup.setVisibility(isInGroup ? View.GONE : View.VISIBLE);
        group_head_layout.setOnClickListener(this);
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {


        friendshipManagerPresenter.getMyProfile();
        info = groupInfos.get(0);
        isGroupOwner = info.getGroupOwner().equals(UserInfo.getInstance().getId());

        roleType = GroupInfo.getInstance().getRole(identify);
        type = info.getGroupType();
        LineControllerView member = (LineControllerView) findViewById(R.id.member);
        if (isInGroup) {
            group_head_layout.setVisibility(View.VISIBLE);
            key = getIntent().getStringExtra("key");
            if (groupInfos.get(0) == null || groupInfos.get(0).getFaceUrl().isEmpty())
                Glide.with(GroupProfileActivity.this).load(R.drawable.head_group).apply(getGlideOptions()).into(group_head);
            else
                Glide.with(GroupProfileActivity.this).load(groupInfos.get(0).getFaceUrl()).apply(getGlideOptions()).into(group_head);

            member.setContent(String.valueOf(info.getMemberNum()));
            member.setOnClickListener(this);
        } else {
            member.setVisibility(View.GONE);
        }
        name.setContent(info.getGroupName());
        LineControllerView id = (LineControllerView) findViewById(R.id.idText);
        id.setContent(info.getGroupId());

        intro.setContent(info.getGroupIntroduction());
        LineControllerView opt = (LineControllerView) findViewById(R.id.addOpt);
        switch (info.getGroupAddOpt()) {
            case TIM_GROUP_ADD_AUTH:
                opt.setContent(getString(R.string.chat_setting_group_auth));
                break;
            case TIM_GROUP_ADD_ANY:
                opt.setContent(getString(R.string.chat_setting_group_all_accept));
                break;
            case TIM_GROUP_ADD_FORBID:
                opt.setContent(getString(R.string.chat_setting_group_all_reject));
                break;
        }
        LineControllerView msgNotify = (LineControllerView) findViewById(R.id.messageNotify);
        if (GroupInfo.getInstance().isInGroup(identify)) {
            switch (GroupInfo.getInstance().getMessageOpt(identify)) {
                case NotReceive:
                    msgNotify.setContent(getString(R.string.chat_setting_no_rev));
                    break;
                case ReceiveAndNotify:
                    msgNotify.setContent(getString(R.string.chat_setting_rev_notify));
                    break;
                case ReceiveNotNotify:
                    msgNotify.setContent(getString(R.string.chat_setting_rev_not_notify));
                    break;
            }
            msgNotify.setOnClickListener(this);
            messageOptContent = new HashMap<>();
            messageOptContent.put(getString(R.string.chat_setting_no_rev), TIMGroupReceiveMessageOpt.NotReceive);
            messageOptContent.put(getString(R.string.chat_setting_rev_not_notify), TIMGroupReceiveMessageOpt.ReceiveNotNotify);
            messageOptContent.put(getString(R.string.chat_setting_rev_notify), TIMGroupReceiveMessageOpt.ReceiveAndNotify);
        } else {
            msgNotify.setVisibility(View.GONE);
        }
        if (isManager()) {
            opt.setCanNav(true);
            opt.setOnClickListener(this);
            allowTypeContent = new HashMap<>();
            allowTypeContent.put(getString(R.string.chat_setting_group_auth), TIMGroupAddOpt.TIM_GROUP_ADD_AUTH);
            allowTypeContent.put(getString(R.string.chat_setting_group_all_accept), TIMGroupAddOpt.TIM_GROUP_ADD_ANY);
            allowTypeContent.put(getString(R.string.chat_setting_group_all_reject), TIMGroupAddOpt.TIM_GROUP_ADD_FORBID);
            name.setCanNav(true);
            name.setOnClickListener(this);
            intro.setCanNav(true);
            intro.setOnClickListener(this);
        }
        TextView btnDel = (TextView) findViewById(R.id.btnDel);
        btnDel.setText(isGroupOwner ? getString(R.string.chat_setting_dismiss) : getString(R.string.chat_setting_quit));

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChat:
                ChatActivity.navToChat(this, identify, TIMConversationType.Group);
                break;
            case R.id.btnDel:
                if (isGroupOwner) {
                    GroupManagerPresenter.dismissGroup(identify, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.i(TAG, "onError code" + i + " msg " + s);
                            if (i == 10004 && type.equals(GroupInfo.privateGroup)) {
                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_quit_fail_private), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_dismiss_succ), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    GroupManagerPresenter.quitGroup(identify, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.i(TAG, "onError code" + i + " msg " + s);
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_quit_succ), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                break;
            case R.id.controlOutGroup:
                Intent intent = new Intent(this, ApplyGroupActivity.class);
                intent.putExtra("identify", identify);
                startActivity(intent);
                break;
            case R.id.member:
                Intent intentGroupMem = new Intent(this, GroupMemberActivity.class);
                intentGroupMem.putExtra("id", identify);
                intentGroupMem.putExtra("type", type);
                startActivity(intentGroupMem);
                break;
            case R.id.addOpt:
                final String[] stringList = allowTypeContent.keySet().toArray(new String[allowTypeContent.size()]);
                new ListPickerDialog().show(stringList, getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        TIMGroupManager.getInstance().modifyGroupAddOpt(identify, allowTypeContent.get(stringList[which]), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_change_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                LineControllerView opt = (LineControllerView) findViewById(R.id.addOpt);
                                opt.setContent(stringList[which]);
                            }
                        });
                    }
                });
                break;
            case R.id.nameText:
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditActivity.navToEdit(GroupProfileActivity.this, getString(R.string.chat_setting_change_group_name), info.getGroupName(), REQ_CHANGE_NAME, new EditActivity.EditInterface() {
                            @Override
                            public void onEdit(final String text, TIMCallBack callBack) {
                                TIMGroupManager.getInstance().modifyGroupName(identify, text, callBack);
                            }
                        }, 20);

                    }
                });
                break;
            case R.id.groupIntro:
                intro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditActivity.navToEdit(GroupProfileActivity.this, getString(R.string.chat_setting_change_group_intro), intro.getContent(), REQ_CHANGE_INTRO, new EditActivity.EditInterface() {
                            @Override
                            public void onEdit(final String text, TIMCallBack callBack) {
                                TIMGroupManager.getInstance().modifyGroupIntroduction(identify, text, callBack);
                            }
                        }, 20);

                    }
                });
                break;
            case R.id.messageNotify:
                final String[] messageOptList = messageOptContent.keySet().toArray(new String[messageOptContent.size()]);
                new ListPickerDialog().show(messageOptList, getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        TIMGroupManager.getInstance().modifyReceiveMessageOpt(identify, messageOptContent.get(messageOptList[which]), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_change_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                LineControllerView msgNotify = (LineControllerView) findViewById(R.id.messageNotify);
                                msgNotify.setContent(messageOptList[which]);
                            }
                        });
                    }
                });
                break;

            case R.id.group_head_layout:
                if (isGroupOwner)
                    getMULTIPLEPhotoTag(this,PictureConfig.CHOOSE_REQUEST);
//                    groupInfoPresenter.ChangeHeadImg(this);
                else
                    ToastUtil.toast2_bottom(GroupProfileActivity.this, "只有群主才能更改群头像");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    Show(GroupProfileActivity.this, "提交中", true, null);
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    GroupFile = new File(list.get(0).getCompressPath() == null || list.get(0).getCompressPath().isEmpty() ? list.get(0).getPath() : list.get(0).getCompressPath());
                    Glide.with(GroupProfileActivity.this).load(GroupFile).apply(getGlideOptions()).into(group_head);
                    UpGroupHead();
                    break;
            }
        }
        if (requestCode == REQ_CHANGE_NAME) {
            if (resultCode == RESULT_OK) {
                name.setContent(data.getStringExtra(EditActivity.RETURN_EXTRA));
            }
        } else if (requestCode == REQ_CHANGE_INTRO) {
            if (resultCode == RESULT_OK) {
                intro.setContent(data.getStringExtra(EditActivity.RETURN_EXTRA));
            }
        }

    }

    private void UpGroupHead() {
        groupInfoPresenter.UploadGroupImg(key, GroupFile, identify);

    }

    private boolean isManager() {
        return roleType == TIMGroupMemberRoleType.Owner || roleType == TIMGroupMemberRoleType.Admin;
    }

    @Override
    public void showUserInfo(List<TIMUserProfile> users) {
        headImg = users.get(0).getFaceUrl();
    }

    @Override
    public void onSucceed() {
        Cancle();
        ToastUtil.toast2_bottom(GroupProfileActivity.this, "群头像上传成功！");
    }

    @Override
    public void onErrorMSg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
        Cancle();
    }
}
