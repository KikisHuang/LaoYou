package laoyou.com.laoyou.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;

import java.io.File;
import java.util.List;
import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.activity.OverInfoActivity;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.OverInfoListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;
import top.zibin.luban.OnCompressListener;

import static laoyou.com.laoyou.utils.ComPressUtils.Compress;
import static laoyou.com.laoyou.utils.ImUtils.getImUserSig;
import static laoyou.com.laoyou.utils.JsonUtils.getFileMap;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhoto;
import static laoyou.com.laoyou.utils.SynUtils.gets;

/**
 * Created by lian on 2017/10/26.
 */
public class OverInfoPresenter implements HttpResultListener, OnCompressListener {
    private static final String TAG = "OverInfoPresenter";
    private OverInfoListener listener;
    private File file;
    private String name;
    private int sex;
    //1注册,2修改;
    private int flag = 0;

    public OverInfoPresenter(OverInfoListener listener) {
        this.listener = listener;
    }

    public void setListener(final EditText pass_ed) {
        pass_ed.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    pass_ed.setText(str1);
                    pass_ed.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordNum.length() >= 1 && wordNum.length() < 10)
                    listener.onCommit();
                else
                    listener.onClear();
            }
        });
    }

    public void ChangeHeadImg(OverInfoActivity ac, int i) {
        getMULTIPLEPhoto(ac, i);
    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    SPreferences.saveUserToken(getJsonSring(response));
                    ChangeInfo(file);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case Fields.REQUEST2:
                listener.onSucced(flag);

                break;
            case Fields.REQUEST3:

                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                SPreferences.saveUserId(ub.getId());
                SPreferences.saveUserHeadImg(ub.getHeadImgUrl());
                SPreferences.saveUserName(ub.getName());

                if (ub.getCloudTencentAccount() != null && !ub.getCloudTencentAccount().isEmpty()) {
                    SPreferences.saveIdentifier(ub.getCloudTencentAccount());
                    getImUserSig(ub.getCloudTencentAccount(), this);
                } else
                    listener.onImFailed(gets(R.string.get_im_info_error));

                break;
            case Fields.REQUEST4:
                Log.i(TAG, "user sig ===" + response);
                SPreferences.saveUserSig(getJsonSring(response));
                listener.onImSucceed();
                break;
        }
    }


    private void ChangeInfo(File file) {
        Map<String, String> map = getKeyMap();
        map.put("name", name);
        map.put("sex", String.valueOf(sex));

        if (file != null) {
            Map<String, File> f = getFileMap();
            f.put("file", file);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.MODIFYUSER, null, f);
        } else
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.MODIFYUSER, null, null);
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onErrorMsg(gets(R.string.networkerror));
    }

    public void onParseError(Exception e) {
        Log.i(TAG, "解析Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        switch (tag) {
            case Fields.REQUEST3:
                listener.onImFailed(gets(R.string.get_im_info_error));
                break;
            case Fields.REQUEST4:
                listener.onImFailed(gets(R.string.get_im_info_error));
                break;
            default:
                listener.onFailed(response);

        }

    }

    public void Register(String phone, String pass, String code) {
        Map<String, String> map = getParamsMap();
        map.put("phone", phone);
        map.put("password", pass);
        map.put("code", code);
        map.put("v2Flag", String.valueOf(1));
        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.REGISTER,null,null);
    }

    public void CheckInfo(File headFile, String name, String phone, String pass, String code, int sex) {
        this.sex = sex;
        this.name = name;
        //修改信息;
        if (!phone.isEmpty() && !pass.isEmpty() && code.isEmpty())
            ChangeInfo(headFile);
            //注册创建信息;
        else {
            if (headFile != null && !name.isEmpty()) {
                file = headFile;

                Register(phone, pass, code);
//            httpUtils.OkHttpsPost();
            } else if (headFile == null)
                listener.onErrorMsg(gets(R.string.headnullmsg));
            else if (name.isEmpty())
                listener.onErrorMsg(gets(R.string.nicknamenullmsg));
        }
    }

    /**
     * 判断是完善信息还是第一次添加信息;
     *
     * @param phone
     * @param pass
     * @param code
     */
    public void Changejudge(String phone, String pass, String code) {
        if (code != null && !code.isEmpty()) {
            flag = 1;
            listener.setHeadImgAndName("", "");
        } else if (!phone.isEmpty() && !pass.isEmpty() && code.isEmpty()) {
            flag = 2;
            listener.setHeadImgAndName(phone, pass);
        }
    }

    /**
     * 获取腾讯Im Identifier;
     */
    public void getImIdentifier() {
        Map<String, String> map = getKeyMap();
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST3, Interface.URL + Interface.MYINFODETAILS);
    }

    public void CompressFile(Context context, List<String> p, int size) {
        Compress(context, p, this, size);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(File file) {
        listener.onCompressSucceed(file);
    }

    @Override
    public void onError(Throwable e) {
        listener.onErrorMsg(gets(R.string.compress_error));
    }
}
