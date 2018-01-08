package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.AddressBean;

/**
 * Created by lian on 2017/11/23.
 */
public interface AddressBookListener {

    void onErrorMsg(String desc);

    void ongetFriendData(List<AddressBean> result);

    void onAdd(String faceUrl, String identifier, String name);

    void onCancle(String faceUrl, String identifier, String name);

    void onCreateGroupSuccess(String gets);

    void onSelected(List<AddressBean> list, int pos);

    void GoHomePage(String identifier);
}
