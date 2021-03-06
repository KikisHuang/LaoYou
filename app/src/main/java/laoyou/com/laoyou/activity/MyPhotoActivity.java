package laoyou.com.laoyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.adapter.MyPhotoAdapter;
import laoyou.com.laoyou.bean.PhotoBean;
import laoyou.com.laoyou.listener.MyPhotoListener;
import laoyou.com.laoyou.presenter.MyPhotoPresenter;
import laoyou.com.laoyou.utils.ActivityCollector;
import laoyou.com.laoyou.utils.Fields;

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
    private boolean Photo_IsMe;
    private String id = "";
    private String delUrl = "";

    protected void click() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    if (!recyclerView.canScrollVertically(1) && list.size() > 0) {
                        Refresh = false;
                        mp.getPhotoListData(id, list.size() - 1);
                    }

            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.my_photo_layout);
        ActivityCollector.addActivity(this, getClass());
        setTitlesAndBack(this, gets(R.string.goback), "");
        Photo_IsMe = getIntent().getBooleanExtra("Photo_IsMe", false);
        id = getIntent().getStringExtra("Photo_id");
        recyclerView = f(R.id.recyclerView);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);//3列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager);
        mp = new MyPhotoPresenter(this);

        list = new ArrayList<>();
        files = new ArrayList<>();
        if (Photo_IsMe)
            list.add(null);

        mp.getPhotoListData(id, 0);

        adapter = new MyPhotoAdapter(this, list, this, Photo_IsMe);
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
            if (Photo_IsMe)
                goPhotoViewerPage(this, photo, pos - 1, 0);
            else
                goPhotoViewerPage(this, photo, pos, 1);
        }
    }

    @Override
    public void onDeletePhoto(String url) {
        Log.i(TAG, "delete url ==" + url);
        if (Photo_IsMe) {
            for (PhotoBean pb : list) {
                if (pb != null && pb.getUrl().equals(url)) {
                    delUrl = url;
                    mp.DeletePhoto(String.valueOf(pb.getId()));
                    break;
                }
            }
        }
    }

    @Override
    public void onPhotoList(List<PhotoBean> ar) {
        if (Refresh) {
            list.clear();
            if (Photo_IsMe)
                list.add(null);
        }
        for (PhotoBean pb : ar) {
            list.add(pb);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void DeleteSucceed() {
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "DeleteSucceed");
            if (list.get(i) != null && list.get(i).getUrl().equals(delUrl)) {
                list.remove(i);
                Log.i(TAG, "删除删除删除删除删除删除");
                adapter.notifyDataSetChanged();
                break;
            }
        }

        Refresh = true;
        mp.RefreshPhotoListData(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:

                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    ArrayList<String> p = new ArrayList<>();
                    for (LocalMedia lma : list) {
                        p.add(lma.getCompressPath() != null || !lma.getCompressPath().isEmpty() ? lma.getCompressPath() : lma.getPath());
                    }
//                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
