package laoyou.com.laoyou.tencent.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.TIMValueCallBack;

import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.InitActivity;
import laoyou.com.laoyou.bean.Notice;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.save.db.LouSQLite;
import laoyou.com.laoyou.save.db.PhraseEntry;

import static laoyou.com.laoyou.utils.SynUtils.gets;

public class MessageNotifySettingActivity extends InitActivity implements TIMValueCallBack {

    private String TAG = "MessageNotifySettingActivity";
    private TIMOfflinePushSettings settings;
    private LinearLayout messagePush, c2cMusic, groupMusic;
    private final Uri notifyMusic = Uri.parse("android.resource://com.tencent.qcloud.timchat/" + R.raw.dudulu);
    private TextView c2ctag, c2cmsg, grouptag, groupmsg;

    private ImageView c2cswitch, groupswitch, msgswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void click() {
        msgswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setEnabled(!settings.isEnabled());
                TIMManager.getInstance().configOfflinePushSettings(settings);
                msgswitch.setImageResource(settings.isEnabled() ? R.mipmap.on_icon : R.mipmap.off_icon);
            }
        });
        c2cswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setC2cMsgRemindSound(settings.getC2cMsgRemindSound() != null ? null : notifyMusic);
                TIMManager.getInstance().configOfflinePushSettings(settings);
                c2cswitch.setImageResource(settings.getC2cMsgRemindSound() != null ? R.mipmap.on_icon : R.mipmap.off_icon);
            }
        });
        groupswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setGroupMsgRemindSound(settings.getGroupMsgRemindSound() != null ? null : notifyMusic);
                TIMManager.getInstance().configOfflinePushSettings(settings);
                groupswitch.setImageResource(settings.getGroupMsgRemindSound() != null ? R.mipmap.on_icon : R.mipmap.off_icon);
            }
        });
    }

    private void UpDate() {
        if (settings != null)
            LouSQLite.update(PhraseEntry.TABLE_NAME_MSG_NOTICE, new Notice(SPreferences.getUserId(), settings.isEnabled() ? 1 : 0, settings.getC2cMsgRemindSound() != null ? 1 : 0, settings.getGroupMsgRemindSound() != null ? 1 : 0), PhraseEntry.COLEUM_NAME_ID + "=?", new String[]{SPreferences.getUserId()});
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_message_notify_setting);
        messagePush = f(R.id.messagePush);
        c2cMusic = f(R.id.c2cMusic);
        groupMusic = f(R.id.groupMusic);

        c2cswitch = (ImageView) c2cMusic.findViewById(R.id.switch_img);
        groupswitch = (ImageView) groupMusic.findViewById(R.id.switch_img);
        msgswitch = (ImageView) messagePush.findViewById(R.id.switch_img);

        c2ctag = (TextView) c2cMusic.findViewById(R.id.tag_tv);
        grouptag = (TextView) groupMusic.findViewById(R.id.tag_tv);

        c2ctag.setText(gets(R.string.open_c2c_music));
        grouptag.setText(gets(R.string.open_group_music));

        c2cmsg = (TextView) c2cMusic.findViewById(R.id.switch_tv);
        groupmsg = (TextView) groupMusic.findViewById(R.id.switch_tv);

        c2cmsg.setText(gets(R.string.open_c2c_music));
        groupmsg.setText(gets(R.string.open_group_music));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpDate();
    }

    @Override
    protected void initData() {
        getSwitchStatus();
    }

    /**
     * 获取开关状态;
     */
    private void getSwitchStatus() {

        List<Notice> lists = LouSQLite.query(PhraseEntry.TABLE_NAME_MSG_NOTICE, PhraseEntry.SELECTFROM + PhraseEntry.TABLE_NAME_MSG_NOTICE, null);
        Log.e(TAG, "lists . size === " + lists.size());

        if (lists.size() > 0 && lists.get(0).getId().equals(SPreferences.getUserId())) {
            for (Notice nc : lists) {
                if (nc.getId().equals(SPreferences.getUserId())) {
                    msgswitch.setImageResource(nc.getNotice() == 1 ? R.mipmap.on_icon : R.mipmap.off_icon);
                    c2cswitch.setImageResource(nc.getC2c() == 1 ? R.mipmap.on_icon : R.mipmap.off_icon);
                    groupswitch.setImageResource(nc.getGroup() == 1 ? R.mipmap.on_icon : R.mipmap.off_icon);
                }
            }
        } else
            LouSQLite.insert(PhraseEntry.TABLE_NAME_MSG_NOTICE, new Notice(SPreferences.getUserId(), 0, 0, 0));

        TIMManager.getInstance().getOfflinePushSettings(this);
    }

    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "get offline push setting error " + s);
    }


    @Override
    public void onSuccess(Object timOfflinePushSettings) {

        settings = (TIMOfflinePushSettings) timOfflinePushSettings;
        msgswitch.setImageResource(settings.isEnabled() ? R.mipmap.on_icon : R.mipmap.off_icon);
        c2cswitch.setImageResource(settings.getC2cMsgRemindSound() != null ? R.mipmap.on_icon : R.mipmap.off_icon);
        groupswitch.setImageResource(settings.getGroupMsgRemindSound() != null ? R.mipmap.on_icon : R.mipmap.off_icon);

    }
}
