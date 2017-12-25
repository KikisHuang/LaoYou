package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.MyPhotoAdapter;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.listener.MyPhotoListener;
import laoyou.com.laoyou.presenter.MyPhotoPresenter;
import laoyou.com.laoyou.utils.Fields;
import laoyou.com.laoyou.utils.ToastUtil;
import me.iwf.photopicker.PhotoPicker;

import static laoyou.com.laoyou.dialog.CustomProgress.Show;
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
    private List<PhotoBean> list;
    private MyPhotoAdapter adapter;
    private MyPhotoPresenter mp;
    private int upNum = 0;
    private List<File> files;
    private boolean Refresh = true;

    protected void click() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    if (!recyclerView.canScrollVertically(1)) {
                        Refresh = false;
                        mp.page += 10;
                        mp.getPhotoListData();
                    }

            }
        });
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
        files = new ArrayList<>();
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
    public void onUpLoadFile(File f) {
        files.add(f);

        if (files.size() == upNum) {
            Show(MyPhotoActivity.this, "提交中", true, null);
            Refresh = true;
            mp.AddPhoto(files);
        }

    }

    @Override
    public void onAddPhoto() {
        files.clear();
        getMULTIPLEPhoto(MyPhotoActivity.this, 9);
    }

    @Override
    public void onItemClick(int pos) {
        if (list.size() > 0) {
            List<String> photo = new ArrayList<>();
            for (PhotoBean pb : list) {
                if (pb != null)
                    photo.add(pb.getUrl());
            }
            goPhotoViewerPage(this, photo, pos - 1, 0);
        }
    }

    @Override
    public void onDeletePhoto(String url) {
        ToastUtil.toast2_bottom(this, "并没有删除功能。");
        Log.i(TAG, "delete url ==" + url);
    }

    @Override
    public void onPhotoList(List<PhotoBean> ar) {
        if (Refresh) {
            list.clear();
            list.add(null);
        }
        for (PhotoBean pb : ar) {
            list.add(pb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    upNum = p.size();
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
