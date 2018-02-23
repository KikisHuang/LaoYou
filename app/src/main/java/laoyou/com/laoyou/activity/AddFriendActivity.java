package laoyou.com.laoyou.activity;

import android.content.DialogInterface;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.AttentionGameBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HomePageListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.view.NotifyDialog;
import laoyou.com.laoyou.utils.ActivityCollector;

import static laoyou.com.laoyou.dialog.CustomProgress.Cancle;
import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2018/1/4.
 */
public class AddFriendActivity extends InitActivity implements View.OnClickListener, HomePageListener {

    private static final String TAG = "AddFriendActivity";

    private TextView send_apply_for;
    private EditText apply_for_info_ed;
    private String identify;

    @Override
    protected void click() {
        send_apply_for.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.add_friend_layout);
        ActivityCollector.addActivity(this, getClass());
        setImgTitles(this);
        identify = getIntent().getStringExtra("apply_for_id");
        send_apply_for = f(R.id.send_apply_for);
        apply_for_info_ed = f(R.id.apply_for_info_ed);
        apply_for_info_ed.setText(SPreferences.getMyNickName() == null || SPreferences.getMyNickName().isEmpty() ? gets(R.string.apply_for_str) : "我是" + SPreferences.getMyNickName() + "，加个好友吧！");
        apply_for_info_ed.setSelection(apply_for_info_ed.getText().length());
        apply_for_info_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_apply_for:
                if (!identify.isEmpty())
                    addFriend(identify, "", "", apply_for_info_ed.getText().toString());
                break;
        }
    }

    /**
     * 添加好友
     *
     * @param id      添加对象Identify
     * @param remark  备注名
     * @param group   分组
     * @param message 附加消息
     */
    public void addFriend(final String id, String remark, String group, String message) {
        Show(AddFriendActivity.this, "提交中", true, null);
        List<TIMAddFriendRequest> reqList = new ArrayList<>();
        TIMAddFriendRequest req = new TIMAddFriendRequest();
        req.setAddWording(message);
        req.setIdentifier(id);
        req.setRemark(remark);
        req.setFriendGroup(group);
        reqList.add(req);
        TIMFriendshipManager.getInstance().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {

            @Override
            public void onError(int arg0, String arg1) {
                Log.e(TAG, "onError code" + arg0 + " msg " + arg1);
                onAddFriend(TIMFriendStatus.TIM_FRIEND_STATUS_UNKNOWN);
                Cancle();
            }

            @Override
            public void onSuccess(List<TIMFriendResult> arg0) {
                for (TIMFriendResult item : arg0) {
                    if (item.getIdentifer().equals(id)) {
                        onAddFriend(item.getStatus());
                    }
                }
                Cancle();
            }

        });
    }

    @Override
    public void onFailedMSg(String msg) {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onShowUserInfo(UserInfoBean ub) {

    }

    @Override
    public void onCertificaTion(int status) {

    }

    @Override
    public void onMyHeartValue(String num) {

    }

    @Override
    public void onAttentGames(List<AttentionGameBean> ll) {

    }

    @Override
    public void onPhotoList(List<PhotoBean> photos) {

    }

    @Override
    public void onBottom() {

    }

    @Override
    public void onStatusInfo(List<TopicTypeBean> nblist) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onAddFriend(TIMFriendStatus status) {
        switch (status) {
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succeed), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succ), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Toast.makeText(this, getResources().getString(R.string.add_friend_refuse_all), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                Toast.makeText(this, getResources().getString(R.string.add_friend_to_blacklist), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.add_friend_del_black_list), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FriendshipManagerPresenter.delBlackList(Collections.singletonList(identify), new TIMValueCallBack<List<TIMFriendResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(AddFriendActivity.this, getResources().getString(R.string.add_friend_del_black_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                                Toast.makeText(AddFriendActivity.this, getResources().getString(R.string.add_friend_del_black_succ), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.add_friend_error), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDelFriend(TIMFriendStatus status) {

    }

    @Override
    public void ondeleteStatus(String id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
