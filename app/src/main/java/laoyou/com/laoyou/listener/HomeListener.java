package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.NearbyBean;
import laoyou.com.laoyou.bean.PageTopBean;

/**
 * Created by lian on 2017/10/25.
 */
public interface HomeListener {
    void onFailed(String msg);

    void onError(String msg);

    void onOneImg(PageTopBean ptb);


    void onLogout();

    void onOutSideGo(String url);

    void BannerHide();

    void BannerShow();

    void onForbidSlide();

    void RefreshRecyclerView(List<NearbyBean> nblist);

    void onEnable(boolean b);

    void onDownload(String jsonSring);
}
