package laoyou.com.laoyou.tencent.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupMemberResult;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.adapters.ProfileSummaryAdapter;
import laoyou.com.laoyou.tencent.model.GroupInfo;
import laoyou.com.laoyou.tencent.model.GroupMemberProfile;
import laoyou.com.laoyou.tencent.model.ProfileSummary;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.presenter.GroupManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.FriendshipMessageView;
import laoyou.com.laoyou.tencent.view.TemplateTitle;

public class GroupMemberActivity extends Activity implements TIMValueCallBack<List<TIMGroupMemberInfo>>, FriendshipMessageView {

    private static final String TAG = "GroupMemberActivity";
    ProfileSummaryAdapter adapter;
    List<ProfileSummary> list = new ArrayList<>();
    ListView listView;
    TemplateTitle title;
    String groupId, type;
    private static FriendshipManagerPresenter presenter;
    private final int MEM_REQ = 100;
    private final int CHOOSE_MEM_CODE = 200;
    private int memIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        title = (TemplateTitle) findViewById(R.id.group_mem_title);
        groupId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        listView = (ListView) findViewById(R.id.list);
        adapter = new ProfileSummaryAdapter(this, R.layout.item_profile_summary, list);
        listView.setAdapter(adapter);
        presenter = new FriendshipManagerPresenter(this);

        TIMGroupManager.getInstance().getGroupMembers(groupId, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memIndex = position;
                Intent intent = new Intent(GroupMemberActivity.this, GroupMemberProfileActivity.class);
                GroupMemberProfile profile = (GroupMemberProfile) list.get(position);
                intent.putExtra("data", profile);
                intent.putExtra("groupId", groupId);
                intent.putExtra("type", type);
                startActivityForResult(intent, MEM_REQ);
            }
        });
        if (type.equals(GroupInfo.privateGroup)) {
            title.setMoreImg(R.drawable.ic_add);
            title.setMoreImgAction(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupMemberActivity.this, ChooseFriendActivity.class);
                    ArrayList<String> selected = new ArrayList<>();
                    for (ProfileSummary profile : list) {
                        selected.add(profile.getIdentify());
                    }
                    intent.putStringArrayListExtra("selected", selected);
                    startActivityForResult(intent, CHOOSE_MEM_CODE);
                }
            });
        }
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
        list.clear();
        if (timGroupMemberInfos == null) return;
        for (TIMGroupMemberInfo item : timGroupMemberInfos) {
            list.add(new GroupMemberProfile(item));
        }
        List<String> id = new ArrayList<>();
        for (ProfileSummary pf : list) {
            id.add(pf.getIdentify());
        }
        presenter.getUserProFile(id);

//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MEM_REQ == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean isKick = data.getBooleanExtra("isKick", false);
                if (isKick) {
                    list.remove(memIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    GroupMemberProfile profile = (GroupMemberProfile) data.getSerializableExtra("data");
                    if (memIndex < list.size() && list.get(memIndex).getIdentify().equals(profile.getIdentify())) {
                        GroupMemberProfile mMemberProfile = (GroupMemberProfile) list.get(memIndex);
                        mMemberProfile.setRoleType(profile.getRole());
                        mMemberProfile.setQuietTime(profile.getQuietTime());
                        mMemberProfile.setName(profile.getNameCard());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (CHOOSE_MEM_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                GroupManagerPresenter.inviteGroup(groupId, data.getStringArrayListExtra("select"),
                        new TIMValueCallBack<List<TIMGroupMemberResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupMemberActivity.this, getString(R.string.chat_setting_invite_error), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMGroupMemberResult> timGroupMemberResults) {
                                TIMGroupManager.getInstance().getGroupMembers(groupId, GroupMemberActivity.this);
                            }
                        });

            }
        }
    }


    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {

    }

    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {

    }

    @Override
    public void onSystemInfo(List<TIMUserProfile> result) {

        for (int i = 0; i < list.size(); i++) {
            for (TIMUserProfile tf : result) {

                if (list.get(i).getIdentify().equals(tf.getIdentifier())) {
                    String myid = SPreferences.getIdentifier().trim();
                    if (list.get(i).getIdentify().trim().equals(myid)) {
                        ((GroupMemberProfile) list.get(i)).setFaceUrl(SPreferences.getUserHeadImg());
                        ((GroupMemberProfile) list.get(i)).setUserName(SPreferences.getUserName());
                    } else {
                        ((GroupMemberProfile) list.get(i)).setFaceUrl(tf.getFaceUrl());
                        ((GroupMemberProfile) list.get(i)).setUserName(tf.getNickName());
                    }
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
