package laoyou.com.laoyou.view.popup;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.TopicCommentListener;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.DeviceUtils;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;

/**
 * Created by lian on 2017/6/5.
 * 客服页面；
 */
public class CommentPhotoPopupWindow {
    private static final String TAG = getTAG(CommentPhotoPopupWindow.class);
    private ImageView comment_img;
    private ImageView cancel_comment_img;
    private Context mContext;
    private String wx = "";
    private PopupWindow popupWindow;
    private TopicCommentListener listener;

    public CommentPhotoPopupWindow(TopicCommentListener listener) {
        this.mContext = SPreferences.context;
        this.listener = listener;
    }

    public synchronized void ScreenPopupWindow(String imgPath, View view) {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.comment_photo_include, null);
            int wh = DeviceUtils.dip2px(mContext, 90);
            popupWindow = new PopupWindow(contentView, wh, wh);
            popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
            init(contentView);
            iniData(imgPath);
            click();

            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(false);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
//              popupWindow.dismiss();
                    popupWindow = null;
                    comment_img = null;
                    cancel_comment_img = null;
                }
            });
            int[] location = new int[2];
            //获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
            view.getLocationOnScreen(location);

            //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
            popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, location[1] - wh);
//        popupWindow.showAtLocation(view, Gravity.TOP|Gravity.LEFT ,0, 0);
        } else {
            try {
                Glide.with(mContext).load(imgPath).into(comment_img);
            } catch (Exception e) {
                Log.e(TAG, "Error === " + e);
            }
        }
    }

    private void iniData(String imgPath) {
        Glide.with(mContext).load(imgPath).into(comment_img);
    }

    private void click() {

        comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoImgClick();
            }
        });
        cancel_comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoCancle();
                popupWindow.dismiss();
            }
        });
    }

    private void init(View contentView) {
        comment_img = (ImageView) contentView.findViewById(R.id.comment_img);
        cancel_comment_img = (ImageView) contentView.findViewById(R.id.cancel_comment_img);
    }

    /**
     * 跟随控件移动;
     *
     * @param view
     */
    public void MoveWindow(View view) {
        if (popupWindow != null) {
            int wh = DeviceUtils.dip2px(mContext, 90);
            int[] location = new int[2];
            //获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
            view.getLocationOnScreen(location);
            popupWindow.update(0, location[1] - wh, wh, wh);
        }
    }

    /**
     * Activity结束的情况下popup还显示则关闭pop;
     */
    public void ClosePopup() {
        if (popupWindow != null)
            popupWindow.dismiss();
    }
}
