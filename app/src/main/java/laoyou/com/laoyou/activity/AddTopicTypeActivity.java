package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicTypeListAdapter;
import laoyou.com.laoyou.bean.AddTopicTypeBean;
import laoyou.com.laoyou.bean.TopicBean;
import laoyou.com.laoyou.listener.AddTopicTypeListener;
import laoyou.com.laoyou.listener.EditChangedListener;
import laoyou.com.laoyou.listener.EdittextListener;
import laoyou.com.laoyou.presenter.AddTopicTypePresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/12/28.
 */
public class AddTopicTypeActivity extends InitActivity implements EdittextListener, View.OnClickListener, View.OnKeyListener, AddTopicTypeListener, AdapterView.OnItemClickListener {

    private static final String TAG = getTAG(AddTopicTypeActivity.class);
    private EditText topic_type_name_ed;
    private ListView listView;
    private List<TopicBean> list;
    private TextView over_topic_type_tv;
    private AddTopicTypePresenter ap;
    private TopicTypeListAdapter adapter;
    private ImageView close_img;
    private String TypeId = "";
    private ImageView clear_img;

    @Override
    protected void click() {
        topic_type_name_ed.addTextChangedListener(new EditChangedListener(this));
        over_topic_type_tv.setOnClickListener(this);
        close_img.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        clear_img.setOnClickListener(this);
        topic_type_name_ed.setOnKeyListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.add_topic_type_layout);
        ActivityCollector.addActivity(this, getClass());
        list = new ArrayList<>();
        ap = new AddTopicTypePresenter(this);
        topic_type_name_ed = f(R.id.topic_type_name_ed);
        over_topic_type_tv = f(R.id.over_topic_type_tv);
        listView = f(R.id.listView);
        close_img = f(R.id.close_img);
        clear_img = f(R.id.clear_img);
        adapter = new TopicTypeListAdapter(this, list);
        listView.setAdapter(adapter);
        topic_type_name_ed.setImeOptions(EditorInfo.IME_ACTION_SEND);
        topic_type_name_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onQuery(String s) {
        over_topic_type_tv.setTextColor(getRouColors(R.color.blue_text));
    }

    @Override
    public void onClear() {
        over_topic_type_tv.setTextColor(getRouColors(R.color.content3));
    }

    @Override
    public void onContentChange(String s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_topic_type_tv:
                if (topic_type_name_ed.getText().toString().length() > 0) {
                    if (TypeId.isEmpty())
                        ap.AddTopicType(topic_type_name_ed.getText().toString());
                    else {
                        Intent intent = new Intent();
                        intent.putExtra("Topic_Type_Name", topic_type_name_ed.getText().toString().trim());
                        intent.putExtra("Topic_Type_Id", TypeId);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                break;
            case R.id.close_img:
                finish();
                break;
            case R.id.clear_img:
                topic_type_name_ed.setBackgroundResource(R.drawable.imaginary_circ_editt);
                topic_type_name_ed.setText("");
                TypeId = "";
                listView.setVisibility(View.VISIBLE);
                clear_img.setVisibility(View.INVISIBLE);
                topic_type_name_ed.setEnabled(false);
                break;
        }
    }

    @Override
    public void onFailedsMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed(AddTopicTypeBean attb) {
        Intent intent = new Intent();
        intent.putExtra("Topic_Type_Name", attb.getName());
        intent.putExtra("Topic_Type_Id", attb.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onTopicTypeList(List<TopicBean> ar) {
        for (TopicBean tb : ar) {
            list.add(tb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        topic_type_name_ed.setBackgroundResource(R.drawable.circ_editt);
        topic_type_name_ed.setText(list.get(position).getName());
        TypeId = list.get(position).getId();
        listView.setVisibility(View.INVISIBLE);
        clear_img.setVisibility(View.VISIBLE);
        topic_type_name_ed.setEnabled(false);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
            if (topic_type_name_ed.getText().toString().length() > 0) {
                topic_type_name_ed.setBackgroundResource(R.drawable.circ_editt);
                listView.setVisibility(View.INVISIBLE);
                clear_img.setVisibility(View.VISIBLE);
                topic_type_name_ed.setEnabled(false);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
