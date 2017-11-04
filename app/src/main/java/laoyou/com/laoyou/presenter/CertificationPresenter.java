package laoyou.com.laoyou.presenter;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import laoyou.com.laoyou.bean.IpBean;
import laoyou.com.laoyou.listener.CertificationListener;
import laoyou.com.laoyou.listener.HttpResultListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.Interface;
import laoyou.com.laoyou.utils.httpUtils;
import okhttp3.Request;

import static laoyou.com.laoyou.utils.JsonUtils.getKeyMap;

/**
 * Created by lian on 2017/10/25.
 */
public class CertificationPresenter implements HttpResultListener {
    private static final String TAG = "CertificationPresenter";
    private CertificationListener listener;

    public CertificationPresenter(CertificationListener listener) {
        this.listener = listener;

    }

    public void Presenter() {

    }

    public void CommitCheck(String name, String id, File frontFile, File tailFile, File handFile, String ip, String imei) {
        if (!name.isEmpty() && !id.isEmpty() && frontFile != null && tailFile != null && handFile != null)
            CommitAutonym(name, id, frontFile, tailFile, handFile, ip, imei);
        else if (name.isEmpty())
            listener.onCheckFailed(Fields.NAMENULLMSG);
        else if (id.isEmpty())
            listener.onCheckFailed(Fields.IDNULLMSG);
        else if (frontFile == null)
            listener.onCheckFailed(Fields.FRONTIDNULLMSG);
        else if (tailFile == null)
            listener.onCheckFailed(Fields.TAILIDNULLMSG);
        else if (handFile == null)
            listener.onCheckFailed(Fields.HANDIDNULLMSG);
    }

    private void CommitAutonym(String name, String id, File frontFile, File tailFile, File handFile, String ip, String imei) {
        Map<String, String> map = getKeyMap();
        map.put("name", name);
        map.put("idcard", id);
        map.put("deviceName", android.os.Build.MODEL);

        if (!Fields.address.isEmpty())
            map.put("address", Fields.address);
        if (Fields.Longitude > 0)
            map.put("longitude", String.valueOf(Fields.Longitude));
        if (Fields.Latitud > 0)
            map.put("latitude", String.valueOf(Fields.Latitud));
        if (!ip.isEmpty())
            map.put("ip", ip);
        if (!imei.isEmpty())
            map.put("deviceNumber", imei);


        Map<String, File> files = new HashMap<>();
        files.put("idcardImgFile", handFile);
        files.put("idcardImg2File", frontFile);
        files.put("idcardImg3File", tailFile);

        httpUtils.OkHttpsPost(map, this, Fields.REQUEST1, Interface.URL + Interface.APPLYQUERY, null, files);

    }

    @Override
    public void onSucceed(String response, int tag) {
        if (tag == Fields.REQUEST1)
            listener.onSucceed();
        if (tag == Fields.REQUEST3) {
            IpBean ib = new Gson().fromJson(response, IpBean.class);
            listener.setIP(ib.getIp());
        }
    }

    @Override
    public void onError(Request request, Exception e) {
        listener.onError(Fields.NETWORKERROR);
    }

    @Override
    public void onParseError(Exception e) {
        Log.i(TAG, "解析异常 Error ===" + e);
    }

    @Override
    public void onFailed(String response, int code, int tag) {
        switch (tag) {
            case Fields.REQUEST1:
                listener.onFailed(response);
                break;
            case Fields.REQUEST3:
                listener.setIP("");
                break;
        }
    }

    public void getAddressIP() {

        httpUtils.OkHttpsCustomGet(null, this, Fields.REQUEST3, Interface.IPQUERYURL);
    }
}
