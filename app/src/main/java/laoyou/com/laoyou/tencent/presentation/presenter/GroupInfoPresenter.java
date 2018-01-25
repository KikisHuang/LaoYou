package laoyou.com.laoyou.tencent.presentation.presenter;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMValueCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupInfoView;
import laoyou.com.laoyou.tencent.presentation.viewfeatures.GroupUpLoadListener;
import laoyou.com.laoyou.utils.Interface;
import okhttp3.Call;


/**
 * 群信息逻辑
 */
public class GroupInfoPresenter implements TIMValueCallBack<List<TIMGroupDetailInfo>> {

    private GroupInfoView view;
    private boolean isInGroup;
    private List<String> groupIds;
    private GroupUpLoadListener listener;

    public GroupInfoPresenter(GroupInfoView view, List<String> groupIds, boolean isInGroup, GroupUpLoadListener listener) {
        this.view = view;
        this.isInGroup = isInGroup;
        this.groupIds = groupIds;
        this.listener = listener;
    }


    public void getGroupDetailInfo() {
        if (isInGroup) {
            TIMGroupManager.getInstance().getGroupDetailInfo(groupIds, this);
        } else {
            TIMGroupManager.getInstance().getGroupPublicInfo(groupIds, this);
        }
    }

    /**
     * 上传群组头像;
     */
    public void UploadGroupImg(String key, File f, String identify) {

        OkHttpUtils.post()
                .addFile("faceImg", f.getName(), f)
                .url(Interface.URL+Interface.GROUPBASEINFO)
                .addParams("key", key)
                .addParams("groupId",identify)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onErrorMSg("网络不顺畅");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);

                            if (code == 1) {
                                listener.onSucceed();
                            } else
                                listener.onErrorMSg(ErrorMsg(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onError(int i, String s) {

    }

    /**
     * 获取Code通用方法;
     *
     * @return
     */
    public static int getCode(String str) throws JSONException {
        int code = new JSONObject(str).optInt("code");
        return code;
    }

    @Override
    public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
        view.showGroupInfo(timGroupDetailInfos);
    }

    /**
     * 服务端返回异常信息
     * 如果本地存储异常code没有则toast服务端返回的msg;
     *
     * @param
     */
    public static String ErrorMsg(String src) throws JSONException {
        return new JSONObject(src).optString("erroMsg");
    }


}
