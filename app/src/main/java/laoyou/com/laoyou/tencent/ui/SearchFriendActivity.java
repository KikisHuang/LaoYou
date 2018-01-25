package laoyou.com.laoyou.tencent.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.tencent.adapters.SearchFindAdapter;
import laoyou.com.laoyou.tencent.presentation.presenter.FriendshipManagerPresenter;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.SearchListener;
import laoyou.com.laoyou.tencent.utils.SearchBean;

import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;

/**
 * 查找添加新朋友
 */
public class SearchFriendActivity extends Activity implements AdapterView.OnItemClickListener, View.OnKeyListener, SearchListener {

    private final static String TAG = "SearchFriendActivity";

    private FriendshipManagerPresenter presenter;
    ListView mSearchList;
    EditText mSearchInput;
    TextView tvNoResult;
    SearchFindAdapter adapter;
    List<SearchBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        mSearchInput = (EditText) findViewById(R.id.inputSearch);
        mSearchList = (ListView) findViewById(R.id.list);
        tvNoResult = (TextView) findViewById(R.id.noResult);
        adapter = new SearchFindAdapter(this, R.layout.item_profile_summary, list);
        mSearchList.setAdapter(adapter);
        mSearchList.setOnItemClickListener(this);
        presenter = new FriendshipManagerPresenter(this);
        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchInput.setOnKeyListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        String identify = list.get(i).getCloudTencentAccount();
//        if (!SPreferences.getIdentifier().equals(identify))
            goHomePage(this, list.get(i).getId(),false);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {   // 忽略其它事件
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                adapter.notifyDataSetChanged();
                String key = mSearchInput.getText().toString();
                if (key.equals("")) return true;

                presenter.searchFriend(key);

                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    private boolean needAdd(String id) {
        for (SearchBean item : list) {
            if (item.getCloudTencentAccount().equals(id)) return false;
        }
        return true;
    }

    @Override
    public void onShowInfo(List<SearchBean> users) {
        if (users == null) return;
        for (SearchBean item : users) {
            if (needAdd(item.getCloudTencentAccount()))
                list.add(item);
        }
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            tvNoResult.setVisibility(View.VISIBLE);
        } else {
            tvNoResult.setVisibility(View.GONE);
        }
    }
}
