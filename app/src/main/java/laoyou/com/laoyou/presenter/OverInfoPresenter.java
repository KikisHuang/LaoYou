package laoyou.com.laoyou.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;

import java.io.File;
import java.util.Map;

import laoyou.com.laoyou.activity.OverInfoActivity;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.OverInfoListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.FilesUtil.getFileMap;
import static laoyou.com.laoyou.utils.FilesUtil.getParamsMap;
import static laoyou.com.laoyou.utils.JsonUtils.getJsonSring;
import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhoto;

/**
 * Created by lian on 2017/10/26.
 */
public class OverInfoPresenter implements HttpResultListener {
    private static final String TAG = "OverInfoPresenter";
    private OverInfoListener listener;
    private File file;
    private String name;

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
    public void onSucceed(String response, int tag) {
        switch (tag) {
            case Fields.REQUEST1:
                try {
                    SPreferences.saveUserToken(getJsonSring(response));
                    ChangeInfo(file, name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case Fields.REQUEST2:
                listener.onSucced();

                break;
        }
    }

    private void ChangeInfo(File file, String name) {
        Map<String, String> map = getKeyMap();
        map.put("name", name);

        if (file != null) {
            Map<String, File> f = getFileMap();
            f.put("file", file);
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.MODIFYUSER, null, f);
        } else
            httpUtils.OkHttpsPost(map, this, Fields.REQUEST2, Interface.URL + Interface.MODIFYUSER, null, null);
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onErrorMsg(Fields.NETWORKERROR);
    }

    public void onParseError(Exception e) {
        Log.i(TAG, "解析Error ==" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }

    public void Register(String phone, String pass, String code) {
        Map<String, String> map = getParamsMap();
        map.put("phone", phone);
        map.put("password", pass);
        map.put("code", code);
        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.REGISTER);
    }

    public void CheckInfo(File headFile, String name, String phone, String pass, String code) {

        if (!phone.isEmpty() && !pass.isEmpty() && code.isEmpty())
            ChangeInfo(headFile, name);
        else {
            if (headFile != null && !name.isEmpty()) {
                file = headFile;
                this.name = name;
                Register(phone, pass, code);
//            httpUtils.OkHttpsPost();
            } else if (headFile == null)
                listener.onErrorMsg(Fields.HEADNULLMSG);
            else if (name.isEmpty())
                listener.onErrorMsg(Fields.NICKNAMENULLMSG);
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
        if (phone == null || pass == null) {
            listener.setHeadImgAndName("", "");
        } else if (!phone.isEmpty() && !pass.isEmpty() && code.isEmpty())
            listener.setHeadImgAndName(phone, pass);
    }
}
