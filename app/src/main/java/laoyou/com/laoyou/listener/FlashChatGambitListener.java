package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.FlashTypeIconBean;

/**
 * Created by lian on 2017/12/4.
 */
public interface FlashChatGambitListener {
    void onItemClick(int position);

    void onIconInfo(List<FlashTypeIconBean> list);
}
