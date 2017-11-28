package laoyou.com.laoyou.utils;

import com.tencent.TIMUserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laoyou.com.laoyou.bean.AddressBean;

/**
 * Created by lian on 2017/11/23.
 */
public class FriendUtils {
    /**
     * 按字母顺序排序联系人;
     *
     * @param result
     * @return
     */
    public static List<AddressBean> getAddressBean(List<TIMUserProfile> result) {
        List<AddressBean> list = new ArrayList<>();
        for (TIMUserProfile tp : result) {
            String name = tp.getNickName() == null || tp.getNickName().isEmpty() ? tp.getIdentifier() : tp.getNickName();
            AddressBean ab = new AddressBean(name);
            ab.setRemark(tp.getRemark());
            ab.setFaceUrl(tp.getFaceUrl());
            ab.setIdentifier(tp.getIdentifier());
            list.add(ab);
        }

        Collections.sort(list);
        return list;
    }
}
