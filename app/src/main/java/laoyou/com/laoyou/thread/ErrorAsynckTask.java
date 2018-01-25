package laoyou.com.laoyou.thread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import laoyou.com.laoyou.utils.Interface;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by ${Kikis} on 2016-11-24.
 */

public class ErrorAsynckTask extends AsyncTask {
    private static final String TAG = getTAG(ErrorAsynckTask.class);
    private Context context;

    public ErrorAsynckTask(Context mContext) {
        this.context = mContext;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        String name = String.valueOf(params[0]);
        String path = String.valueOf(params[1]);

        try {
            URL url = new URL(Interface.ERRORURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            // 设置http连接属性
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + name + "\"" + end);
            ds.writeBytes(end);

            // 取得文件的FileInputStream
            FileInputStream fStream = new FileInputStream(path + File.separator + name);
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            fStream.close();
            ds.flush();
            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            Log.i(TAG, "异常信息成功上传....");
            /* 关闭DataOutputStream */
//            ((Activity)mContext).finish();
            System.exit(0);
            ds.close();
        } catch (Exception e) {

            Log.i(TAG, "异常信息上传失败...." + e);
//            ((Activity)mContext).finish();
            System.exit(0);
        }

        return null;
    }
}
