package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.TopicTypeBean;

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

    void RefreshRecyclerView(List<TopicTypeBean> nblist);

    void onEnable(boolean b);

    void onDownload(String jsonSring);

    void onBottom();

    void onRefresh();

}
