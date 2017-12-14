package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.tencent.TIMCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.ProvinceBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.listener.MyHomePageListener;
import laoyou.com.laoyou.presenter.MyHomePagePresenter;
import laoyou.com.laoyou.tencent.ui.EditActivity;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import laoyou.com.laoyou.view.ObservableScrollView;
import me.iwf.photopicker.PhotoPicker;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.DateUtils.getTime;
import static laoyou.com.laoyou.utils.Fields.getAdministrative;
import static laoyou.com.laoyou.utils.IntentUtils.goCertificationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLikeGamePage;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhoto;
import static laoyou.com.laoyou.utils.SynUtils.getRouColors;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/6.
 */
public class MyHomePageActivity extends InitActivity implements ObservableScrollView.ScrollViewListener, MyHomePageListener, View.OnClickListener {
    private static final String TAG = "MyHomePageActivity";
    private ImageView background_img, back_img;
    private RelativeLayout title_layout;
    private CircleImageView head_img;
    private TextView nickname_tv, sex_tv, region_tv, signature_tv, attestation_state_tv, like_game_tv, hometown_tv, height_tv, birthday_tv, love_state_tv, save_tv;
    private LinearLayout go_add_layout, attestation_layout, nickname_layout, signature_layout,like_game_layout;
    private ObservableScrollView scrollView;
    private int imageHeight;
    private MyHomePagePresenter mp;
    private OptionsPickerView pvOptions;
    private OptionsPickerView hgOptions;
    private TimePickerView pvTime;

    private int sex = 0;

    private boolean HomeTown;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<ProvinceBean>> options2Items = new ArrayList<>();

    private List<String> heightItems = new ArrayList<>();
    private File headFile = null;
    private File backFile = null;
    private boolean isHead = false;

