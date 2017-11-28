package laoyou.com.laoyou.listener;

import java.util.List;

import laoyou.com.laoyou.bean.AddressBean;

/**
 * Created by lian on 2017/11/23.
 */
public interface AddressBookListener {

    void onErrorMsg(String desc);

    void ongetFriendData(List<AddressBean> result);

    void onSelected(String identifier, String faceUrl, int pos);

    void onAdd(String identifier, String faceUrl);

    void onCancle(String identifier, String faceUrl);

    void onCreateGroupSuccess(String gets);
}
