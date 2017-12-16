package laoyou.com.laoyou.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendInfoView;
import com.tencent.qcloud.ui.LineControllerView;
import com.tencent.qcloud.ui.ListPickerDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.activity.MainActivity.MainInstance;
import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.utils.IntentUtils.goBlackListPage;
import static laoyou.com.laoyou.utils.IntentUtils.goChangePassPage;
import static laoyou.com.laoyou.utils.IntentUtils.goMessageNotifySettingPage;
import static laoyou.com.laoyou.utils.SynUtils.LogOut;

/**
 * Created by lian on 2017/12/1.
 */
public class SettingActivity extends InitActivity implements View.OnClickListener, FriendInfoView {
    private String[] stringList;
    private TextView logout;
    private LineControllerView friendConfirm, messageNotify, blackList, change_pass;
    private Map<String, TIMFriendAllowType> allowTypeContent;

    private FriendshipManagerPresenter friendshipManagerPresenter;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void click() {
        logout.setOnClickListener(this);
        friendConfirm.setOnClickListener(this);
        messageNotify.setOnClickListener(this);
        blackList.setOnClickListener(this);
        change_pass.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.setting_layout);
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        friendshipManagerPresenter.getMyProfile();

        logout = f(R.id.logout);
        friendConfirm = f(R.id.friendConfirm);
        messageNotify = f(R.id.messageNotify);
        blackList = f(R.id.blackList);
        change_pass = f(R.id.change_pass);
    }

    @Override
    protected void initData() {
        allowTypeContent = new HashMap<>();
        allowTypeContent.put(getString(R.string.friend_allow_all), TIMFriendAllowType.TIM_FRIEND_ALLOW_ANY);
        allowTypeContent.put(getString(R.string.friend_need_confirm), TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
        allowTypeContent.put(getString(R.string.friend_refuse_all), TIMFriendAllowType.TIM_FRIEND_DENY_ANY);
        stringList = allowTypeContent.keySet().toArray(new String[allowTypeContent.size()]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                Show(SettingActivity.this, "提交中", true, null);
                LoginBusiness.logout(new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(SettingActivity.this, getResources().getString(R.string.setting_logout_fail), Toast.LENGTH_SHORT).show();
                        Cancle();
                    }

                    @Override
                    public void onSuccess() {

                        Cancle();
                        Toast.makeText(SettingActivity.this, getResources().getString(R.string.setting_logout_succeed), Toast.LENGTH_SHORT).show();
                        LogOut(SettingActivity.this);
                        if (MainInstance() != null)
                            MainInstance().onInitFragment();

                        finish();
                    }
                });
                break;
            case R.id.friendConfirm:

                new ListPickerDialog().show(stringList, fragmentManager, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        FriendshipManagerPresenter.setFriendAllowType(allowTypeContent.get(stringList[which]), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(SettingActivity.this, getString(R.string.setting_friend_confirm_change_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                friendConfirm.setContent(stringList[which]);
                            }
                        });
                    }
                });
                break;
            case R.id.messageNotify:
                goMessageNotifySettingPage(SettingActivity.this);
                break;
            case R.id.blackList:
                goBlackListPage(SettingActivity.this);
                break;
            case R.id.change_pass:
                goChangePassPage(SettingActivity.this);
                break;
        }
    }


    @Override
    public void showUserInfo(List<TIMUserProfile> users) {
        for (String item : allowTypeContent.keySet()) {
            if (allowTypeContent.get(item) == users.get(0).getAllowType()) {
                friendConfirm.setContent(item);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Fields.ACRESULET3) {
            if (MainInstance() != null)
                MainInstance().onInitFragment();
            if (getHomeInstance() != null)
                getHomeInstance().onLogout();

            finish();
        }
    }
}
