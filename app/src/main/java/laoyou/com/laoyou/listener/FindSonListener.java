package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.NearbyBean;

/**
 * Created by lian on 2017/12/11.
 */
public interface FindSonListener {
    void onSucceed();

    void onFailedMsg(String str);

    void RefreshRecyclerView(List<NearbyBean> nblist);
}
