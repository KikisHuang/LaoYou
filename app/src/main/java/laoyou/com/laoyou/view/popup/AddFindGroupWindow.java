package laoyou.com.laoyou.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.fragment.HomeFragment.getHomeInstance;
import static laoyou.com.laoyou.utils.IntentUtils.goCertificationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOverInfoPage;


/**
 * Created by lian on 2017/3/29.
 * Page页面侧边菜单Popupwindos实现;
 */
public class AddFindGroupWindow implements View.OnClickListener {
    private static final String TAG = "SlidePopupWindow";
    private static TextView user_name;
    private static ImageView user_icon;
    private static LinearLayout login_ll, layout_1, layout_2, layout_3;
    private static PopupWindow popupWindow;
    private Context mContext;
    private UserInfoBean info;
    private int status;


    public AddFindGroupWindow(Context context, UserInfoBean info, int status) {
        this.mContext = context;
        this.info = info;
        this.status = status;
    }

    public void ScreenPopupWindow() {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.slide_popu_layout, null);
            init(contentView);
            click();
            int width = DeviceUtils.getWindowWidth(mContext) * 7 / 10;
            popupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
            backgroundAlpha(0.4f, mContext);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f, mContext);
                    popupWindow.dismiss();
                    popupWindow = null;
                    user_name = null;
                    user_icon = null;
                    login_ll = null;
                    layout_1 = null;
                    layout_2 = null;
                    layout_3 = null;
                }
            });

            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));

            // 设置好参数之后再show
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            popupWindow.showAsDropDown(LayoutInflater.from(mContext).inflate(R.layout.activity_main, null));
        }
    }

    private void click() {
        login_ll.setOnClickListener(this);

        layout_1.setOnClickListener(this);
        layout_2.setOnClickListener(this);
        layout_3.setOnClickListener(this);
    }


    private void init(View contentView) {
        login_ll = (LinearLayout) contentView.findViewById(R.id.login_ll);
        user_icon = (ImageView) contentView.findViewById(R.id.user_icon);
        user_name = (TextView) contentView.findViewById(R.id.user_name);

        layout_1 = (LinearLayout) contentView.findViewById(R.id.layout_1);
        layout_2 = (LinearLayout) contentView.findViewById(R.id.layout_2);
        layout_3 = (LinearLayout) contentView.findViewById(R.id.layout_3);

        Glide.with(mContext).load(info.getHeadImgUrl()).bitmapTransform(new CropCircleTransformation(mContext)).into(user_icon);

        user_name.setText(info.getName());
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @param mContext
     */
    public static void backgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_ll:
                goOverInfoPage(mContext, info.getHeadImgUrl(), info.getName(), "",info.getSex());
                popupWindow.dismiss();
                break;
            case R.id.layout_1:
                if (status == 0 || status == -1) {
                    goCertificationPage(mContext);
                    popupWindow.dismiss();
                } else
                    ToastUtil.toast2_bottom(mContext, "已审核通过无法修改实名信息");

                break;
            case R.id.layout_2:
                new MyAlertDialog(mContext).builder().setCancelable(true).setTitle("提示").setMsg("是否退出登录？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPreferences.saveUserToken("");
                        if (getHomeInstance() != null)
                            getHomeInstance().onLogout();

//                        if (ThreadInstance() != null){
//                            ThreadInstance().ClearThread();
//                            Fields.CHECKFLAG = false;
//                        }
                        popupWindow.dismiss();
                    }
                }).show();
                break;
            case R.id.layout_3:
                break;
        }
    }
}
