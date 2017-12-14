package laoyou.com.laoyou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by lian on 2017/4/22.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initContentView(), container, false);
        init();
        click();
        initData();
        return view;
    }

    protected abstract int initContentView();

    /**
     * 添加监听事件
     */
    protected abstract void click();

    /**
     * 所有初始化在此方法完成
     */
    protected abstract void init();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 自定义findViewById方法;
     *
     * @param viewID
     * @param <T>
     * @return
     */
    public <T> T f(int viewID) {
        return (T) view.findViewById(viewID);
    }

}
