package laoyou.com.laoyou.listener;

import com.tencent.TIMFriendStatus;

import java.util.List;

import laoyou.com.laoyou.bean.AttentionGameBean;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.bean.TopicTypeBean;
import laoyou.com.laoyou.bean.UserInfoBean;

/**
 * Created by lian on 2017/12/7.
 */
public interface HomePageListener {

    void onFailedMSg(String msg);

    void onSucceed();

    void onShowUserInfo(UserInfoBean ub);

    void onCertificaTion(int status);

    void onMyHeartValue(String num);

    void onAttentGames(List<AttentionGameBean> ll);

    void onPhotoList(List<PhotoBean> photos);

    void onBottom();

    void onStatusInfo(List<TopicTypeBean> nblist);

    void onRefresh();

    void onAddFriend(TIMFriendStatus timFriendStatusUnknown);

    void onDelFriend(TIMFriendStatus status);

    void ondeleteStatus(String id);
}
