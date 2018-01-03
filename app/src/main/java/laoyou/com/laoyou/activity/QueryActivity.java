package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.GamsAdAdapter;
import laoyou.com.laoyou.bean.PageTopBannerBean;
import laoyou.com.laoyou.bean.UserInfoBean;
import laoyou.com.laoyou.listener.QueryListener;
import laoyou.com.laoyou.presenter.QueryPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
import static laoyou.com.laoyou.utils.IntentUtils.goCertificationPage;
import static laoyou.com.laoyou.utils.IntentUtils.goLoginOperPage;
import static laoyou.com.laoyou.utils.IntentUtils.goOutSidePage;
import static laoyou.com.laoyou.utils.SynUtils.LoginStatusQuery;

/**
 * Created by lian on 2017/11/14.
 */
public class QueryActivity extends InitActivity implements View.OnClickListener, QueryListener{

    private static final String TAG = "QueryActivity";

    private TextView query_of_login_tv, pass_tv, check_pass_tv, loading_tv, refresh_tv;
    private LinearLayout refresh_layout;
    private ImageView refresh_img, return_img;
    //flag 0登录,1查询;
    private int flag = 0;
    private QueryPresenter hp;

    private TextView refuse_tv, again_query_tv, regain_tv;
    private EditText refuse_ed;
    private FrameLayout head;
    private ListView listview;
    private GamsAdAdapter adapter;
    private List<PageTopBannerBean> list;

    @Override
    protected void click() {
        query_of_login_tv.setOnClickListener(this);
        again_query_tv.setOnClickListener(this);
        regain_tv.setOnClickListener(this);
        return_img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.query_layout);

        listview = f(R.id.listView);
        head = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.front_include, null);
        query_of_login_tv = (TextView) head.findViewById(R.id.query_of_login_tv);
        pass_tv = (TextView) head.findViewById(R.id.pass_tv);
        check_pass_tv = (TextView) head.findViewById(R.id.check_pass_tv);
        loading_tv = (TextView) head.findViewById(R.id.loading_tv);
        refresh_layout = (LinearLayout) head.findViewById(R.id.refresh_layout);
        refresh_img = (ImageView) head.findViewById(R.id.refresh_img);
        refresh_tv = (TextView) head.findViewById(R.id.refresh_tv);
        refuse_tv = (TextView) head.findViewById(R.id.refuse_tv);
        again_query_tv = (TextView) head.findViewById(R.id.again_query_tv);
        refuse_ed = (EditText) head.findViewById(R.id.refuse_ed);
        regain_tv = (TextView) head.findViewById(R.id.regain_tv);
        return_img = f(R.id.return_img);

        listview.addHeaderView(head);
        list = new ArrayList<>();
        adapter = new GamsAdAdapter(this, list,this);
        listview.setAdapter(adapter);
        hp = new QueryPresenter(this);
        HideOver();
    }

    @Override
    protected void initData() {
        hp.Presenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_of_login_tv:
                if (flag == 0)
                    if (!LoginStatusQuery())
                        goLoginOperPage(QueryActivity.this);

                if (flag == 1) {
                    Show(QueryActivity.this, "", true, null);
                    hp.getUseDetails();
                }

                break;
            case R.id.again_query_tv:
                goCertificationPage(QueryActivity.this);
                break;
            case R.id.regain_tv:
                Show(QueryActivity.this, "提交中", true, null);
                hp.RegainPassWord();
                break;

            case R.id.return_img:
                finish();
                break;
        }
    }

    @Override
    public void onFailed(String msg) {
        ToastUtil.toast2_bottom(QueryActivity.this, msg);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.toast2_bottom(QueryActivity.this, msg);
    }

    @Override
    public void onDetails(UserInfoBean ub) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Fields.ACRESULET3)
            onRefresh();
    }

    private void onRefresh() {
        hp.IsLogin();
    }

    @Override
    public void onCertificaTion() {
        if (LoginStatusQuery()) {
            query_of_login_tv.setVisibility(View.VISIBLE);
            ToastUtil.toast2_bottom(QueryActivity.this, "必须实名认证后才可查询密码");
            goCertificationPage(QueryActivity.this);
        }
    }

    @Override
    public void IsLogin(boolean b) {
        HideOver();
        query_of_login_tv.setVisibility(View.VISIBLE);
        if (b) {
            flag = 1;
            query_of_login_tv.setText("查询");

        } else {
            flag = 0;
            query_of_login_tv.setText("登录");

        }
    }

    @Override
    public void unCheck() {
        HideOver();
        loading_tv.setVisibility(View.VISIBLE);
        refresh_layout.setVisibility(View.VISIBLE);

        hp.startAnima(refresh_img);
    }

    @Override
    public void CheckOK(String passwd) {
        HideOver();
        hp.checkPassLength(passwd.length(), passwd);

        check_pass_tv.setVisibility(View.VISIBLE);
        pass_tv.setVisibility(View.VISIBLE);
        regain_tv.setVisibility(View.VISIBLE);
        hp.stopAnima(refresh_img);
    }

    @Override
    public void Refuse(String msg) {
        HideOver();
        refuse_ed.setText(msg);
        refuse_ed.setVisibility(View.VISIBLE);
        refuse_tv.setVisibility(View.VISIBLE);
        again_query_tv.setVisibility(View.VISIBLE);
        hp.stopAnima(refresh_img);
    }

    @Override
    public void onTime(int time) {

        refresh_tv.setText(time + "秒自动刷新");
        if (time == 0)
            hp.CheckID();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hp.CloseQueryThread();
    }

    @Override
    public void onNormalPassSize(String passwd) {
        pass_tv.setTextSize(55);
        pass_tv.setText(passwd);
    }

    @Override
    public void onMinPassSize(String passwd) {
        pass_tv.setTextSize(25);
        pass_tv.setText(passwd);
    }

    @Override
    public void ShowBannerInfo(List<PageTopBannerBean> ar) {
        for (PageTopBannerBean pbb : ar) {
            list.add(pbb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void GoOutSide(String url) {
        goOutSidePage(this, url);
    }

    private void HideOver() {
        check_pass_tv.setVisibility(View.GONE);
        pass_tv.setVisibility(View.GONE);
        query_of_login_tv.setVisibility(View.GONE);
        loading_tv.setVisibility(View.GONE);
        refresh_layout.setVisibility(View.GONE);
        again_query_tv.setVisibility(View.GONE);
        refuse_tv.setVisibility(View.GONE);
        refuse_ed.setVisibility(View.GONE);
        regain_tv.setVisibility(View.GONE);
    }
}
