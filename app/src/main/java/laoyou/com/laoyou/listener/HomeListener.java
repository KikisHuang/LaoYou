package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.ActiveBean;
import laoyou.com.laoyou.bean.AddressBookBean;
import laoyou.com.laoyou.bean.PageTopBean;
import laoyou.com.laoyou.bean.TopicTypeBean;

/**
 * Created by lian on 2017/10/25.
 */
public interface HomeListener {
    void onFailed(String msg);

    void onOneImg(PageTopBean ptb);

    void onLogout();

    void onOutSideGo(String url);

    void BannerHide();

    void BannerShow();

    void onForbidSlide();

    void RefreshRecyclerView(List<TopicTypeBean> nblist);


    void onDownload(String jsonSring);

    void onBottom();

    void onRefresh();

    void onHideFlashChatInfo();

    void onFlashChatInfo(List<ActiveBean> ab);

    void onReComInfo(List<AddressBookBean> add);
}
