package laoyou.com.laoyou.activity;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.CreateGroupAdapter;
import laoyou.com.laoyou.adapter.InfoAdapter;
import laoyou.com.laoyou.adapter.SearchAdapter;
import laoyou.com.laoyou.bean.AddressBean;
import laoyou.com.laoyou.listener.AddressBookListener;
import laoyou.com.laoyou.listener.EditChangedListener;
import laoyou.com.laoyou.listener.EdittextListener;
import laoyou.com.laoyou.presenter.AddressbookPresenter;
import laoyou.com.laoyou.utils.AnimationUtil;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.HorizontalListView;

import static laoyou.com.laoyou.tencent.ui.ConversationFragment.ConversationInstance;
import static laoyou.com.laoyou.utils.AnimationUtil.showAndHiddenAnimation;
import static laoyou.com.laoyou.utils.Cn2Spell.getPinYin;

/**
 * Created by lian on 2017/11/27.
 */
public class MyCreateGroupActivity extends InitActivity implements AddressBookListener, View.OnClickListener, View.OnKeyListener, EdittextListener {

    private static final String TAG = "MyCreateGroupActivity";

    private TextView cancle_tv, confirm_tv, member_tv, none_tv;
    private ImageView search_img;
    private LinearLayout search_layout;
    private EditText search_ed;
    private AddressbookPresenter ap;
    private ListView listView, sratch_listView;
    //水平listView;
    private HorizontalListView infolistView;
    /**
     * list 好友列表集合
     * adapter 还有列表适配器
     */
    private List<AddressBean> list;
    private CreateGroupAdapter adapter;
    /**
     * searchlist 搜索集合
     * searchadapter 搜索适配器
     */
    private List<AddressBean> searchlist;
    private SearchAdapter searchadapter;
    /**
     * addGroupPeopleList 添加信息集合
     * infoadapter 添加信息适配器
     */
    private InfoAdapter infoadapter;
    private List<AddressBean> addGroupPeopleList;


    @Override
    protected void click() {
        cancle_tv.setOnClickListener(this);
        confirm_tv.setOnClickListener(this);
        search_img.setOnClickListener(this);
        search_ed.setOnKeyListener(this);
        search_ed.addTextChangedListener(new EditChangedListener(this));
    }

    @Override
    protected void init() {
        setContentView(R.layout.create_group_layout);
        ap = new AddressbookPresenter(this);
        listView = f(R.id.listView);
        sratch_listView = f(R.id.sratch_listView);
        infolistView = f(R.id.infolistView);
        member_tv = f(R.id.member_tv);
        confirm_tv = f(R.id.confirm_tv);
        cancle_tv = f(R.id.cancle_tv);
        none_tv = f(R.id.none_tv);

        search_img = f(R.id.search_img);
        search_layout = f(R.id.search_layout);
        search_ed = f(R.id.search_ed);

        searchlist = new ArrayList<>();
        searchadapter = new SearchAdapter(this, searchlist, this);
        sratch_listView.setAdapter(searchadapter);

        addGroupPeopleList = new ArrayList<>();
        AddressBean ab = new AddressBean();
        ab.setIdentifier("");
        addGroupPeopleList.add(ab);
        infoadapter = new InfoAdapter(this, addGroupPeopleList);
        infolistView.setAdapter(infoadapter);

        list = new ArrayList<>();
        adapter = new CreateGroupAdapter(this, list, this);
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
    public void onSelected(List<AddressBean> list, int pos) {
        if (pos == 10293) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIdentifier().equals(list.get(pos).getIdentifier()) && list.get(i).getFaceUrl().equals(list.get(pos).getFaceUrl())) {
                    list.get(i).setAddFlag(!list.get(i).isAddFlag());
                    adapter.notifyDataSetChanged();
                    ap.AddOfCancle(list.get(i).isAddFlag(), list.get(pos).getIdentifier(), list.get(pos).getFaceUrl(), list.get(pos).getNickName());
                    break;
                }
            }
            search_ed.setText("");
            searchlist.clear();
            searchadapter.notifyDataSetChanged();

            sratch_listView.setVisibility(View.GONE);
            search_layout.setVisibility(View.GONE);
            none_tv.setVisibility(View.GONE);

