package laoyou.com.laoyou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.InternetCafAdapter;
import laoyou.com.laoyou.application.MyApplication;
import laoyou.com.laoyou.bean.CafBean;
import laoyou.com.laoyou.bean.CafCommentBean;
import laoyou.com.laoyou.dialog.MyAlertDialog;
import laoyou.com.laoyou.listener.InternetCapListener;
import laoyou.com.laoyou.listener.KeyboardChangeListener;
import laoyou.com.laoyou.listener.RequestPermissionType;
import laoyou.com.laoyou.listener.SpringListener;
import laoyou.com.laoyou.presenter.InternetCafPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.ParallaxScollListView;
import laoyou.com.laoyou.view.RoundAngleImageView;

import static laoyou.com.laoyou.utils.ClickUtils.isFastDoubleClick;
import static laoyou.com.laoyou.utils.GlideUtils.getGlideOptions;
import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goLocationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.SynUtils.IsListViewTopOfBottom;
import static laoyou.com.laoyou.utils.SynUtils.StringIsNull;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.getTypeface;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.showSoftInputFromWindow;
import static laoyou.com.laoyou.utils.TitleUtils.handleTitleBarColorEvaluate;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/12.
 */
public class InternetCafActivity extends InitActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, SpringListener, KeyboardChangeListener.KeyBoardListener, InternetCapListener, View.OnClickListener {

    private static final String TAG = "InternetCafActivity";

    private LinearLayout caf_photo_layout, config_top_layout, config_mid_layout, config_bottom_layout;
    private CircleImageView caf_logo_img;
    private TextView grade_tv;
    private TextView caf_name_tv;
    private TextView caf_address_tv;
    private TextView caf_price_tv;
    private ParallaxScollListView listView;
    private View head_layout;
    private View foot_layout;
    private ImageView back_img, background_img;
    private RelativeLayout title_layout;
    private int height;
    private int imageHeight;
    private InternetCafAdapter adapter;

    private TextView cpu, card, mouse, key, play;

    private InternetCafPresenter ip;
    private String caf_id = "";
    private String phone = "";
    private double latitude = 0;
    private double longitude = 0;
    private List<String> Barlist = new ArrayList<>();
    private FrameLayout comment_fragment_layout, call_fragment_layout, loacation_fragment_layout;
    private LinearLayout send_comment_layout, menu_layout, config_info_layout, environment_layout;
    private CircleImageView photo_img;
    private EditText comment_ed;
    private TextView send_comment_tv, foot_tv;

    private List<CafCommentBean> list;
    private KeyboardChangeListener keyboard;

    @Override
    protected void click() {
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        comment_fragment_layout.setOnClickListener(this);
        call_fragment_layout.setOnClickListener(this);
        loacation_fragment_layout.setOnClickListener(this);
        caf_photo_layout.setOnClickListener(this);
        keyboard = new KeyboardChangeListener(this);
        keyboard.setKeyBoardListener(this);
        send_comment_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.internet_cat_layout);
        ActivityCollector.addActivity(this, getClass());
        setImgTitles(this);
        //第一次进入不弹出软键盘;
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        caf_id = getIntent().getStringExtra("caf_id");
        loacation_fragment_layout = f(R.id.loacation_fragment_layout);
        call_fragment_layout = f(R.id.call_fragment_layout);
        comment_fragment_layout = f(R.id.comment_fragment_layout);
        menu_layout = f(R.id.menu_layout);
        config_info_layout = f(R.id.config_info_layout);
        environment_layout = f(R.id.environment_layout);

        send_comment_layout = f(R.id.send_comment_layout);
        photo_img = f(R.id.photo_img);
        photo_img.setVisibility(View.GONE);

        comment_ed = f(R.id.comment_ed);
        send_comment_tv = f(R.id.send_comment_tv);
        list = new ArrayList<>();

        ip = new InternetCafPresenter(this, caf_id);
        listView = f(R.id.listView);
        back_img = f(R.id.back_img);
        title_layout = f(R.id.title_layout);
        HeadViewInit();
        FootViewInit();
        listView.addHeaderView(head_layout);
        listView.addFooterView(foot_layout);

        adapter = new InternetCafAdapter(this, list);
        listView.setAdapter(adapter);

