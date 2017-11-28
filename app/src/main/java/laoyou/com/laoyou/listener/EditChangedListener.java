package laoyou.com.laoyou.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by lian on 2017/6/8.
 * edittext内容状态监听;
 */
public class EditChangedListener implements TextWatcher {
    private CharSequence temp; // 监听前的文本
    private EdittextListener listener;

    public EditChangedListener(EdittextListener listener) {
        this.listener = listener;
    }

    // 输入文本之前的状态
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        temp = s;
    }

    // 输入文字中的状态，count是一次性输入字符数
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//          if (charMaxNum - s.length() <= 5) {
//              tip.setText("还能输入" + (charMaxNum - s.length()) + "字符");
//          }
        if (s.length() > 0)
            listener.onQuery(String.valueOf(s));
        else
            listener.onClear();

    }

    // 输入文字后的状态
    @Override
    public void afterTextChanged(Editable s) {
 /*       *//** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 *//*
        editStart = editText.getSelectionStart();
        editEnd = editText.getSelectionEnd();
        if (temp.length() > charMaxNum) {
//              Toast.makeText(getApplicationContext(), "最多输入10个字符", Toast.LENGTH_SHORT).show();
            s.delete(editStart - 1, editEnd);
            editText.setText(s);
            editText.setSelection(s.length());
        }
    }*/
    }
}
