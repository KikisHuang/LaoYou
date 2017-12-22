package laoyou.com.laoyou.activity;

import android.widget.ListView;

import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/12/21.
 */
public class ParticipationActivity extends InitActivity {

    private static final String TAG = "ParticipationActivity";
    private ListView listView;
    private String Listname;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.participation_layout);
        listView = f(R.id.listView);
        Listname = getIntent().getStringExtra("List_Name");
    }

    @Override
    protected void initData() {

    }
}
