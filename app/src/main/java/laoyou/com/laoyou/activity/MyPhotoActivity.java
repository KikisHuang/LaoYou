package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.MyPhotoAdapter;
import laoyou.com.laoyou.listener.MyPhotoListener;
import laoyou.com.laoyou.presenter.MyPhotoPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import me.iwf.photopicker.PhotoPicker;

import static laoyou.com.laoyou.utils.IntentUtils.goPhotoViewerPage;
import static laoyou.com.laoyou.utils.PhotoUtils.getMULTIPLEPhoto;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.TitleUtils.setTitlesAndBack;

/**
 * Created by lian on 2017/12/8.
 */
public class MyPhotoActivity extends InitActivity implements MyPhotoListener {

    private static final String TAG = "MyPhotoActivity";
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<File> list;
    private MyPhotoAdapter adapter;
    private MyPhotoPresenter mp;

    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.my_photo_layout);
        setTitlesAndBack(this, gets(R.string.goback), "");
        recyclerView = f(R.id.recyclerView);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);//3列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
        mp = new MyPhotoPresenter(this);
        list = new ArrayList<>();
        list.add(null);
        adapter = new MyPhotoAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onFailedMsg(String msg) {

    }

    @Override
    public void onSucceed() {

    }

    @Override
    public void onTest(File f) {
        list.add(f);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddPhoto() {
        getMULTIPLEPhoto(MyPhotoActivity.this, 9);
    }

    @Override
    public void onItemClick(int pos) {
        List<String> list = new ArrayList<>();
        list.add(Fields.Catalina);
        list.add(Fields.Catalina);
        list.add(Fields.Catalina);
        goPhotoViewerPage(this, list, pos - 1, 0);
    }

    @Override
    public void onDeletePhoto(String url) {
        ToastUtil.toast2_bottom(this, "删除图片 " + url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //头像、背景图;
                case PhotoPicker.REQUEST_CODE:
                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    mp.ComPressFile(this, p, 300);

                    break;
                case Fields.ACRESULET4:
                    Bundle bundle = data.getExtras();
                    this.onDeletePhoto(bundle.getString("delete_url"));
                    break;
            }
        }
    }
}
