package laoyou.com.laoyou.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.Map;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.listener.RegisterListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getParamsMap;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.validPhoneNumber;


/**
 * Created by lian on 2017/10/26.
 */
public class RegisterPresenter implements HttpResultListener {
    private static final String TAG = "RegisterPresenter";
    private RegisterListener listener;

    private boolean sflag;
    private Handler handler;
    private int page = 20;
    private Runnable regisRunnable;


    public RegisterPresenter(RegisterListener listener) {
        this.listener = listener;
    }

    public void Presenter() {
        hand();
    }

    private void hand() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        listener.onCountDown(page);
                        if (page <= 0) {
                            listener.onOver();
                            sflag = false;
                            page = 20;
                        } else
                            page--;
                        break;
                }

            }
        };
    }

    /**
     * EditText空格禁止;
     *
     * @param ed
     */
    public void EdittextInit(final EditText ed) {
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    ed.setText(str1);
                    ed.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取验证码;
     *
     * @param phone
     */
    public void getPhoneCode(String phone) {
        Map<String, String> map = getParamsMap();
        map.put("phone", phone);

        httpUtils.OkHttpsGet(map, this, Fields.REQUEST1, Interface.URL + Interface.GETREGISTERCODE);
    }


    /**
     * 开启倒计时;
     *
     * @param phone
     */
    public void CodeCountDown(String phone) {
        if (validPhoneNumber(phone)) {
            getPhoneCode(phone);
        } else
            listener.onPhoneLengthError();
    }

    public void HandlerClear() {
        if (handler != null) {
            handler.removeCallbacks(regisRunnable);
            handler = null;
            sflag = false;
        }
    }

    public void CommitData(String phone, String pass, String code, boolean codeOk) {
        if (!phone.isEmpty() && validPhoneNumber(phone) && !pass.isEmpty() && !code.isEmpty() && codeOk)
            listener.OverInfo();
        else if (phone.isEmpty())
            listener.onRegisterFailedMsg(gets(R.string.phonenullmsg));
        else if (!validPhoneNumber(phone))
            listener.onRegisterFailedMsg(gets(R.string.phoneuncorrectmsg));
        else if (pass.isEmpty())
            listener.onRegisterFailedMsg(gets(R.string.passnullmsg));
        else if (code.isEmpty())
            listener.onRegisterFailedMsg(gets(R.string.codenullmsg));
        else if (!codeOk)
            listener.onRegisterFailedMsg(gets(R.string.dontgetcode));
    }

    @Override
    public void onSucceed(String response, int tag) {
        switch (tag) {
            //获取验证码回调;
            case Fields.REQUEST1:
                listener.onRegisterFailedMsg("已发送验证码到手机..");
                //开启下一步开关;
                listener.onForbid();
                sflag = true;
                new Thread(regisRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "倒计时...");
                        while (sflag) {
                            Message msg = new Message();
                            msg.what = 0;
                            if (handler != null)
                                handler.sendMessage(msg);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case Fields.REQUEST2:
                listener.SendCode();
                break;
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onFailed(gets(R.string.networkerror));
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        listener.onFailed(response);
    }

    /**
     * 检测账号是否注册过,检测完毕如未注册发送验证码;
     */
    public void CheckAccount(String account) {
        if (validPhoneNumber(account)) {
            Map<String, String> map = getParamsMap();
            map.put("account", account);
            httpUtils.OkHttpsGet(map, this, Fields.REQUEST2, Interface.URL + Interface.CHECKACCOUNT);
        } else
            listener.onPhoneLengthError();

    }
}
