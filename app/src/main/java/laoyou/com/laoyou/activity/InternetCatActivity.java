package laoyou.com.laoyou.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.InternetCatAdapter;
import laoyou.com.laoyou.listener.InternetCapListener;
import laoyou.com.laoyou.presenter.InternetCapPresenter;
import laoyou.com.laoyou.utils.DeviceUtils;
import laoyou.com.laoyou.utils.Fields;

import static laoyou.com.laoyou.utils.SynUtils.getTypeface;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.handleTitleBarColorEvaluate;
import static laoyou.com.laoyou.utils.TitleUtils.setImgTitles;

/**
 * Created by lian on 2017/12/12.
 */
public class InternetCatActivity extends InitActivity implements AbsListView.OnScrollListener, InternetCapListener {

    private static final String TAG = "InternetCatActivity";

    private LinearLayout cat_photo_layout, config_top_layout, config_mid_layout, config_bottom_layout;
    private CircleImageView cat_logo_img;
    private TextView grade_tv;
    private TextView cat_name_tv;
    private TextView cat_address_tv;
    private TextView cat_price_tv;
    private ListView listView;
    private View head_layout;
    private ImageView back_img;
    private RelativeLayout title_layout;
    private int height;
    private int imageHeight;
    private InternetCatAdapter adapter;

    private List<String> list;

    private TextView cpu, card, mouse, key, play;

    private InternetCapPresenter ip;

    @Override
    protected void click() {
        listView.setOnScrollListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.internet_cat_layout);
        setImgTitles(this);
        head_layout = LayoutInflater.from(this).inflate(R.layout.internet_cat_head_layout, null);
        ip = new InternetCapPresenter(this);
        config_top_layout = (LinearLayout) head_layout.findViewById(R.id.config_top_layout);
        config_mid_layout = (LinearLayout) head_layout.findViewById(R.id.config_mid_layout);
        config_bottom_layout = (LinearLayout) head_layout.findViewById(R.id.config_bottom_layout);

        cat_photo_layout = (LinearLayout) head_layout.findViewById(R.id.cat_photo_layout);
        cat_logo_img = (CircleImageView) head_layout.findViewById(R.id.cat_logo_img);
        grade_tv = (TextView) head_layout.findViewById(R.id.grade_tv);
        cat_name_tv = (TextView) head_layout.findViewById(R.id.cat_name_tv);
        cat_address_tv = (TextView) head_layout.findViewById(R.id.cat_address_tv);
        cat_price_tv = (TextView) head_layout.findViewById(R.id.cat_price_tv);
        listView = f(R.id.listView);
        back_img = f(R.id.back_img);
        title_layout = f(R.id.title_layout);
        listView.addHeaderView(head_layout);
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new InternetCatAdapter(this, list);
        listView.setAdapter(adapter);
        grade_tv.setTypeface(getTypeface(this));
        cat_price_tv.setTypeface(getTypeface(this));
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 3; i++) {
            int w = DeviceUtils.getWindowWidth(this) * 1 / 3;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w * 3 / 4);

            lp.rightMargin = DeviceUtils.dip2px(this, 10);
            ImageView im = new ImageView(this);

            im.setLayoutParams(lp);
            Glide.with(this).load(Fields.Catalina).bitmapTransform(new CenterCrop(this), new RoundedCornersTransformation(this, 15, 0, RoundedCornersTransformation.CornerType.ALL)).into(im);
            cat_photo_layout.addView(im);
        }

        ConfigTvInit();

    }

    private void ConfigTvInit() {
        TextView topo = (TextView) config_top_layout.findViewById(R.id.config_name_tv);
        cpu = (TextView) config_top_layout.findViewById(R.id.config_content_tv);
        TextView topt = (TextView) config_top_layout.findViewById(R.id.config_name1_tv);
        play = (TextView) config_top_layout.findViewById(R.id.config_content1_tv);

        topo.setText(gets(R.string.cpu));
        topt.setText(gets(R.string.graphics_card));

        TextView mido = (TextView) config_mid_layout.findViewById(R.id.config_name_tv);
        mouse = (TextView) config_mid_layout.findViewById(R.id.config_name_tv);
        TextView midt = (TextView) config_mid_layout.findViewById(R.id.config_name1_tv);
        key = (TextView) config_mid_layout.findViewById(R.id.config_name1_tv);

        mido.setText(gets(R.string.mouse));
        midt.setText(gets(R.string.keyboard));

        TextView boto = (TextView) config_bottom_layout.findViewById(R.id.config_name_tv);
        play = (TextView) config_mid_layout.findViewById(R.id.config_name_tv);
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

        imageHeight = head_layout.getHeight() - DeviceUtils.dip2px(this, 0);
        handleTitleBarColorEvaluate(height, imageHeight, title_layout, back_img);
    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onFailedMsg(String msg) {

    }
}
