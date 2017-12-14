package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.MyCommentAdapter;
import laoyou.com.laoyou.adapter.MyHeartValueAdapter;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.utils.SpringUtils;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/5/5.
 */
public class CommentFragment extends BaseFragment implements SpringListener {
    private static final String TAG = getTAG(CommentFragment.class);
    private SpringView springView;
    private int tag;
    private int flag;
    private ListView listView;
    private MyCommentAdapter cadapter;
    private MyHeartValueAdapter hadapter;
    private List<String> list;

    public static CommentFragment setTag(int tag, int flag) {
        CommentFragment f = new CommentFragment();
        Bundle args = new Bundle();
        args.putString("Tag", String.valueOf(tag));
        args.putString("Flag", String.valueOf(flag));
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = Integer.parseInt(getArguments() != null ? getArguments().getString("Tag") : "0");
        flag = Integer.parseInt(getArguments() != null ? getArguments().getString("Flag") : "0");
    }

    @Override
    protected void click() {

    }

    private void getData() {
    }

    @Override
    protected int initContentView() {
        return R.layout.comment_fragment;
    }

    protected void init() {
        listView = f(R.id.listView);
        springView = f(R.id.springView);
        SpringUtils.SpringViewInit(springView, getActivity(), this);

        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        switch (flag) {
            case 0:
                cadapter = new MyCommentAdapter(getActivity(), list);
                listView.setAdapter(cadapter);
                break;
            case 1:
                hadapter = new MyHeartValueAdapter(getActivity(), list);
                listView.setAdapter(hadapter);
                break;
        }
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void IsonRefresh(int init) {
        switch (flag) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public void IsonLoadmore(int move) {
        switch (flag) {
            case 0:
                break;
            case 1:
                break;
        }
    }
}
