package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.IntentUtils.goFlashChatGambitPage;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/4.
 */
public class FlashChatBasicsActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "FlashChatBasicsActivity";
    private EditText group_name_ed, intro_ed;
    private ImageView next_img, clear_name;
    private CharSequence wordNum;//记录输入的字数
    private RelativeLayout titles_layout;

    @Override
    protected void click() {
        next_img.setOnClickListener(this);
        clear_name.setOnClickListener(this);
        group_name_ed.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    group_name_ed.setText(str1);
                    group_name_ed.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordNum.length() >= 1) {
                    Glide.with(MyApplication.getContext()).load(R.mipmap.next_icon).into(next_img);
                    clear_name.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(MyApplication.getContext()).load(R.mipmap.next_icon_gray).into(next_img);
                    clear_name.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    protected void init() {
        setContentView(R.layout.flash_chat_basics_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitlesAndBack(this, gets(R.string.goback), "");
        titles_layout = f(R.id.titles_layout);
        titles_layout.setBackgroundResource(R.color.background_color);
        intro_ed = f(R.id.intro_ed);
        group_name_ed = f(R.id.group_name_ed);
        next_img = f(R.id.next_img);
        clear_name = f(R.id.clear_name);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_img:
                if (group_name_ed.getText().toString().trim().length() > 0)
                    goFlashChatGambitPage(this, group_name_ed.getText().toString().trim(), intro_ed.getText().toString().trim());

                break;
            case R.id.clear_name:
                group_name_ed.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Fields.ACRESULET3) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
