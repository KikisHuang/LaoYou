package laoyou.com.laoyou.presenter;

import laoyou.com.laoyou.listener.MainListener;

/**
 * Created by lian on 2017/10/25.
 */
public class MainPresenter {
    private MainListener listener;

    public MainPresenter(MainListener listener) {
        this.listener = listener;

    }

    public void Presenter() {
        listener.onCheckePermission();
        listener.onInitFragment();

    }
}
