package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goFlashChatBasicsPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class CreateFlashChatActivity extends InitActivity implements View.OnClickListener {
    private ImageView next_img;

    @Override
    protected void click() {
        next_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.create_flash_chat_layout);
        setTitlesAndBack(this, gets(R.string.goback), "");
        next_img = f(R.id.next_img);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_img:
            goFlashChatBasicsPage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Fields.ACRESULET3){
            ToastUtil.toast2_bottom(this, "创建成功！");
            finish();
        }
    }
}
