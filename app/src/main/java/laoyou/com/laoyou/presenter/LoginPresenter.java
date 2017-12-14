package laoyou.com.laoyou.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.tencent.qcloud.sdk.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.LoginListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.ImUtils.CreateUserIm;
import static laoyou.com.laoyou.utils.ImUtils.getImIdentifier;
import static laoyou.com.laoyou.utils.ImUtils.getImUserSig;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonOb;
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
            httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.LOGIN);
        } else if (phone.isEmpty())
            listener.onError(gets(R.string.phonenullmsg));
        else if (!validPhoneNumber(phone))
            listener.onError(gets(R.string.phoneuncorrectmsg));
        else if (pass.isEmpty())
            listener.onError(gets(R.string.passnullmsg));

    }

    @Override
    public void onSucceed(String response, int tag) {

        switch (tag) {
            case Fields.REQUEST1:
                try {
                    SPreferences.saveUserToken(getJsonSring(response));
                    getImIdentifier(this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                listener.onSucceed();
                break;
            case Fields.REQUEST6:
                try {
                    String id = getJsonSring(response);
                    if (!id.isEmpty()) {
                        SPreferences.saveIdentifier(id);
                        Log.i(TAG, "创建获得的id ===" + id);
                        getImUserSig(id, this);
//                      ImportImInfo(id, name, faceUrl, this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Fields.REQUEST4:
                Log.i(TAG, "user sig ===" + response);
                try {
                    SPreferences.saveUserSig(getJsonSring(response));
                    listener.onSucceed();
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onImFailed(gets(R.string.getuser_sig_error));
                }
                break;

            case Fields.REQUEST2:
                try {
                    JSONObject ob = getJsonOb(response);
                    UserInfoBean ub = new Gson().fromJson(String.valueOf(ob), UserInfoBean.class);
                    faceUrl = ub.getHeadImgUrl();
                    name = ub.getName();

                    if (ub.getCloudTencentAccount() != null && !ub.getCloudTencentAccount().isEmpty()) {
                        Log.i(TAG, "详情获得的id ===" + ub.getCloudTencentAccount());
                        SPreferences.saveIdentifier(ub.getCloudTencentAccount());
                        getImUserSig(ub.getCloudTencentAccount(), this);
                    } else
                        CreateUserIm(this);

                } catch (JSONException e) {
                    Log.e(TAG, "Error === " + e);
                    e.printStackTrace();
                }
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
