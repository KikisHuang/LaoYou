package laoyou.com.laoyou.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhy.http.okhttp.OkHttpUtils;

import laoyou.com.laoyou.R;
import laoyou.com.laoyou.dialog.ActionSheetDialog;
import laoyou.com.laoyou.utils.ToastUtil;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static laoyou.com.laoyou.utils.SynUtils.getTAG;
import static laoyou.com.laoyou.utils.SynUtils.gets;
import static laoyou.com.laoyou.utils.SynUtils.saveImageToGallery;

/**
 * Created by lian on 2017/5/5.
 */
public class PhotoViewerFragment extends BaseFragment {
    private static final String TAG = getTAG(PhotoViewerFragment.class);
    private String url;
    private PhotoView imageView;
    private PhotoViewAttacher photoViewAttacher;
    private GestureDetector.OnDoubleTapListener gest;
    private int function;

    /**
     * 动态创建Fragment;
     *
     * @param path     路径
     * @param function
     * @return
     */
    public static PhotoViewerFragment newInstance(String path, int function) {

        PhotoViewerFragment f = new PhotoViewerFragment();
        Bundle args = new Bundle();
        args.putString("url", path);
        args.putString("function", String.valueOf(function));
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments() != null ? getArguments().getString("url") : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494050810732&di=12648ae1cfe05df8bf586bd65c60961f&imgtype=0&src=http%3A%2F%2Fres2.esf.leju.com%2Fesf_www%2Fstatics%2Fimages%2Fdefault-img%2Fdetail.png";
        function = Integer.parseInt(getArguments() != null ? getArguments().getString("function") : "1");
    }

    @Override
    protected int initContentView() {
        return R.layout.photo_view_fragment;
    }

    private void getData() {

        Glide.with(getActivity().getApplicationContext()).load(url).into(imageView);
        Glide.with(getActivity().getApplicationContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                onLong(resource);
            }
        });
    }

    private void onLong(final Bitmap bm) {
        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ActionSheetDialog dialog = new ActionSheetDialog(getActivity());
                switch (function) {
                    case 0:
                        dialog.builder().addSheetItem(gets(R.string.delete_photo), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                Intent intent = new Intent();
                                intent.putExtra("delete_url", url);
                                getActivity().setResult(Activity.RESULT_OK, intent);
                                getActivity().finish();
                            }
                        }).addSheetItem(gets(R.string.save_photo), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (saveImageToGallery(getActivity().getApplicationContext(), bm))
                                    ToastUtil.toast2_bottom(getActivity(), gets(R.string.save_succ));
                                else
                                    ToastUtil.toast2_bottom(getActivity(), gets(R.string.save_fail));
                            }
                        }).show();
                        break;
                    case 1:
                        dialog.builder().addSheetItem(gets(R.string.save_photo), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (saveImageToGallery(getActivity().getApplicationContext(), bm))
                                    ToastUtil.toast2_bottom(getActivity(), gets(R.string.save_succ));
                                else
                                    ToastUtil.toast2_bottom(getActivity(), gets(R.string.save_fail));
                            }
                        }).show();
                        break;
                    case 2:
                        dialog.builder().addSheetItem(gets(R.string.delete_photo), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                Intent intent = new Intent();
                                intent.putExtra("delete_url", url);
                                getActivity().setResult(Activity.RESULT_OK, intent);
                                getActivity().finish();
                            }
                        }).show();
                        break;
                    case 3:

                        break;
                }

                return false;
            }
        });
    }


    @Override
    protected void click() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        gest = null;
        photoViewAttacher.cleanup();
        photoViewAttacher = null;
    }

    @Override
    protected void init() {
        imageView = (PhotoView) view.findViewById(R.id.iv_main_pic);
        photoViewAttacher = new PhotoViewAttacher(imageView);

        photoViewAttacher.setOnDoubleTapListener(gest = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i(TAG, "onSingleTapConfirmed");
                getActivity().finish();
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (photoViewAttacher == null)
                    return false;

                try {
                    float scale = photoViewAttacher.getScale();//获取当前缩放值
                    float x = e.getX();
                    float y = e.getY();
                    //PhotoView预定义了3种缩放大小，大，中，小，可以实际使用感受下，双击后先缩放到中，再双击缩放到大，再双击缩放到小。
                    if (scale < photoViewAttacher.getMediumScale()) {//当前缩放为小，变换为中
                        photoViewAttacher.setScale(photoViewAttacher.getMediumScale(), x, y, true);
                    } else if (scale >= photoViewAttacher.getMediumScale() && scale < photoViewAttacher.getMaximumScale()) {//当前缩放为中，变化为大
                        photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), x, y, true);
                    }/* else {//当前缩放为大，变换为小
                        photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), x, y, true);
                    }*/
                } catch (ArrayIndexOutOfBoundsException e1) {
                    // Can sometimes happen when getX() and getY() is called
                }

                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.i(TAG, "onDoubleTapEvent");
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        getData();
    }

}