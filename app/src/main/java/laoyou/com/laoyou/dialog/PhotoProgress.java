package laoyou.com.laoyou.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;
import static laoyou.com.laoyou.utils.TextViewColorUtils.setTextColor2;

/**
 * Created by lian on 2017/6/19.
 */
public class PhotoProgress extends Dialog {
    private static final String TAG = getTAG(PhotoProgress.class);
    public static PhotoProgress dialog;
    public static ProgressBar pb;

    public PhotoProgress(Context context) {
        super(context);
    }

    public PhotoProgress(Context context, int theme) {
        super(context, theme);
    }


    /**
     * 弹出自定义ProgressDialog
     *
     * @param context    上下文
     * @param cancelable 是否按返回键取消
     * @return
     */
    public static PhotoProgress LoadingShow(Context context, boolean cancelable, String msg) {
        if (context != null) {
            dialog = new PhotoProgress(context, R.style.Custom_Progress);
            dialog.setTitle("");
            dialog.setContentView(R.layout.photo_progress_layout);
            pb = (ProgressBar) dialog.findViewById(R.id.progressbar);
            TextView msg_tv = (TextView) dialog.findViewById(R.id.msg_tv);
            Random ra = new Random();
            int x = ra.nextInt(100);
            //升级包大小随意填写;
            setTextColor2(msg_tv, msg + Fields.br, "   升级包大小：5." + x + "M", "#FF4D87");

            pb.setVisibility(View.VISIBLE);

            // 按返回键是否取消
            dialog.setCancelable(cancelable);
            // 设置居中
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            // 设置背景层透明度
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.show();
            Log.i(TAG, "dialog show!!!!");
            return dialog;
        } else
            return null;
    }

    public static void SettingRate(int pro) {
        if (pb != null && pb.getVisibility() == View.VISIBLE)
            pb.setProgress(pro);
    }

    public static void LoadingCancle() {
        if (dialog != null && dialog.isShowing()) {
            Log.i(TAG, "dialog Cancle!!!!");
            dialog.cancel();
            if (pb != null && pb.getVisibility() == View.VISIBLE) {
                pb.setVisibility(View.GONE);
            }
            pb = null;
            dialog = null;
        }
    }
}