        grade_tv.setTypeface(getTypeface());
        caf_price_tv.setTypeface(getTypeface());
    }

    /**
     * 底部控件初始化;
     */
    private void FootViewInit() {
        foot_layout = LayoutInflater.from(this).inflate(R.layout.foot_include, null);

        foot_tv = (TextView) foot_layout.findViewById(R.id.foot_tv);
        foot_tv.setVisibility(View.GONE);
    }

    /**
     * 头部控件初始化;
     */
    private void HeadViewInit() {
        head_layout = LayoutInflater.from(this).inflate(R.layout.internet_cat_head_layout, null);

        config_top_layout = (LinearLayout) head_layout.findViewById(R.id.config_top_layout);
        config_mid_layout = (LinearLayout) head_layout.findViewById(R.id.config_mid_layout);
        config_bottom_layout = (LinearLayout) head_layout.findViewById(R.id.config_bottom_layout);
        background_img = (ImageView) head_layout.findViewById(R.id.background_img);

        caf_photo_layout = (LinearLayout) head_layout.findViewById(R.id.caf_photo_layout);
        caf_logo_img = (CircleImageView) head_layout.findViewById(R.id.caf_logo_img);
        grade_tv = (TextView) head_layout.findViewById(R.id.grade_tv);
        caf_name_tv = (TextView) head_layout.findViewById(R.id.caf_name_tv);
        caf_address_tv = (TextView) head_layout.findViewById(R.id.caf_address_tv);
        caf_price_tv = (TextView) head_layout.findViewById(R.id.caf_price_tv);

        listView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
        listView.setParallaxImageView(background_img);

    }

    @Override
    protected void initData() {
        ConfigTvInit();
    }

    /**
     * 网吧环境图片初始化;
     *
     * @param internetBarImgs
     */
    private void EnvironmentInit(final List<CafBean.InternetBarImgsBean> internetBarImgs) {

        if (internetBarImgs == null || internetBarImgs.size() <= 0)
            environment_layout.setVisibility(View.GONE);

        for (int i = 0; i < internetBarImgs.size(); i++) {
            int w = DeviceUtils.getWindowWidth(MyApplication.getContext()) * 1 / 3;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w * 3 / 4);

            lp.rightMargin = DeviceUtils.dip2px(MyApplication.getContext(), 10);
            RoundAngleImageView im = new RoundAngleImageView(MyApplication.getContext());

            im.setLayoutParams(lp);
            Glide.with(MyApplication.getContext()).load(internetBarImgs.get(i).getImgUrl()).apply(getGlideOptions()).into(im);

            final int finalI = i;
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Barlist.size() > 0)
                        goPhotoViewerPage(InternetCafActivity.this, Barlist, finalI, 1);

                }
            });
            caf_photo_layout.addView(im);
        }
    }

    private void ConfigTvInit() {
        TextView topo = (TextView) config_top_layout.findViewById(R.id.config_name_tv);
        cpu = (TextView) config_top_layout.findViewById(R.id.config_content_tv);
        TextView topt = (TextView) config_top_layout.findViewById(R.id.config_name1_tv);
        card = (TextView) config_top_layout.findViewById(R.id.config_content1_tv);

        topo.setText(gets(R.string.cpu));
        topt.setText(gets(R.string.graphics_card));

        TextView mido = (TextView) config_mid_layout.findViewById(R.id.config_name_tv);
        mouse = (TextView) config_mid_layout.findViewById(R.id.config_content_tv);
        TextView midt = (TextView) config_mid_layout.findViewById(R.id.config_name1_tv);
        key = (TextView) config_mid_layout.findViewById(R.id.config_content1_tv);

        mido.setText(gets(R.string.mouse));
        midt.setText(gets(R.string.keyboard));

        TextView boto = (TextView) config_bottom_layout.findViewById(R.id.config_name_tv);
        play = (TextView) config_bottom_layout.findViewById(R.id.config_content_tv);

        config_bottom_layout.findViewById(R.id.config_name1_tv).setVisibility(View.GONE);
        config_bottom_layout.findViewById(R.id.config_content1_tv).setVisibility(View.GONE);
        boto.setText(gets(R.string.display));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        int location[] = new int[2];

        head_layout.getLocationInWindow(location);
        height = location[1];

//        imageHeight = head_layout.getHeight() - DeviceUtils.dip2px(this, 0);
        handleTitleBarColorEvaluate(height, title_layout, back_img, null);

        if (IsListViewTopOfBottom(firstVisibleItem, visibleItemCount, totalItemCount, listView) == Fields.IsBottom) {
            ip.page = list.size();
            ip.getCatComment(false);
        } else if (IsListViewTopOfBottom(firstVisibleItem, visibleItemCount, totalItemCount, listView) == Fields.IsTop) {
            back_img.setImageResource(R.mipmap.return_icon_white);
            title_layout.setBackgroundColor(getRouColors(R.color.transparent));
        }

    }

    @Override
    public void onSucceed() {
        ip.getCatComment(true);
        ToastUtil.toast2_bottom(this, gets(R.string.comment_send_succeed));
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onInternetCafDetails(final CafBean cb) {

        //内存泄露;
        if (cb != null) {
            for (CafBean.InternetBarImgsBean ibi : cb.getInternetBarImgs()) {
                Barlist.add(ibi.getImgUrl());
            }
            caf_name_tv.setText(StringIsNull(cb.getName()));
            grade_tv.setText(StringIsNull(String.valueOf(cb.getAvgEvaluate())));
            caf_address_tv.setText(StringIsNull(cb.getAddress()));

            Glide.with(MyApplication.getContext()).load(cb.getLogoUrl()).apply(getGlideOptions()).into(caf_logo_img);

            if (!StringIsNull(cb.getBackgroundUrl()).isEmpty())
                Glide.with(MyApplication.getContext()).load(cb.getBackgroundUrl()).apply(getGlideOptions()).into(background_img);
            else
                Glide.with(MyApplication.getContext()).load(Fields.RandomBackGGround()).apply(getGlideOptions()).into(background_img);

            EnvironmentInit(cb.getInternetBarImgs());

            cpu.setText(StringIsNull(cb.getConfigureCPU()));
            caf_price_tv.setText(String.valueOf(cb.getHourlyPrice()));
            card.setText(StringIsNull(cb.getConfigureGraphicsCard()));
            key.setText(StringIsNull(cb.getConfigureKeyboard()));
            mouse.setText(StringIsNull(cb.getConfigureMouse()));
            play.setText(StringIsNull(cb.getConfigureScreen()));
            phone = StringIsNull(cb.getPhone());
            latitude = cb.getLatitude();
            longitude = cb.getLongitude();

        }
    }

    @Override
    public void onInternetCafComment(List<CafCommentBean> l) {
        foot_tv.setVisibility(View.GONE);
        if (ip.isRefresh)
            list.clear();

        for (CafCommentBean cfb : l) {
            list.add(cfb);
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onEndBottom() {
        foot_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_fragment_layout:
                if (send_comment_layout.getVisibility() == View.GONE) {
                    comment_ed.setText("");
                    send_comment_layout.setVisibility(View.VISIBLE);
                    menu_layout.setVisibility(View.GONE);
                    showSoftInputFromWindow(comment_ed);
                } else {
                    send_comment_layout.setVisibility(View.GONE);
                    menu_layout.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.call_fragment_layout:
                if (isFastDoubleClick())
                    return;

                if (!phone.isEmpty())
                    CallInternetCaf();
                break;
            case R.id.loacation_fragment_layout:
                if (isFastDoubleClick())
                    return;

                if (longitude > 0 && latitude > 0)
                    goLocationPage(InternetCafActivity.this, longitude, latitude);
                break;
            case R.id.caf_photo_layout:

                break;

            case R.id.send_comment_tv:
                if (isFastDoubleClick())
                    return;
                ip.SenCafComment(caf_id, comment_ed.getText().toString());
                break;

        }
    }

    private void CallInternetCaf() {
        new MyAlertDialog(this).builder().setCancelable(true).setTitle(gets(R.string.hint)).setMsg(gets(R.string.is_call_phone)).setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        }).show();
    }


    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else
                callPhone();
        } else
            callPhone();
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callPhone();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 拨号方法
     */
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (!isShow) {
            menu_layout.setVisibility(View.VISIBLE);
            send_comment_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void IsonRefresh(int init) {
        ip.page = init;
        ip.getCatComment(true);
    }

    @Override
    public void IsonLoadmore(int move) {
        ip.page = list.size();
        ip.getCatComment(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0)
            goHomePage(this, list.get(position).getId(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