    @Override
    protected void click() {
        scrollView.setScrollViewListener(MyHomePageActivity.this);
        sex_tv.setOnClickListener(this);
        nickname_layout.setOnClickListener(this);
        signature_layout.setOnClickListener(this);
        region_tv.setOnClickListener(this);
        hometown_tv.setOnClickListener(this);
        height_tv.setOnClickListener(this);
        birthday_tv.setOnClickListener(this);
        love_state_tv.setOnClickListener(this);
        signature_tv.setOnClickListener(this);
        save_tv.setOnClickListener(this);
        head_img.setOnClickListener(this);
        background_img.setOnClickListener(this);
        attestation_layout.setOnClickListener(this);
        like_game_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.my_home_page_layout);
        setImgTitles(this);
        title_layout = f(R.id.title_layout);
        background_img = f(R.id.background_img);
        head_img = f(R.id.head_img);
        nickname_tv = f(R.id.nickname_tv);
        nickname_layout = f(R.id.nickname_layout);
        signature_layout = f(R.id.signature_layout);
        sex_tv = f(R.id.sex_tv);
        region_tv = f(R.id.region_tv);
        save_tv = f(R.id.save_tv);
        signature_tv = f(R.id.signature_tv);
        attestation_state_tv = f(R.id.attestation_state_tv);
        like_game_tv = f(R.id.like_game_tv);
        go_add_layout = f(R.id.go_add_layout);
        attestation_layout = f(R.id.attestation_layout);
        scrollView = f(R.id.scrollView);
        back_img = f(R.id.back_img);
        like_game_layout = f(R.id.like_game_layout);
        mp = new MyHomePagePresenter(this);
        hometown_tv = f(R.id.hometown_tv);
        height_tv = f(R.id.height_tv);
        birthday_tv = f(R.id.birthday_tv);
        love_state_tv = f(R.id.love_state_tv);
        imageHeight = DeviceUtils.dip2px(MyHomePageActivity.this, 360);
        initTimePicker();
    }

    @Override
    protected void initData() {
        Glide.with(this).load(Fields.Catalina).centerCrop().into(background_img);
        for (String str : Fields.HEIGHT) {
            heightItems.add(str);
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub

        if (y <= 0) {
            title_layout.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
            back_img.setImageResource(R.mipmap.return_icon_white);
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            title_layout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            back_img.setImageResource(R.mipmap.return_icon);
        } else
            title_layout.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));


    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(MyHomePageActivity.this, gets(R.string.change_succeed));
        setResult(Fields.ACRESULET1);
        finish();
    }

    @Override
    public void onFailedMsg(String msg) {
        ToastUtil.toast2_bottom(this, msg);
    }

    @Override
    public void onShowUserInfo(UserInfoBean ub) {
        Glide.with(MyHomePageActivity.this).load(ub.getHeadImgUrl()).into(head_img);
        nickname_tv.setText(ub.getName());
        sex = ub.getSex();
        switch (ub.getSex()) {
            case 0:
                sex_tv.setText(gets(R.string.woman));
                sex_tv.setTextColor(getRouColors(R.color.black));
                break;
            case 1:
                sex_tv.setText(gets(R.string.man));
                sex_tv.setTextColor(getRouColors(R.color.black));
                break;
            default:
                sex_tv.setText(gets(R.string.click_setting));
                sex_tv.setTextColor(getRouColors(R.color.content4));
        }
    }

    @Override
    public void onCertificaTion(int state) {
        //	status  审核状态 0未审核，1已通过，-1已拒绝，2密码错误要求复检,3重新上传
        if (state == 1 || state == 2) {
            attestation_state_tv.setText(gets(R.string.certification));
            attestation_state_tv.setTextColor(getRouColors(R.color.dodgerblue));
            attestation_layout.setVisibility(View.INVISIBLE);
        } else if (state == 0 || state == 3) {
            attestation_state_tv.setText(gets(R.string.in_certification));
            sex_tv.setTextColor(getRouColors(R.color.dodgerblue));
            attestation_layout.setVisibility(View.INVISIBLE);
        } else if (state == -1) {
            attestation_state_tv.setText(gets(R.string.refuse_certification));
            sex_tv.setTextColor(getRouColors(R.color.red3));
            attestation_layout.setVisibility(View.VISIBLE);
        } else {
            attestation_state_tv.setText(gets(R.string.un_certification));
            sex_tv.setTextColor(getRouColors(R.color.content4));
            attestation_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProvinceInfo(List<ProvinceBean> list) {
        options1Items = (ArrayList<ProvinceBean>) list;
    }

    @Override
    public void onCityInfo(List<ProvinceBean> list) {
        //选项2
        ArrayList<ProvinceBean> options2Items_01 = (ArrayList<ProvinceBean>) list;

        for (ProvinceBean pb : options1Items) {
            ArrayList<ProvinceBean> options2Items_02 = new ArrayList<>();
            for (ProvinceBean city : options2Items_01) {
                if (String.valueOf(pb.getId()).substring(0, 2).equals(String.valueOf(city.getId()).substring(0, 2)))
                    options2Items_02.add(city);
            }
            options2Items.add(options2Items_02);
        }
        initOptionPicker();
    }

    @Override
    public void onComPressSucceed(File f) {
        if (isHead) {
            headFile = f;
            Glide.with(MyHomePageActivity.this).load(f).centerCrop().into(head_img);
        } else {
            backFile = f;
            Glide.with(MyHomePageActivity.this).load(f).centerCrop().into(background_img);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_tv:
                new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.man), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        sex_tv.setText(gets(R.string.man));
                        sex = 1;
                    }
                }).addSheetItem(gets(R.string.woman), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        sex_tv.setText(gets(R.string.woman));
                        sex = 0;
                    }
                }).show();
                break;
            case R.id.nickname_layout:
                EditActivity.navToEdit(MyHomePageActivity.this, getResources().getString(R.string.setting_nick_name_change), nickname_tv.getText().toString(), Fields.ACRESULET2, new EditActivity.EditInterface() {
                    @Override
                    public void onEdit(String text, TIMCallBack callBack) {
                        callBack.onSuccess();
//                        FriendshipManagerPresenter.setMyNick(text, callBack);
                    }
                }, 15);

                break;
            case R.id.signature_layout:
                EditActivity.navToEdit(MyHomePageActivity.this, getResources().getString(R.string.setting_signature_change), signature_tv.getText().toString(), Fields.ACRESULET3, new EditActivity.EditInterface() {
                    @Override
                    public void onEdit(String text, TIMCallBack callBack) {
                        callBack.onSuccess();
//                        FriendshipManagerPresenter.setMyNick(text, callBack);
                    }
                }, 15);
                break;
            case R.id.region_tv:
                if (pvOptions != null) {
                    HomeTown = false;
                    pvOptions.show();
                }
                break;
            case R.id.hometown_tv:
                if (pvOptions != null) {
                    HomeTown = true;
                    pvOptions.show();
                }

                break;
            case R.id.height_tv:
                if (hgOptions != null)
                    hgOptions.show();
                break;
            case R.id.birthday_tv:
                if (pvTime != null)
                    pvTime.show();
                break;
            case R.id.love_state_tv:
                new ActionSheetDialog(this).builder().addSheetItem(gets(R.string.bachelordom), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        love_state_tv.setText(gets(R.string.bachelordom));
                    }
                }).addSheetItem(gets(R.string.Frenesi), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        love_state_tv.setText(gets(R.string.Frenesi));
                        love_state_tv.setTextColor(getRouColors(R.color.tomato));
                    }
                }).addSheetItem(gets(R.string.secrecy), ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        love_state_tv.setText(gets(R.string.secrecy));
                        love_state_tv.setTextColor(getRouColors(R.color.black));
                    }
                }).show();
                break;
            case R.id.save_tv:
                Show(MyHomePageActivity.this, "提交中", true, null);
                mp.ChangeInfo(headFile, nickname_tv.getText().toString(), sex);
                break;
            case R.id.head_img:
                isHead = true;
                getMULTIPLEPhoto(MyHomePageActivity.this, 1);
                break;
            case R.id.background_img:
                isHead = false;
                getMULTIPLEPhoto(MyHomePageActivity.this, 1);
                break;
            case R.id.attestation_layout:
                goCertificationPage(MyHomePageActivity.this);
                break;
            case R.id.like_game_layout:
                goLikeGamePage(this);
                break;
        }
    }

    /**
     * 条件选择器初始化
     */
    private void initOptionPicker() {//条件选择器初始化

        /**
         * 地区二级联动
         */
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx1 = options1Items.get(options1).getPickerViewText()
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                String tx2;
//                "-" + options2Items.get(options1).get(options2).getName()
                if (tx1.substring(0, 2).equals(getAdministrative(0)) || tx1.substring(0, 2).equals(getAdministrative(1)) || tx1.substring(0, 2).equals(getAdministrative(2)))
                    tx2 = "";
                else if (options2Items.get(options1).get(options2).getName().equals(tx1) || options2Items.get(options1).get(options2).getName().equals(gets(R.string.municipal_district)))
                    tx2 = "";
                else
                    tx2 = " - " + options2Items.get(options1).get(options2).getName();
                if (!HomeTown)
                    region_tv.setText(tx1 + tx2);
                else
                    hometown_tv.setText(tx1 + tx2);
            }
        })
                .setTitleText("")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.LTGRAY)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .isDialog(false)
                .build();

        pvOptions.setSelectOptions(1, 1);
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/

        /**
         * 单选;
         */
        hgOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                height_tv.setText(heightItems.get(options1));
            }
        })
                .setTitleText("")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.LTGRAY)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .isDialog(false)
                .build();

        hgOptions.setSelectOptions(1);
        hgOptions.setPicker(heightItems);//一级选择器

    }

    /**
     * 时间控件初始化;
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1960, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 11, 7);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                birthday_tv.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.LTGRAY)
                .setContentSize(18)
                .setTitleBgColor(Color.WHITE)
                .setSubmitColor(Color.BLACK)
                .setCancelColor(Color.LTGRAY)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //个性签名;
                case Fields.ACRESULET3:
                    setSignature(data.getStringExtra(EditActivity.RETURN_EXTRA));
                    break;
                //昵称;
                case Fields.ACRESULET2:
                    setNickName(data.getStringExtra(EditActivity.RETURN_EXTRA));
                    break;
                //头像、背景图;
                case PhotoPicker.REQUEST_CODE:
                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (isHead)
                        mp.CompressFile(this, p, 150);
                    else
                        mp.CompressFile(this, p, 500);

                    break;
            }
        }
        if (resultCode == Fields.ACRESULET3)
            mp.CheckID();
    }

    private void setNickName(String name) {
        if (name == null) return;
        this.nickname_tv.setText(name);
    }

    private void setSignature(String sig) {
        if (sig == null) return;
        this.signature_tv.setText(sig);
    }
}
