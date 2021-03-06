package laoyou.com.laoyou.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.GsonUtil;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.ImUtils.CreateUserIm;
import static laoyou.com.laoyou.utils.ImUtils.getImIdentifier;
import static laoyou.com.laoyou.utils.ImUtils.getImUserSig;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.validPhoneNumber;

/**
 * Created by lian on 2017/10/26.
 */
public class LoginPresenter implements HttpResultListener {
    private static final String TAG = "LoginPresenter";
    private LoginListener listener;
    private EditText pass_ed;
    private String name = "";
    private String faceUrl = "";

    public LoginPresenter(LoginListener listener, EditText pass_ed) {
        this.listener = listener;
        this.pass_ed = pass_ed;
    }

    public void Presenter() {
        EditListenrInit();
    }

    private void EditListenrInit() {
        setListener();
    }

    private void setListener() {
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
                if (wordNum.length() >= 1)
                    listener.onCommit();
                else
                    listener.onClear();
            }
        });
    }

    public void Login(String phone, String pass) {
        if (!phone.isEmpty() && !pass.isEmpty() && validPhoneNumber(phone)) {
            Map<String, String> map = getParamsMap();
            map.put("account", phone);
            map.put("password", pass);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.LOGIN, null, null);
        } else if (phone.isEmpty())
            listener.onError(gets(R.string.phonenullmsg));
        else if (!validPhoneNumber(phone))
            listener.onError(gets(R.string.phoneuncorrectmsg));
        else if (pass.isEmpty())
            listener.onError(gets(R.string.passnullmsg));

    }

    @Override
    public void onSucceed(String response, int tag) throws JSONException {

        switch (tag) {
            case Fields.REQUEST1:
                Log.i(TAG, "server key === " + getJsonSring(response));

                SPreferences.saveUserToken(getJsonSring(response));
                //REQUEST2
                getImIdentifier(this);
                break;
            case Fields.REQUEST6:

                UserInfoBean ub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);

                if (!ub.getCloudTencentAccount().isEmpty()) {
                    SPreferences.saveIdentifier(ub.getCloudTencentAccount());
                    Log.i(TAG, "创建获得的id ===" + ub.getCloudTencentAccount());
                    //REQUEST4
                    getImUserSig(ub.getCloudTencentAccount(), this);
//                      ImportImInfo(id, name, faceUrl, this);
                }
                break;
            case Fields.REQUEST4:
                Log.i(TAG, "user sig ===" + response);
                SPreferences.saveUserSig(getJsonSring(response));
                listener.onSucceed();
                break;

            case Fields.REQUEST2:

                UserInfoBean uub = GsonUtil.GsonToBean(getJsonSring(response), UserInfoBean.class);
                SPreferences.saveUserId(uub.getId());
                faceUrl = uub.getHeadImgUrl();
                name = uub.getName();

                if (uub.getCloudTencentAccount() != null && !uub.getCloudTencentAccount().isEmpty()) {
                    Log.i(TAG, "详情获得的id ===" + uub.getCloudTencentAccount());
                    SPreferences.saveIdentifier(uub.getCloudTencentAccount());
                    //REQUEST4
                    getImUserSig(uub.getCloudTencentAccount(), this);
                } else
                    CreateUserIm(this);

                break;
            case Fields.REQUEST5:
                Log.i(TAG, response);
//                listener.onSucceed();
                break;

        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onError(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }

}
