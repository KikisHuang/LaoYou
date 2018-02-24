package laoyou.com.laoyou.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.MyListener;
import laoyou.com.laoyou.presenter.MyPresenter;
import laoyou.com.laoyou.save.SPreferences;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.utils.IntentUtils.goHomePage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyCommentPage;
import static laoyou.com.laoyou.utils.IntentUtils.goMyPhotoPage;
import static laoyou.com.laoyou.utils.IntentUtils.goSettingPage;
import static laoyou.com.laoyou.utils.IntentUtils.goShareMyQrCodePage;
import static laoyou.com.laoyou.utils.SynUtils.gets;


/**
 * Created by lian on 2017/4/22.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, MyListener {
    private static final String TAG = "MyFragment";
    private LinearLayout photo_layout, preference_layout, comment_layout, heart_layout, topic_layout, flash_chat_layout, setting_layout;
    public MyPresenter mp;
    private TextView user_name_tv, signature_tv;
    private CircleImageView user_head_img;
    private ImageView qr_code_img;
    private static MyFragment fragment;
    private FrameLayout head_layout;
    private String headImgUrl = "";
    private int sex = 99;

    @Override
    protected int initContentView() {
        return R.layout.my_fragment;
    }

    @Override
    protected void click() {
        photo_layout.setOnClickListener(this);
        preference_layout.setOnClickListener(this);
        comment_layout.setOnClickListener(this);
        heart_layout.setOnClickListener(this);
        topic_layout.setOnClickListener(this);
        flash_chat_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        head_layout.setOnClickListener(this);
        qr_code_img.setOnClickListener(this);

    }

    @Override
    protected void init() {
        photo_layout = f(R.id.photo_layout);
        fragment = this;
        user_name_tv = f(R.id.user_name_tv);
        user_head_img = f(R.id.user_head_img);
        signature_tv = f(R.id.signature_tv);
        qr_code_img = f(R.id.qr_code_img);
        head_layout = f(R.id.head_layout);

        preference_layout = f(R.id.preference_layout);
        preference_layout.setVisibility(View.GONE);

        comment_layout = f(R.id.comment_layout);
        heart_layout = f(R.id.heart_layout);
        topic_layout = f(R.id.topic_layout);
        topic_layout.setVisibility(View.GONE);

        flash_chat_layout = f(R.id.flash_chat_layout);
        flash_chat_layout.setVisibility(View.GONE);

        setting_layout = f(R.id.setting_layout);
        mp = new MyPresenter(this);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            mp.getUseDetails();
    }

    @Override
    protected void initData() {
        ImageView photo_icon = (ImageView) photo_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.camera_icon).into(photo_icon);
        TextView photo = (TextView) photo_layout.findViewById(R.id.menu_name_tv);
        photo.setText(R.string.photo_album);

        ImageView preference_icon = (ImageView) preference_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.preference_icon).into(preference_icon);
        TextView preference = (TextView) preference_layout.findViewById(R.id.menu_name_tv);
        preference.setText(R.string.preference);

        ImageView comment_icon = (ImageView) comment_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.comment_icon).into(comment_icon);
        TextView comment = (TextView) comment_layout.findViewById(R.id.menu_name_tv);
        comment.setText(R.string.comment);

        ImageView heart_icon = (ImageView) heart_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.myheart_icon).into(heart_icon);
        TextView heart = (TextView) heart_layout.findViewById(R.id.menu_name_tv);
        heart.setText(R.string.heart_value);

        ImageView topic_icon = (ImageView) topic_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.topic_icon).into(topic_icon);
        TextView topic = (TextView) topic_layout.findViewById(R.id.menu_name_tv);
        topic.setText(R.string.topic);

        ImageView flash_chat_icon = (ImageView) flash_chat_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.flash_chat_icon).into(flash_chat_icon);
        TextView flash_chat = (TextView) flash_chat_layout.findViewById(R.id.menu_name_tv);
        flash_chat.setText(R.string.my_create_flash_chat);

        ImageView setting_icon = (ImageView) setting_layout.findViewById(R.id.menu_icon);
        Glide.with(getActivity().getApplicationContext()).load(R.mipmap.setting_icon).into(setting_icon);
        setting_layout.findViewById(R.id.tag_img).setVisibility(View.GONE);

        TextView setting = (TextView) setting_layout.findViewById(R.id.menu_name_tv);
        setting.setText(R.string.setting);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.photo_layout:
                goMyPhotoPage(getActivity(), true, "");
                break;
            case R.id.preference_layout:
                break;
            case R.id.comment_layout:
                goMyCommentPage(getActivity(), 0);
                break;
            case R.id.heart_layout:
                goMyCommentPage(getActivity(), 1);
                break;
            case R.id.topic_layout:
                break;
            case R.id.flash_chat_layout:
                break;
            case R.id.setting_layout:
                goSettingPage(getActivity());
                break;
            case R.id.qr_code_img:
                goShareMyQrCodePage(getActivity(), headImgUrl);
                break;
            case R.id.head_layout:
                if (sex != 99 && user_name_tv.getText().toString().length() > 0)
//                    goOverInfoPage(getActivity(), headImgUrl, user_name_tv.getText().toString(), "", sex);
//                    goMyHomePage(getActivity());
                    goHomePage(getActivity(), SPreferences.getUserId(), false);
                break;
        }
    }

    @Override
    public void ongetDetails(UserInfoBean ub) {
        user_name_tv.setText(ub.getName());
        signature_tv.setText(ub.getAutograph() != null && !ub.getAutograph().isEmpty() ? ub.getAutograph() : gets(R.string.default_signature));
        sex = ub.getSex();
        SPreferences.saveUserId(ub.getId());

        if (ub.getHeadImgUrl() != null && !ub.getHeadImgUrl().isEmpty()) {
            Glide.with(getActivity().getApplicationContext()).load(ub.getHeadImgUrl()).into(user_head_img);
            headImgUrl = ub.getHeadImgUrl();
            SPreferences.saveUserHeadImg(headImgUrl);
        } else
            Glide.with(getActivity().getApplicationContext()).load(R.mipmap.test_icon).into(user_head_img);

    }

    public static MyFragment SettingInstance() {
        return fragment;
    }

    @Override
    public void onErrorMsg(String msg) {
        ToastUtil.toast2_bottom(getActivity(), msg);
    }
}
