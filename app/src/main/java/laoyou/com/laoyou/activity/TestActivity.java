package laoyou.com.laoyou.activity;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.listener.RecyclerViewOnItemClickListener;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.view.CustomImageView;
import laoyou.com.laoyou.view.NineGridlayout;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener, RecyclerViewOnItemClickListener {

    private static final String TAG = "TestActivity";
    private NineGridlayout nine_gd_layout;
    private CustomImageView customImageView;

    @Override
    protected void click() {

    }


    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        nine_gd_layout = f(R.id.nine_gd_layout);
        customImageView = f(R.id.customImageView);
    }

    @Override
    protected void initData() {
        String str = "[[高仿陈晓燕1,,高仿陈晓鸭脖,ud7163f19736d4bee8f907cfa9d2333a6,],[默默,,8325effa72,ua664fd002910441aa098bcaf295622f8,],[默默,,古古怪怪,ua664fd002910441aa098bcaf295622f8,],[默默,,Yun was a really fun day ,ua664fd002910441aa098bcaf295622f8,],[高仿陈晓燕1,,kk,ud7163f19736d4bee8f907cfa9d2333a6,]]";
//            JSONArray ar = new JSONArray(str);
//            Log.i(TAG, " ar ===" + ar);

            Gson gson = new Gson();
            String[][] ss = gson.fromJson(String.valueOf(str.replace(" ", "")), new TypeToken<String[][]>() {
            }.getType());
            for (String[] strings : ss) {
                Log.i(TAG, " strings ===" + strings);
                for (String string : strings) {
                    System.out.println(string);
                    Log.i(TAG, " string ===" + string);
                }
            }

        List<String> images = new ArrayList<>();

        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);
        images.add(Fields.Catalina);

        nine_gd_layout.setItemClickListener(this);
        nine_gd_layout.setImagesData(images);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void RcOnItemClick(int pos, List<String> list) {
        Log.i(TAG, "点击的 Postion  ==" + pos);
    }

    @Override
    public void LikeClick(String id) {

    }

    @Override
    public void GoPageHome(String userId) {

    }

    @Override
    public void GoCommentPage(String id, String userId, String name, String content) {

    }
}
