package laoyou.com.laoyou.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.TopicTypeListAdapter;
import laoyou.com.laoyou.listener.AddTopicTypeListener;
import laoyou.com.laoyou.listener.EditChangedListener;
import laoyou.com.laoyou.listener.EdittextListener;
import laoyou.com.laoyou.presenter.AddTopicTypePresenter;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/12/28.
 */
public class AddTopicTypeActivity extends InitActivity implements EdittextListener, View.OnClickListener, AddTopicTypeListener {

    private static final String TAG = getTAG(AddTopicTypeActivity.class);
    private EditText topic_type_name_ed;
    private ListView listView;
    private List<String> list;
    private TextView over_topic_type_tv;
    private AddTopicTypePresenter ap ;
    private TopicTypeListAdapter adapter;

    @Override
    protected void click() {
        topic_type_name_ed.addTextChangedListener(new EditChangedListener(this));
        over_topic_type_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.add_topic_type_layout);
        list = new ArrayList<>();
        ap = new AddTopicTypePresenter(this);
        topic_type_name_ed = f(R.id.topic_type_name_ed);
        over_topic_type_tv = f(R.id.over_topic_type_tv);
        listView = f(R.id.listView);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onQuery(String s) {

    }

    @Override
    public void onClear() {

    }

    @Override
    public void onContentChange(String s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_topic_type_tv:
                if (topic_type_name_ed.getText().toString().length() > 0)


                    break;

        }
    }

    @Override
    public void onFailedsMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onSucceed() {

    }
}