            showAndHiddenAnimation(member_tv, null, AnimationUtil.AnimationState.STATE_SHOW, 300);
            showAndHiddenAnimation(search_img, null, AnimationUtil.AnimationState.STATE_SHOW, 300);
            search_ed.setCursorVisible(false);//动态代码设置隐藏guangbiao
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        } else {
            list.get(pos).setAddFlag(!list.get(pos).isAddFlag());
            adapter.notifyDataSetChanged();
            ap.AddOfCancle(list.get(pos).isAddFlag(), list.get(pos).getIdentifier(), list.get(pos).getFaceUrl(), list.get(pos).getNickName());
        }

    }

    @Override
    public void GoHomePage(String identifier) {

    }

    @Override
    public void onAdd(String faceUrl, String identifier, String name) {

        if (addGroupPeopleList.size() == 1 && addGroupPeopleList.get(0).getIdentifier().isEmpty()) {
            Log.i(TAG, "clear集合 ===");
            addGroupPeopleList.clear();
        }
        AddressBean ab = new AddressBean();
        ab.setIdentifier(identifier);
        ab.setNickName(name);
        ab.setFaceUrl(faceUrl);
        addGroupPeopleList.add(ab);
        infoadapter.notifyDataSetChanged();
        Log.i(TAG, "添加信息  size ===" + addGroupPeopleList.size() + " id ==" + identifier + " face ==" + name);
    }

    @Override
    public void onCancle(String faceUrl, String identifier, String name) {

        for (int i = 0; i < addGroupPeopleList.size(); i++) {

            if (addGroupPeopleList.get(i).getFaceUrl().equals(faceUrl) && addGroupPeopleList.get(i).getIdentifier().equals(identifier)) {
                addGroupPeopleList.remove(i);
                if (addGroupPeopleList.size() == 0) {
                    AddressBean ab = new AddressBean();
                    ab.setIdentifier("");
                    addGroupPeopleList.add(ab);
                }
                infoadapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onCreateGroupSuccess(String msg) {
        ConversationInstance().presenter.getConversation();
        ToastUtil.toast2_bottom(this, msg);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle_tv:
                finish();
                break;
            case R.id.confirm_tv:
                ap.CreateGroup(addGroupPeopleList);
                break;
            case R.id.search_img:
                showAndHiddenAnimation(member_tv, null, AnimationUtil.AnimationState.STATE_HIDDEN, 300);
                showAndHiddenAnimation(search_img, search_layout, AnimationUtil.AnimationState.STATE_HIDDEN, 300);
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (search_ed.getText().toString().length() <= 0) {
                sratch_listView.setVisibility(View.GONE);
                search_layout.setVisibility(View.GONE);
                none_tv.setVisibility(View.GONE);

                showAndHiddenAnimation(member_tv, null, AnimationUtil.AnimationState.STATE_SHOW, 300);
                showAndHiddenAnimation(search_img, null, AnimationUtil.AnimationState.STATE_SHOW, 300);
                search_ed.setCursorVisible(false);//动态代码设置隐藏guangbiao
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //TODO:
            return false;
        }
        return false;
    }

    @Override
    public void onQuery(String s) {
        searchlist.clear();
        int num = 0;
        for (AddressBean ab : list) {
            String name = ab.getNickName() != null || !ab.getNickName().isEmpty() ? getPinYin(ab.getNickName()) : getPinYin(ab.getIdentifier());

            if (s.trim().length() > 1) {
                if (name.indexOf(getPinYin(s.trim())) != -1) {
                    searchlist.add(ab);
                    none_tv.setVisibility(View.GONE);
                    sratch_listView.setVisibility(View.VISIBLE);
                    searchadapter.notifyDataSetChanged();
                    num++;
                }

            } else if (s.trim().length() == 1) {
                if (name.indexOf(getPinYin(s.trim())) != -1) {

                    searchlist.add(ab);
                    none_tv.setVisibility(View.GONE);
                    sratch_listView.setVisibility(View.VISIBLE);
                    searchadapter.notifyDataSetChanged();
                    num++;
                }
            }
        }
        if (num <= 0)
            none_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClear() {
        searchlist.clear();
        searchadapter.notifyDataSetChanged();
    }

    @Override
    public void onContentChange(String s) {

    }
}
