package laoyou.com.laoyou.tencent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMValueCallBack;

import java.util.Collections;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.AddressbookActivity;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.tencent.model.FriendProfile;
import laoyou.com.laoyou.tencent.model.FriendshipInfo;
import laoyou.com.laoyou.tencent.presentation.event.FriendshipEvent;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.FriendshipManageView;
import laoyou.com.laoyou.tencent.view.LineControllerView;
import laoyou.com.laoyou.tencent.view.ListPickerDialog;

import static laoyou.com.laoyou.activity.AddressbookActivity.AddressBookInstance;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;

public class ProfileActivity extends FragmentActivity implements FriendshipManageView, View.OnClickListener {


    private static final String TAG = ProfileActivity.class.getSimpleName();

    private final int CHANGE_CATEGORY_CODE = 100;
    private final int CHANGE_REMARK_CODE = 200;

    private FriendshipManagerPresenter friendshipManagerPresenter;
    private String identify, categoryStr;


    public static void navToProfile(Context context, String identify) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("identify", identify);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        identify = getIntent().getStringExtra("identify");
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        showProfile(identify);
    }

    /**
     * 显示用户信息
     *
     * @param identify
     */
    public void showProfile(final String identify) {
        final FriendProfile profile = FriendshipInfo.getInstance().getProfile(identify);
        Log.d(TAG, "show profile isFriend " + (profile != null));
        if (profile == null) return;
        TextView name = (TextView) findViewById(R.id.name);
        ImageView avatar = (ImageView) findViewById(R.id.avatar);
        if (profile.getAvatarUrl() == null || profile.getAvatarUrl().isEmpty())
            avatar.setImageResource(R.drawable.head_me);
        else
            Glide.with(MyApplication.getContext()).load(profile.getAvatarUrl()).apply(getGlideOptions()).into(avatar);

        name.setText(profile.getName());
        LineControllerView id = (LineControllerView) findViewById(R.id.id);
        id.setContent(profile.getIdentify());
        final LineControllerView remark = (LineControllerView) findViewById(R.id.remark);
        remark.setContent(profile.getRemark());
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.navToEdit(ProfileActivity.this, getString(R.string.profile_remark_edit), remark.getContent(), CHANGE_REMARK_CODE, new EditActivity.EditInterface() {
                    @Override
                    public void onEdit(String text, TIMCallBack callBack) {
                        FriendshipManagerPresenter.setRemarkName(profile.getIdentify(), text, callBack);
                    }
                }, 20);

            }
        });
        LineControllerView category = (LineControllerView) findViewById(R.id.group);
        //一个用户可以在多个分组内，客户端逻辑保证一个人只存在于一个分组
        category.setContent(categoryStr = profile.getGroupName());
        LineControllerView black = (LineControllerView) findViewById(R.id.blackList);
        black.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FriendshipManagerPresenter.addBlackList(Collections.singletonList(identify), new TIMValueCallBack<List<TIMFriendResult>>() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "add black list error " + s);
                        }

                        @Override
                        public void onSuccess(List<TIMFriendResult> timFriendResults) {
                            if (timFriendResults.get(0).getStatus() == TIMFriendStatus.TIM_FRIEND_STATUS_SUCC) {
                                Toast.makeText(ProfileActivity.this, getString(R.string.profile_black_succ), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });

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
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("identify", identify);
                intent.putExtra("type", TIMConversationType.C2C);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDel:
                friendshipManagerPresenter.delFriend(identify);
                break;
            case R.id.group:
                final String[] groups = FriendshipInfo.getInstance().getGroupsArray();
                for (int i = 0; i < groups.length; ++i) {
                    if (groups[i].equals("")) {
                        groups[i] = getString(R.string.default_group_name);
                        break;
                    }
                }
                new ListPickerDialog().show(groups, getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (groups[which].equals(categoryStr)) return;
                        friendshipManagerPresenter.changeFriendGroup(identify,
                                categoryStr.equals(getString(R.string.default_group_name)) ? null : categoryStr,
                                groups[which].equals(getString(R.string.default_group_name)) ? null : groups[which]);
                    }
                });
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_CATEGORY_CODE) {
            if (resultCode == RESULT_OK) {
                LineControllerView category = (LineControllerView) findViewById(R.id.group);
                category.setContent(categoryStr = data.getStringExtra("category"));
            }
        } else if (requestCode == CHANGE_REMARK_CODE) {
            if (resultCode == RESULT_OK) {
                LineControllerView remark = (LineControllerView) findViewById(R.id.remark);
                remark.setContent(data.getStringExtra(EditActivity.RETURN_EXTRA));

            }
        }

    }

    /**
     * 添加好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onAddFriend(TIMFriendStatus status) {

    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {
        switch (status) {
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.profile_del_succeed), Toast.LENGTH_SHORT).show();
                AddressbookActivity ac = AddressBookInstance();
                if(ac!=null)
                    ac.onRefresh();
                finish();
                break;
            case TIM_FRIEND_STATUS_UNKNOWN:
                Toast.makeText(this, getResources().getString(R.string.profile_del_fail), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 修改好友分组回调
     *
     * @param status    返回状态
     * @param groupName 分组名
     */
    @Override
    public void onChangeGroup(TIMFriendStatus status, String groupName) {
        LineControllerView category = (LineControllerView) findViewById(R.id.group);
        if (groupName == null) {
            groupName = getString(R.string.default_group_name);
        }
        switch (status) {
            case TIM_FRIEND_STATUS_UNKNOWN:
                Toast.makeText(this, getString(R.string.change_group_error), Toast.LENGTH_SHORT).show();
            case TIM_FRIEND_STATUS_SUCC:
                category.setContent(groupName);
                FriendshipEvent.getInstance().OnFriendGroupChange();
                break;
            default:
                Toast.makeText(this, getString(R.string.change_group_error), Toast.LENGTH_SHORT).show();
                category.setContent(getString(R.string.default_group_name));
                break;
        }
    }
}
