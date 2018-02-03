package laoyou.com.laoyou.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.FlashChatMemberListener;
import laoyou.com.laoyou.presenter.FlashChatMemberPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.ScrollPicker.ScrollPickerView;
import laoyou.com.laoyou.view.ScrollPicker.StringScrollPicker;

import static laoyou.com.laoyou.tencent.utils.Constant.getGroupErrorTxt;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatMemberActivity extends InitActivity implements View.OnClickListener, FlashChatMemberListener {

    private static final String TAG = "FlashChatMemberActivity";
    private List<CharSequence> list;
    private StringScrollPicker picker_horizontal;
    private int Num = 0;
    private ImageView next_img;
    private String GroupName;
    private String GroupInfo;
    private String GroupType;
    private FlashChatMemberPresenter fp;
    private RelativeLayout titles_layout;

    @Override
    protected void click() {
        next_img.setOnClickListener(this);
        picker_horizontal.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Num = position + 1;
                Log.i(TAG, " Member num ===" + Num);
            }
        });
    }

    @Override
    protected void init() {

        setContentView(R.layout.flash_chat_member_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitlesAndBack(this,"","");
        titles_layout = f(R.id.titles_layout);
        titles_layout.setBackgroundResource(R.color.background_color);

        list = new ArrayList<>();
        picker_horizontal = f(R.id.picker_horizontal);

        next_img = f(R.id.next_img);

        GroupType = getIntent().getStringExtra("group_type");
        GroupName = getIntent().getStringExtra("group_name");
        GroupInfo = getIntent().getStringExtra("group_info");
        fp = new FlashChatMemberPresenter(this);
    }

    @Override
    protected void initData() {
        for (int i = 1; i < 41; i++) {
            list.add(i + "");
        }
        picker_horizontal.setData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_img:
                Num = picker_horizontal.getItem() + 1;
                fp.onCreateFlashChatGroup(GroupName, GroupInfo, GroupType, Num);
                break;
        }
    }

    @Override
    public void onCreateSucceed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCreateFailed(int code) {
        ToastUtil.toast2_bottom(this, getGroupErrorTxt(code));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
