package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.AddressBookAdapter;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.listener.AddressBookListener;
import laoyou.com.laoyou.presenter.AddressbookPresenter;
import laoyou.com.laoyou.tencent.ui.FriendshipManageMessageActivity;
import laoyou.com.laoyou.tencent.ui.SearchFriendActivity;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.SideBar;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;

/**
 * Created by lian on 2017/11/23.
 */
public class AddressbookActivity extends InitActivity implements AddressBookListener, View.OnClickListener {

    private static final String TAG = "AddressbookActivity";

    private ListView listView;
    private AddressbookPresenter ap;
    private String tag = "";
    private AddressBookAdapter adapter;
    private SideBar side_bar;
    private List<AddressBean> list;
    private ImageView close_img;
    private LinearLayout find_manage_layout, add_find_layout;
    private static AddressbookActivity activity;

    @Override
    protected void click() {
        add_find_layout.setOnClickListener(this);
        find_manage_layout.setOnClickListener(this);
        close_img.setOnClickListener(this);

        side_bar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < list.size(); i++) {
                    if (selectStr.equalsIgnoreCase(list.get(i).getLetter())) {
                        listView.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }

    public static AddressbookActivity AddressBookInstance() {
        return activity;
    }

    @Override
    protected void init() {
        setContentView(R.layout.address_book_layout);
        ActivityCollector.addActivity(this, getClass());
        list = new ArrayList<>();
        ap = new AddressbookPresenter(this);
        activity = this;
        listView = f(R.id.listView);
        side_bar = f(R.id.side_bar);
        close_img = f(R.id.close_img);
        View view = LayoutInflater.from(this).inflate(R.layout.address_book_head, null);
        find_manage_layout = (LinearLayout) view.findViewById(R.id.find_manage_layout);
        add_find_layout = (LinearLayout) view.findViewById(R.id.add_find_layout);

        listView.addHeaderView(view);

        list = new ArrayList<>();
        adapter = new AddressBookAdapter(this, list, this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        ap.getFriendList();
    }

    @Override
    public void onErrorMsg(String desc) {
        ToastUtil.toast2_bottom(this, desc);
    }

    @Override
    public void ongetFriendData(List<AddressBean> result) {
        list.clear();
        for (AddressBean ab : result) {
            list.add(ab);
        }
        Log.i(TAG, "list size ===" + list.size());
        if (adapter != null)
            adapter.notifyDataSetChanged();

    }

    @Override
    public void onAdd(String faceUrl, String identifier, String name) {

    }

    @Override
    public void onCancle(String faceUrl, String identifier, String name) {

    }

    @Override
    public void onCreateGroupSuccess(String gets) {

    }

    @Override
    public void onSelected(List<AddressBean> list, int pos) {

    }

    @Override
    public void GoHomePage(String identifier) {
        goHomePage(this, identifier, true);
    }

    public void onRefresh() {

        ap.getFriendList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                finish();
                break;
            case R.id.find_manage_layout:
                Intent intent = new Intent(AddressbookActivity.this, FriendshipManageMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.add_find_layout:
                Intent intents = new Intent(AddressbookActivity.this, SearchFriendActivity.class);
                startActivity(intents);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        ActivityCollector.removeActivity(this);
    }
}
